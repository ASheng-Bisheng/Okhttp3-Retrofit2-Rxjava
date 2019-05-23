package cn.whzwl.xbs.network.net;


import java.util.concurrent.TimeUnit;

import cn.whzwl.xbs.network.net.download.DownloadProgressInterceptor;
import cn.whzwl.xbs.network.net.download.DownloadProgressListener;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xp
 * 网络请求类
 */
public class HttpMethods {
    //接口根地址
    public static final String BASE_URL = " ";
    //设置超时时间
    private static final long DEFAULT_TIMEOUT = 10_000L;

    private Retrofit retrofit;
    private OkHttpClient client;

    ///设为默认的 URL
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods(BASE_URL);
    }


    //私有化构造方法   在这里添加 BASE_URL 的原因的话 是因为 在开发中 可能你用的并不仅仅有你们的后台写的接口还有可能不是用的你们公司域名的接口
    //做一个保险起见
    public HttpMethods(String BASE_URL) {
        //OkHttp的使用
        client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                //添加请求头
                //.addInterceptor(new HeaderInterceptor())
                //添加日志打印拦截器  拓展
                .addInterceptor(new LoggerInterceptor("===", true))
                .build();
        //retrofit 的使用
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                //添加Gson解析
                .addConverterFactory(GsonConverterFactory.create())
                //添加rxjava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    //下载
    public HttpMethods(String BASE_URL, DownloadProgressListener listener) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new DownloadProgressInterceptor(listener))
                //连接失败时重试
                .retryOnConnectionFailure(true)
                //设置超时时间
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public <T> T createService(Class<T> clazz) {
        return retrofit.create(clazz);
    }

}

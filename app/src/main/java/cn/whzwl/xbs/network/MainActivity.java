package cn.whzwl.xbs.network;


import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.whzwl.xbs.network.bean.BaseEntity;
import cn.whzwl.xbs.network.bean.Foods;
import cn.whzwl.xbs.network.net.ApiService;
import cn.whzwl.xbs.network.net.DefaultTransformer;
import cn.whzwl.xbs.network.net.HttpMethods;
import cn.whzwl.xbs.network.net.download.DownloadProgressListener;
import cn.whzwl.xbs.network.utils.APIEncryptionUtils;
import cn.whzwl.xbs.network.utils.DateUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {
    CompositeSubscription mCompositeSubscription;
    TextView show;
    File flie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = findViewById(R.id.show);
        String time = DateUtils.getCurrentTime() + "";
        flie = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "test.apk");
        //post 方法 和 get 方法都是一样的 在此就不写哪么多了
        findViewById(R.id.post).setOnClickListener(v -> {
            Subscription subscribe = HttpMethods.getInstance()
                    .createService(ApiService.class)
                    .postFoodList(time, APIEncryptionUtils.TokenEncryption(time), "1")
                    .compose(DefaultTransformer.<BaseEntity<Foods>>create())
                    .subscribe(foodsBaseEntity -> {
                        runOnUiThread(() -> {
                            show.setText(foodsBaseEntity.toString());

                        });

                    });


            addSubscription(subscribe);
        });

        //下载
        findViewById(R.id.download).setOnClickListener(v -> {
            ProgressDialog("/Apk/2019-05-22/ZWLRobot_Medium-v1.2.0.apk");
        });

        //上传
        findViewById(R.id.uploading).setOnClickListener(v -> {
            //找到文件路径
            File fileUploadlog = null;
            final String Token = APIEncryptionUtils.TokenEncryption(time);
            //MediaType 有多种类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), flie);
            Log.i("FilePath", requestBody.contentType().type() + "=====" + fileUploadlog.getName());

            MultipartBody.Part body = MultipartBody.Part.createFormData("log", fileUploadlog.getName(), requestBody);
            Subscription subscribe = HttpMethods.getInstance()
                    .createService(ApiService.class)
                    .postUploadlog(time, Token, body)
                    .compose(DefaultTransformer.<BaseEntity>create())
                    .subscribe(baseEntity -> {
                        Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    });
            addSubscription(subscribe);
        });


    }

    int downloadCount = 0;

    /**
     * @param Url 下载地址
     *            下载显示进度条操作
     */
    private void ProgressDialog(String Url) {

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("正在下载！");//2.设置标题
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();//

        //更新进度条
        DownloadProgressListener listener = (bytesRead, contentLength, done) -> {
            //不频繁发送通知，防止通知栏下拉卡顿
            int progress = (int) ((bytesRead * 100) / contentLength);
            progressDialog.setProgress(progress);
            if ((downloadCount == 0) || progress > downloadCount) {
                if (progress == 100) {
                    runOnUiThread(() ->
                            progressDialog.setTitle("下载完成！")

                    );
                }
            }

        };
        //下载操作开始
        Subscription subscribe = new HttpMethods(" ", listener).createService(ApiService.class)
                .download(Url).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io()).map(responseBody ->
                        responseBody.byteStream()
                ).observeOn(Schedulers.computation()).doOnNext(inputStream -> {

                    try {
                        saveFile(inputStream, flie);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<InputStream>() {
                    @Override
                    public void onCompleted() {
                        //下载完成后
                        progressDialog.incrementProgressBy(100);
                        progressDialog.dismiss();

                        Log.i("Update", flie.getPath());
                        //转到安装


                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i("Log---", "onError");
                    }

                    @Override
                    public void onNext(InputStream inputStream) {
                        Log.i("Log---", "onNext");
                    }
                });
        addSubscription(subscribe);
    }


    /**
     * 所有rx订阅后，需要调用此方法，用于在detachView时取消订阅
     */
    private void addSubscription(Subscription subscribe) {
        if (mCompositeSubscription == null)
            mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(subscribe);
    }

    /**
     * 取消本页面所有订阅
     */
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void saveFile(InputStream in, File file) throws IOException {

        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        if (file != null && file.exists())
            file.delete();
        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024 * 128];
        int len = -1;

        while ((len = in.read(buffer)) != -1) {

            out.write(buffer, 0, len);
        }
        out.flush();
        out.close();
        in.close();


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
    }
}

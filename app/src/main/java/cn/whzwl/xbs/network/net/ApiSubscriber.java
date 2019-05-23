package cn.whzwl.xbs.network.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;


import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import rx.Subscriber;


public abstract class ApiSubscriber<T> extends Subscriber<T> {


    Context context;

    //可以在这个类中添加一个LoadDialog 加载时显示

    public ApiSubscriber() {

    }

    public ApiSubscriber(@NonNull Context context) {
        this.context = context;
        //实例化load
    }

    @Override
    public void onStart() {
        //show load
    }

    @Override
    public void onCompleted() {
        //dismiss load
    }

    /**
     * 只要链式调用中抛出了异常都会走这个回调
     */
    @Override
    public void onError(Throwable e) {
        //dismiss load
        if (e instanceof ApiException) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            //服务器返回的错误
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            Toast.makeText(context, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
        } else if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络超时，请稍后再试！", Toast.LENGTH_SHORT).show();
        } else if (e instanceof JsonSyntaxException) {
            //这里数据解析异常  就是说你在绑定接口是 返回的数据跟你 填写的实体类中的属性 不一样
            //还有就是 当你访问接口 获取的信息 为null  什么都没有 但是服务器给你的code为200  那就另加判断
            Toast.makeText(context, "数据解析异常！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "服务端错误！", Toast.LENGTH_SHORT).show();
        }
        e.printStackTrace();
    }


}

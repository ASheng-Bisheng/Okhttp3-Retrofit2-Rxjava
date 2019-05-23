package cn.whzwl.xbs.network.net;


import cn.whzwl.xbs.network.bean.BaseEntity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class DefaultTransformer<T> implements Observable.Transformer<T, T> {

    @Override
    public Observable<T> call(Observable<T> tObservable) {

        return tObservable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(t -> {// 通用错误处理，判断code
                    if (!((BaseEntity<T>) t).getCode().equals("200")) {
                        throw new ApiException(Integer.parseInt(((BaseEntity<T>) t).getCode()), ((BaseEntity<T>) t).getMsg());
                    }
                    return t;

                });
    }

    public static <T> DefaultTransformer<T> create() {
        return new DefaultTransformer<>();
    }
}

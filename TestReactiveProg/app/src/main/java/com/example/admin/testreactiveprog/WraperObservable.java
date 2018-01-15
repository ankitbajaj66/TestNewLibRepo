package com.example.admin.testreactiveprog;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 1/14/2018.
 */

public class WraperObservable {

    public static <T> Observable<T> getDatafromGoogle(String data) {

        
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
// make the container call
            }
        });
//        return observable.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.computation());
    }
}

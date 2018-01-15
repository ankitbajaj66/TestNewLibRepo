package com.example.admin.testreactiveprog;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 1/14/2018.
 */

public class GenericObservable<T> {

        public static <T> Observable<T> setupObservable(Observable<T> observable) {
            return observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.computation());
        }

}

package com.example.admin.testreactiveprog;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import rx.Completable;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSubscribe;
    private EditText edtInputText;
    private TextView txtOutput;
    private Observable<String> fetchDataforService1;
    private Observable<String> fetchDataforService2;
    private Observer<String> observer;
    private ProgressDialog dialog;
    private Subscription subscription;
    private String TAG = "Ankit";
    private Observable<String> combinedResult;
    private  Action1 combileres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        btnSubscribe = (Button) findViewById(R.id.btn_subscribe);
        edtInputText = (EditText) findViewById(R.id.edt_input_text);
        txtOutput = (TextView) findViewById(R.id.id_output_text);

        btnSubscribe.setOnClickListener(this);
//        Observable<String> observable = Observable.just("Hello Ankit thanks for using Just Operator");

//        Action1 action1 = new Action1<Integer>() {
//            @Override
//            public void call(Integer s) {
//                Log.i(TAG, "Sqare for numbers " + s + "");
//            }
//        };
//        observable.subscribe(action1);

//        Subscription subscription = observable.subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                Log.i(TAG, "onCompleted Method called");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i(TAG, "onError Method called");
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.i(TAG, "onNext Method called" + s);
//            }
//        });

//        Observable<Integer> observable1 = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6});

//        Observable<Integer> observable2 = observable1.filter(new Func1<Integer, Boolean>() {
//            @Override
//            public Boolean call(Integer integer) {
//                return integer > 2;
//            }
//        }).map(new Func1<Integer, Integer>() {
//            @Override
//            public Integer call(Integer integer) {
//                return integer * integer;
//            }
//        });
//
//        observable2.subscribe(action1);


        // Now coming to asynctask


       Observable<String> fetchFromGoogle = Observable.create(new Observable.OnSubscribe<String>() {
           @Override
           public void call(Subscriber<? super String> subscriber) {
               String content = fetchData("https://www.google.com");
               subscriber.onNext("Result from google: "+ content+"\n");
           }
       });

        Observable<String> fetchFromYahoo = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String content = fetchData("https://www.yahoo.com");
                subscriber.onNext("Result from yahoo: "+content+"\n");
            }
        });

        // concat Operator

        Observable<Integer> observable4 = Observable.from(new Integer[]{1, 2, 3, 4, 5});
        Observable<Integer> observable5 = Observable.from(new Integer[]{6, 7, 8, 9, 10});


        Observable.merge(observable4, observable5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "Merge Operator: "+integer);
            }
        });

        // // give first 3 elemenmts
        observable4.take(3).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "Take Operator: "+integer);
            }
        });


        // give last 3 elemenmts
        observable4.takeLast(3).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "Take Last: "+integer);
            }
        });

        // skip last 2 elemenmts
        observable4.skipLast(2).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "Skip Last: "+integer);
            }
        });

        // skip first 2 elemenmts
        observable4.skip(2).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "Skip First: "+integer);
            }
        });


        observable4.takeFirst(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return null;
            }
        });


        Observable.concat(observable4, observable5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
           Log.i(TAG, "Concatination of two integer array result are: "+integer);
            }
        });
//        fetchFromYahoo.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                Log.i(TAG, "fetch data from Yahoo completd");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i(TAG, "Error from Yahoo " + e);
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.i(TAG, "Result from Yahoo " + s );
//            }
//        });
//
//        fetchFromGoogle.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
//           @Override
//           public void onCompleted() {
//               Log.i(TAG, "fetch data from Google completd");
//           }
//
//           @Override
//           public void onError(Throwable e) {
//               Log.i(TAG, "Error from Google " + e);
//           }
//
//           @Override
//           public void onNext(String s) {
//               Log.i(TAG, "Result from Google " + s );
//           }
//       });

        combinedResult = Observable.zip(fetchFromGoogle, fetchFromYahoo, new Func2<String, String, String>() {
            @Override
            public String call(String s, String s2) {
                return s +s2;
            }
        });

         combileres = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "Combined result " + s + "");
            }
        };

//        combinedResult.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(combileres);
/*
// --------------------------------------------------------------------- Case1: Basic Scenario-----------------------------------------------------------------------------------------------------
// 1. Define simple Observable and Observer
// 2. Run Observable on background thread
// 3. Run Observer on main thread
// 4. Connect both Observable and Observer using subscribe method

        //  -----------------------   Creating Observable ------------------------------
        fetchDataforService1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onNext("Hi Ankit, This is first Reactive Android App");
                subscriber.onCompleted();
            }
        });

        //  -----------------------   Creating Observable ------------------------------
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                // dismiss the dialog and Update the UI
                dialog.dismiss();
                txtOutput.setText(txtOutput.getText().toString() + s);
            }
        };

        //--------------------------Linking Observable and Observer---------------------------
        showProgressDialog();
        // Linking between observer and observable
        fetchDataforService1.subscribeOn(Schedulers.io()) // This is telling which thread observable running on
                .observeOn(AndroidSchedulers.mainThread()).// This is telling which thread Observer running on
                subscribe(observer); // connecting both observer and observable
// --------------------------------------------------------------------- Case1 ends -----------------------------------------------------------------------------------------------------
*/







/*
// --------------------------------------------------------------------- Case2: Error Scenario-----------------------------------------------------------------------------------------------------
        //  -----------------------   Creating Observable ------------------------------
        fetchDataforService1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(3000);

                    // Generating some error
                    throw new InterruptedException();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                subscriber.onNext("Hi Ankit, This is first Reactive Android App");
                subscriber.onCompleted();
            }
        });

        //  -----------------------   Creating Observable ------------------------------
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                dialog.dismiss();
                Log.i("Ankit", "onError");
                txtOutput.setText("Something went wrong... please try again later");
            }

            @Override
            public void onNext(String s) {
                // dismiss the dialog and Update the UI
                dialog.dismiss();
                txtOutput.setText(txtOutput.getText().toString()+s);
            }
        };

        //--------------------------Linking Observable and Observer---------------------------
        showProgressDialog();
        // Linking between observer and observable
        fetchDataforService1.doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i("Ankit", "doOnError");
            }
        }).subscribeOn(Schedulers.io()) // This is telling which thread observable running on
                .observeOn(AndroidSchedulers.mainThread()).// This is telling which thread Observer running on
                subscribe(observer); // connecting both observer and observable
// --------------------------------------------------------------------- Case2 ends -----------------------------------------------------------------------------------------------------
*/









/*

// --------------------------------------------------------------------- Case3 starts: just operator-----------------------------------------------------------------------------------------------------
        // The Observable class has many static methods, called operators, to create Observable objects.
        // The following code shows you how to use the just operator to create a very simple Observable that emits a single String.

        //  -----------------------   Creating Observable ------------------------------
        Observable<String> observable2  = Observable.just("Hello Ankit this is jsut operator");

        //  -----------------------   Creating Observable ------------------------------
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                txtOutput.setText(txtOutput.getText().toString()+s);
            }
        };

        //--------------------------Linking Observable and Observer---------------------------
        observable2.subscribeOn(Schedulers.io()) // This is telling which thread observable running on
                .observeOn(AndroidSchedulers.mainThread()).// This is telling which thread Observer running on
                subscribe(observer); // connecting both observer and observable


// --------------------------------------------------------------------- Case3 ends -----------------------------------------------------------------------------------------------
*/





/*

// --------------------------------------------------------------------- Case4 starts: Action method ----------------------------------------------------------------------------------------------
        Observable<String> observable3  = Observable.just("Hello Ankit this is Action class only conatins single call method");
        observable3.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                txtOutput.setText(txtOutput.getText().toString()+s);
            }

        });
// --------------------------------------------------------------------- Case4 ends --------------------------------------------------------------------------------------------
*/

/*

// --------------------------------------------------------------------- Case5 starts: from operator----------------------------------------------------------------------------------------------
        Observable<Integer> observable4 = Observable.from(new Integer[]{1, 2, 3, 4});
        observable4.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer s) {
                txtOutput.setText(txtOutput.getText().toString() + s + "\n");
            }
        });
        //-------------------------------------------------------------------- Case5 ends --------------------------------------------------------------------------------------------
*/


/*

// --------------------------------------------------------------------- Case6 starts Map operator----------------------------------------------------------------------------------------------
        Observable<Integer> observable5 = observable4.map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer * integer;
            }
        });

        observable5.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                txtOutput.setText(txtOutput.getText().toString() + integer + "\n");
            }
        });
// --------------------------------------------------------------------- Case6 ends --------------------------------------------------------------------------------------------
*/











/*

// --------------------------------------------------------------------- Case7: Filter and doOnNext----------------------------------------------------------
        Observable<Integer> observable4 = Observable.from(new Integer[]{1, 2, 3, 4});
        observable4.doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.print(integer+"");
            }
        }).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer % 2 == 0;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer s) {
                txtOutput.setText(txtOutput.getText().toString() + s + "\n");
            }
        });

        //  -----------------------   Creating Observable ------------------------------
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                // dismiss the dialog and Update the UI
                dialog.dismiss();
                txtOutput.setText(txtOutput.getText().toString()+s);
            }
        };
        // --------------------------------------------------------------------- Case7 ends --------------------------------------------------------------------------------------------
*/


/*
// --------------------------------------------------------------------- Case 8: Subject--------------------------------------------------------------------------------------------------
        PublishSubject<Integer> source = PublishSubject.create();
// It will get 1, 2, 3, 4 and onComplete
        source.subscribe(getFirstObserver());

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

// It will get 4 and onComplete for second observer also.
        source.subscribe(getSecondObserver());

        source.onNext(4);
        source.onCompleted();

// We can check other subject too using same way:
//         Replay, Behavior and Async Subject
// --------------------------------------------------------------------- Case8 ends -----------------------------------------------------------------------------------------------------
*/


    }

    private String fetchData(String s) {
        String result = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(s).openStream()));

            String inputLine = "";
            while ((inputLine = bufferedReader.readLine()) != null) {
                result = inputLine;
            }
        } catch (IOException e) {
           result = "Error occured, due to reason"+e;
        }
        return result;
    }

    private void showProgressDialog() {
        dialog.setMessage("Processing request.....");
        dialog.show();
    }

    private Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, " First onNext value : " + value);
                txtOutput.setText(txtOutput.getText().toString() + "First onNext value : " + value + "\n");
            }

            @Override
            public void onError(Throwable e) {

                Log.d(TAG, " First onError : " + e.getMessage());
                txtOutput.setText(txtOutput.getText().toString() + " First onError : " + e.getMessage() + "\n");
            }

            @Override
            public void onCompleted() {

                Log.d(TAG, " First onComplete");
                txtOutput.setText(txtOutput.getText().toString() + "First onComplete" + "\n");
            }
        };
    }

    private Observer<Integer> getSecondObserver() {
        return new Observer<Integer>() {

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, " Second onNext value : " + value);
                txtOutput.setText(txtOutput.getText().toString() + "Second onNext value : " + value + "\n");
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, " Second onComplete");
                txtOutput.setText(txtOutput.getText().toString() + "Second onComplete" + "\n");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, " Second onError : " + e.getMessage());
                txtOutput.setText(txtOutput.getText().toString() + "Second onError" + "\n");
            }

        };
    }


    @Override
    public void onClick(View view) {
        combinedResult.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(combileres);
    }

}

package com.shentuo.bakingapp.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.shentuo.bakingapp.R;
import com.shentuo.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ShentuoZhan on 9/5/17.
 */

public class NetworkUtils {
    private final static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private final static String TAG = "NetworkUtils";

    private NetworkUtils() {
        throw new AssertionError();
    }

    public static boolean isOnline(Activity activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean isOnline = netInfo != null && netInfo.isConnectedOrConnecting();
        if (!isOnline) {
            Toast.makeText(activity, activity.getResources().getString(R.string.error_no_internet), Toast.LENGTH_LONG).show();
        }
        return isOnline;
    }

    public static Observable<ArrayList<Recipe>> getRecipeList() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        Retrofit tmpRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        RecipeService service = tmpRetrofit.create(RecipeService.class);
        return service.getRecipeList();
    }

    public static void showErrorMessage(Context context, String errorMessage) {
        Toast.makeText(context, errorMessage + "\n" + context.getResources().getString(R.string.error_message), Toast.LENGTH_LONG).show();
    }
}

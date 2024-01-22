package com.example.jetmessenger;
import android.app.Application;
import dagger.hilt.android.HiltAndroidApp;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;

@HiltAndroidApp
public class JetMessengerApplication extends Application {

    Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Builder()
                .baseUrl(BuildConfig.WEBHOOK_URL)
                .build();
    }

}

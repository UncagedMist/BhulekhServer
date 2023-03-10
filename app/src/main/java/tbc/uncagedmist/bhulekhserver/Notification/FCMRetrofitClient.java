package tbc.uncagedmist.bhulekhserver.Notification;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FCMRetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getClient(String baseUrl)    {

        if (retrofit == null)   {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

package dk.au.mad22spring.group04.cibusapp.API;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private Retrofit retrofit;
    private static RetrofitClient mInstance;

    public RetrofitClient() {
        OkHttpClient client = new OkHttpClient();

        String BASE_URL = "https://tasty.p.rapidapi.com/recipes/";
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();

    }

    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public RecipeAPI getJsonApi() {
        return retrofit.create(RecipeAPI.class);
    }

}

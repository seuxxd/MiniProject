package httpclient;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import constant.NetUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @ author leonxdxiao
 * @ date 2018/6/9
 * 单例模式
*/
public class RetrofitClient {
    private volatile static Retrofit mRetrofit;
    private static OkHttpClient mClient;

    public static Retrofit getInstance(){
        if (mRetrofit == null){
            synchronized (RetrofitClient.class){
                if (mRetrofit == null)
                    mRetrofit = new Retrofit.
                            Builder()
                            .baseUrl(NetUtil.HOST)
                            .client(getHTTPInstance())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
            }
        }
        return mRetrofit;
    }

    private static OkHttpClient getHTTPInstance(){
        if (mClient == null){
            synchronized (OkHttpClient.class){
                if (mClient == null){
                    mClient = new OkHttpClient()
                            .newBuilder()
                            .connectTimeout(5, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return mClient;
    }
}

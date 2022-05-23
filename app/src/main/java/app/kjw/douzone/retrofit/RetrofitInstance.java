package app.kjw.douzone.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 레트로핏 빌드 클래스 - 인스턴스 생성 */
public class RetrofitInstance {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/search/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static RetrofitService service = retrofit.create(RetrofitService.class);
    public static RetrofitService getRetrofitService() {
        return service;
    }
}

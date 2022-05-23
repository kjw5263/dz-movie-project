package app.kjw.douzone.retrofit;

import app.kjw.douzone.data.MovieResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitService {
    @Headers({
            "X-Naver-Client-Id: G8Sh2_NUmslycprkxJPe",
            "X-Naver-Client-Secret: 1qECCbap7d"
    })
    @GET("movie.json")
    Call<MovieResult> getMovieList(@Query("query") String query);
}

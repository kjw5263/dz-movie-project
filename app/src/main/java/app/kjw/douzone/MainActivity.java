package app.kjw.douzone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import app.kjw.douzone.adapter.MovieAdapter;
import app.kjw.douzone.data.MovieResult;
import app.kjw.douzone.retrofit.RetrofitInstance;
import app.kjw.douzone.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /* View 선언 */
    private RecyclerView recyclerView;
    private EditText searchTitle;
    private Button searchBtn;
    private MovieAdapter adapter;
    private RetrofitService retrofitService = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


    }

    /**
     * 객체 초기화 메소드
     * */
    public void init() {
        // View Init
        recyclerView = findViewById(R.id.recyclerView);
        searchTitle = findViewById(R.id.input_movie_name);
        searchBtn = findViewById(R.id.search_btn);

        // 레트로핏 Init
        retrofitService = RetrofitInstance.getRetrofitService();

        // 검색버튼 클릭 이벤트
        searchBtn.setOnClickListener(view -> searchMovie(searchTitle.getText().toString()));
    }

    /**
     * 영화 제목을 입력해 영화 정보 리스트를 받아온다.
     * @param movieTitle 검색할 영화제목*/
    public void searchMovie(String movieTitle) {

        Log.i(TAG, movieTitle);

        retrofitService.getMovieList(movieTitle).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if(response.isSuccessful()){        // 연결 성공 시
                    adapter = new MovieAdapter(response.body().getItems(), MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

                } else {                            // 연결 실패 시
                    Log.i(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
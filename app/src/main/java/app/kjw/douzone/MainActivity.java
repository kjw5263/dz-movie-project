package app.kjw.douzone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.kjw.douzone.adapter.MovieAdapter;
import app.kjw.douzone.data.MovieResult;
import app.kjw.douzone.retrofit.RetrofitInstance;
import app.kjw.douzone.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /* View */
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

        // 레트로핏 인스턴스 가져오기
        retrofitService = RetrofitInstance.getRetrofitService();

        // 검색버튼 클릭 이벤트
        searchBtn.setOnClickListener(view -> {
            if(searchTitle.getText().toString().isEmpty())
                Toast.makeText(MainActivity.this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
            else searchMovie(searchTitle.getText().toString());
        });
    }

    /**
     * 영화 제목을 입력해 영화 정보 리스트를 받아온다.
     * @param movieTitle 검색할 영화제목*/
    public void searchMovie(String movieTitle) {

        retrofitService.getMovieList(movieTitle).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if(response.isSuccessful()){        // 검색 성공 시

                    // 제목에 해당하는 영화 없을 때(결과 개수가 0개) 토스트 메시지
                    if(response.body().getTotal() == 0)
                        Toast.makeText(MainActivity.this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();

                    adapter = new MovieAdapter(response.body().getItems(), MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
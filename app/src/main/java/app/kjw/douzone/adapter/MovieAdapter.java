package app.kjw.douzone.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.kjw.douzone.R;
import app.kjw.douzone.WebViewActivity;
import app.kjw.douzone.data.Item;

/** 영화 리스트를 보여주는 RecyclerView의 어댑터 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private List<Item> datas;
    private Context mContext;

    private static final String TAG = "MovieAdapter";

    /** 생성자
     * @param datas 리사이클러 뷰에 올릴 데이터 리스트 */
    public MovieAdapter(List<Item> datas, Context mContext){
        this.datas = datas;
        this.mContext = mContext;
    }


    /**
     * 뷰홀더 정의 : RecyclerView 의 개별 아이템 데이터 정의
     * */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private Context mContext;
        // 뷰 아이템 정의
        private ImageView posterImage;
        private RatingBar ratingBar;
        private TextView rating;
        private TextView title;
        private TextView year;
        private TextView director;
        private TextView actors;
        private String url;

        public ViewHolder(@NonNull View itemView, Context mContext) {
            super(itemView);
            posterImage = itemView.findViewById(R.id.iv_movie_poster);
            title = itemView.findViewById(R.id.tv_movie_title);
            ratingBar = itemView.findViewById(R.id.movie_rating);
            rating = itemView.findViewById(R.id.tv_movie_rating);
            year = itemView.findViewById(R.id.tv_movie_year);
            director = itemView.findViewById(R.id.tv_movie_director);
            actors = itemView.findViewById(R.id.tv_actor_list);
            this.mContext = mContext;


            // 아이템 뷰 클릭 시 웹뷰 액티비티로 이동
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("URL_NAME", url);
                mContext.startActivity(intent);
            });

        }

        /**
         * 실제 개별 항목에 데이터 설정하는 메소드
         * @param result 영화검색 1개 결과
         * */
        public void setItem(Item result){

            // 포스터 이미지 없을 때, no_image 아이콘 설정
            if(result.getImage().equals("")) posterImage.setImageResource(R.drawable.ic_no_image);
            else Glide.with(itemView).load(result.getImage()).into(posterImage);

            // 영화 별점 및 평점 표시
            ratingBar.setRating(Float.parseFloat(result.getUserRating())/2.0f);
            rating.setText(result.getUserRating());

            // 영화 Info
            title.setText(Html.fromHtml(result.getTitle()));
            year.setText(result.getPubDate());
            director.setText(result.getDirector());
            actors.setText(result.getActor());

            // 웹뷰에 영화 소개 사이트 링크 전달하기 위한 변수 할당
            url = result.getLink();
        }

    }

    /**
     * 뷰홀더 생성 및 초기화 - 개별 아이템 뷰홀더 생성
     * 아이템이 새로 보일 때 마다 생성된다.
     * */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "뷰홀더 새로 생성");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_info_item, parent, false);
        return new ViewHolder(view, mContext);
    }

    /**
     * 데이터를 가져와 뷰홀더 레이아웃 그리는 메소드
     * */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item data = datas.get(position); // 리스트들 중 해당 위치의 데이터를 받아서
        holder.setItem(data);   // 뷰홀더가 다시 그릴 때, 그려야하는 데이터를 세팅해줌

    }

    /**
     * 데이터 총 개수
     * */
    @Override
    public int getItemCount() {
        return datas.size();
    }

}

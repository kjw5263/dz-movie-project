package app.kjw.douzone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private WebSettings webSettings;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        url = getIntent().getStringExtra("URL_NAME");

        // 웹뷰 세팅 초기화
        webView = findViewById(R.id.web_view);
        webSettings = webView.getSettings();

        webView.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl(url);   // 웹뷰 실행


    }
}
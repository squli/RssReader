package ru.squel.myrssreader.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ru.squel.myrssreader.R;

public class PostDetailActivity extends AppCompatActivity {

    String currentLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_detail) != null) {
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            PostFragment postDetailFragment = new PostFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            Bundle extras = getIntent().getExtras();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_detail, postDetailFragment).commit();

            currentLink = extras.getString("selectedValue");
            WebView viewer = (WebView) findViewById(R.id.webView);
            viewer.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            viewer.loadUrl(currentLink);
        }
    }

    /*
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("selectedValue", currentLink);
            startActivity(intent);
        }
    }
    */
}

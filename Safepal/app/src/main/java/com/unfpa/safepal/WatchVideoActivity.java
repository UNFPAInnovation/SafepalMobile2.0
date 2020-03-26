package com.unfpa.safepal;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.pixplicity.easyprefs.library.Prefs;
import com.unfpa.safepal.Utils.Constants;

import static com.unfpa.safepal.Utils.Constants.CATEGORY;
import static com.unfpa.safepal.Utils.Constants.DESCRIPTION;
import static com.unfpa.safepal.Utils.Constants.VIDEO_URL;

public class WatchVideoActivity extends AppCompatActivity {

    private VideoView video;
    private TextView title;
    private TextView category;
    private TextView description;
    private RecyclerView relatedVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_video);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        video = findViewById(R.id.video);
        title = findViewById(R.id.title);
        category = findViewById(R.id.category);
        description = findViewById(R.id.description);
        relatedVideos = findViewById(R.id.related_videos_recycler);

        String videoUrl = Prefs.getString(VIDEO_URL, "");
        title.setText(Prefs.getString(Constants.TITLE, ""));
        category.setText(Prefs.getString(CATEGORY, ""));
        description.setText(Prefs.getString(DESCRIPTION, ""));

        Uri uri = Uri.parse("https://www.sample-videos.com/video123/mp4/480/big_buck_bunny_480p_5mb.mp4"); //Declare your url here.
        video.setMediaController(new MediaController(this));
        video.setVideoURI(uri);
        video.requestFocus();
        video.start();
    }
}

package com.unfpa.safepal;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.unfpa.safepal.adapters.VideoAdapter;
import com.unfpa.safepal.provider.videotable.VideotableCursor;
import com.unfpa.safepal.provider.videotable.VideotableSelection;

import static com.unfpa.safepal.provider.videotable.VideotableColumns.TITLE;

public class WatchVideoActivity extends AppCompatActivity {
    private VideoView video;
    private TextView title;
    private TextView category;
    private TextView description;
    private RecyclerView relatedVideosRecyclerView;
    private VideoAdapter videoAdapter;

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
        relatedVideosRecyclerView = findViewById(R.id.related_videos_recycler);

        // Get the title passed to use from the VideoAdapter and query for the video
        VideotableCursor videotableCursor = new VideotableSelection().orderByCreatedAt(true)
                .title(getIntent().getStringExtra(TITLE)).query(getContentResolver());

        videotableCursor.moveToFirst();
        title.setText(videotableCursor.getTitle());
        category.setText(videotableCursor.getCategory());
        description.setText(videotableCursor.getDescription());

        video.setMediaController(new MediaController(this));
        video.setVideoURI(Uri.parse(videotableCursor.getUrl()));
        video.requestFocus();
        video.setOnPreparedListener(mp -> video.start());

        videoAdapter = new VideoAdapter(this, new VideotableSelection()
                .orderByCreatedAt(true).category(videotableCursor.getCategory())
                .query(getContentResolver()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        relatedVideosRecyclerView.setLayoutManager(layoutManager);
        relatedVideosRecyclerView.setAdapter(videoAdapter);
    }
}

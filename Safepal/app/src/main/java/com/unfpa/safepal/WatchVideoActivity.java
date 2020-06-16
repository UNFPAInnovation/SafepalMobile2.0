package com.unfpa.safepal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.unfpa.safepal.adapters.VideoAdapter;
import com.unfpa.safepal.provider.videotable.VideotableCursor;
import com.unfpa.safepal.provider.videotable.VideotableSelection;

import static com.unfpa.safepal.provider.videotable.VideotableColumns.TITLE;

class AbsolutefitLayourManager extends GridLayoutManager {
    // https://gist.github.com/KaveriKR/04bfef5ffc9c00a8b6fca503da497322
    // https://stackoverflow.com/questions/47300862/recycler-view-horizontal-scroll-with-2-columns

    // column count for orientation = HORIZONTAL
    private static final int spanColumnCount = 2;

    public AbsolutefitLayourManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AbsolutefitLayourManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public AbsolutefitLayourManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }


    /* Setting LayoutParams for the child views of the recycler view.*/
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return spanLayoutSize(super.generateDefaultLayoutParams());
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        return spanLayoutSize(super.generateLayoutParams(c, attrs));
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return spanLayoutSize(super.generateLayoutParams(lp));
    }

    /* The function has been made for Horizontal Recycler View.
     *  1. It checks for the orientation to be HORIZONTAL.
     *  2. Its spancount has already been set to 2 in this case throught the constuctor at the initialisation time.
     *    i.e,  layoutManager = new AbsolutefitLayourManager(this,2,GridLayoutManager.HORIZONTAL,false);
     *    the spancount = 2 , specifies the no. of rows for HORIZONTAL orientation
     * 3. The rest of the function divides the horizontal screen width by 2 (spanColumnCount = 2 HERE) hence specyfing the column
          width of each view and hence specifying  2 columns (can be made 3, by dividing by three.)
     */
    private RecyclerView.LayoutParams spanLayoutSize(RecyclerView.LayoutParams layoutParams) {
        if (getOrientation() == HORIZONTAL) {
            layoutParams.width = (int) Math.round(getHorizontalSpace() / spanColumnCount);
            // its the margin between the items
            layoutParams.setMargins(2, 2, 2, 2);
        }
        return layoutParams;
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
        return super.checkLayoutParams(lp);
    }

    private int getHorizontalSpace() {
        return getWidth() - (getPaddingRight()) - (getPaddingLeft());
    }
}

public class WatchVideoActivity extends AppCompatActivity {
    private VideoView video;
    private TextView title;
    private TextView category;
    private TextView description;
    private RecyclerView relatedVideosRecyclerView;
    private VideoAdapter videoAdapter;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_video);

        video = findViewById(R.id.video);
        title = findViewById(R.id.title);
        category = findViewById(R.id.category);
        description = findViewById(R.id.description);
        relatedVideosRecyclerView = findViewById(R.id.related_videos_recycler);

        // Get the title passed to this view from the VideoAdapter and query for the video
        VideotableCursor videotableCursor = new VideotableSelection().orderByCreatedAt(true)
                .title(getIntent().getStringExtra(TITLE)).query(getContentResolver());

        videotableCursor.moveToFirst();
        title.setText(videotableCursor.getTitle());
        category.setText(videotableCursor.getCategory());
        description.setText(videotableCursor.getDescription());

        video.setMediaController(new MediaController(this));
        String lowResVideoUrl = videotableCursor.getUrl().replace(".mp4", "_480p.m3u8");
        video.setVideoURI(Uri.parse(lowResVideoUrl));
        video.requestFocus();
        video.setOnPreparedListener(mp -> {
            mediaController = new MediaController(WatchVideoActivity.this);
            video.setMediaController(mediaController);
            mediaController.setAnchorView(video);
            video.start();
        });

        videoAdapter = new VideoAdapter(this, new VideotableSelection()
                .orderByCreatedAt(true).category(videotableCursor.getCategory())
                .query(getContentResolver()));
        AbsolutefitLayourManager gridLayoutManager = new AbsolutefitLayourManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        relatedVideosRecyclerView.setLayoutManager(gridLayoutManager);
        relatedVideosRecyclerView.setAdapter(videoAdapter);
    }
}

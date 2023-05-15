package com.unfpa.safepal;

import static com.unfpa.safepal.provider.videotable.VideotableColumns.TITLE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unfpa.safepal.adapters.ArticleAdapter;
import com.unfpa.safepal.provider.articletable.ArticletableCursor;
import com.unfpa.safepal.provider.articletable.ArticletableSelection;

public class ReadArticleActivity extends AppCompatActivity {
    private TextView title, category, questions, content;
    private ImageView articleImage;
    private RecyclerView relatedArticlesRecyclerView;
    private Button startQuizButton;
    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_article);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.title);
        category = findViewById(R.id.category);
        questions = findViewById(R.id.questions);
        content = findViewById(R.id.content);
        articleImage = findViewById(R.id.article_image);
        startQuizButton = findViewById(R.id.start_quiz_button);
        relatedArticlesRecyclerView = findViewById(R.id.related_articles_recycler);

        // Get the title passed to use from the VideoAdapter and query for the video
        ArticletableCursor articletableCursor = new ArticletableSelection().orderByCreatedAt(true)
                .title(getIntent().getStringExtra(TITLE)).query(getContentResolver());

        articletableCursor.moveToFirst();
        title.setText(articletableCursor.getTitle());
        questions.setText(articletableCursor.getQuestions());
        category.setText(articletableCursor.getCategory());
        content.setText(articletableCursor.getContent());

        Log.d("URLX", "onCreate: URLX articles " + articletableCursor.getThumbnail());


//        PicassoTrustAll.getInstance(this).load("https://10.0.2.2/path/to/picture").into(mainPictureView);
        Picasso.get()
                .load(articletableCursor.getThumbnail())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(articleImage);

        startQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizActivity.class)
                    .putExtra(TITLE, title.getText().toString());
            startActivity(intent);
        });

        articleAdapter = new ArticleAdapter(this, new ArticletableSelection()
                .orderByCreatedAt(true).category(articletableCursor.getCategory())
                .and().titleNot(articletableCursor.getTitle())
                .query(getContentResolver()));
        AbsolutefitLayourManager gridLayoutManager = new AbsolutefitLayourManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        relatedArticlesRecyclerView.setLayoutManager(gridLayoutManager);
        relatedArticlesRecyclerView.setAdapter(articleAdapter);
    }

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
}

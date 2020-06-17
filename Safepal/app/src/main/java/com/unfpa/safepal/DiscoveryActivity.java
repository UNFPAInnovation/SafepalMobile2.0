package com.unfpa.safepal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import com.unfpa.safepal.report.ReportingActivity;
import com.unfpa.safepal.ui.main.SectionsPagerAdapter;

import timber.log.Timber;

public class DiscoveryActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_video_library_48px,
            R.drawable.ic_bookmark_48px,
            R.drawable.ic_folder_open_48px
    };
    private ItemClickListener mClickListener;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_more);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),
                ReportingActivity.class)));

        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            int selectedTabPosition = tabLayout.getSelectedTabPosition();
            @Override
            public boolean onQueryTextSubmit(String query) {
                Timber.d("searching");
                if (mClickListener != null)
                    mClickListener.onItemClick(query, selectedTabPosition);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (mClickListener != null)
                    mClickListener.onItemClick(query, selectedTabPosition);
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            if (mClickListener != null)
                mClickListener.onItemClick("", 1);
            return false;
        });
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    // allows clicks events to be caught. activity registers here as the listener
    public void setClickListener(DiscoveryActivity.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(String query, int position);
    }
}
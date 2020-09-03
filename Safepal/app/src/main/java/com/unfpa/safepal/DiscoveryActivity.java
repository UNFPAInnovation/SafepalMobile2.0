package com.unfpa.safepal;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.unfpa.safepal.report.ReportingActivity;
import com.unfpa.safepal.ui.main.DiscoveryFragment;
import com.unfpa.safepal.ui.main.SectionsPagerAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class DiscoveryActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_video_library_48px,
            R.drawable.ic_bookmark_48px,
            R.drawable.ic_folder_open_48px
    };
    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_more);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),
                ReportingActivity.class)));

        // reset the search view every two seconds
        try {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    resetSearch();
                }
            }, 0, 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void resetSearch() {
        Fragment currentFragment = sectionsPagerAdapter.getCurrentFragment();
        if (currentFragment instanceof DiscoveryFragment)
            ((Searchable) currentFragment).initializeSearch();
    }
}
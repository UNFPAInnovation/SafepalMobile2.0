package com.unfpa.safepal.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.unfpa.safepal.DiscoveryActivity;
import com.unfpa.safepal.R;
import com.unfpa.safepal.Searchable;
import com.unfpa.safepal.adapters.ArticleAdapter;
import com.unfpa.safepal.adapters.VideoAdapter;
import com.unfpa.safepal.provider.articletable.ArticletableCursor;
import com.unfpa.safepal.provider.articletable.ArticletableSelection;
import com.unfpa.safepal.provider.videotable.VideotableCursor;
import com.unfpa.safepal.provider.videotable.VideotableSelection;

/**
 * Displays list of Video, Article and Quiz by loading adapters for either videos or articles
 * depending on what the tab that the user clicks
 *
 * @author Phillip Kigenyi (codephillip@gmail.com)
 */
public class DiscoveryFragment extends Fragment implements Searchable {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private VideoAdapter videoAdapter;
    private ArticleAdapter articleAdapter;
    private int index = 0;
    private DiscoveryActivity activity;

    public static DiscoveryFragment newInstance(int index) {
        DiscoveryFragment fragment = new DiscoveryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover_more, container, false);
        recyclerView = rootView.findViewById(R.id.media_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        activity = ((DiscoveryActivity) getActivity());
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        searchView = activity.findViewById(R.id.search);
        index = getArguments().getInt(ARG_SECTION_NUMBER);
        Toast.makeText(activity, "index " + index, Toast.LENGTH_SHORT).show();
        if (index == 1) {
            videoAdapter = new VideoAdapter(getActivity(), getVideosFromTable());
            recyclerView.setAdapter(videoAdapter);
        } else if (index == 2) {
            articleAdapter = new ArticleAdapter(getActivity(), getArticlesFromTable());
            recyclerView.setAdapter(articleAdapter);
        }
        return rootView;
    }

    private VideotableCursor getVideosFromTable() {
        return new VideotableSelection().orderByCreatedAt(true).query(getContext().getContentResolver());
    }

    private ArticletableCursor getArticlesFromTable() {
        return new ArticletableSelection().orderByCreatedAt(true).query(getContext().getContentResolver());
    }

    @Override
    public void initializeSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                runSearchQuery(query, true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                runSearchQuery(s, false);
                return false;
            }

            private void runSearchQuery(String query, boolean retractSearchView) {
                TabLayout tabLayout = activity.findViewById(R.id.tabs);
                int selectedTabPosition = tabLayout.getSelectedTabPosition();

                if (retractSearchView)
                    searchView.setIconified(retractSearchView);

                if (selectedTabPosition == 0) {
                    videoAdapter = new VideoAdapter(getActivity(), getVideosFromTable());
                    videoAdapter.filter(query);
                    recyclerView.setAdapter(videoAdapter);
                } else if (selectedTabPosition == 1) {
                    articleAdapter = new ArticleAdapter(getActivity(), getArticlesFromTable());
                    articleAdapter.filter(query);
                    recyclerView.setAdapter(articleAdapter);
                }
            }
        });

        searchView.setOnCloseListener(() -> {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            if (index == 1) {
                videoAdapter = new VideoAdapter(getActivity(), getVideosFromTable());
                recyclerView.setAdapter(videoAdapter);
            } else if (index == 2) {
                articleAdapter = new ArticleAdapter(getActivity(), getArticlesFromTable());
                recyclerView.setAdapter(articleAdapter);
            }
            searchView.onActionViewCollapsed();
            return false;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        searchView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        searchView.setVisibility(View.INVISIBLE);
    }
}
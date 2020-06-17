package com.unfpa.safepal.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.unfpa.safepal.DiscoveryActivity;
import com.unfpa.safepal.R;
import com.unfpa.safepal.adapters.ArticleAdapter;
import com.unfpa.safepal.adapters.VideoAdapter;
import com.unfpa.safepal.provider.articletable.ArticletableCursor;
import com.unfpa.safepal.provider.articletable.ArticletableSelection;
import com.unfpa.safepal.provider.videotable.VideotableCursor;
import com.unfpa.safepal.provider.videotable.VideotableSelection;

import timber.log.Timber;

/**
 * Displays list of Video, Article and Quiz by loading adapters for either videos or articles
 * depending on what the tab that the user clicks
 *
 * @author Phillip Kigenyi (codephillip@gmail.com)
 */
public class DiscoveryFragment extends Fragment implements DiscoveryActivity.ItemClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private VideoAdapter videoAdapter;
    private ArticleAdapter articleAdapter;
    private int index = 0;
    private String currentFragment = "Video";
    private DiscoveryActivity.ItemClickListener mClickListener;

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

        DiscoveryActivity activity = ((DiscoveryActivity) getActivity());
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        index = getArguments().getInt(ARG_SECTION_NUMBER);
        TabLayout tabLayout = activity.findViewById(R.id.tabs);
        int selectedTabPosition = tabLayout.getSelectedTabPosition();
        Timber.d("selected #" + selectedTabPosition);
        Toast.makeText(activity, "index " + index, Toast.LENGTH_SHORT).show();
        if (index == 1) {
            videoAdapter = new VideoAdapter(getActivity(), getVideosFromTable());
            recyclerView.setAdapter(videoAdapter);
        } else if (index == 2) {
            articleAdapter = new ArticleAdapter(getActivity(), getArticlesFromTable());
            recyclerView.setAdapter(articleAdapter);
        }

        activity.setClickListener(this);

        // access the searchview from the parent activity directly
//        searchView = activity.findViewById(R.id.search);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Timber.d("searching");
//                index = getArguments().getInt(ARG_SECTION_NUMBER);
//                Toast.makeText(activity, "Index search " + index, Toast.LENGTH_SHORT).show();
//
//                TabLayout tabLayout = activity.findViewById(R.id.tabs);
//                int selectedTabPosition = tabLayout.getSelectedTabPosition();
//                Timber.d("selected 2 #" + selectedTabPosition);
//
//                if (!searchView.isIconified()) {
//                    searchView.setIconified(true);
//                    if (selectedTabPosition == 0) {
//                        Timber.d("search video");
//                        videoAdapter = new VideoAdapter(getActivity(), getVideosFromTable());
//                        videoAdapter.filter(query);
//                        recyclerView.setAdapter(videoAdapter);
//                    } else if (selectedTabPosition == 1) {
//                        Timber.d("search article");
//                        articleAdapter = new ArticleAdapter(getActivity(), getArticlesFromTable());
//                        articleAdapter.filter(query);
//                        recyclerView.setAdapter(articleAdapter);
//                    }
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
//
//        searchView.setOnCloseListener(() -> {
//            if (index == 1) {
//                videoAdapter = new VideoAdapter(getActivity(), getVideosFromTable());
//                recyclerView.setAdapter(videoAdapter);
//            } else if (index == 2) {
//                articleAdapter = new ArticleAdapter(getActivity(), getArticlesFromTable());
//                recyclerView.setAdapter(articleAdapter);
//            }
//            return false;
//        });
        return rootView;
    }

    private VideotableCursor getVideosFromTable() {
        return new VideotableSelection().orderByCreatedAt(true).query(getContext().getContentResolver());
    }

    private ArticletableCursor getArticlesFromTable() {
        return new ArticletableSelection().orderByCreatedAt(true).query(getContext().getContentResolver());
    }

    @Override
    public void onItemClick(String query, int position) {
        Toast.makeText(getActivity().getApplicationContext(), "Clicked ##### " + position + "######" + query, Toast.LENGTH_SHORT).show();
        index = getArguments().getInt(ARG_SECTION_NUMBER);
        if (index == 1) {
            Timber.d("search video");
            videoAdapter = new VideoAdapter(getActivity(), getVideosFromTable());
            videoAdapter.filter(query);
            recyclerView.setAdapter(videoAdapter);
        } else {
            Timber.d("search article");
            articleAdapter = new ArticleAdapter(getActivity(), getArticlesFromTable());
            articleAdapter.filter(query);
            recyclerView.setAdapter(articleAdapter);
        }
    }
}
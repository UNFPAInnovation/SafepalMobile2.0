package com.unfpa.safepal.ui.main;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unfpa.safepal.DiscoveryActivity;
import com.unfpa.safepal.R;
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
public class VideoFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private VideoAdapter adapter;

    public static VideoFragment newInstance(int index) {
        VideoFragment fragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public VideoFragment() {
        setHasOptionsMenu(true);
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

        adapter = new VideoAdapter(getActivity(), getVideosFromTable());
        recyclerView.setAdapter(adapter);

        // access the searchview from the parent activity directly
        searchView = activity.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                    adapter.filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            adapter = new VideoAdapter(getActivity(), getVideosFromTable());
            recyclerView.setAdapter(adapter);
            return false;
        });
        return rootView;
    }

    private VideotableCursor getVideosFromTable() {
        return new VideotableSelection().orderByCreatedAt(true).query(getContext().getContentResolver());
    }

    private ArticletableCursor getArticlesFromTable() {
        return new ArticletableSelection().orderByCreatedAt(true).query(getContext().getContentResolver());
    }

    @Override
    public void onResume() {
        super.onResume();

        DiscoveryActivity activity = ((DiscoveryActivity) getActivity());
        // access the searchview from the parent activity directly
        searchView = activity.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Timber.d("searching vid");
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                    adapter.filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            adapter = new VideoAdapter(getActivity(), getVideosFromTable());
            recyclerView.setAdapter(adapter);
            return false;
        });
    }
}
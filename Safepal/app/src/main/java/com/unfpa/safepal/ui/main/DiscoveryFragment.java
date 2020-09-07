package com.unfpa.safepal.ui.main;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.unfpa.safepal.R;
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
public class DiscoveryFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private RecyclerView recyclerView;

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

        int index = getArguments().getInt(ARG_SECTION_NUMBER);
        if (index == 1) {
            recyclerView.setAdapter(new VideoAdapter(getActivity(), getVideosFromTable()));
        } else if (index == 2) {
            recyclerView.setAdapter(new ArticleAdapter(getActivity(), getArticlesFromTable()));
        }
        return rootView;
    }

    private VideotableCursor getVideosFromTable() {
        return new VideotableSelection().orderByCreatedAt(true).query(getContext().getContentResolver());
    }

    private ArticletableCursor getArticlesFromTable() {
        return new ArticletableSelection().orderByCreatedAt(true).query(getContext().getContentResolver());
    }
}
package com.unfpa.safepal.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.unfpa.safepal.R;
import com.unfpa.safepal.adapters.ArticleAdapter;
import com.unfpa.safepal.adapters.OrganizationAdapter;
import com.unfpa.safepal.adapters.VideoAdapter;
import com.unfpa.safepal.provider.articletable.ArticletableCursor;
import com.unfpa.safepal.provider.articletable.ArticletableSelection;
import com.unfpa.safepal.provider.districttable.DistricttableCursor;
import com.unfpa.safepal.provider.districttable.DistricttableSelection;
import com.unfpa.safepal.provider.organizationtable.OrganizationtableCursor;
import com.unfpa.safepal.provider.organizationtable.OrganizationtableSelection;
import com.unfpa.safepal.provider.videotable.VideotableCursor;
import com.unfpa.safepal.provider.videotable.VideotableSelection;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays list CSO(Civil Society Organization) that a user can quickly contact
 *
 * @author Phillip Kigenyi (codephillip@gmail.com)
 */
public class CSODirectoryFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private RecyclerView recyclerView;
    private Spinner districtSpinner;

    ArrayList<String> districts = new ArrayList<>();

    public static CSODirectoryFragment newInstance(int index) {
        CSODirectoryFragment fragment = new CSODirectoryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_cso_directory, container, false);
        recyclerView = rootView.findViewById(R.id.media_recycler_view);
        districtSpinner = rootView.findViewById(R.id.district_spinner);

        DistricttableCursor districttableCursor = getDistrictsTableData();
        districttableCursor.moveToFirst();
        do {
            districts.add(districttableCursor.getName());
        } while(districttableCursor.moveToNext());

        districtSpinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, districts.toArray());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(arrayAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new OrganizationAdapter(getActivity(), getOrganizationTableData()));
        return rootView;
    }

    private DistricttableCursor getDistrictsTableData() {
        return new DistricttableSelection().orderByName().query(getContext().getContentResolver());
    }

    private OrganizationtableCursor getOrganizationTableData() {
        return new OrganizationtableSelection().orderByCreatedAt(true).query(getContext().getContentResolver());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        OrganizationtableSelection selection = new OrganizationtableSelection();
        selection.districtContains(districts.get(position));
        recyclerView.setAdapter(new OrganizationAdapter(getActivity(),
                selection.orderByFacilityName().query(getContext().getContentResolver())));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
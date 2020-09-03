package com.unfpa.safepal.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unfpa.safepal.R;
import com.unfpa.safepal.adapters.OrganizationAdapter;
import com.unfpa.safepal.provider.districttable.DistricttableCursor;
import com.unfpa.safepal.provider.districttable.DistricttableSelection;
import com.unfpa.safepal.provider.organizationtable.OrganizationtableSelection;

import java.util.ArrayList;

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

        if (districts.size() <= 0) {
            DistricttableCursor districttableCursor = getDistrictsTableData();
            districttableCursor.moveToFirst();
            do {
                districts.add(districttableCursor.getName());
            } while (districttableCursor.moveToNext());
        }

        districtSpinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.spinner_item, districts.toArray());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(arrayAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    private DistricttableCursor getDistrictsTableData() {
        return new DistricttableSelection().orderByName().query(getContext().getContentResolver());
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
package com.unfpa.safepal.ProvideHelp.RVCsoModel;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unfpa.safepal.R;
import com.unfpa.safepal.messages.csoDialogFragment;

import java.util.List;

/**
 * Created by Kisa on 11/3/2016.
 */

public class CsoRvAdapter extends RecyclerView.Adapter<CsoRvAdapter.CustomViewHolder> {

    private List<TheCSO> csosList;

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView csoName, csoDistance, csoPhonenumber;

        public CustomViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);

            csoName = (TextView) view.findViewById(R.id.cso_name);
            csoDistance = (TextView) view.findViewById(R.id.cso_distance);
            csoPhonenumber = (TextView) view.findViewById(R.id.cso_phonenumber);
        }

        @Override
        public void onClick(View view) {
            csoDialogFragment singleCsoDialog = csoDialogFragment.newInstance(
                    csoName.getText().toString(),
                    csoDistance.getText().toString(),
                    csoPhonenumber.getText().toString(),
                    "Call",
                    "Close"
            );

            singleCsoDialog.show(((AppCompatActivity)view.getContext()).getFragmentManager(), "call");
        }
    }


    public CsoRvAdapter(List<TheCSO> csosList) {
        this.csosList = csosList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cso_list_row, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        TheCSO cso = csosList.get(position);
        holder.csoName.setText(cso.getCso_name());
        holder.csoDistance.setText(cso.getCso_distance());
        holder.csoPhonenumber.setText(cso.getCso_phonenumber());
    }

    @Override
    public int getItemCount() {
        return csosList.size();
    }
}

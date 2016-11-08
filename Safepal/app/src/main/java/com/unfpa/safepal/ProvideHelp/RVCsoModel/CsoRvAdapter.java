package com.unfpa.safepal.ProvideHelp.RVCsoModel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unfpa.safepal.R;

import java.util.List;

/**
 * Created by Kisa on 11/3/2016.
 */

public class CsoRvAdapter extends RecyclerView.Adapter<CsoRvAdapter.CustomViewHolder> {

    private List<TheCSO> csosList;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView csoName, csoDistance;

        public CustomViewHolder(View view) {
            super(view);
            csoName = (TextView) view.findViewById(R.id.cso_name);
            csoDistance = (TextView) view.findViewById(R.id.cso_distance);

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
        holder.csoDistance.setText(cso.getCso_distance()+ " km away from you");
         }

    @Override
    public int getItemCount() {
        return csosList.size();
    }
}

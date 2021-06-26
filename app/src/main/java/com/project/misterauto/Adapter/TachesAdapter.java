package com.project.misterauto.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.project.misterauto.Activity.tacheActivity;
import com.project.misterauto.R;
import com.project.misterauto.model.taches;
import com.project.misterauto.model.users;

import java.util.ArrayList;

public class TachesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<taches> items;
    Context context;


    public TachesAdapter(Context context, ArrayList<taches> items) {
        this.context = context;
        this.items = items;

    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final taches item = items.get(position);
        final TachesAdapter.ViewHolder mHolder = (TachesAdapter.ViewHolder) holder;

        if (item.getTitle() != null)
            mHolder.titre.setText(item.getTitle());


        if (item.getCompleted() != null)
            mHolder.progres.setText(item.getCompleted());



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                      int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tache_item, viewGroup, false);
        return new TachesAdapter.ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView progres,titre;

        public ViewHolder(View convertView) {
            super(convertView);
            progres = convertView.findViewById(R.id.progres);
            titre = convertView.findViewById(R.id.titre);

        }
    }


}
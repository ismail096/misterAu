package com.project.misterauto.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.misterauto.Activity.tacheActivity;
import com.project.misterauto.R;
import com.project.misterauto.model.users;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<users> items;
    Context context;


    public UserAdapter(Context context, ArrayList<users> items) {
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
        final users item = items.get(position);
        final UserAdapter.ViewHolder mHolder = (UserAdapter.ViewHolder) holder;

        if (item.getName() != null)
            mHolder.name.setText(item.getName());


        if (item.getUsername() != null)
            mHolder.username.setText(item.getUsername());


        if (item.getEmail() != null)
            mHolder.mail.setText(item.getEmail());

        mHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, tacheActivity.class);
                intent.putExtra("id", item.getId());
                context.startActivity(intent);
            }
        });


    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                      int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item, viewGroup, false);
        return new UserAdapter.ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,username,mail;

        View container;

        public ViewHolder(View convertView) {
            super(convertView);
            name = convertView.findViewById(R.id.name);
            username = convertView.findViewById(R.id.username);
            mail = convertView.findViewById(R.id.mail);
            container = convertView.findViewById(R.id.user_data);

        }
    }


}
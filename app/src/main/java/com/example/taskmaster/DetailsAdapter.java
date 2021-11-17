package com.example.taskmaster;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {

     List<Details> detailsList = new ArrayList<>();

    public DetailsAdapter(List<Details> detailsList) {
        this.detailsList = detailsList;
    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder{

            public Details details;
            View itemView;
        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;


        }
    }
    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_details, viewGroup , false);

        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, @SuppressLint("RecyclerView") int i) {
        detailsViewHolder.details = detailsList.get(i);
        TextView title = detailsViewHolder.itemView.findViewById(R.id.titleDetails);
        TextView body = detailsViewHolder.itemView.findViewById(R.id.bodyDetails);
        TextView states = detailsViewHolder.itemView.findViewById(R.id.statesDetails);

        title.setText(detailsViewHolder.details.title);
        body.setText(detailsViewHolder.details.body);
        states.setText(detailsViewHolder.details.state);


        detailsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext() , TaskDetailsPage.class);
                intent.putExtra("title", detailsList.get(i).getTitle());
                intent.putExtra("body", detailsList.get(i).getBody());
                intent.putExtra("state", detailsList.get(i).getState());
                view.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return detailsList.size();
    }
}

//package com.example.taskmaster;
//
//import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
//
//    List<Details> allDetailsData = new ArrayList<>();
//    public  TaskAdapter (ArrayList<Details> allDetailsData) {
//        this.allDetailsData = allDetailsData;
//    }
//
//    public class TaskHolder extends RecyclerView.ViewHolder {
//
//        public Details task;
//        View itemView;
//        public TaskHolder(@NonNull View itemView) {
//            super(itemView);
//            this.itemView = itemView;
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d("my Adapter", "Element " + allDetailsData.get(getAdapterPosition()).getBody() + " clicked");
//                    Intent goToDetailsPage = new Intent(view.getContext(), TaskDetailsPage.class);
//                    goToDetailsPage.putExtra("body", allDetailsData.get(getAdapterPosition()).getBody());
//                    goToDetailsPage.putExtra("status", allDetailsData.get(getAdapterPosition()).getState());
//                    view.getContext().startActivity(goToDetailsPage);
//                }
//            });
//
//        }
//    }
//
//
//
//
//
//
//    @NonNull
//    @Override
//    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_details, parent , false);
//
//
//        return  new TaskHolder(view);    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
//        holder.task = allDetailsData.get(position);
//        TextView body = holder.itemView.findViewById(R.id.body);
//        TextView status = holder.itemView.findViewById(R.id.status);
//
//
//        body.setText(holder.task.body);
//        status.setText(holder.task.status);
//    }
//
//    @Override
//    public int getItemCount() {
//        return allDetailsData.size();    }
//
//
//}
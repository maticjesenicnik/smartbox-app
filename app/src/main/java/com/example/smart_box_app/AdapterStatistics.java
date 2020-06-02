package com.example.smart_box_app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.Statistics;

public class AdapterStatistics extends RecyclerView.Adapter<AdapterStatistics.ViewHolder> {
    private MyApplication myApplication;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public AdapterStatistics(MyApplication myApplication){
        this.myApplication = myApplication;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        // Inflating the custom layout
        View view = layoutInflater.inflate(R.layout.rv_row_layout, parent, false);
        // Returning the new holder instance
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStatistics.ViewHolder holder, int position) {
        final Statistics current = myApplication.getStatistics(position);
        holder.boxId.setText(current.getId());
        holder.date.setText(current.getCreated().toString());
        if(current.isSuccessful()){
            holder.success.setText(R.string.success);
            holder.success.setTextColor(Color.GREEN);
        } else {
            holder.success.setText(R.string.failed);
            holder.success.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return myApplication.getListStatistics().getSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView boxId;
        public TextView date;
        public TextView success;
        public View background;

        public ViewHolder(@NonNull final View view){
            super(view);
            boxId = itemView.findViewById(R.id.boxId);
            date = itemView.findViewById(R.id.dateText);
            success = itemView.findViewById(R.id.successText);
            background = itemView.findViewById(R.id.mylayoutrow);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }
}

package com.example.pande.recallsafety.recalls;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pande.recallsafety.R;
import com.example.pande.recallsafety.models.RecallResults;

public class RV4 extends RecyclerView.Adapter<RV4.RecallViewHolder> {

    RecallResults results;
    String category;
    static OnItemClickListener mItemClickListener;

    public static class RecallViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView recallID;

        RecallViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.recall_title);
            recallID = (TextView)itemView.findViewById(R.id.recall_id);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }



    public RV4(RecallResults results, String category){
        this.results = results;
        this.category = category;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RecallViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        RecallViewHolder rvh = new RecallViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RecallViewHolder recallViewHolder, int i) {
        switch (category){
            case "food":
                recallViewHolder.recallID.setText("Recall ID: " + String.format(results.getResults().getmFood().get(i).getRecallId()));
                recallViewHolder.title.setText(results.getResults().getmFood().get(i).getTitle());
                break;
            case "health":
                recallViewHolder.recallID.setText("Recall ID: " + String.format(results.getResults().getmHealth().get(i).getRecallId()));
                recallViewHolder.title.setText(results.getResults().getmHealth().get(i).getTitle());
                break;
            case "vehicle":
                recallViewHolder.recallID.setText("Recall ID: " + String.format(results.getResults().getmVehicles().get(i).getRecallId()));
                recallViewHolder.title.setText(results.getResults().getmVehicles().get(i).getTitle());
                break;
            case "cp":
                recallViewHolder.recallID.setText("Recall ID: " + String.format(results.getResults().getmConsumerProducts().get(i).getRecallId()));
                recallViewHolder.title.setText(results.getResults().getmConsumerProducts().get(i).getTitle());
                break;
            default:
                recallViewHolder.recallID.setText("Error");
                recallViewHolder.title.setText("Error fetching recalls");
                break;
        }

    }

    @Override
    public int getItemCount() {
        switch (category){
            case "food":
                return results.getResults().getmFood().size();
            case "health":
                return results.getResults().getmHealth().size();
            case "vehicle":
                return results.getResults().getmVehicles().size();
            case "cp":
                return results.getResults().getmConsumerProducts().size();
            default:
                return 0;

        }
    }
}

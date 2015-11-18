package com.example.pande.recallsafety.recalls.search;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pande.recallsafety.R;

import com.example.pande.recallsafety.models.SearchResults;

public class RecallSearchRV extends RecyclerView.Adapter<RecallSearchRV.RecallViewHolder> {

    SearchResults results;
    static OnItemClickListener mItemClickListener;

    public static class RecallViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView recallID;

        RecallViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.recall_title);
            recallID = (TextView) itemView.findViewById(R.id.recall_id);

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
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public RecallSearchRV(SearchResults results) {
        this.results = results;
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

        recallViewHolder.recallID.setText("Recall ID: " + String.format(results.getResults().get(i).getRecallId()));
        recallViewHolder.title.setText(results.getResults().get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return results.getResults().size();
    }
}

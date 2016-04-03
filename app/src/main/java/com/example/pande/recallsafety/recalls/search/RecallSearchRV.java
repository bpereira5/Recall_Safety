package com.example.pande.recallsafety.recalls.search;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pande.recallsafety.R;

import com.example.pande.recallsafety.models.SearchResults;

public class RecallSearchRV extends RecyclerView.Adapter<RecallSearchRV.RecallViewHolder> {

    SearchResults results;
    static OnItemClickListener mItemClickListener;

    public static class RecallViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        LinearLayout horizontalBar;
        ImageView icon;

        RecallViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.recall_title);
            horizontalBar = (LinearLayout) itemView.findViewById(R.id.horizontal_bar);
            icon = (ImageView) itemView.findViewById(R.id.recallIcon);
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
        int category = Integer.parseInt(results.getResults().get(i).getCategory().get(0));

        switch (category){
            case 1:
                recallViewHolder.horizontalBar.setBackgroundColor(Color.parseColor("#00E676"));
                recallViewHolder.icon.setImageResource(R.drawable.food);
                recallViewHolder.title.setText(results.getResults().get(i).getTitle());
                break;
            case 3:
                recallViewHolder.horizontalBar.setBackgroundColor(Color.parseColor("#F50057"));
                recallViewHolder.icon.setImageResource(R.drawable.hospital);
                recallViewHolder.title.setText(results.getResults().get(i).getTitle());
                break;
            case 2:
                recallViewHolder.horizontalBar.setBackgroundColor(Color.parseColor("#00B0FF"));
                recallViewHolder.icon.setImageResource(R.drawable.car);
                recallViewHolder.title.setText(results.getResults().get(i).getTitle());
                break;
            case 4:
                recallViewHolder.horizontalBar.setBackgroundColor(Color.parseColor("#E040FB"));
                recallViewHolder.icon.setImageResource(R.drawable.cart_outline);
                recallViewHolder.title.setText(results.getResults().get(i).getTitle());
                break;
            default:
                recallViewHolder.title.setText("Error fetching recalls");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return results.getResults().size();
    }
}

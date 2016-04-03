package com.bpcoding.pande.recallsafety.recalls;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bpcoding.pande.recallsafety.R;
import com.bpcoding.pande.recallsafety.models.RecallResults;

public class RecallRVAdapter extends RecyclerView.Adapter<RecallRVAdapter.RecallViewHolder> {

    RecallResults results;
    String category;
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
        void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }



    public RecallRVAdapter(RecallResults results, String category){
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
                recallViewHolder.horizontalBar.setBackgroundColor(Color.parseColor("#00E676"));
                recallViewHolder.icon.setImageResource(R.drawable.food);
                recallViewHolder.title.setText(results.getResults().getmFood().get(i).getTitle());
                break;
            case "health":
                recallViewHolder.horizontalBar.setBackgroundColor(Color.parseColor("#F50057"));
                recallViewHolder.icon.setImageResource(R.drawable.hospital);
                recallViewHolder.title.setText(results.getResults().getmHealth().get(i).getTitle());
                break;
            case "vehicle":
                recallViewHolder.horizontalBar.setBackgroundColor(Color.parseColor("#00B0FF"));
                recallViewHolder.icon.setImageResource(R.drawable.car);
                recallViewHolder.title.setText(results.getResults().getmVehicles().get(i).getTitle());
                break;
            case "cp":
                recallViewHolder.horizontalBar.setBackgroundColor(Color.parseColor("#E040FB"));
                recallViewHolder.icon.setImageResource(R.drawable.cart_outline);
                recallViewHolder.title.setText(results.getResults().getmConsumerProducts().get(i).getTitle());
                break;
            default:
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


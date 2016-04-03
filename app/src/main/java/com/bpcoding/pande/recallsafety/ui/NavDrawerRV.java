package com.bpcoding.pande.recallsafety.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bpcoding.pande.recallsafety.R;
import com.bpcoding.pande.recallsafety.models.details.TrackedRecall;

import io.realm.RealmResults;

public class NavDrawerRV extends RecyclerView.Adapter<NavDrawerRV.RecallViewHolder> {

    RealmResults<TrackedRecall> recalls;
    static OnItemClickListener mItemClickListener;

    public static class RecallViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;

        RecallViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.trackedTitle);
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


    public NavDrawerRV(RealmResults<TrackedRecall> recalls) {
        this.recalls = recalls;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RecallViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nav_item, viewGroup, false);
        RecallViewHolder rvh = new RecallViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RecallViewHolder recallViewHolder, int i) {
        if(getItemCount() == 0){
            recallViewHolder.title.setText("Not tracking any recalls");
        }else {
            recallViewHolder.title.setText(recalls.get(i).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return recalls.size();
    }
}

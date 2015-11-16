package com.example.pande.recallsafety.recalls.details;


import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pande.recallsafety.R;
import com.example.pande.recallsafety.models.details.Panel;
import com.squareup.picasso.Picasso;


import java.util.List;

public class RecallDetailsRVAdapter extends RecyclerView.Adapter<RecallDetailsRVAdapter.RecallDetailsViewHolder> {

    public static class RecallDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView text;
        LinearLayout layout;


        RecallDetailsViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.panelTitle);
            text = (TextView) itemView.findViewById(R.id.panelText);
            layout = (LinearLayout) itemView.findViewById(R.id.linear);
        }
    }

    List<Panel> panels;
    Activity mActivity;
    boolean imageLoad = false;

    public RecallDetailsRVAdapter(List<Panel> panels, Activity mActivity) {
        this.panels = panels;
        this.mActivity = mActivity;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RecallDetailsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_item, viewGroup, false);
        RecallDetailsViewHolder rvh = new RecallDetailsViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RecallDetailsViewHolder recallViewHolder, int i) {
        if(panels.get(i).getTitle() != null) {
            if(panels.get(i).getTitle().equals("Images") && !imageLoad){
                for (int a = 0; a < panels.get(i).getData().size(); a++) {
                    final int iReplacement = i;
                    final int aReplacement = a;
                    final ImageView imageView = new ImageView(mActivity);
                    imageView.setId(a);
                    imageView.setPadding(2, 2, 2, 2);
                    Picasso.with(mActivity).load("http://healthycanadians.gc.ca" + panels.get(i).getData().get(a).getFullUrl())
                            .error(R.drawable.coffee_beans).into(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

                            final AlertDialog dialog = builder.create();
                            LayoutInflater inflater = mActivity.getLayoutInflater();
                            View dialogLayout = inflater.inflate(R.layout.image_popup, null);
                            dialog.setView(dialogLayout);

                            dialog.show();

                            final ImageView image = (ImageView) dialog.findViewById(R.id.imagePopup);

                            Picasso.with(mActivity).load("http://healthycanadians.gc.ca" + panels.get(iReplacement).getData().get(aReplacement).getFullUrl())
                                    .error(R.drawable.coffee_beans).into(image);

                        }

                    });
                    recallViewHolder.layout.addView(imageView);
                }
            }else{
                recallViewHolder.layout.removeAllViews();
            }

            recallViewHolder.title.setText(panels.get(i).getTitle());
        }
        if(panels.get(i).getText() != null) {
            recallViewHolder.text.setText(stripHtml(panels.get(i).getText()));
        }
    }

    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }

    @Override
    public int getItemCount() {
        return panels.size();
    }
}


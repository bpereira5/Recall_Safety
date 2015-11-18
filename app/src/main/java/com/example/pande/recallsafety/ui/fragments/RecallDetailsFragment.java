package com.example.pande.recallsafety.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pande.recallsafety.R;
import com.example.pande.recallsafety.models.details.Details;
import com.example.pande.recallsafety.models.details.TrackedRecall;
import com.example.pande.recallsafety.recalls.details.IRecallDetailsView;
import com.example.pande.recallsafety.recalls.details.RecallDetailsPresenter;
import com.example.pande.recallsafety.recalls.details.RecallDetailsRVAdapter;

import java.sql.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class RecallDetailsFragment extends DialogFragment implements IRecallDetailsView {

    @Bind(R.id.detailsTitle)
    TextView detailsTitle;
    @Bind(R.id.detailsStartDate)
    TextView detailsStartDate;
    @Bind(R.id.detailsDatePublished)
    TextView detailsDatePublished;
    @Bind(R.id.recallRecycler)
    RecyclerView recallRecycler;
    @Bind(R.id.track_recall)
    ImageView trackRecall;

    private RecallDetailsPresenter presenter;
    private static String recallID;
    private ProgressDialog progress;

    public RecallDetailsFragment() {
    }

    private Details details;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RecallDetailsPresenter(this);
        progress = new ProgressDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recall_details_view, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);

        recallID = getArguments().getString("recall_id");

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recallRecycler.setLayoutManager(llm);
        recallRecycler.setHasFixedSize(true);

        progress.setMessage("Loading Details..");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        presenter.queryDetailsAPI(recallID);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.queryDetailsAPI(recallID);
    }

    @OnClick(R.id.track_recall)
    public void trackRecall() {
        Realm realm = Realm.getInstance(getContext());
        TrackedRecall tracking = new TrackedRecall(recallID, details.getTitle().replaceAll(System.getProperty("line.separator"), ""));

        RealmQuery<TrackedRecall> query = realm.where(TrackedRecall.class);
        query.equalTo("recallID", tracking.getRecallID());
        RealmResults<TrackedRecall> result1 = query.findAll();

        if(result1.size() == 0){
            realm.beginTransaction();
            realm.copyToRealm(tracking);
            realm.commitTransaction();
            Toast.makeText(getActivity(), "Recall saved", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "You are already tracking this recall", Toast.LENGTH_SHORT).show();
        }

        realm.close();
    }

    @Override
    public void onRecallLoadedSuccess(Details details) {
        progress.dismiss();
        this.details = details;

        detailsTitle.setText(stripHtml(details.getTitle()));
        detailsStartDate.setText(DateFormat.getDateFormat(getContext()).format(new Date(details.getStartDate() * 1000)));
        detailsDatePublished.setText(DateFormat.getDateFormat(getContext()).format(new Date(details.getDatePublished() * 1000)));

        RecallDetailsRVAdapter adapter = new RecallDetailsRVAdapter(details.getPanels(), getActivity());
        recallRecycler.setAdapter(adapter);
    }

    @Override
    public void onRecallLoadedFailure(String error) {
        progress.dismiss();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        recallRecycler.setAdapter(null);
    }

    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }
}
package com.bpcoding.pande.recallsafety.ui.fragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bpcoding.pande.recallsafety.R;
import com.bpcoding.pande.recallsafety.models.RecallResults;
import com.bpcoding.pande.recallsafety.recalls.IRecallView;
import com.bpcoding.pande.recallsafety.recalls.RecallPresenter;
import com.bpcoding.pande.recallsafety.recalls.RV2;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecallHealthFragment extends Fragment implements IRecallView {

    public RecallHealthFragment() {}

    @Bind(R.id.recallRecycler)
    RecyclerView healthView;

    private RecallPresenter presenter;
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RecallPresenter(this);
        progress = new ProgressDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recall_base_fragment, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        healthView.setLayoutManager(llm);
        healthView.setHasFixedSize(true);

        progress.setMessage("Loading Recalls..");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        presenter.queryAPI("health");
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.queryAPI("health");
    }

    @Override
    public void onRecallLoadedSuccess(final RecallResults results, String category){
        progress.dismiss();
        RV2 adapter = new RV2(results, category);
        healthView.setAdapter(adapter);
        adapter.SetOnItemClickListener(new RV2.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                RecallDetailsFragment dialogFragment = new RecallDetailsFragment();
                Bundle arg = new Bundle();
                arg.putString("recall_id", results.getResults().getmHealth().get(position).getRecallId());
                dialogFragment.setArguments(arg);
                dialogFragment.show(fm, "Recall Details");
            }
        });
    }

    @Override
    public void onRecallLoadedFailure(String error){
        progress.dismiss();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        //testing
        healthView.setAdapter(null);
    }
}

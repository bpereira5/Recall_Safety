package com.example.pande.recallsafety.ui.fragments;

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

import com.example.pande.recallsafety.R;
import com.example.pande.recallsafety.models.RecallResults;
import com.example.pande.recallsafety.recalls.IRecallView;
import com.example.pande.recallsafety.recalls.RV4;
import com.example.pande.recallsafety.recalls.RecallPresenter;
import com.example.pande.recallsafety.recalls.RecallRVAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecallCPFragment extends Fragment implements IRecallView {

    public RecallCPFragment() {}

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

        presenter.queryAPI("cp");
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.queryAPI("cp");
    }

    @Override
    public void onRecallLoadedSuccess(final RecallResults results, String category){
        progress.dismiss();
        RV4 adapter = new RV4(results, category);
        healthView.setAdapter(adapter);
        adapter.SetOnItemClickListener(new RV4.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                RecallDetailsFragment dialogFragment = new RecallDetailsFragment();
                Bundle arg = new Bundle();
                arg.putString("recall_id", results.getResults().getmConsumerProducts().get(position).getRecallId());
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
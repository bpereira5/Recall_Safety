package com.example.pande.recallsafety.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pande.recallsafety.R;
import com.example.pande.recallsafety.models.SearchResults;
import com.example.pande.recallsafety.recalls.search.IRecallSearchView;
import com.example.pande.recallsafety.recalls.search.RecallSearchPresenter;
import com.example.pande.recallsafety.recalls.search.RecallSearchRV;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RecallSearch extends DialogFragment implements IRecallSearchView {

    private ProgressDialog progress;
    private RecallSearchPresenter presenter;

    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.searchButton)
    Button searchButton;
    @Bind(R.id.searchRecycler)
    RecyclerView searchRecyler;
    @Bind(R.id.searchText)
    TextView searchText;

    public RecallSearch(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress = new ProgressDialog(getContext());
        presenter = new RecallSearchPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_layout, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        searchRecyler.setLayoutManager(llm);
        searchRecyler.setHasFixedSize(true);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.searchCategories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        return view;
    }

    @OnClick(R.id.searchButton)
    public void search(){
        if(searchText.getText().length() > 1) {

            searchText.clearFocus();
            InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(searchText.getWindowToken(), 0);

            progress.setMessage("Loading Searches..");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();

            presenter.querySearchAPI("/recall-alert-rappel-avis/api/search?search=" + searchText.getText().toString() + "&cat=" + spinner.getSelectedItemPosition() + "&lim=20");
        }else{
            Toast.makeText(getActivity(), "Please enter a search term", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRecallLoadedSuccess(final SearchResults results){
        progress.dismiss();
        RecallSearchRV adapter = new RecallSearchRV(results);
        searchRecyler.setAdapter(adapter);
        adapter.SetOnItemClickListener(new RecallSearchRV.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                RecallDetailsFragment dialogFragment = new RecallDetailsFragment();
                Bundle arg = new Bundle();
                arg.putString("recall_id", results.getResults().get(position).getRecallId());
                dialogFragment.setArguments(arg);
                dialogFragment.show(fm, "Recall Details");
            }
        });
    }

    @Override
    public void onRecallLoadedFailure(String error){
        progress.dismiss();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRecalEmpty (final SearchResults results){
        progress.dismiss();
        Toast.makeText(getActivity(), "No matches found", Toast.LENGTH_SHORT).show();

        RecallSearchRV adapter = new RecallSearchRV(results);
        searchRecyler.setAdapter(adapter);

    }
}

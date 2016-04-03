package com.bpcoding.pande.recallsafety.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpcoding.pande.recallsafety.R;
import com.bpcoding.pande.recallsafety.models.SearchResults;
import com.bpcoding.pande.recallsafety.recalls.search.IRecallSearchView;
import com.bpcoding.pande.recallsafety.recalls.search.RecallSearchPresenter;
import com.bpcoding.pande.recallsafety.recalls.search.RecallSearchRV;


/*public class RecallSearch extends DialogFragment implements IRecallSearchView {

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
}*/
//New search implementation including some transitions and basic animation

public class RecallSearch extends DialogFragment implements IRecallSearchView {

    private ProgressDialog progress;
    private RecallSearchPresenter presenter;
    private Scene mSceneSearch;
    private Scene mSceneResults;
    private TextView searchText;
    private Spinner spinner;
    private Button searchButton;
    private ViewGroup mSceneRoot;

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


        mSceneRoot = (ViewGroup) view.findViewById(R.id.scene_root);
        mSceneSearch = Scene.getSceneForLayout(mSceneRoot, R.layout.scene_search, getContext());
        mSceneResults =
                Scene.getSceneForLayout(mSceneRoot, R.layout.scene_results, getContext());

        TransitionManager.go(mSceneSearch);
        sceneSearch();

        return view;
    }

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

        TransitionManager.go(mSceneResults);
        TextView returnSearch = (TextView)mSceneResults.getSceneRoot().findViewById(R.id.returnSearch);
        RecyclerView searchRecycler = (RecyclerView)mSceneResults.getSceneRoot().findViewById(R.id.searchRecycler);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        searchRecycler.setLayoutManager(llm);
        searchRecycler.setHasFixedSize(true);

        returnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.go(mSceneSearch);
                sceneSearch();
            }
        });

        RecallSearchRV adapter = new RecallSearchRV(results);
        searchRecycler.setAdapter(adapter);
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

    public void sceneSearch(){
        searchText =  (TextView)mSceneSearch.getSceneRoot().findViewById(R.id.searchText);
        spinner = (Spinner)mSceneSearch.getSceneRoot().findViewById(R.id.spinner);
        searchButton = (Button)mSceneResults.getSceneRoot().findViewById(R.id.searchButton);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.searchCategories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        //stackoverflow need to find link
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
                    search();
                    return true;
                } else {
                    return false;
                }
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
    }
}
package com.example.pande.recallsafety.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;


import com.example.pande.recallsafety.R;
import com.example.pande.recallsafety.models.details.TrackedRecall;
import com.example.pande.recallsafety.ui.NavDrawerRV;
import com.example.pande.recallsafety.ui.SimpleDividerItemDecoration;
import com.example.pande.recallsafety.ui.fragments.RecallCPFragment;
import com.example.pande.recallsafety.ui.fragments.RecallDetailsFragment;
import com.example.pande.recallsafety.ui.fragments.RecallFoodFragment;
import com.example.pande.recallsafety.ui.fragments.RecallHealthFragment;
import com.example.pande.recallsafety.ui.fragments.RecallSearch;
import com.example.pande.recallsafety.ui.fragments.RecallVehicleFragment;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView mDrawerRV;
    private NavDrawerRV adapter;
    private RealmResults<TrackedRecall> myTrackedRecalls;
    private Realm realm;
    private RealmChangeListener realmListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realm = Realm.getInstance(getApplicationContext());
        realmListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                getMyTrackedRecalls();

                adapter.notifyDataSetChanged();
            }};

        realm.addChangeListener(realmListener);

        getMyTrackedRecalls();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                RecallSearch dialogFragment = new RecallSearch();
                dialogFragment.show(fm, "Search");
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerRV = (RecyclerView) findViewById(R.id.left_drawer);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        mDrawerRV.setLayoutManager(llm);
        mDrawerRV.setHasFixedSize(true);

        adapter = new NavDrawerRV(myTrackedRecalls);
        mDrawerRV.addItemDecoration(new SimpleDividerItemDecoration(this));

        mDrawerRV.setAdapter(adapter);
        adapter.SetOnItemClickListener(new NavDrawerRV.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                FragmentManager fm = getSupportFragmentManager();
                RecallDetailsFragment dialogFragment = new RecallDetailsFragment();
                Bundle arg = new Bundle();
                arg.putString("recall_id", myTrackedRecalls.get(position).getRecallID());
                dialogFragment.setArguments(arg);
                dialogFragment.show(fm, "Recall Details");
            }
        });

        //http://jmcdale.com/201505567/android-recyclerviews-102-swipe-to-dismiss
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                realm.beginTransaction();
                myTrackedRecalls.remove(position);
                realm.commitTransaction();

                adapter.notifyItemRemoved(position);
            }
        });
        itemTouchHelper.attachToRecyclerView(mDrawerRV);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.openDrawer, R.string.closeDrawer) {
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(realmListener);
        realm.close();
    }

    public void getMyTrackedRecalls() {
        myTrackedRecalls = realm.where(TrackedRecall.class).findAll();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecallFoodFragment(), "Food");
        adapter.addFragment(new RecallHealthFragment(), "Health");
        adapter.addFragment(new RecallVehicleFragment(), "Vehicles");
        adapter.addFragment(new RecallCPFragment(), "CP");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

package com.example.bgangadharaiah.recyclerexample;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PackageManager mPackageManager;
    List<ApplicationInfo> mAppList;
    private RecyclerView mRecyclerView;
    private CustomRecyclerAdapter mCustomRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration( new SimpleDividerItemDecoration(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPackageManager = getPackageManager();
        new LoadApplications().execute();
    }



    private class LoadApplications extends AsyncTask<Void, Void, Void>{
        private ProgressDialog mProgress = null;

        @Override
        protected void onPreExecute() {
            mProgress = ProgressDialog.show(MainActivity.this, null,
                    "Loading application info...");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            mAppList = checkForLaunchIntent(mPackageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            mCustomRecyclerAdapter = new CustomRecyclerAdapter(MainActivity.this,mAppList);
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            mRecyclerView.setAdapter(mCustomRecyclerAdapter);
            mProgress.dismiss();
        }
    }

    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != mPackageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return applist;
    }

}

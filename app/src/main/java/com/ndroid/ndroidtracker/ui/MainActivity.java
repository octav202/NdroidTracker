package com.ndroid.ndroidtracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ndroid.ndroidtracker.Constants;
import com.ndroid.ndroidtracker.R;
import com.ndroid.ndroidtracker.server.ServerApi;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG + MainActivity.class.getSimpleName();
    PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ServerApi.setCurrentDeviceId(0);
        ServerApi.setCurrentDeviceStatus(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                LocationFragment fragment = (LocationFragment) mPagerAdapter.getItem(1);
                if (fragment != null) {
                    fragment.refresh();
                }
                break;
            default:
                break;
        }
        return true;
    }
}

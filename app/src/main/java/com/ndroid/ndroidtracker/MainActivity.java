package com.ndroid.ndroidtracker;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;

public class MainActivity extends AppCompatActivity {

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
        Service.setCurrentDeviceId(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int type;
        switch (item.getItemId()) {
            case R.id.type_normal:
                type = GoogleMap.MAP_TYPE_NORMAL;
                break;
            case R.id.type_satellite:
                type = GoogleMap.MAP_TYPE_SATELLITE;
                break;
            case R.id.type_hybrid:
                type = GoogleMap.MAP_TYPE_HYBRID;
                break;
            case R.id.terrain:
                type = GoogleMap.MAP_TYPE_TERRAIN;
                break;
            default:
                type = GoogleMap.MAP_TYPE_NORMAL;
                break;
        }
        LocationFragment fragment = (LocationFragment) mPagerAdapter.getItem(1);
        if (fragment != null) {
            fragment.setMapType(type);
        }
        return true;
    }
}

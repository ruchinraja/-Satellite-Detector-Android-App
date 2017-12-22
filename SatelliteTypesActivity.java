package com.example.android.satellite_finder_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.satellite_finder_app.utilities.SatelliteUtils;

public class SatelliteTypesActivity extends AppCompatActivity
    implements com.example.android.satellite_finder_app.SatelliteTypesAdapter.SatelliteTypesAdapterOnClickHandler{
    private RecyclerView mRecyclerView;
    private com.example.android.satellite_finder_app.SatelliteTypesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satellite_types);

        // For RecyclerView, we need to set in two things,
        // Layout Manager(Layout of items in Grid, Linear List or customized grid)
        // and an Adapter(for setting the data into the Layout).

        mRecyclerView = (RecyclerView) findViewById(R.id.sat_types_rv);

        mRecyclerView.setHasFixedSize(true); //Used for optimizations purpose

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new com.example.android.satellite_finder_app.SatelliteTypesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        setTitle("Satellite Types");

        String[] typesDataset = SatelliteUtils.getAllSatelliteTypeStrings();

        mAdapter.setTypesData(typesDataset);

    }

    /**
     * Handles the event of clicking the listItem
     * @param s
     */
    @Override
    public void onListItemClick(String s) {
        Context context = this;
        Class destinitionClass = com.example.android.satellite_finder_app.TlesActivity.class;
        Intent intentToStartListActivity = new Intent(context, destinitionClass);
        String sat_type_name = s;

        intentToStartListActivity.putExtra(SatelliteUtils.TYPE, SatelliteUtils.getType(s));
        intentToStartListActivity.putExtra("title_name", sat_type_name);
        startActivity(intentToStartListActivity);
    }
}

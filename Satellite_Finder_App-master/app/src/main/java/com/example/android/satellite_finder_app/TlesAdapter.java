package com.example.android.satellite_finder_app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.me.g4dpz.satellite.TLE;

/**
 * Created by spd on 7/9/17.
 * And adapter class for the recycler view
 */

public class TlesAdapter
        extends RecyclerView.Adapter<TlesAdapter.SatelliteListViewHolder> {

    // The data to be displayed
    private TLE[] mSatelliteData;

    // The click handler
    private final TlesAdapterOnClickHandler mClickHandler;

    // The constructor to bind the click handler
    TlesAdapter(TlesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * Interface for handling click events
     */
    interface TlesAdapterOnClickHandler {
        void onClick(TLE selectedSatellite);
    }

    /**
     * Called on creation of the view holder. This is called a limited number of times
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public TlesAdapter.SatelliteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.tle_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new SatelliteListViewHolder(view);
    }

    /**
     * Fills up the view holder with data on the binding of the view holder to recycler view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(TlesAdapter.SatelliteListViewHolder holder, int position) {
        String satelliteName = mSatelliteData[position].getName();
        holder.mTextView.setText(satelliteName);
    }

    /**
     * Used by the layout manager to fill up the recycler view
     * @return
     */
    @Override
    public int getItemCount() {
        if (null == mSatelliteData) return 0;
        return mSatelliteData.length;
    }

    /**
     * A view holder that has the view that is replicated
     */
    public class SatelliteListViewHolder extends RecyclerView.ViewHolder
            implements OnClickListener {
        public final TextView mTextView;

        /**
         * A constructor that takes care of the view holder.
         * Useful for that onClickLister call
         * @param itemView
         */
        public SatelliteListViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_satellite);
            itemView.setOnClickListener(this);
        }

        /**
         * Calls the onClick method of the ClickHandler of the adapter
         * @param view
         */
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            TLE selectedSatellite = mSatelliteData[adapterPosition];
            mClickHandler.onClick(selectedSatellite);
        }
    }

    /**
     * Fills up the satelliteData array
     * @param satelliteData
     */
    public void setSatelliteData(TLE[] satelliteData) {
        mSatelliteData = satelliteData;
        notifyDataSetChanged();
    }
}

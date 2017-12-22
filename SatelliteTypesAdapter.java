package com.example.android.satellite_finder_app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by dbzabhiram on 7/8/17.
 */

public class SatelliteTypesAdapter extends RecyclerView.Adapter<SatelliteTypesAdapter.ViewHolder>{
    private String[] mTypesDataset;

    //Click handler for each List Item
    private final SatelliteTypesAdapterOnClickHandler mClickHandler;

    //Interface to be implemented by each Click Handler
    public interface SatelliteTypesAdapterOnClickHandler {
        void onListItemClick(String s);
    }

    /**
     * Holds the view and listens for the click Event
     */
    public class ViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{
        private final TextView mTextView;


        /**
         * Initialize the viewHolder with the textView and set the onClickListener
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_satellite_type);

            // Set the OnClickListener to the viewHolder
            itemView.setOnClickListener(this);
        }

        /**
         * Passes the control to the click handler on click event
         * @param view
         */
        @Override
        public void onClick(View view) {

            // Get the clicked Number
            int clickedType = getAdapterPosition();

            // Call the clickHandler with the respective String
            mClickHandler.onListItemClick(mTypesDataset[clickedType]);
        }
    }

    /**
     * Initalize the adapter with the clickHandler
     * @param onClickHandler
     */
    public SatelliteTypesAdapter(SatelliteTypesAdapterOnClickHandler onClickHandler) {
        mClickHandler = onClickHandler;
    }


    /**
     * On creating the view holder this method is called.
     * Only used to create once for each viewHolder while initializing
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public SatelliteTypesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.type_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    /**
     * Sets the text on binding the viewHolder to the recycler view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(SatelliteTypesAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(mTypesDataset[position]);
    }

    /**
     * Used by the layoutManager to get the number of items in the list
     * @return
     */
    @Override
    public int getItemCount() {
        return mTypesDataset.length;
    }

    /**
     * Set the data for the recyclerView
     * @param typesData
     */
    public void setTypesData(String[] typesData) {
        mTypesDataset = typesData;
        notifyDataSetChanged();
    }
}

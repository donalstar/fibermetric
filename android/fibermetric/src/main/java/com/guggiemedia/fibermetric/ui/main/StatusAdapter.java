package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.db.PartModel;


/**
 *
 */
public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    public static final String LOG_TAG = StatusAdapter.class.getName();

    public static final String ARG_PARAM_ROW_ID = "PARAM_ROW_ID";
    public static final String ARG_PARAM_ITEM_NAME = "PARAM_ITEM_NAME";

    private MainActivityListener _listener;

    private Context _context;
    private Cursor _cursor;


    public StatusAdapter(Activity activity) {
        _listener = (MainActivityListener) activity;
        _context = activity;
    }

    public void setCursor(Cursor cursor) {
        _cursor = cursor;

    }


    @Override
    public int getItemCount() {
        int count = 0;

        if (_cursor != null) {
            count = _cursor.getCount();
        }

        return count;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        if (_cursor != null) {

            _cursor.moveToPosition(position);

            final PartModel model = new PartModel();
            model.setDefault();
            model.fromCursor(_cursor);

            if (viewHolder.header != null) {


                viewHolder.header.setVisibility(View.GONE);


                viewHolder.header.setText("OK");
            }

            int visibility = View.VISIBLE;

            int imageResourceId = (model.getBleAddress().equals("0"))
                    ? R.drawable.ic_checkmark_green
                    : R.drawable.ic_beacon_blue;

            viewHolder.statusCheck.setImageResource(imageResourceId);

            viewHolder.statusCheck.setVisibility(visibility);

            int partIndicatorResourceId = R.drawable.ic_part_default;


            viewHolder.partIndicator.setImageResource(partIndicatorResourceId);

            viewHolder.itemName.setText(model.getName());
            viewHolder.itemSerialNumber.setText(model.getSerial());

            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();

                    bundle.putLong(ARG_PARAM_ROW_ID, model.getId());

                    bundle.putString(ARG_PARAM_ITEM_NAME, model.getName());

                    _listener.fragmentSelect(MainActivityFragmentEnum.PART_VIEW, bundle);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewId = R.layout.row_todays_inventory;

        View view = LayoutInflater.from(parent.getContext()).inflate(viewId, parent, false);

        return new ViewHolder(view);
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        public final TextView header;
        public final TextView itemName;
        public final TextView itemSerialNumber;
        public final ImageView imageView;
        public final ImageView statusCheck;
        public final ImageView partIndicator;

        public ViewHolder(View arg) {
            super(arg);
            view = arg;

            header = (TextView) view.findViewById(R.id.rowDelimiter);

            itemName = (TextView) view.findViewById(R.id.itemName);
            itemSerialNumber = (TextView) view.findViewById(R.id.itemSerialNumber);

            imageView = (ImageView) view.findViewById(R.id.imageView);

            statusCheck = (ImageView) view.findViewById(R.id.statusCheck);
            partIndicator = (ImageView) view.findViewById(R.id.partIndicator);
        }
    }

}
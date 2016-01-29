package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.db.ItemModel;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;


/**
 *
 */
public class SelectorListAdapter extends RecyclerView.Adapter<SelectorListAdapter.ViewHolder> {
    public static final String LOG_TAG = SelectorListAdapter.class.getName();

    public static final String ARG_PARAM_ROW_ID = "PARAM_ROW_ID";
    public static final String ARG_PARAM_ITEM_NAME = "PARAM_ITEM_NAME";

    private MainActivityListener _listener;

    private Context _context;
    private Cursor _cursor;


    public SelectorListAdapter(Activity activity) {
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

            final ItemModel model = new ItemModel();
            model.setDefault();
            model.fromCursor(_cursor);

            if (viewHolder.header != null) {
                viewHolder.header.setVisibility(View.GONE);
                viewHolder.header.setText("OK");
            }

            int visibility = View.VISIBLE;

            int imageResourceId = R.drawable.ic_checkmark_green;

            viewHolder.statusCheck.setImageResource(imageResourceId);

            viewHolder.statusCheck.setVisibility(visibility);

            int partIndicatorResourceId = R.drawable.ic_grain;

            if (position % 3 == 0) {
                partIndicatorResourceId = R.drawable.ic_vegetable;
            } else if (position % 3 == 1) {
                partIndicatorResourceId = R.drawable.ic_fruit;
            }


            viewHolder.itemIcon.setImageResource(partIndicatorResourceId);

            viewHolder.itemName.setText(model.getName());
            viewHolder.itemPortion.setText(model.getPortion());

            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastHelper.show("Add item " + model.getName(), _context);

                    _listener.fragmentPop();
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewId = R.layout.row_home;

        View view = LayoutInflater.from(parent.getContext()).inflate(viewId, parent, false);

        return new ViewHolder(view);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        public final TextView header;
        public final TextView itemName;
        public final TextView itemPortion;
        public final ImageView statusCheck;
        public final ImageView itemIcon;

        public ViewHolder(View arg) {
            super(arg);
            view = arg;

            header = (TextView) view.findViewById(R.id.rowDelimiter);

            itemName = (TextView) view.findViewById(R.id.itemName);
            itemPortion = (TextView) view.findViewById(R.id.itemPortion);

            statusCheck = (ImageView) view.findViewById(R.id.statusCheck);
            itemIcon = (ImageView) view.findViewById(R.id.itemIcon);
        }
    }

}
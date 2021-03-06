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
import com.guggiemedia.fibermetric.db.AddedItemModel;


/**
 *
 */
public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {

    public static final String LOG_TAG = HomeListAdapter.class.getName();

    public static final String ARG_PARAM_ROW_ID = "PARAM_ROW_ID";
    public static final String ARG_PARAM_ITEM_NAME = "PARAM_ITEM_NAME";

    private MainActivityListener _listener;

    private Context _context;
    private Cursor _cursor;


    public HomeListAdapter(Activity activity) {
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

            final AddedItemModel model = new AddedItemModel();
            model.setDefault();
            model.fromCursor(_cursor);

            viewHolder.id = model.getId();

            if (viewHolder.header != null) {
                viewHolder.header.setVisibility(View.GONE);
                viewHolder.header.setText("OK");
            }

            int partIndicatorResourceId = R.drawable.ic_grain;

            switch (model.getType()) {
                case fruit:
                    partIndicatorResourceId = R.drawable.ic_fruit;
                    break;
                case vegetable:
                    partIndicatorResourceId = R.drawable.ic_vegetable;
                    break;
                case grain:
                    partIndicatorResourceId = R.drawable.ic_grain;
                    break;
            }

            viewHolder.itemIcon.setImageResource(partIndicatorResourceId);
            viewHolder.itemIcon.setImageResource(partIndicatorResourceId);

            viewHolder.itemName.setText(model.getName());
            viewHolder.itemPortion.setText(model.getSelectedPortion());

            Double grams = model.getGrams() * model.getWeightMultiple();

            String gramsValue = String.valueOf(grams) + " g";

            viewHolder.grams.setText(gramsValue);

            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();

                    bundle.putLong(ARG_PARAM_ROW_ID, model.getId());

                    bundle.putString(ARG_PARAM_ITEM_NAME, model.getName());

                    _listener.fragmentSelect(Fragments.STATUS_VIEW, bundle);
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
        public final TextView grams;
        public final ImageView itemIcon;
        public Long id;


        public ViewHolder(View arg) {
            super(arg);
            view = arg;

            header = (TextView) view.findViewById(R.id.rowDelimiter);

            itemName = (TextView) view.findViewById(R.id.itemName);
            itemPortion = (TextView) view.findViewById(R.id.itemPortion);
            grams = (TextView) view.findViewById(R.id.grams);
            itemIcon = (ImageView) view.findViewById(R.id.itemIcon);
        }
    }


}
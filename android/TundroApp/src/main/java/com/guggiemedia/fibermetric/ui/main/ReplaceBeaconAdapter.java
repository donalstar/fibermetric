package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.go_factory.tundro.R;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;

/**
 * Created by donal on 9/30/15.
 */

public class ReplaceBeaconAdapter extends RecyclerView.Adapter<ReplaceBeaconAdapter.ViewHolder> {
    public static final String LOG_TAG = ReplaceBeaconAdapter.class.getName();

    private MainActivityListener _listener;

    private Context _context;
    private Cursor _cursor;

    private ReplaceBeaconFragment _fragment;
    private String _partName;

    public ReplaceBeaconAdapter(Activity activity, ReplaceBeaconFragment fragment, String partName) {
        _listener = (MainActivityListener) activity;
        _context = activity;

        _fragment = fragment;

        _partName = partName;
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
        _cursor.moveToPosition(position);

        String name = _cursor.getString(0);
        final String address = _cursor.getString(1);

        viewHolder.itemName.setText(name);
        viewHolder.itemSerialNumber.setText(address);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastHelper.show("Replaced " + _partName + " beacon with:\n " + address, _context);

                _fragment.replaceBeacon(address);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_replace_beacon, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        public TextView itemSerialNumber;
        public TextView itemName;

        public ViewHolder(View arg) {
            super(arg);
            view = arg;

            itemName = (TextView) view.findViewById(R.id.itemName);
            itemSerialNumber = (TextView) view.findViewById(R.id.itemSerialNumber);
        }
    }
}

package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.ChainOfCustodyModel;
import com.guggiemedia.fibermetric.lib.db.SiteModel;

import net.go_factory.tundro.R;


import java.text.SimpleDateFormat;

/**
 * Created by donal on 9/30/15.
 */

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.ViewHolder> {
    public static final String LOG_TAG = PartAdapter.class.getName();

    private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("K:mm a, dd MMM yyyy");

    private MainActivityListener _listener;

    private Context _context;
    private Cursor _cursor;

    public PartAdapter(Activity activity) {
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
        _cursor.moveToPosition(position);

        ChainOfCustodyModel model = new ChainOfCustodyModel();
        model.setDefault();
        model.fromCursor(_cursor);

        viewHolder.rowId = model.getId();

        String name = model.getCustodian();

        viewHolder.name.setText(name);

        Long siteId = model.getPickupSiteId();

        String locationName;

        if (siteId != 0) {
            SiteModel siteModel = CommandFacade.siteSelectByRowId(model.getPickupSiteId(), _context);

            locationName = siteModel.getName();
        } else {
            Location location = model.getLocation();

            locationName = location.getLatitude() + ", " + location.getLongitude();
        }

        viewHolder.location.setText(locationName);

        String pickedUpDate = DATE_FORMATTER.format(model.getPickupDate());

        viewHolder.pickedUpDate.setText(pickedUpDate);

        int imageResourceId = (position == 0)
                ? R.drawable.ic_chain_current
                : R.drawable.ic_chain_previous;

        viewHolder.statusImage.setImageResource(imageResourceId);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chain_of_custody, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        public Long rowId;

        public TextView name;
        public TextView location;
        public TextView pickedUpDate;
        public ImageView statusImage;

        public ViewHolder(View arg) {
            super(arg);
            view = arg;

            name = (TextView) view.findViewById(R.id.name);
            location = (TextView) view.findViewById(R.id.location);
            pickedUpDate = (TextView) view.findViewById(R.id.pickedUpDate);
            statusImage = (ImageView) view.findViewById(R.id.statusImage);
        }
    }
}

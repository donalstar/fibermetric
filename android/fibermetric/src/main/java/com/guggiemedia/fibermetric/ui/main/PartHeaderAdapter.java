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
import com.guggiemedia.fibermetric.lib.db.InventoryCategoryEnum;
import com.guggiemedia.fibermetric.lib.db.InventoryStatusEnum;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;



/**
 * Created by donal on 9/30/15.
 */

public class PartHeaderAdapter extends RecyclerView.Adapter<PartHeaderAdapter.ViewHolder> {
    public static final String LOG_TAG = PartHeaderAdapter.class.getName();

    private MainActivityListener _listener;

    private Context _context;
    private Cursor _cursor;

    public PartHeaderAdapter(Activity activity) {
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

        final PartModel model = new PartModel();
        model.setDefault();
        model.fromCursor(_cursor);

        viewHolder.rowId = model.getId();

        String name = model.getName();

        viewHolder.partName.setText(name);


        String status = model.getStatus().equals(InventoryStatusEnum.pickUp)
                ? "Pick up" : "In custody";

        viewHolder.status.setText(status);

        int statusImageResourceId = R.drawable.ic_part_default;

        if (model.getCategory().equals(InventoryCategoryEnum.tools)) {
            if (model.getStatus().equals(InventoryStatusEnum.inCustody)) {
                statusImageResourceId = R.drawable.ic_tool_in_custody;
            } else {
                statusImageResourceId = R.drawable.ic_tool_default;
            }
        } else {
            if (model.getStatus().equals(InventoryStatusEnum.inCustody)) {
                statusImageResourceId = R.drawable.ic_part_in_custody;
            }
        }

        viewHolder.statusImage.setImageResource(statusImageResourceId);


        if (model.getBleAddress().equals("0")) {
            viewHolder.beaconIndicator.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.beaconIndicator.setVisibility(View.VISIBLE);

            int beaconImageResourceId = (model.getBleInRange() == 1)
                    ? R.drawable.ic_beacon_blue : R.drawable.ic_beacon_red;

            viewHolder.beaconIndicator.setImageResource(beaconImageResourceId);

            viewHolder.beaconIndicator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = String.format(
                            _context.getResources().getString(R.string.assigned_to_beacon),
                            model.getBleAddress());

                    ToastHelper.show(message, _context);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_part_header, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        public Long rowId;

        public TextView partName;
        public ImageView statusImage;
        public ImageView beaconIndicator;
        public TextView status;

        public ViewHolder(View arg) {
            super(arg);
            view = arg;

            partName = (TextView) view.findViewById(R.id.partName);
            statusImage = (ImageView) view.findViewById(R.id.statusImage);
            beaconIndicator = (ImageView) view.findViewById(R.id.beaconIndicator);
            status = (TextView) view.findViewById(R.id.status);
        }
    }
}


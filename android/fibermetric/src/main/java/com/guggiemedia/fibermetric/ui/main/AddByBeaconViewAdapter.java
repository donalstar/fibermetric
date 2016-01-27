package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.InventoryCategoryEnum;
import com.guggiemedia.fibermetric.lib.db.InventoryStatusEnum;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.lib.db.SiteModel;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;


import java.util.List;

/**
 *
 */
public class AddByBeaconViewAdapter extends RecyclerView.Adapter<AddByBeaconViewAdapter.ViewHolder> {
    public static final String LOG_TAG = AddByBeaconViewAdapter.class.getName();

    private MainActivityListener _listener;

    private Context _context;
    private Cursor _cursor;
    private List<SiteModel> _jobSites;

    public AddByBeaconViewAdapter(Activity activity) {
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

    public void setJobSites(List<SiteModel> jobSites) {
        _jobSites = jobSites;
    }

    /**
     * @param model
     * @return
     */
    private String getJobSiteName(PartModel model) {
        String siteName = null;

        for (SiteModel siteModel : _jobSites) {
            if (siteModel.getId().equals(model.getSiteId())) {
                siteName = siteModel.getName();
                break;
            }
        }

        return siteName;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        if (_cursor != null) {

            _cursor.moveToPosition(position);

            final PartModel model = new PartModel();
            model.setDefault();
            model.fromCursor(_cursor);

            viewHolder.itemName.setText(model.getName());
            viewHolder.itemDescription.setText(getJobSiteName(model) + ", " + model.getSerial());

            int imageId = model.getCategory().equals(InventoryCategoryEnum.tools)
                    ? R.drawable.ic_tool_default
                    : R.drawable.ic_part_default;

            viewHolder.partIndicator.setImageResource(imageId);

            viewHolder.beaconSelect.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    addToCustody(model);
                    ToastHelper.show("Added [" + model.getName() + "] to custody", _context);
                }
            });
        }
    }


    /**
     *
     */
    private void addToCustody(PartModel model) {
        model.setStatus(InventoryStatusEnum.inCustody);

        model.setBleInRange(1);


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewId = R.layout.row_add_by_beacon;

        View view = LayoutInflater.from(parent.getContext()).inflate(viewId, parent, false);

        return new ViewHolder(view);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        public final ImageView partIndicator;
        public final TextView itemName;
        public final TextView itemDescription;

        public final CheckBox beaconSelect;

        public ViewHolder(View arg) {
            super(arg);
            view = arg;

            itemName = (TextView) view.findViewById(R.id.itemName);
            itemDescription = (TextView) view.findViewById(R.id.itemDescription);

            beaconSelect = (CheckBox) view.findViewById(R.id.beaconSelect);

            partIndicator = (ImageView) view.findViewById(R.id.partIndicator);
        }
    }

}
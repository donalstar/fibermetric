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
import com.guggiemedia.fibermetric.lib.db.InventoryCategoryEnum;
import com.guggiemedia.fibermetric.lib.db.InventoryStatusEnum;
import com.guggiemedia.fibermetric.lib.db.PartModel;



import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class InventoryViewAdapter extends RecyclerView.Adapter<InventoryViewAdapter.ViewHolder> {
    public static final String LOG_TAG = InventoryViewAdapter.class.getName();

    private MainActivityListener _listener;

    private Context _context;
    private Cursor _cursor;
    private InventoryCategoryEnum _category;

    private InventoryPagerFragment.ViewType _viewType;
    private Map<InventoryStatusEnum, Integer> _itemCountsByStatus;

    public InventoryViewAdapter(Activity activity, InventoryPagerFragment.ViewType viewType) {
        _listener = (MainActivityListener) activity;
        _context = activity;

        _viewType = viewType;
    }

    public void setCursor(Cursor cursor) {
        _cursor = cursor;

        _itemCountsByStatus = getCountsByStatus();
    }

    public void setCategory(InventoryCategoryEnum category) {
        _category = category;
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
                String status = model.getStatus().equals(InventoryStatusEnum.pickUp)
                        ? "Pick up" : "In custody";

                if (isFirstItemWithStatus(position, model.getStatus())) {
                    viewHolder.header.setVisibility(View.VISIBLE);
                    viewHolder.header.setText(status);
                } else {
                    viewHolder.header.setVisibility(View.GONE);
                }

                status = (isFirstItemWithStatus(position, model.getStatus()))
                        ? status : "";

                viewHolder.header.setText(status);
            }

            int visibility = model.getStatus().equals(InventoryStatusEnum.pickUp)
                    ? View.INVISIBLE : View.VISIBLE;

            int imageResourceId = (model.getBleAddress().equals("0"))
                    ? R.drawable.ic_checkmark_green
                    : R.drawable.ic_beacon_blue;

            viewHolder.statusCheck.setImageResource(imageResourceId);

            viewHolder.statusCheck.setVisibility(visibility);

            int partIndicatorResourceId = model.getStatus().equals(InventoryStatusEnum.pickUp)
                    ? R.drawable.ic_part_default
                    : R.drawable.ic_part_in_custody;

            if (_category.equals(InventoryCategoryEnum.tools)) {
                partIndicatorResourceId = model.getStatus().equals(InventoryStatusEnum.pickUp)
                        ? R.drawable.ic_tool_default
                        : R.drawable.ic_tool_in_custody;
            }

            viewHolder.partIndicator.setImageResource(partIndicatorResourceId);

            viewHolder.itemName.setText(model.getName());
            viewHolder.itemSerialNumber.setText(model.getSerial());

            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();

                    bundle.putLong(PartFragment.ARG_PARAM_ROW_ID, model.getId());

                    bundle.putString(PartFragment.ARG_PARAM_ITEM_NAME, model.getName());

                    MainActivityFragmentEnum parent
                            = _viewType.equals(InventoryPagerFragment.ViewType.todaysInventory)
                            ? MainActivityFragmentEnum.TODAYS_INVENTORY_VIEW
                            : MainActivityFragmentEnum.MY_INVENTORY_VIEW;

                    bundle.putSerializable(PartFragment.ARG_PARAM_PARENT, parent);

                    _listener.fragmentSelect(MainActivityFragmentEnum.PART_VIEW, bundle);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewId = (_viewType.equals(InventoryPagerFragment.ViewType.todaysInventory)) ?
                R.layout.row_todays_inventory : R.layout.row_my_inventory;

        View view = LayoutInflater.from(parent.getContext()).inflate(viewId, parent, false);

        return new ViewHolder(view);
    }

    private boolean isFirstItemWithStatus(int position, InventoryStatusEnum status) {
        Integer pickupItemsCount = _itemCountsByStatus.get(InventoryStatusEnum.pickUp);
        pickupItemsCount = (pickupItemsCount == null) ? 0 : pickupItemsCount;

        int offset = (status.equals(InventoryStatusEnum.pickUp))
                ? 0 : pickupItemsCount;

        return (position == offset);
    }

    /**
     * @return
     */
    private Map<InventoryStatusEnum, Integer> getCountsByStatus() {
        Map<InventoryStatusEnum, Integer> map = new HashMap<>();

        if (_cursor != null) {

            for (int i = 0; i < _cursor.getCount(); i++) {
                _cursor.moveToPosition(i);

                PartModel model = new PartModel();
                model.setDefault();
                model.fromCursor(_cursor);

                InventoryStatusEnum status = model.getStatus();

                Integer count = map.get(status);

                count = (count == null) ? 1 : count + 1;

                map.put(status, count);
            }
        }

        return map;
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
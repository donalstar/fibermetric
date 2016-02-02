package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.db.AddedItemModel;
import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.ItemModel;

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

    private ContentFacade _contentFacade;


    public SelectorListAdapter(Activity activity) {
        _listener = (MainActivityListener) activity;
        _context = activity;

        _contentFacade = new ContentFacade();
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

            viewHolder.itemName.setText(model.getName());

            String gramsValue = String.valueOf(model.getGrams()) + " g";

            viewHolder.grams.setText(gramsValue);

            final String portionTypes[] = new String[3];

            switch (model.getPortionType()) {
                case unit:
                    portionTypes[0] = "1/2 medium";
                    portionTypes[1] = "1 medium";
                    portionTypes[2] = "1 large";
                    break;
                case cup:
                    portionTypes[0] = "1/4 cup";
                    portionTypes[1] = "1/2 cup";
                    portionTypes[2] = "1 cup";
                    break;
                case ounces:
                    portionTypes[0] = "1 ounce";
                    portionTypes[1] = "2 ounces";
                    portionTypes[2] = "4 ounces";
                    break;
            }

            viewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // add
                    AddedItemModel addedItem = new AddedItemModel();
                    addedItem.setDefault();
                    addedItem.setItemId(model.getId());
                    addedItem.setSelectedPortion(portionTypes[viewHolder.portionPicker.getValue()]);

                    double weightMultiple = getWeightMultiple(viewHolder.portionPicker.getValue());

                    addedItem.setWeightMultiple(weightMultiple);

                    _contentFacade.updateAddedItem(addedItem, _context);

                    _listener.fragmentPop();
                }
            });


            viewHolder.portionPicker.setMinValue(0);
            viewHolder.portionPicker.setMaxValue(portionTypes.length - 1);
            viewHolder.portionPicker.setDisplayedValues(portionTypes);

            NumberPicker.OnValueChangeListener myValueChangedListener = new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    double weightMultiple = getWeightMultiple(newVal);

                    Double grams = model.getGrams() * weightMultiple;

                    String gramsValue = String.valueOf(grams) + " g";

                    viewHolder.grams.setText(gramsValue);
                }
            };

            viewHolder.portionPicker.setOnValueChangedListener(myValueChangedListener);
        }
    }

    /**
     * @param portionTypeIndex
     * @return
     */
    private Double getWeightMultiple(int portionTypeIndex) {
        double weightMultiple = 1.0;

        switch (portionTypeIndex) {
            case 0:
                weightMultiple = 0.5;
                break;
            case 1:
                weightMultiple = 1.0;
                break;
            case 2:
                weightMultiple = 2.0;
                break;
        }

        return weightMultiple;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewId = R.layout.row_food_selector;

        View view = LayoutInflater.from(parent.getContext()).inflate(viewId, parent, false);

        return new ViewHolder(view);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        public final TextView header;
        public final TextView itemName;
        public final TextView grams;
        public final ImageView itemIcon;
        public final RelativeLayout mainLayout;
        public final NumberPicker portionPicker;

        public ViewHolder(View arg) {
            super(arg);
            view = arg;

            header = (TextView) view.findViewById(R.id.rowDelimiter);

            mainLayout = (RelativeLayout) view.findViewById(R.id.mainLayout);

            itemName = (TextView) view.findViewById(R.id.itemName);
            grams = (TextView) view.findViewById(R.id.grams);
            itemIcon = (ImageView) view.findViewById(R.id.itemIcon);

            portionPicker = (NumberPicker) view.findViewById(R.id.numberPicker1);
        }
    }

}
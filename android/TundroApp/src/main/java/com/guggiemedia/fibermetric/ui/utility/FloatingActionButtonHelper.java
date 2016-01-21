package com.guggiemedia.fibermetric.ui.utility;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.guggiemedia.fibermetric.ui.component.FloatingActionMenu;
import com.guggiemedia.fibermetric.ui.component.SubActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donal on 10/12/15.
 */
public class FloatingActionButtonHelper {

    private List<ButtonClickListener> listeners;

    public FloatingActionButtonHelper() {
        listeners = new ArrayList<>();
    }

    public FloatingActionMenu createFloatingActionButtonMenu(
            int fabMenuButtonImages[],
            int fabMenuButtonLabels[], Context context,
            ViewGroup parent, android.support.design.widget.FloatingActionButton fab) {

        FloatingActionMenu inventoryFabMenu = null;

        if (fabMenuButtonImages != null) {
            // Build the menu with default options: light theme, 90 degrees, 72dp radius.
            FloatingActionMenu.Builder menuBuilder = new FloatingActionMenu.Builder(context);

            menuBuilder.setParentViewGroup(parent);

            for (int subActionButtonImage : fabMenuButtonImages) {
                ImageView imageView = new ImageView(context);

                imageView.setImageDrawable(context.getResources().getDrawable(subActionButtonImage));

                SubActionButton button = new SubActionButton(context, imageView);

                button.setId(subActionButtonImage);

                menuBuilder.addSubActionView(button);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (ButtonClickListener listener : listeners) {
                            SubActionButton button = (SubActionButton) view;

                            listener.handleButtonClick(button.getId());
                        }
                    }
                });
            }

            for (int labelId : fabMenuButtonLabels) {
                menuBuilder.addSubActionLabel(labelId, context);
            }

            inventoryFabMenu = menuBuilder.attachTo(fab).build();
        }

        return inventoryFabMenu;
    }

    public void addOnButtonClickListener(ButtonClickListener listener) {
        listeners.add(listener);
    }

    public interface ButtonClickListener {
        void handleButtonClick(int imageId);
    }
}

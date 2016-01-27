package com.guggiemedia.fibermetric.ui.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.guggiemedia.fibermetric.R;

/**
 * Created by donal on 9/29/15.
 */
public class SubActionButton extends FrameLayout {

    public SubActionButton(Context context, View contentView) {
        super(context);

        // Default SubActionButton settings
        int size = context.getResources().getDimensionPixelSize(R.dimen.sub_action_button_size);
        LayoutParams params = new LayoutParams(size, size, Gravity.TOP | Gravity.LEFT);
        setLayoutParams(params);

        Drawable backgroundDrawable = context.getResources().getDrawable(R.drawable.button_sub_action_selector);

        setBackgroundResource(backgroundDrawable);
        if (contentView != null) {
            setContentView(contentView);
        }
        setClickable(true);
    }

    /**
     * Sets a content view with custom LayoutParams that will be displayed inside this SubActionButton.
     *
     * @param contentView
     */
    public void setContentView(View contentView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        final int margin = getResources().getDimensionPixelSize(R.dimen.sub_action_button_content_margin);
        params.setMargins(margin, margin, margin, margin);

        contentView.setClickable(false);
        this.addView(contentView, params);
    }

    private void setBackgroundResource(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }
}

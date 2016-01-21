package com.guggiemedia.fibermetric.ui.component.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Point;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.guggiemedia.fibermetric.ui.component.FloatingActionMenu;

/**
 * Created by donal on 9/29/15.
 */
public class DefaultAnimationHandler extends MenuAnimationHandler {

    /**
     * duration of animations, in milliseconds
     */
    protected static final int DURATION = 500;
    /**
     * duration to wait between each of
     */
    protected static final int LAG_BETWEEN_ITEMS = 20;
    /**
     * holds the current state of animation
     */
    private boolean animating;

    public DefaultAnimationHandler() {
        setAnimating(false);
    }

    @Override
    public void animateMenuOpening(Point center) {
        super.animateMenuOpening(center);

        setAnimating(true);

        Animator lastAnimation = null;
        for (int i = 0; i < menu.getSubActionItems().size(); i++) {

            menu.getSubActionItems().get(i).view.setScaleX(0);
            menu.getSubActionItems().get(i).view.setScaleY(0);
            menu.getSubActionItems().get(i).view.setAlpha(0);

            menu.getSubActionLabels().get(i).setScaleX(0);
            menu.getSubActionLabels().get(i).setScaleY(0);
            menu.getSubActionLabels().get(i).setAlpha(0);

            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, menu.getSubActionItems().get(i).x - center.x + menu.getSubActionItems().get(i).width / 2);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, menu.getSubActionItems().get(i).y - center.y + menu.getSubActionItems().get(i).height / 2);
            PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 720);
            PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1);
            PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1);
            PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 1);

            final ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(
                    menu.getSubActionItems().get(i).view, pvhX, pvhY, pvhR, pvhsX, pvhsY, pvhA);
            animation.setDuration(DURATION);
            animation.setInterpolator(new OvershootInterpolator(0.9f));
            animation.addListener(new SubActionItemAnimationListener(
                    menu.getSubActionItems().get(i), menu.getSubActionLabels().get(i), ActionType.OPENING));

            if (i == 0) {
                lastAnimation = animation;
            }

            // Put a slight lag between each of the menu items to make it asymmetric
            animation.setStartDelay((menu.getSubActionItems().size() - i) * LAG_BETWEEN_ITEMS);
            animation.start();

            lastAnimation = animateLabel(i, lastAnimation);
        }
        if (lastAnimation != null) {
            lastAnimation.addListener(new LastAnimationListener());
        }

    }

    private Animator animateLabel(int i, Animator lastAnimation) {
        PropertyValuesHolder pvhX2 = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0);
        PropertyValuesHolder pvhY2 = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0);
        PropertyValuesHolder pvhR2 = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
        PropertyValuesHolder pvhsX2 = PropertyValuesHolder.ofFloat(View.SCALE_X, 1);
        PropertyValuesHolder pvhsY2 = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1);
        PropertyValuesHolder pvhA2 = PropertyValuesHolder.ofFloat(View.ALPHA, 1);
        final ObjectAnimator animation2 = ObjectAnimator.ofPropertyValuesHolder(
                menu.getSubActionLabels().get(i), pvhX2, pvhY2, pvhR2, pvhsX2, pvhsY2, pvhA2);
        animation2.setDuration(DURATION);
        animation2.setInterpolator(new OvershootInterpolator(0.9f));
        animation2.addListener(new SubActionItemAnimationListener(
                menu.getSubActionItems().get(i), menu.getSubActionLabels().get(i), ActionType.OPENING));

        if (i == 0) {
            lastAnimation = animation2;
        }

        // Put a slight lag between each of the menu items to make it asymmetric
        animation2.setStartDelay((menu.getSubActionItems().size() - i) * LAG_BETWEEN_ITEMS);
        animation2.start();

        return lastAnimation;
    }

    @Override
    public void animateMenuClosing(Point center) {
        super.animateMenuOpening(center);

        setAnimating(true);

        Animator lastAnimation = null;
        for (int i = 0; i < menu.getSubActionItems().size(); i++) {
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -(menu.getSubActionItems().get(i).x - center.x + menu.getSubActionItems().get(i).width / 2));
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -(menu.getSubActionItems().get(i).y - center.y + menu.getSubActionItems().get(i).height / 2));
            PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, -720);
            PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0);
            PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0);
            PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 0);

            final ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(menu.getSubActionItems().get(i).view, pvhX, pvhY, pvhR, pvhsX, pvhsY, pvhA);
            animation.setDuration(DURATION);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            animation.addListener(new SubActionItemAnimationListener(menu.getSubActionItems().get(i),
                    menu.getSubActionLabels().get(i), ActionType.CLOSING));

            if (i == 0) {
                lastAnimation = animation;
            }

            animation.setStartDelay((menu.getSubActionItems().size() - i) * LAG_BETWEEN_ITEMS);
            animation.start();
        }
        if (lastAnimation != null) {
            lastAnimation.addListener(new LastAnimationListener());
        }
    }

    @Override
    public boolean isAnimating() {
        return animating;
    }

    @Override
    protected void setAnimating(boolean animating) {
        this.animating = animating;
    }

    protected class SubActionItemAnimationListener implements Animator.AnimatorListener {

        private FloatingActionMenu.Item subActionItem;
        private View subActionLabel;
        private ActionType actionType;

        public SubActionItemAnimationListener(FloatingActionMenu.Item subActionItem, View subActionLabel, ActionType actionType) {
            this.subActionItem = subActionItem;
            this.subActionLabel = subActionLabel;
            this.actionType = actionType;
        }

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            restoreSubActionViewAfterAnimation(subActionItem, subActionLabel, actionType);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            restoreSubActionViewAfterAnimation(subActionItem, subActionLabel, actionType);
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }
}



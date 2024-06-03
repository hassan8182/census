package com.census.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * Created by RV on 06/09/17.
 */

public final class ViewAnimationUtils {

    private ViewAnimationUtils() {
        // This class is not publicly instantiable
    }

    public static void scaleAnimateView(View view) {
        ScaleAnimation animation =
                new ScaleAnimation(
                        1.15f, 1, 1.15f, 1,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);

        view.setAnimation(animation);
        animation.setDuration(100);
        animation.start();
    }

    public static void HideViewsWithAnimation(int id, View view, int duration) {
        view.animate().translationY(view.getId() == id ? view.getHeight() : -view.getHeight()).setDuration(duration);
    }

    public static void ShowViewsWithAnimation(View view, int duration) {
        view.animate().translationY(0).setDuration(duration);
    }
}

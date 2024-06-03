package com.census.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import java.util.HashMap;

public class KeyboardUtils implements ViewTreeObserver.OnGlobalLayoutListener {
    private final static int MAGIC_NUMBER = 200;
    private static HashMap<SoftKeyboardToggleListener, KeyboardUtils> sListenerMap = new HashMap<>();
    private SoftKeyboardToggleListener mCallback;
    private View mRootView;
    private Boolean prevValue = null;
    private float mScreenDensity = 1;

    public KeyboardUtils(Activity act, SoftKeyboardToggleListener listener) {
        mCallback = listener;

        mRootView = ((ViewGroup) act.findViewById(android.R.id.content)).getChildAt(0);
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);

        mScreenDensity = act.getResources().getDisplayMetrics().density;
    }

    /**
     * Add a new keyboard listener
     *
     * @param act      calling activity
     * @param listener callback
     */
    public static void addKeyboardToggleListener(Activity act, SoftKeyboardToggleListener listener) {
        removeKeyboardToggleListener(listener);

        sListenerMap.put(listener, new KeyboardUtils(act, listener));
    }

    /**
     * Remove a registered listener
     *
     * @param listener {@link SoftKeyboardToggleListener}
     */
    public static void removeKeyboardToggleListener(SoftKeyboardToggleListener listener) {
        if (sListenerMap.containsKey(listener)) {
            KeyboardUtils k = sListenerMap.get(listener);
            k.removeListener();

            sListenerMap.remove(listener);
        }
    }

    /**
     * Remove all registered keyboard listeners
     */
    public static void removeAllKeyboardToggleListeners() {
        for (SoftKeyboardToggleListener l : sListenerMap.keySet())
            sListenerMap.get(l).removeListener();

        sListenerMap.clear();
    }

    /**
     * Manually toggle soft keyboard visibility
     *
     * @param view calling view
     */
    public static void toggleKeyboardVisibility(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, 0);
    }

    /**
     * Force closes the soft keyboard
     *
     * @param activeView the view with the keyboard focus
     */
    public static void forceCloseKeyboard(View activeView) {
        if (activeView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activeView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activeView.getWindowToken(), 0);
        }
    }

    public static void openSoftKeyboard(Context cxt) {
        InputMethodManager imm = (InputMethodManager) cxt.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideSoftKeyboard(Context cxt) {
        InputMethodManager imm = (InputMethodManager) cxt.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }


    @Override
    public void onGlobalLayout() {
        if (mRootView == null)
            return;

        Rect r = new Rect();
        mRootView.getWindowVisibleDisplayFrame(r);

        int mainHeightDiff = mRootView.getRootView().getHeight() - (r.bottom - r.top);
        // int heightDiff_1 = mRootView.getRootView().getHeight() - mRootView.getHeight();
        // int heightDiff =  mRootView.getHeight() - (r.bottom - r.top);
        int heightDiff = mRootView.getHeight() - r.bottom;
        float dp = mainHeightDiff / mScreenDensity;
        boolean isVisible = dp > MAGIC_NUMBER;
        /*Log.d("usm_keyboard_utils", "heightDiff= " + heightDiff + " ,bottom= " + r.bottom + " ,top= " + r.top
                + " ,dp= " + dp + " ,isVisible= " + isVisible
                //+ " ,heightDiff_1= " + heightDiff_1
                + " ,rootHeight= " + mRootView.getRootView().getHeight()
                + " ,height= " + mRootView.getHeight()
        );*/

        if (mCallback != null && (prevValue == null || isVisible != prevValue)) {
            prevValue = isVisible;
            mCallback.onToggleSoftKeyboard(isVisible, heightDiff);
            // mCallback.onToggleSoftKeyboard(isVisible, heightDiff - heightDiff_1);
        }

    }

    public void removeListener() {
        try {
            mCallback = null;

            mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            mRootView = null;
        } catch (Exception ignore) {
        }
    }

    public interface SoftKeyboardToggleListener {
        void onToggleSoftKeyboard(boolean isVisible, int height);
    }
}
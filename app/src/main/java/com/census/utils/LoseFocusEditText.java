package com.census.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.appcompat.widget.AppCompatEditText;

public class LoseFocusEditText extends AppCompatEditText {

    protected final String TAG = getClass().getName();
    private OnKeyDownListener mKeyDownListener;

    public LoseFocusEditText(Context context) {
        super(context);
    }

    public LoseFocusEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoseFocusEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {

        return mKeyDownListener != null && mKeyDownListener.onKeyDown(keyCode, event);
    }

    public void setKeyDownListener(OnKeyDownListener keyDownListener) {
        mKeyDownListener = keyDownListener;
    }

    public interface OnKeyDownListener {
        boolean onKeyDown(int keyCode, KeyEvent event);
    }
}
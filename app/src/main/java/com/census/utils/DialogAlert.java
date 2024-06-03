package com.census.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.census.R;


public class DialogAlert extends AlertDialog.Builder {

    private Context context;
    private AlertDialog.Builder builder;
    private String title;
    private String message;
    private Object object;

    protected DialogAlert(@NonNull Context context) {
//        super(context);
        super(context, R.style.AlertTheme);

//        builder = new AlertDialog.Builder(context/*, android.R.style.Theme_Material_Dialog_Alert*/);
        builder = new AlertDialog.Builder(context, R.style.AlertTheme);
//        setCancelable(true);
    }

    protected DialogAlert(@NonNull Context context, String title, String message, String positiveMsg,
                          String negativeMsg, onDialogClick onDialogClick, boolean singleButton) {
//        super(context);
        super(context, R.style.AlertTheme);

//        builder = new AlertDialog.Builder(context/*, android.R.style.Theme_Material_Dialog_Alert*/);
        builder = new AlertDialog.Builder(context, R.style.AlertTheme);
//        setCancelable(true);
        setTitle(title);
        setMessage(message);
        setPositiveButton(positiveMsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (onDialogClick != null)
                    onDialogClick.onOptionSelect(dialogInterface, positiveMsg);
            }
        });
        if (!singleButton)
            setNegativeButton(negativeMsg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onDialogClick.onOptionSelect(dialogInterface, negativeMsg);
                }
            });
    }

    public static DialogAlert getInstance(Context context) {
        return new DialogAlert(context);
    }

    public static DialogAlert getInstance(Context context, String title, String message,
                                          String positiveMsg, String negativeMsg, onDialogClick onDialogClick, boolean singleButton) {
        return new DialogAlert(context, title, message, positiveMsg, negativeMsg, onDialogClick, singleButton);
    }

    public static DialogAlert getInstance(Context context, String title, String message,
                                          String positiveMsg, String negativeMsg, onDialogClick onDialogClick) {
        return new DialogAlert(context, title, message, positiveMsg, negativeMsg, onDialogClick, false);
    }

    @Override
    public AlertDialog show() {
        return super.show();
    }

    public interface onDialogClick {
        void onOptionSelect(DialogInterface dialog, String msg);
    }
}

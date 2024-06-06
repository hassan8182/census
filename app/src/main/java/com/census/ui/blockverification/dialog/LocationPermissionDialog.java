package com.census.ui.blockverification.dialog;





import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.census.R;
import com.census.databinding.LayoutLocationPermissionBinding;


public class LocationPermissionDialog extends DialogFragment {
    private static final String TAG = LocationPermissionDialog.class.getSimpleName();

    LayoutLocationPermissionBinding binding;
    BasicDialogInterface listener;

    Boolean hideCloseIcon = false;
    Boolean isCancelable = true;

    public static LocationPermissionDialog newInstance() {
        return new LocationPermissionDialog();
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    public void setDialogListener(BasicDialogInterface listener) {
        this.listener = listener;
    }


    public void setHideCloseIcon(Boolean hideCloseIcon) {
        this.hideCloseIcon = hideCloseIcon;
    }

    public void setIsCancelable(Boolean isCancelable) {
        this.isCancelable = isCancelable;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final Dialog dialog = new Dialog(getContext(), R.style.DialogFragment);
        dialog.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> !isCancelable);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.setCanceledOnTouchOutside(isCancelable);
        dialog.setCancelable(isCancelable);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_location_permission, container, false);
        View view = binding.getRoot();

        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(false);

//            getDialog().setOnKeyListener(keyListener);
        }
        binding.tvSubTitle.setMovementMethod(ScrollingMovementMethod.getInstance());
        binding.tvSubTitle.setText("Weather requires the Location permission to provide you local weather information");


        binding.ivClose.setOnClickListener(view1 -> {
            if (listener != null)

            dismiss();
        });

        binding.btnOpenSetting.setOnClickListener(view1 -> {
            if (listener != null)
                listener.onOpenSettingButtonClicked();
            dismiss();
        });



        if (hideCloseIcon)
            binding.ivClose.setVisibility(View.GONE);
        else
            binding.ivClose.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
//        super.onCancel(dialog);
//        if (listener != null) {
//            listener.onCloseDialog();
//        }
    }


    public interface BasicDialogInterface {
        void onOpenSettingButtonClicked();

    }

}







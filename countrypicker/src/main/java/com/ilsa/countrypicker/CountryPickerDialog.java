package com.ilsa.countrypicker;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ilsa.countrypicker.adapters.CountriesAdapter;
import com.ilsa.countrypicker.adapters.IndexesAdapter;
import com.ilsa.countrypicker.interfaces.OnCountryPickerListener;
import com.ilsa.countrypicker.interfaces.OnItemClickListener;
import com.ilsa.countrypicker.models.Country;
import com.ilsa.countrypicker.widgets.IndexesRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class CountryPickerDialog extends DialogFragment implements OnItemClickListener {

    // region Variables
    private CountryPickerDialogInteractionListener dialogInteractionListener;
    private EditText searchEditText;
    private ImageView backIv;
    private RecyclerView countriesRecyclerView;
    private CountriesAdapter countriesAdapter;
    private List<Country> searchResults;
    private OnCountryPickerListener listener;
    private IndexesRecyclerView indexes_rv;
    private IndexesAdapter indexesAdapter;
    // endregion

    // region Constructors
    public static CountryPickerDialog newInstance() {
        return new CountryPickerDialog();
    }
    // endregion

    // region Lifecycle
    @Override
    public void onStart() {
        super.onStart();
        setDialogSize();
        setEnterOrExitAnimation(0f, 1f);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_country_picker, null);
        getDialog().setTitle(R.string.country_picker_header);
        searchEditText = view.findViewById(R.id.country_code_picker_search);
        backIv = view.findViewById(R.id.back_iv);
        countriesRecyclerView = view.findViewById(R.id.countries_recycler_view);
        indexes_rv = view.findViewById(R.id.rv_index);
        setupRecyclerView();
        if (!dialogInteractionListener.canSearch()) {
            searchEditText.setVisibility(View.GONE);
        }

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        addSearchTextChangeListener();

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (listener != null)
            listener.onDismiss();
    }

    private void addSearchTextChangeListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable searchQuery) {
                search(searchQuery.toString());
            }
        });
    }


    @Override
    public void onItemClicked(Object object) {
        if (object instanceof Country && listener != null) {
            Country country = (Country) object;
            listener.onSelectCountry(country);
            dismiss();
        } else if (object instanceof String && indexesAdapter.getMapIndex().containsKey(object)) {
            String key = (String) object;
            int index = indexesAdapter.getMapIndex().get(key.toUpperCase());
            // countriesRecyclerView.scrollToPosition(index);
            ((LinearLayoutManager) countriesRecyclerView.getLayoutManager()).scrollToPositionWithOffset(index, 0);
        }
    }
    // endregion

    // region Setter Methods
    public void setCountryPickerListener(OnCountryPickerListener listener) {
        this.listener = listener;
    }

    public void setDialogInteractionListener(
            CountryPickerDialogInteractionListener dialogInteractionListener) {
        this.dialogInteractionListener = dialogInteractionListener;
    }
    // endregion

    // region Utility Methods


    /**
     * This function is used to set width and height of dialog
     */
    private void setDialogSize() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(width, height);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }

    private void setEnterOrExitAnimation(float start, float end) {
        if (getDialog().getWindow() != null) {
            final View decorView = getDialog()
                    .getWindow()
                    .getDecorView();

            ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(decorView,
                    PropertyValuesHolder.ofFloat("scaleX", start, end),
                    PropertyValuesHolder.ofFloat("scaleY", start, end),
                    PropertyValuesHolder.ofFloat("alpha", start, end));
            scaleDown.setDuration(500);
            scaleDown.start();
        }
    }

    private void search(String searchQuery) {
        searchResults.clear();
        for (Country country : dialogInteractionListener.getAllCountries()) {
            //if (country.getName().toLowerCase(Locale.ENGLISH).contains(searchQuery.toLowerCase()))
            if (country.getName().toLowerCase(Locale.ENGLISH).contains(searchQuery.toLowerCase())
                    || country.getDialCode().contains(searchQuery.toLowerCase())
            ) {
                searchResults.add(country);
            }
        }
        dialogInteractionListener.sortCountries(searchResults);
        countriesAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {

        searchResults = new ArrayList<>();
        searchResults.addAll(dialogInteractionListener.getAllCountries());


        countriesAdapter = new CountriesAdapter(getActivity(), searchResults, this);
        countriesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        countriesRecyclerView.setLayoutManager(layoutManager);
        countriesRecyclerView.setAdapter(countriesAdapter);
        //FastScrollRecyclerViewItemDecoration decoration = new FastScrollRecyclerViewItemDecoration(getContext());
        //countriesRecyclerView.addItemDecoration(decoration);
        countriesRecyclerView.setItemAnimator(new DefaultItemAnimator());


        setupIndexesRecyclerView();

    }

    private void setupIndexesRecyclerView() {
        HashMap<String, Integer> mapIndex = calculateIndexesForName(searchResults);

        indexesAdapter = new IndexesAdapter(getActivity(), mapIndex, this);
        indexes_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        indexes_rv.setLayoutManager(layoutManager);
        indexes_rv.setAdapter(indexesAdapter);
        indexes_rv.setItemAnimator(new DefaultItemAnimator());
        indexes_rv.setListingRecyclerView(countriesRecyclerView);

    }

    private HashMap<String, Integer> calculateIndexesForName(List<Country> items) {
        HashMap<String, Integer> mapIndex = new LinkedHashMap<>();
        for (int i = 0; i < items.size(); i++) {
            String name = items.get(i).getName();
            String index = name.substring(0, 1);
            index = index.toUpperCase();

            if (!mapIndex.containsKey(index)) {
                mapIndex.put(index, i);
            }
        }
        return mapIndex;
    }

    // endregion

    //region Interface
    public interface CountryPickerDialogInteractionListener {
        List<Country> getAllCountries();

        void sortCountries(List<Country> searchResults);

        boolean canSearch();
    }
    // endregion
}

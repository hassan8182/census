package com.ilsa.countrypicker.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ilsa.countrypicker.R;
import com.ilsa.countrypicker.interfaces.FastScrollRecyclerViewInterface;
import com.ilsa.countrypicker.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IndexesAdapter extends RecyclerView.Adapter<IndexesAdapter.ViewHolder> implements FastScrollRecyclerViewInterface {

    // region Variables
    private List<String> indexes;
    private Context context;
    private OnItemClickListener listener;
    private HashMap<String, Integer> mMapIndex;
    // endregion

    //region Constructor
    public IndexesAdapter(Context context, HashMap<String, Integer> mapIndex, OnItemClickListener listener) {
        this.context = context;
        this.indexes = new ArrayList<>(mapIndex.keySet());
        this.mMapIndex = mapIndex;
        this.listener = listener;
    }

    public void setMapIndexes(HashMap<String, Integer> mapIndex) {
        this.indexes.clear();
        this.indexes.addAll(mapIndex.keySet());
        //= new ArrayList<>(mapIndex.keySet());
        this.mMapIndex = mapIndex;
        notifyDataSetChanged();
    }
    // endregion

    // region Adapter Methods
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvIndex.setText(indexes.get(position));

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(indexes.get(position));
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return indexes.size();
    }

    public String getItemAtPosition(int position) {
        return indexes.get(position);
    }

    @Override
    public HashMap<String, Integer> getMapIndex() {
        return this.mMapIndex;
    }

    // region ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIndex;

        ViewHolder(View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tv_index);
        }
    }
    // endregion
}

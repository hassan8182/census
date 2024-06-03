package com.ilsa.countrypicker.interfaces;

import java.util.HashMap;

public interface FastScrollRecyclerViewInterface {
    HashMap<String, Integer> getMapIndex();

    String getItemAtPosition(int position);
}

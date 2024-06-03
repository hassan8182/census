package com.census.data.local.db.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.census.data.local.db.PlaceUpload;

public class PlaceUploadTypeConverter {

    @TypeConverter
    public static PlaceUpload toPlace(String value) {
        return value == null ? null : new Gson().fromJson(value, PlaceUpload.class);
    }

    @TypeConverter
    public static String toString(PlaceUpload data) {
        return data == null ? null : new Gson().toJson(data);
    }
}
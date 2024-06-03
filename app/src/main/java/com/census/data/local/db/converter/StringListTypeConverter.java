package com.census.data.local.db.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class StringListTypeConverter {

    @TypeConverter
    public static List<String> toStringList(String value) {
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        return value == null ? null : new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String toString(List<String> stringList) {
        return stringList == null ? null : new Gson().toJson(stringList);
    }
}
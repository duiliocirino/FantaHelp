package com.example.fantahelp.model.utils;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class FloatConverter {
    @TypeConverter
    public List<Float> gettingListFromString(String genreIds) {
        List<Float> list = new ArrayList<>();

        String[] array = genreIds.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(Float.parseFloat(s));
            }
            else
                list.add(null);
        }
        return list;
    }

    @TypeConverter
    public String writingStringFromList(List<Float> list) {
        String genreIds = "";
        if(list != null) {
            for (Float i : list) {
                if(genreIds.isEmpty() && i != null)
                    genreIds += i;
                else if (i != null)
                    genreIds += "," + i;
                else
                    genreIds += ",";
            }
        }
        return genreIds;
    }
}

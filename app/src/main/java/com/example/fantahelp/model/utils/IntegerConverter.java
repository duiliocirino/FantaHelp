package com.example.fantahelp.model.utils;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class IntegerConverter {
    @TypeConverter
    public List<Integer> gettingListFromString(String genreIds) {
        List<Integer> list = new ArrayList<>();

        String[] array = genreIds.split("_");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(Integer.parseInt(s));
            }
            else
                list.add(null);
        }
        return list;
    }

    @TypeConverter
    public String writingStringFromList(List<Integer> list) {
        String genreIds = "";
        if(list != null) {
            for (Integer i : list) {
                if(genreIds.isEmpty() && i != null)
                    genreIds += i;
                else if (i != null)
                    genreIds += "_" + i;
                else
                    genreIds += "_";
            }
        }
        return genreIds;
    }
}

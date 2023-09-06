package com.example.fantahelp.model.utils;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import com.example.fantahelp.R;
import com.example.fantahelp.model.entities.Player;
import com.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ValueCalculatorTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getValues() throws IOException {

        List<Player> players = new ArrayList<>();

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        InputStream is = appContext.getResources().openRawResource(R.raw.players_temp);
        InputStreamReader csvStreamReader = new InputStreamReader(is);

        CSVReader reader = new CSVReader(csvStreamReader);
        reader.skip(1);

        // read line by line
        String[] record;
        Integer mId, mPrice, mRating, mRegularness, mFvm, mExpPrice;
        String mRole, mName, mSquad, mMate;
        while ((record = reader.readNext()) != null) {
            mId = Integer.parseInt(record[0]);
            mRole = record[1];
            mName = record[2];
            mSquad = record[3];
            mPrice = Integer.parseInt(record[4]);
            mRating = Integer.parseInt(record[5]);
            mMate = record[6];
            mRegularness = Integer.parseInt(record[7]);
            mFvm = Integer.parseInt(record[8]);
            mExpPrice = Integer.parseInt(record[9]);

            Player player = new Player(mId, -1, mRole, mName, mSquad, mPrice, mRating, mMate, mRegularness, mFvm, mExpPrice);
            players.add(player);
        }



        for (int i = 0; i < players.size(); i++)
        System.out.println("Name");
    }
}
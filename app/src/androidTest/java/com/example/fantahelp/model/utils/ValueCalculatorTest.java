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
        Integer mId, mPrice, mRating, mRegularness, mPg19 = null, mPg18 = null, mAss19 = null,
                mAmm19 = null, mAss18 = null, mAmm18 = null;
        Float mMv19 = null, mMf19 = null, mMv18 = null, mMf18 = null;
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
            if (!record[8].equals(""))
                mPg19 = Integer.parseInt(record[8]);
            if (!record[9].equals(""))
                mMv19 = Float.parseFloat(record[9]);
            if (!record[10].equals(""))
                mMf19 = Float.parseFloat(record[10]);
            if (!record[11].equals(""))
                mAss19 = Integer.parseInt(record[11]);
            if (!record[12].equals(""))
                mAmm19 = Integer.parseInt(record[12]);
            if (!record[13].equals(""))
                mPg18 = Integer.parseInt(record[13]);
            if (!record[14].equals(""))
                mMv18 = Float.parseFloat(record[14]);
            if (!record[15].equals(""))
                mMf18 = Float.parseFloat(record[15]);
            if (!record[16].equals(""))
                mAss18 = Integer.parseInt(record[16]);
            if (!record[17].equals(""))
                mAmm18 = Integer.parseInt(record[17]);

            Player player = new Player(mId, mRole, mName, mSquad, mPrice, mRating, mMate, mRegularness, mPg19, mMv19, mMf19,
                    mAss19, mAmm19, mPg18, mMv18, mMf18, mAss18, mAmm18);
            players.add(player);
        }



        for (int i = 0; i < players.size(); i++)
        System.out.println("Name");
    }
}
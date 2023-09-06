package com.example.fantahelp.model;

import android.content.Context;
import android.os.Debug;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.fantahelp.R;
import com.example.fantahelp.model.daos.*;
import com.example.fantahelp.model.entities.*;
import com.opencsv.CSVReader;
import org.apache.commons.collections4.ListUtils;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Player.class, Game.class, Team.class, Squad.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static Context context;
    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract PlayerDao playerDao();
    public abstract GameDao gameDao();
    public abstract TeamDao teamDao();
    public abstract SquadDao serieATeamDao();

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            try {
                loadSquads();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static AppDatabase getDatabase(final Context context) throws IOException {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    AppDatabase.context = context;
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "fantahelp_db")
                            .addCallback(sRoomDatabaseCallback)
                            .createFromAsset("database/player.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void loadPlayers() throws IOException {
        List<Player> players = new ArrayList<>();
        InputStream is = context.getResources().openRawResource(R.raw.players22_23_notfinal_2);
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

            //Log.d(TAG, "Just created: " +"Gender :" +mGender  +"Meaning :"+ mMeaning +"Name"+ mName +"Origin :" +mOrigin);
        }
        List<List<Player>> batches = ListUtils.partition(players, 16);
        for(int i = 0; i < batches.size(); i ++){
            int finalI = i;
            databaseWriteExecutor.execute( () -> {
                INSTANCE.playerDao().insertAll(batches.get(finalI));
                System.out.println("Batch n." + finalI + "saved");
            });
        }
    }

    public static void loadSquads() throws IOException{
        List<Squad> squads = new ArrayList<>();
        InputStream is = context.getResources().openRawResource(R.raw.squads);
        InputStreamReader csvStreamReader = new InputStreamReader(is);

        CSVReader reader = new CSVReader(csvStreamReader);
        reader.skip(1);

        String[] record;
        String name;
        int rating;

        while ((record = reader.readNext()) != null) {
            name = record[0];
            rating = Integer.parseInt(record[1]);

            Squad newSquad = new Squad(name, rating);
            squads.add(newSquad);
        }
        databaseWriteExecutor.execute( () -> {
            INSTANCE.serieATeamDao().insertAll(squads);
        });
    }
}

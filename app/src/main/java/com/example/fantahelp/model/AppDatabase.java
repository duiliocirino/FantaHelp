package com.example.fantahelp.model;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.fantahelp.R;
import com.example.fantahelp.model.daos.GameDao;
import com.example.fantahelp.model.daos.PlayerDao;
import com.example.fantahelp.model.daos.SerieATeamDao;
import com.example.fantahelp.model.daos.UserDao;
import com.example.fantahelp.model.entities.*;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Player.class, Game.class, Team.class, SerieATeam.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static Context context;
    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract PlayerDao playerDao();
    public abstract GameDao gameDao();
    public abstract SerieATeamDao serieATeamDao();

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            try {
                loadPlayers();
                loadSquads();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    AppDatabase.context = context;
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "fantahelp_db")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static void loadPlayers() throws IOException {
        List<Player> players = new ArrayList<>();
        InputStream is = context.getResources().openRawResource(R.raw.players_temp);
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
            if(!record[8].equals(""))
                mPg19 = Integer.parseInt(record[8]);
            if(!record[9].equals(""))
                mMv19 = Float.parseFloat(record[9]);
            if(!record[10].equals(""))
                mMf19 = Float.parseFloat(record[10]);
            if(!record[11].equals(""))
                mAss19 = Integer.parseInt(record[11]);
            if(!record[12].equals(""))
                mAmm19 = Integer.parseInt(record[12]);
            if(!record[13].equals(""))
                mPg18 = Integer.parseInt(record[13]);
            if(!record[14].equals(""))
                mMv18 = Float.parseFloat(record[14]);
            if(!record[15].equals(""))
                mMf18 = Float.parseFloat(record[15]);
            if(!record[16].equals(""))
                mAss18 = Integer.parseInt(record[16]);
            if(!record[17].equals(""))
                mAmm18 = Integer.parseInt(record[17]);

            Player player = new Player(mId, mRole, mName, mSquad, mPrice, mRating, mMate, mRegularness, mPg19, mMv19, mMf19,
                    mAss19, mAmm19, mPg18, mMv18, mMf18, mAss18, mAmm18);
            players.add(player);

            //Log.d(TAG, "Just created: " +"Gender :" +mGender  +"Meaning :"+ mMeaning +"Name"+ mName +"Origin :" +mOrigin);
        }
        INSTANCE.playerDao().insertAll(players);
    }

    private static void loadSquads() throws IOException{
        List<SerieATeam> serieATeams = new ArrayList<>();
        InputStream is = context.getResources().openRawResource(R.raw.players_temp);
        InputStreamReader csvStreamReader = new InputStreamReader(is);

        CSVReader reader = new CSVReader(csvStreamReader);
        reader.skip(1);

        String[] record;
        String name;
        int rating;

        while ((record = reader.readNext()) != null) {
            name = record[0];
            rating = Integer.parseInt(record[1]);

            SerieATeam newSerieATeam = new SerieATeam(name, rating);
            serieATeams.add(newSerieATeam);
        }
        INSTANCE.serieATeamDao().insertAll(serieATeams);
    }
}

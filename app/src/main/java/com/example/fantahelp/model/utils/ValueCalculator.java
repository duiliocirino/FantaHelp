package com.example.fantahelp.model.utils;

import android.app.Application;
import android.content.Context;
import com.example.fantahelp.model.DataRepository;
import com.example.fantahelp.model.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class ValueCalculator {

    public static List<Integer> getValues(Application application, List<Player> players, String role){
        List<Integer> values = new ArrayList<>();
        for (Player player: players){
            values.add(getValue(player, application));
        }
        return values;
    }

    public static int getValue(Player player, Application application){
        return (int) (myVoteMultiplier(player.myRating) * squadMultiplier(player.squad, application) *
                regularnessMultiplier(player.regularness) * roleMultiplier(player.role) * player.price) + 1;
    }

    private static float roleMultiplier(String role) {
        switch(role){
            case "A": return 1.2f;
            case "P": return 1.3f;
        }
        return 1;
    }

    private static float myVoteMultiplier(int myVote){
        switch(myVote){
            case 5:
            case 4:
                return 2;
            case 3: return 1.5f;
            case 2: return 1;
            case 1: return 0.75f;
        }
        return 1;
    }

    private static float squadMultiplier(String name, Application application){
        DataRepository dataRepository = DataRepository.getRepository(application);
        int value = dataRepository.getSquadRating(name);
        switch(value){
            case 5:
            case 4:
                return 1.3f;
            case 3: return 1;
            case 2:
            case 1:
                return 0.8f;
        }
        return 1;
    }

    private static float regularnessMultiplier(int regularness){
        switch(regularness){
            case 5:
                return 1.2f;
            case 4:
                return 1.1f;
            case 3: return 1f;
            case 2: return 0.7f;
            case 1: return 0.5f;
        }
        return 1;
    }
}

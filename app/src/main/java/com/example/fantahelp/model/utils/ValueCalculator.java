package com.example.fantahelp.model.utils;

import android.app.Application;
import android.content.Context;
import com.example.fantahelp.model.DataRepository;
import com.example.fantahelp.model.entities.Player;
import com.example.fantahelp.model.entities.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValueCalculator {

    /**
     * String is the role
     * First Integer is playerId
     * Second Integer is value calculated
     */
    static Map<String, Map<Integer, Integer>> valuesPerRoles = new HashMap<>();

    /*public static int getPrice(int playerId, String role){
        int price = 1;
        if (role.equals("P"))
            price = getPriceP(playerId);
        if (role.equals("D"))
            price = getPriceD(playerId);
        if (role.equals("C"))
            price = getPriceC(playerId);
        if (role.equals("A"))
            price = getPriceA(playerId);
        return price;
    }

    private static int getPriceA(int playerId) {
    }

    private static int getPriceC(int playerId) {
    }

    private static int getPriceP(int playerId) {
        return 0;
    }

    private static int getPriceD(int playerId) {
        return 0;
    }*/

    public static Map<Integer, Integer> getValues(Application application, List<Player> players, String role){
        Map<Integer, Integer> values;
        if(!valuesPerRoles.containsKey(role)){
            values = new HashMap<>();
            List<Player> playersRole = players.stream().filter(x -> x.role.equals(role)).collect(Collectors.toList());
            for (Player player: playersRole){
                values.put(player.id, getValue(player, application));
            }
            valuesPerRoles.put(role, values);
        }
        else
            values = valuesPerRoles.get(role);

        return values;
    }

    public static void updateValues(Application application, List<Player> players, String role){
        Map<Integer, Integer> values = new HashMap<>();
        for (Player player: players.stream().filter(x -> x.role.equals(role)).collect(Collectors.toList())){
            values.put(player.id, getValue(player, application));
        }
        valuesPerRoles.put(role, values);
    }

    public static int getValue(Player player, Application application){
        Team myTeam = DataRepository.getRepository(application).getMyTeam();
        if(myTeam == null) return (int) (myVoteMultiplier(player.myRating) * squadMultiplier(player.squad, application) *
                regularnessMultiplier(player.regularness) * roleMultiplier(player.role) * player.price * 1.2 ) + 1;
        return (int) (myVoteMultiplier(player.myRating) * squadMultiplier(player.squad, application) *
                regularnessMultiplier(player.regularness) * roleMultiplier(player.role) *
                sameRoleSameSquadMultiplier(player, myTeam, application) *
                mateMultiplier(player.mate, myTeam, application) * player.price * 1.2) + 1;
    }

    private static float sameRoleSameSquadMultiplier(Player player, Team team, Application application){
        List<Player> teamPlayers = DataRepository.getRepository(application).getPlayersByTeam(team);
        if(teamPlayers != null) {
            if (teamPlayers.stream()
                    .filter(x -> x.role.equals(player.role) && x.squad.equals(player.squad))
                    .collect(Collectors.toList())
                    .size() > 0) {
                if(player.role.equals("P")) return 1.5f;
                return 0.8f;
            }
        }
        return 1;
    }

    private static float mateMultiplier(String mate, Team team, Application application){
        List<Player> teamPlayers = DataRepository.getRepository(application).getPlayersByTeam(team);
        if(teamPlayers != null && mate != null){
            if(teamPlayers.stream()
                    .filter(x -> x.name.equals(mate))
                    .collect(Collectors.toList())
                    .size() == 1)
                return 2;
        }
        return 1;
    }

    private static float roleMultiplier(String role) {
        switch(role){
            case "A": return 1.3f;
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

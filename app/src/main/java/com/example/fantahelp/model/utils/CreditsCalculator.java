package com.example.fantahelp.model.utils;

import com.example.fantahelp.model.entities.Player;
import com.example.fantahelp.model.entities.Team;

import java.util.List;

public class CreditsCalculator {
    private static final int initialCredits = 500;
    private static int currentCredits;
    //TODO:changeable from settings
    private static float PER_P = 0.12f;
    private static float PER_D = 0.3f;
    private static float PER_C = 0.3f;
    private static float PER_A = 0.28f;

    public static void update(Team team){
        currentCredits = team.credits;

        if(team.players_id.size() == 3){
            int diff = initialCredits - currentCredits;
            float newPER = diff/initialCredits;
            float deltaPER = PER_P - newPER;
            PER_P = newPER;
            if(deltaPER > 0){
                PER_A += deltaPER;
            } else {
                PER_D += deltaPER;
            }
        } else if(team.players_id.size() == 11){
            int diff = initialCredits + (int)(PER_P * initialCredits) - currentCredits;
            float newPER = diff/initialCredits;
            float deltaPER = PER_D - newPER;
            PER_D = newPER;
            if(deltaPER > 0){
                PER_A += deltaPER;
            } else {
                PER_C += deltaPER;
            }
        } else if(team.players_id.size() == 19){
            int diff = initialCredits + (int)(PER_P * initialCredits) + (int)(PER_D * initialCredits)  - currentCredits;
            float newPER = diff/initialCredits;
            float deltaPER = PER_C - newPER;
            PER_C = newPER;
            if(deltaPER > 0){
                PER_A += deltaPER;
            } else {
                PER_A += deltaPER;
            }
        }
    }

    //TODO: implement partial by saving some text i think
    /*public static int getPartial(List<Player> players, String selectedRole){
        switch(selectedRole){
            case "P": return (int) players.stream().filter(x -> x.role.equals("P")).map(x -> x.pricePaid).reduce();
            case "D": return (int) (PER_D * initialCredits);
            case "C": return (int) (PER_C * initialCredits);
            case "A": return (int) (PER_A * initialCredits);
        }
        return 1;
    }*/

    public static int getPartialTotal(String selectedRole) {
        switch(selectedRole){
            case "P": return (int) (PER_P * initialCredits);
            case "D": return (int) (PER_D * initialCredits);
            case "C": return (int) (PER_C * initialCredits);
            case "A": return (int) (PER_A * initialCredits);
        }
        return 1;
    }
}

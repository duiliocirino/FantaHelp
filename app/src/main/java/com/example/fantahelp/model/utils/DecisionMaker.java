package com.example.fantahelp.model.utils;

import com.example.fantahelp.model.entities.Player;

public class DecisionMaker {


    public static String makeDecision(Player player, int suggestedValue, int actualBet) {
        if(evaluateBet(actualBet, suggestedValue, player)) return "Raise UP TO" + evaluateMaxRaise(actualBet, suggestedValue, player);
        return "PASS";
    }

    private static Integer evaluateMaxRaise(int actualBet, int suggestedValue, Player player) {
        float value = actualBet/suggestedValue;
        int returnVal;

        if(value < 0.2f){
            returnVal = (int) ((suggestedValue - actualBet) * 0.2f) + 1;
        } else if(value < 0.4f){
            returnVal = (int) ((suggestedValue - actualBet) * 0.4f) + 1;
        } else if(value < 0.6f){
            returnVal = (int) ((suggestedValue - actualBet) * 0.6f) + 1;
        } else if(value < 0.8f){
            returnVal = (int) ((suggestedValue - actualBet) * 0.8f) + 1;
        } else {
            returnVal = 1;
        }
        return returnVal;
    }

    private static boolean evaluateBet(int actualBet, int suggestedValue, Player player) {
        if(actualBet < suggestedValue) return true;
        switch(player.myRating){
            case 5: if(actualBet < 1.2f * suggestedValue) return true;
            case 4: if(actualBet < 1.1f * suggestedValue) return true;
            case 3: if(actualBet < 1.05f * suggestedValue) return true;
        }
        return false;
    }
}

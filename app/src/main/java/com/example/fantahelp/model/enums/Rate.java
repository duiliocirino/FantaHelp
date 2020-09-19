package com.example.fantahelp.model.enums;

public enum Rate {
    VERY_LOW, LOW, MEDIUM, HIGH, VERY_HIGH;

    public static Rate getRateByInt(int rate){
        switch (rate){
            case 5:
                return Rate.VERY_HIGH;
            case 4:
                return Rate.HIGH;
            case 3:
                return Rate.MEDIUM;
            case 2:
                return Rate.LOW;
            case 1:
                return Rate.VERY_LOW;
        }
        return null;
    }
}

package com.example.fantahelp.model.enums;

public enum Role {
    P, D, C, A;

    public static Role getRoleByString(String role) {
        switch(role){
            case "A":
                return Role.A;
            case "C":
                return Role.C;
            case "D":
                return Role.D;
            case "P":
                return Role.P;
        }
        return null;
    }

}

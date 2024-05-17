package com.example.webeswimming;

import java.io.Serializable;

public class Result implements Serializable {
    String discipline;
    String competitionName;
    int resultInSeconds;
    private static final long serialVersionUID = 1L;

    public Result(){}
    public int resultInMinutes(){ // has to return float instead of int. fixxxxxx
        return resultInSeconds/60;
    }

    @Override
    public String toString() {
        return "\n     Name of competition: " + competitionName +
                "\n     Discipline: " + discipline +
                "\n     Result in seconds: " + resultInSeconds + "\n";
    }
}

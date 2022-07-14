package com.example.numbergame.game;

public class NumberParser {

    public static int parseChronoTimeToSeconds(String time){
        int toReturn=0;
        String[] splitString = time.split(":");
        if(splitString.length==2){
            toReturn=Integer.parseInt(splitString[0])*60+Integer.parseInt(splitString[1]);
            return toReturn;
        }
        else {
            return 0;
        }
    }
}

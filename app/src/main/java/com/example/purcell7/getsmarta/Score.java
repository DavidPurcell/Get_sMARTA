package com.example.purcell7.getsmarta;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;


/**
 * Created by Purcell7 on 2/24/2017.
 */
public class Score implements Comparable<Score>{
    public int score;
    public String date;
    public String name;
    Random rand = new Random();

    public Score(int score, String name){
        this.score = score;
        this.name = name;
        this.date = new Date().toString();
    }

    public Score(byte[] bytes){
        String bytesToString = new String(bytes);
        //Log.i("Score From Bytes", bytesToString);
        String[] splitBytesToString = bytesToString.split("-");
        this.score = Integer.parseInt(splitBytesToString[0]);
        this.name = splitBytesToString[1];
        this.date = splitBytesToString[2];
    }

    public Score(){
        this.score = Math.abs(rand.nextInt()%100);
        this.name = "Guy #"+Math.abs(rand.nextInt()%100);
        this.date = new Date().toString();
    }


    public byte[] convertToBytes() throws UnsupportedEncodingException {
        String stuffToBecomeBytes = score + "-"+name+"-"+date;
        return stuffToBecomeBytes.getBytes("UTF-8");
    }

    public String toString(){
        return "Score: " + score + " user: "+name+" date: "+date;
    }

    public boolean equals(Score score){
        return score.date.equals(this.date) && score.score==this.score && score.name.equals(this.name);
    }

    @Override
    public int compareTo(Score another) {
        return this.score - another.score;
    }
}

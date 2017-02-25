package com.example.purcell7.getsmarta;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;


/**
 * Created by Purcell7 on 2/24/2017.
 */
public class Score {
    public int score;
    public Date date;
    public long userId;
    Random rand = new Random();

    public Score(int score, long userId){
        this.score = score;
        this.userId = userId;
        this.date = new Date();
    }

    public Score(byte[] bytes){
        String bytesToString = new String(bytes);
        Log.i("Score From Bytes", bytesToString);
        String[] splitBytesToString = bytesToString.split("-");
        this.score = Integer.parseInt(splitBytesToString[0]);
        this.userId = Long.parseLong(splitBytesToString[1]);
        this.date = new Date(splitBytesToString[2]);
    }

    public Score(){
        this.score = Math.abs(rand.nextInt()%100);
        this.userId = Math.abs(rand.nextLong()%1000);
        this.date = new Date();
    }


    public byte[] convertToBytes() throws UnsupportedEncodingException {
        String stuffToBecomeBytes = score + "-"+userId+"-"+date;
        return stuffToBecomeBytes.getBytes("UTF-8");
    }

    public String toString(){
        return "Score: " + score + " user: "+userId+" date: "+date;
    }

    public boolean equals(Score score){
        return score.date.equals(this.date) && score.score==this.score && score.userId==this.userId;
    }
}

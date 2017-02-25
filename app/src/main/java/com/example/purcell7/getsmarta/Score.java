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
    public long uuid;
    Random rand = new Random();

    public Score(int score, String name){
        this.score = score;
        this.name = name;
        this.uuid = Math.abs(rand.nextLong());
        this.date = new Date().toString();
    }

    public Score(byte[] bytes){
        String bytesToString = new String(bytes);
        //Log.i("Score From Bytes", bytesToString);
        String[] splitBytesToString = bytesToString.split("-");
        this.score = Integer.parseInt(splitBytesToString[0]);
        this.name = splitBytesToString[1];
        this.date = splitBytesToString[2];
        this.uuid = Long.parseLong(splitBytesToString[3]);
    }

    public Score(){
        this.score = Math.abs(rand.nextInt()%100);
        this.name = "Guy #"+Math.abs(rand.nextInt()%100);
        this.date = new Date().toString();
        this.uuid = Math.abs(rand.nextLong());
    }


    public byte[] convertToBytes() throws UnsupportedEncodingException {
        String stuffToBecomeBytes = score + "-"+name+"-"+date+"-"+uuid;
        return stuffToBecomeBytes.getBytes("UTF-8");
    }

    public String toString(){
        return "Score: " + score + " user: "+name+" date: "+date + " uuid: " + uuid;
    }

    public boolean equals(Score score){
        return score.uuid==this.uuid && score.score==this.score && score.name.equals(this.name);
    }

    @Override
    public int compareTo(Score another) {
        return this.score - another.score;
    }
}

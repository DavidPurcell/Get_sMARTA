package com.example.purcell7.getsmarta;

import android.app.Application;
import android.util.Log;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import io.underdark.transport.Link;

/**
 * Created by C on 2/24/2017.
 */

public class Globals extends Application {

    // ==== scores ====
    public static String deviceIDa;

    public int lastscore, scorenow;
    public Node node;

    List<Score> scores = new ArrayList<Score>();

    // ===== questions and answers ====
    public int Qstart, Qnow, Qcounter=0;

    public List<String> questions= new ArrayList<String>();
    public List<String> anslist1= new ArrayList<String>();
    public List<String> anslist2= new ArrayList<String>();
    public List<String> anslist3= new ArrayList<String>();
    public List<Integer> corrects= new ArrayList<Integer>();

    public void savescore() {
        Collections.sort(scores);
        int rideloc=whereinsertscore(scores,scorenow);
        Score newScore = new Score(scorenow, deviceIDa);
        scores.add(rideloc,newScore);
        try {
            sendScore(newScore);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public int whereinsertscore(List<Score> scores, int score){
        int loc=0;
        loc=Math.abs(Collections.binarySearch(scores, new Score(score,""))+1);

        return loc;
    }

    public void sendScore(Score score) throws UnsupportedEncodingException {
        node.broadcastFrame(score.convertToBytes());
    }

    public void refreshPeers(Link link)
    {
        //peersTextView.setText(node.getLinks().size() + " connected");
        if(link != null){
            try {
                node.sendAllScores(scores, link);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateScores(byte[] newScoreFrameData)
    {
        if(newScoreFrameData.length > 0){
            Score newScore = new Score(newScoreFrameData);
            Log.i("Update Scores", newScore.toString());
            //O(n) checking for duplicates!!!!
            if(!scores.contains(newScore)){
                Log.i("Update Scores", "Does not contain, so add and propagate");
                scores.add(newScore);
                try {
                    sendScore(newScore);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void makeQs() {
        questions.add("What was the first Ice Hockey puck made of?");
        questions.add("In Texas it's illegal to swear in front of a what?");
        questions.add("What does the Latin phrase 'caveat emptor' mean?");
        questions.add("What do you call the smell which wine gives off?");
        questions.add("How many strings does a cello have?");
        questions.add("Name the largest freshwater lake in the world?");
    }
    public void makea1s() {
        anslist1.add("A piece of wood");
        anslist1.add("A corpse");
        anslist1.add("Seize the day");
        anslist1.add("Bouquet");
        anslist1.add("5");
        anslist1.add("Lake Superior");
    }
    public void makea2s() {
        anslist2.add("Frozen cow manure");
        anslist2.add("Cows");
        anslist2.add("I came, I saw, I conquered");
        anslist2.add("Provenance");
        anslist2.add("4");
        anslist2.add("The Red Sea");
    }
    public void makea3s() {
        anslist3.add("A hubcap");
        anslist3.add("The clergy");
        anslist3.add("Buyer beware");
        anslist3.add("Vineyard");
        anslist3.add("6");
        anslist3.add("Lake Lanier");
    }
    public void makeCorrects() {
        corrects.add(2);
        corrects.add(1);
        corrects.add(3);
        corrects.add(1);
        corrects.add(2);
        corrects.add(1);
    }
}

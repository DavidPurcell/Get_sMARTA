package com.example.purcell7.getsmarta;

import android.app.Application;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by C on 2/24/2017.
 */

public class Globals extends Application {

    // ==== scores ====
    public static String deviceIDa;

    public List<String> IDridescoretable= new Vector<String>();

    public List<Long> Tridescoretable= new Vector<Long>();
    //public List<int> Tstationscoretable= new Vector<>();
    public List<Long> Tmyscoretable= new Vector<Long>();

    public int lastscore, scorenow;
    public List<Integer> ridescoretable= new Vector<Integer>();
    //public List<int> stationscoretable= new Vector<>();
    public List<Integer> myscoretable= new Vector<Integer>();



    public void makescores() {
        ridescoretable.add(240);
        ridescoretable.add(300);
        ridescoretable.add(540);
    }


    public void maketimes() {
        Tridescoretable.add((long)9304);
        Tridescoretable.add((long)493043);
        Tridescoretable.add((long) 930403);
    }



    public void makeIDs() {
        IDridescoretable.add("Joe");
        IDridescoretable.add("David");
        IDridescoretable.add("Cate");
    }

    public void savescore() {
        int rideloc=whereinsertscore(ridescoretable,scorenow);
        ridescoretable.add(rideloc,scorenow);
        IDridescoretable.add(rideloc, deviceIDa);
        long T= System.currentTimeMillis();
        Tridescoretable.add(rideloc,(long)T);


        int myloc=whereinsertscore(myscoretable,scorenow);
        myscoretable.add(rideloc,scorenow);
        Tmyscoretable.add(myloc, (long)T);

    }


    public int whereinsertscore(List<Integer> table, int score){
        int loc=0;
        loc=Math.abs(Collections.binarySearch(table, score)+1);

        return loc;
    }


    // ===== questions and answers ====
    public int Qstart, Qnow, Qcounter=0;

   public List<String> questions= new ArrayList<String>();
    public List<String> anslist1= new ArrayList<String>();
    public List<String> anslist2= new ArrayList<String>();
    public List<String> anslist3= new ArrayList<String>();
    public List<Integer> corrects= new ArrayList<Integer>();

    public void makeQs() {
        questions.add("What");
        questions.add("Who");
        questions.add("When");
        questions.add("Where");
        questions.add("Why");
        questions.add("How");
    }



    public void makea1s() {
        anslist1.add("What");
        anslist1.add("Who");
        anslist1.add("When");
        anslist1.add("Where");
        anslist1.add("Why");
        anslist1.add("How");
    }




    public void makea2s() {
        anslist2.add("What2");
        anslist2.add("Who2");
        anslist2.add("When2");
        anslist2.add("Where2");
        anslist2.add("Why2");
        anslist2.add("How2");
    }



    public void makea3s() {
        anslist3.add("What3");
        anslist3.add("Who3");
        anslist3.add("When3");
        anslist3.add("Where3");
        anslist3.add("Why3");
        anslist3.add("How3");
    }



    public void makeCorrects() {
        corrects.add(1);
        corrects.add(2);
        corrects.add(3);
        corrects.add(1);
        corrects.add(2);
        corrects.add(2);
    }

}

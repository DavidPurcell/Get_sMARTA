package com.example.purcell7.getsmarta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TextView;


public class Play extends AppCompatActivity {


    public int add = 0;

    //Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


       /* //  Back
        final Button buttonBack = (Button) findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent myIntent = new Intent(Play.this, MainActivity.class);
                //myIntent.putExtra("key", value);
                Play.this.startActivity(myIntent);
            }
        }); */

        final Globals g = (Globals)getApplication();




        String question1=g.questions.get(g.Qnow);
      //  String question1=Integer.toString(g.Qnow);
        String ans1=g.anslist1.get(g.Qnow);
        String ans2=g.anslist2.get(g.Qnow);
        String ans3=g.anslist3.get(g.Qnow);

       /* String question1="can I?";
      //  String ans1="please";
        String ans2="no";
        String ans3="yes";*/

        TextView quest = (TextView) findViewById(R.id.question);
        quest.setText(question1);

        Button a1 = (Button) findViewById(R.id.button_a1);
        a1.setText("1.) "+ans1);

        Button a2 = (Button) findViewById(R.id.button_a2);
        a2.setText("2.) "+ans2);

        Button a3 = (Button) findViewById(R.id.button_a3);
        a3.setText("3.) "+ans3);

        final int cor=g.corrects.get(g.Qnow);

        //  Answer 1
        final Button A1 = (Button) findViewById(R.id.button_a1);
        A1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                g.Qcounter++;

                // Right or wrong
                // adjust score
                if (cor==1){
                    //reward
                    add=40;
                    g.scorenow+=add;
                    // Vibrate for 500 milliseconds
                   // vibe.vibrate(500);
                }
                else{
                    // punish?
                }

                if (g.Qcounter>=5){
                    // open home
                    Intent myIntent = new Intent(Play.this, MainActivity.class);
                    // close this q
                    finish();
                    startActivity(myIntent);

                    // save score
                    g.savescore();
                }
                else{
                    // open new q
                    Intent myIntent = new Intent(Play.this, Play.class);
                    // close this q
                    finish();
                    startActivity(myIntent);
                }

            }
        });


        //  Answer 2
        final Button A2 = (Button) findViewById(R.id.button_a2);
        A2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                g.Qcounter++;

                // Right or wrong
                // adjust score
                if (cor==2){
                    //reward
                    add=40;
                    g.scorenow+=add;
                    // Vibrate for 500 milliseconds
                  //  vibe.vibrate(500);
                }
                else{
                    // punish?
                }

                if (g.Qcounter>=5){
                    // open home
                    Intent myIntent = new Intent(Play.this, MainActivity.class);
                    // close this q
                    finish();
                    startActivity(myIntent);

                    // save score
                    g.savescore();
                }
                else{
                    // open new q
                    Intent myIntent = new Intent(Play.this, Play.class);
                    // close this q
                    finish();
                    startActivity(myIntent);
                }
            }
        });


        //  Answer 3
        final Button A3 = (Button) findViewById(R.id.button_a3);
        A3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                g.Qcounter++;


                // Right or wrong
                // adjust score
                if (cor==3){
                    //reward
                    add=40;
                    g.scorenow+=add;
                    // Vibrate for 500 milliseconds
                  //  vibe.vibrate(500);
                }
                else{
                    // punish?
                }


                if (g.Qcounter>=5){
                    // open home
                    Intent myIntent = new Intent(Play.this, MainActivity.class);
                    // close this q
                    finish();
                    startActivity(myIntent);

                    // save score
                    g.savescore();
                }
                else{
                    // open new q
                    Intent myIntent = new Intent(Play.this, Play.class);
                    // close this q
                    finish();
                    startActivity(myIntent);
                }
            }
        });


        g.Qnow++;
        if (g.Qnow>=g.questions.size()){
            g.Qnow=0;
        }

       // counter reset in main

    }

}

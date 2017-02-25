package com.example.purcell7.getsmarta;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MyLeaderboard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_leaderboard);

/*        //  Back
        final Button buttonBack = (Button) findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent myIntent = new Intent(LocalLeaderboard.this, MainActivity.class);
                //myIntent.putExtra("key", value);
                LocalLeaderboard.this.startActivity(myIntent);
            }
        });
*/

        Globals g = (Globals)getApplication();
        // int numScores=3;
        int numScores=g.myscoretable.size();

        String leaders="";
        String IDname;
        int score;

        for (int i=(numScores-1);i>=0;i--){
            IDname=g.deviceIDa;
            score=g.myscoretable.get(i);
            String row=Integer.toString(numScores-i)+"). "+IDname+"  "+Integer.toString(score)+"\n";
            leaders+=row;
        }

        TextView MyLeaders = (TextView) findViewById(R.id.MyLeadersTxt);
        MyLeaders.setText(leaders);



    }

}
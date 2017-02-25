package com.example.purcell7.getsmarta;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import io.underdark.Underdark;
import io.underdark.transport.Link;
import io.underdark.transport.Transport;
import io.underdark.transport.TransportKind;
import io.underdark.transport.TransportListener;
import io.underdark.util.nslogger.NSLogger;
import io.underdark.util.nslogger.NSLoggerAdapter;

public class SyncScores extends AppCompatActivity
{
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private TextView peersTextView;
    private TextView framesTextView;
    public TextView scoresTextView;
    public List<Score> scores = new ArrayList<Score>();
    long userId;

    Node node;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_scores);

        userId = Math.abs(new Random().nextLong());
        peersTextView = (TextView) findViewById(R.id.peersTextView);
        framesTextView = (TextView) findViewById(R.id.framesTextView);
        scoresTextView = (TextView) findViewById(R.id.scores);

        node = new Node(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PERMISSION", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    builder.show();
                }
                return;
            }
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        node.start();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(node != null)
            node.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mesh_network_test, menu);
        return true;
    }

    private static boolean started = false;

    public void addScore(View view) throws UnsupportedEncodingException {
        Score newScore = new Score(Math.abs(new Random().nextInt()%100), userId);
        updateScores(newScore.convertToBytes());
        sendScore(newScore);
    }

    public void sendScore(Score score) throws UnsupportedEncodingException {
        node.broadcastFrame(score.convertToBytes());
    }

    public void refreshPeers(Link link)
    {
        peersTextView.setText(node.getLinks().size() + " connected");
        if(link != null){
            try {
                node.sendAllScores(scores, link);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void refreshFrames()
    {
        framesTextView.setText(node.getFramesCount() + " frames");
    }

    public int checkForMatchingScore(byte[] checkScoreFrameData){
        Score checkScore = new Score(checkScoreFrameData);
        for(Score s:scores){
            if(s.date.equals(checkScore.date) && s.userId==checkScore.userId && s.score==s.score){
                return 1;
            }
        }
        return 0;
    }

    public void updateScores(byte[] newScoreFrameData)
    {
        if(newScoreFrameData.length > 0){
            Score newScore = new Score(newScoreFrameData);
            Log.i("NEW SCORE PLEASE", newScore.toString());
            //O(n) checking for duplicates!!!!
            for(Score s:scores){
                if(s.equals(newScore)){
                    return;
                }
            }
            scores.add(newScore);
            try {
                sendScore(newScore);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //TODO: remove ths.
            scoresTextView.setText(genereateScoreString());
        }
    }

    //TODO: remove this
    public String genereateScoreString(){
        String scoreString = "";
        for(Score s:scores){
            scoreString = scoreString + "\n" + s.toString();
        }
        return scoreString;
    }
} // MainActivity
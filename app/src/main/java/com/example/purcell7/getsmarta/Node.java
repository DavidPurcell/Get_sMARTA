package com.example.purcell7.getsmarta;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

public class Node implements TransportListener
{
    private boolean running;
    private MainActivity activity;
    private long nodeId;
    private Transport transport;

    private ArrayList<Link> links = new ArrayList<>();
    private int framesCount = 0;

    public Node(MainActivity activity)
    {
        this.activity = activity;

        do
        {
            nodeId = new Random().nextLong();
        } while (nodeId == 0);

        if(nodeId < 0)
            nodeId = -nodeId;

        EnumSet<TransportKind> kinds = EnumSet.of(TransportKind.BLUETOOTH, TransportKind.WIFI);

        this.transport = Underdark.configureTransport(
                234235,
                nodeId,
                this,
                null,
                activity.getApplicationContext(),
                kinds
        );
    }

    public void start()
    {
        if(running)
            return;

        running = true;
        transport.start();
    }

    public void stop()
    {
        if(!running)
            return;

        running = false;
        transport.stop();
    }

    public ArrayList<Link> getLinks()
    {
        return links;
    }

    public int getFramesCount()
    {
        return framesCount;
    }

    public void broadcastFrame(byte[] frameData)
    {
        if(links.isEmpty())
            return;

        for(Link link : links)
            link.sendFrame(frameData);
    }

    public void sendAllScores(List<Score> scores, Link link)  throws UnsupportedEncodingException {
        for(Score s:scores){
            link.sendFrame(s.convertToBytes());
        }
    }

    //region TransportListener
    @Override
    public void transportNeedsActivity(Transport transport, ActivityCallback callback)
    {

    }

    @Override
    public void transportLinkConnected(Transport transport, Link link)
    {
        Toast toast = Toast.makeText(activity.getApplicationContext(), "New Link Made", Toast.LENGTH_SHORT);
        toast.show();
        links.add(link);
        activity.g.refreshPeers(link);
    }

    @Override
    public void transportLinkDisconnected(Transport transport, Link link)
    {
        Toast toast = Toast.makeText(activity.getApplicationContext(), "Link Lost", Toast.LENGTH_SHORT);
        toast.show();
        links.remove(link);
        activity.g.refreshPeers(null);

        if(links.isEmpty())
        {
            framesCount = 0;
        }
    }

    @Override
    public void transportLinkDidReceiveFrame(Transport transport, Link link, byte[] frameData)
    {
        activity.g.updateScores(frameData);
        Toast toast = Toast.makeText(activity.getApplicationContext(), "Got some scores", Toast.LENGTH_SHORT);
        toast.show();
    }
    //endregion
} // Node
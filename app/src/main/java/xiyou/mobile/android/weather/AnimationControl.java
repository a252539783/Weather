package xiyou.mobile.android.weather;

import android.app.PendingIntent;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by user on 2016/11/29.
 */

public class AnimationControl implements Runnable{

    private ArrayList<View> controler;
    private boolean moving=false,stop=false;
    private int duration=500,currentFrame=0;
    private MH mh;

    public AnimationControl()
    {
        mh=new MH();
        controler=new ArrayList<>();
    }

    public void addView(View v)
    {
        controler.add(v);
        if (v.getTag()==null)
        {
            v.setTag(new Anims(v));
        }
    }

    public void addState(View v,int index,float x,float y,float w,float h)
    {
        Anims a=(Anims)v.getTag();
        if (x==-1)
            x=v.getX();
        if (y==-1)
            y=v.getY();
        if (w==-1)
            w=v.getWidth();
        if (h==-1)
            h=v.getHeight();
        a.states.add(new State(x,y,w,h,index));
    }

    public void go(int frame)
    {
        if (currentFrame==frame)
            return ;
        currentFrame=frame;

        for (int i=0;i<controler.size();i++)
        {
            Anims a=(Anims)controler.get(i).getTag();
            State s=null;
            for (int j=0;j<a.states.size();j++)
            {
                if (a.states.get(j).index==frame) {
                    s = a.states.get(j);
                    a.index=j;
                    break;
                }
            }
            a.dx=(s.x-a.states.get(0).x)/500;
            a.dy=(s.y-a.states.get(0).y)/500;
            a.dw=(s.w-a.states.get(0).w)/500;
            a.dh=(s.h-a.states.get(0).h)/500;
        }
        mh.frame=0;
        if (!moving)
        {
            new Thread(this).start();
        }
    }

    @Override
    public void run() {
        moving=true;


        while (true)
        {
            if (stop)
                break;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mh.sendEmptyMessage(0);
        }
        moving=false;
    }

    private class MH extends Handler
    {
        public int frame=0;

        @Override
        public void handleMessage(Message msg) {
            frame++;
            for (int i=0;i<controler.size();i++)
            {
                Anims a=(Anims)controler.get(i).getTag();
                State s=a.states.get(a.index);
                a.v.setX(a.v.getX()+a.dx);
                a.v.setY(a.v.getY()+a.dy);
                a.v.setMinimumWidth((int)(a.v.getWidth()+a.dw));
                a.v.setMinimumHeight((int)(a.v.getHeight()+a.dh));
            }
            Anims a=(Anims)controler.get(0).getTag();
            if (frame==500)
                stop=true;
            a.v.invalidate();
            super.handleMessage(msg);
        }
    }

    private static class Anims
    {
        public View v;
        public State current;
        public float dx,dy,dw,dh;
        public int index=0;
        public ArrayList<State> states;

        public Anims(View v)
        {
            this.v=v;
            states=new ArrayList<>();
            states.add(new State(v.getX(),v.getY(),v.getWidth(),v.getHeight(),0));
            current=new State(v.getX(),v.getY(),v.getWidth(),v.getHeight(),0);
        }
    }

    private static class State
    {
        public int index;
        public float x,y;
        public float w,h;
        public State(float x,float y,float w,float h,int index)
        {
            this.x=x;
            this.y=y;
            this.w=w;
            this.h=h;
            this.index=index;
        }
    }
}

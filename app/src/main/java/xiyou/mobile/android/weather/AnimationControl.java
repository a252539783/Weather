package xiyou.mobile.android.weather;

import android.app.Dialog;
import android.app.PendingIntent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by user on 2016/11/29.
 */

public class AnimationControl implements Runnable{

    private ArrayList<View> controler;
    private boolean moving=false,stop=false;
    private int duration=300,currentFrame=0;
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

    public int current()
    {
        return currentFrame;
    }

    /*public void addState(View v,int index,float x,float y,float w,float h)
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

    public void addState(View v,int index,float sx,float sy)
    {
        Anims a=(Anims)v.getTag();
        if (sx==-1)
            sx=v.getScaleX();
        if (sy==-1)
            sy=v.getScaleY();
        a.states.add(new State(x,y,w,h,index));
    }*/

    public State getState(View v,int index)
    {
        Anims a=(Anims)v.getTag();
        for (int i=0;i<a.states.size();i++)
        {
            if (a.states.get(i).index==index)
            {
                return a.states.get(i);
            }
        }

        a.states.add(new State(v,index));

        return a.states.get(a.states.size()-1);
    }

    public void go(int frame)
    {
        if (currentFrame==frame||moving)
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
            a.dx=(s.x-a.current.x)/duration;
            a.dy=(s.y-a.current.y)/duration;
            a.dw=(s.w-a.current.w)/duration;
            a.dh=(s.h-a.current.h)/duration;
            a.dsx=(s.sx-a.current.sx)/duration;
            a.dsy=(s.sy-a.current.sy)/duration;
            a.current=s;
        }
        stop=false;
        mh.frame=0;
            new Thread(this).start();
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
         //       if (s.x!=-1)
                a.v.setX(a.v.getX()+a.dx);
         //       if (s.y!=-1)
                a.v.setY(a.v.getY()+a.dy);
        //        if (s.w!=-1)
                a.cw=a.cw+a.dw;
                a.ch=a.ch+a.dh;
                ViewGroup.LayoutParams lp=a.v.getLayoutParams();
                lp.height=(int)a.ch;
                lp.width=(int)a.cw;
               // a.v.setMinimumWidth((int)(a.cw));
         //       if (s.h!=-1)
                a.v.setLayoutParams(lp);
                //a.v.setMinimumHeight((int)(a.ch));
        //        if (s.sx!=-1)
                    a.v.setScaleX(a.v.getScaleX()+a.dsx);
         //       if (s.sy!=-1)
                    a.v.setScaleY(a.v.getScaleY()+a.dsy);
            }
            Anims a=(Anims)controler.get(0).getTag();
            if (frame==duration) {
                stop = true;
                frame=0;
            }
            a.v.invalidate();
            super.handleMessage(msg);
        }
    }

    private static class Anims
    {
        public View v;
        public State current;
        public float dx,dy,dw,dh,dsx,dsy;
        public float cw=0,ch=0;
        public int index=0;
        public ArrayList<State> states;

        public Anims(View v)
        {
            this.v=v;
            states=new ArrayList<>();
            states.add(new State(v));
            current=states.get(0);
            cw=v.getLayoutParams().width;
            ch=v.getLayoutParams().height;
        }
    }

    public static class State
    {
        public int index;
        public float x,y,sx,sy,w,h,alpha;

        public State(View v,int index)
        {
            this(v);
            this.index=index;
        }

        public State(View v)
        {
            index=0;
            x=v.getX();
            y=v.getY();
            sx=v.getScaleX();
            sy=v.getScaleY();
            w=v.getLayoutParams().width;
            h=v.getLayoutParams().height;
            alpha=v.getAlpha();
        }

        public State setX( float x)
        {
            this.x=x;
            return this;
        }
        public State setY( float y)
        {
            this.y=y;
            return this;
        }
        public State setW( float w)
        {
            this.w=w;
            return this;
        }
        public State setH( float h)
        {
            this.h=h;
            return this;
        }
        public State setSx( float sx)
        {
            this.sx=sx;
            return this;
        }
        public State setSy( float sy)
        {
            this.sy=sy;
            return this;
        }
        public State setA( float a)
        {
            this.alpha=a;
            return this;
        }
    }
}

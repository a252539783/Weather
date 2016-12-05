package xiyou.mobile.android.weather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/12/4.
 */

public class LinesView extends View {

    Paint p;
    private int w,h,unitW;
    private LinesAdapter lines=null;

    public LinesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p=new Paint();
        p.setColor(Color.BLACK);
    }

    public void setAdapter(LinesAdapter la)
    {
        lines=la;
        unitW=w/lines.get(0).length;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w=w;
        this.h=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public static class LinesAdapter
    {
        private ArrayList<int []> lines;

        public LinesAdapter()
        {
            lines=new ArrayList<>();
        }

        public int[] get(int index) {
            return lines.get(index);
        }

        public float[] getW()
        {
            float []r=new float[lines.size()];
            float all=0;

            for (int i=0;i<lines.size();i++)
            {
                for (int j=0;j<lines.get(i).length;j++)
                {

                }
            }
            return r;
        }

        public LinesAdapter addLine(int []src)
        {
            lines.add(src);
            return this;
        }
    }
}

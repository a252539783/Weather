package xiyou.mobile.android.weather;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by user on 2016/11/28.
 */

public class AutoTextView extends TextView {
    private boolean relyH=true;

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //relyH=attrs.getAttributeBooleanValue("xy3g","relyH",true);
        relyH=context.obtainStyledAttributes(attrs,R.styleable.xy3g).getBoolean(R.styleable.xy3g_relyH,true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (relyH) {
            if (h>w/getText().toString().length())
                setTextSize(w/getText().toString().length()*160/getResources().getDisplayMetrics().densityDpi);
            else
                setTextSize(h*160/getResources().getDisplayMetrics().densityDpi);
        }else
        {
                setTextSize(w/getText().toString().length()*160/getResources().getDisplayMetrics().densityDpi);
        }
    }
}

package xiyou.mobile.android.weather;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by user on 2016/11/28.
 */

public class AutoTextView extends TextView {
    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h>w/getText().toString().length())
            setTextSize(w/getText().toString().length()*160/getResources().getDisplayMetrics().densityDpi);
        else
        setTextSize(h*160/getResources().getDisplayMetrics().densityDpi);
    }
}

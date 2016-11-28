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
        setTextSize(h*160/getResources().getDisplayMetrics().density);
    }
}

package xiyou.mobile.android.weather;

import android.app.Dialog;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private AnimationControl ac;
    private RequestQueue rq;
    private SelectDialog sd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rq= Volley.newRequestQueue(this);
        sd=new SelectDialog(this);
        initAnimation();
    }

    public void initAnimation()
    {
        ac=new AnimationControl();
        View test= findViewById(R.id.margin_top);
        ac.addView(test);
        ac.getState(test,1).setH(1000);
        test=findViewById(R.id.contain_simple);
        test.setOnClickListener(this);/*
        test=findViewById(R.id.margin_bottom);
        ac.addView(test);
        ac.getState(test,1).setH(200);
        test.setMinimumHeight(1000);*/
    }

    @Override
    public void onClick(View v) {
        if (ac.current()==0)
        ac.go(1);
        else
            ac.go(0);

        Log.e("ac",""+ac.current());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m,menu);
        TextView v=(TextView) menu.findItem(R.id.select_loc).getActionView();
        v.setOnClickListener(this);
        v.setText("选择地区");
        sd.init();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }



    public RequestQueue getRq()
    {
        return rq;
    }
}

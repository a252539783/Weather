package xiyou.mobile.android.weather;

import android.app.Dialog;
import android.app.WallpaperInfo;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2016/11/22.
 */

public class SelectDialog{

    private MainActivity c;
    private int barHeight=0;
    private Dialog select_sheng,select_shi;
    private HashMap<String,ArrayList<String[]>> location1=new HashMap<>();
    private HashMap<String,ArrayList> location0=new HashMap<>();
    private HashMap<Integer,BaseAdapter> allShi=new HashMap();
    private ArrayList<String> sheng=new ArrayList<>();
    private Window window_sheng,window_shi;
    private ListView list_shi;
    private LayoutInflater li;

    public SelectDialog(MainActivity c){
        this.c=c;
        li=c.getLayoutInflater();
    }

    public void show()
    {
        select_sheng.show();

    }

    public void init()
    {
        select_sheng=new Dialog(c,R.style.DefaultDialog);
        select_sheng.setContentView(li.inflate(R.layout.select_location,null,false));
        select_sheng.setCanceledOnTouchOutside(true);
        select_shi=new Dialog(c,R.style.DefaultDialog);
        select_shi.setContentView(li.inflate(R.layout.select_location,null,false));
        select_shi.setCanceledOnTouchOutside(true);
        window_sheng=select_sheng.getWindow();
        window_shi=select_shi.getWindow();
        window_sheng.getAttributes().width=window_shi.getAttributes().width=c.getWindowManager().getDefaultDisplay().getWidth()/4;

        window_shi.getAttributes().height=window_sheng.getAttributes().height=c.getWindowManager().getDefaultDisplay().getHeight()/3;
        window_sheng.setGravity(Gravity.RIGHT|Gravity.TOP);
        window_shi.setGravity(Gravity.TOP);
        window_shi.getAttributes().x+=window_sheng.getAttributes().width/2;;
        window_sheng.setWindowAnimations(R.style.anim_1);
        window_shi.setWindowAnimations(R.style.anim_1);
        list_shi=(ListView)select_shi.findViewById(R.id.list_loc);
        ((ListView)select_sheng.findViewById(R.id.list_loc)).setAdapter(new ShengAdapter());

        getLocation2();
        barHeight=(int)c.obtainStyledAttributes(new int[] { android.R.attr.actionBarSize }).getDimension(0,0);
        window_sheng.getAttributes().y=barHeight;
    }

    public void getLocation2()
    {
        c.getRq().add(new JsonArrayRequest("http://files.heweather.com/china-city-list.json", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject c=null;
                    try {
                        c=jsonArray.getJSONObject(i);
                        if (location1.containsKey(c.getString("provinceZh")))
                        {
                            location1.get(c.getString("provinceZh")).add(new String[]{c.getString("cityZh"),c.getString("id")});
                        }else
                        {
                            ArrayList<String[]> x=new ArrayList<String[]>();
                            sheng.add(c.getString("provinceZh"));
                            x.add(new String[]{c.getString("cityZh"),c.getString("id")});
                            location1.put(c.getString("provinceZh"),x);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("error",volleyError.toString());
            }
        }));
    }

    private class ShengAdapter extends BaseAdapter implements View.OnClickListener
    {

        @Override
        public int getCount() {
            return sheng.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null||position!=convertView.getId()) {
                convertView=li.inflate(R.layout.texx, null, false);
                convertView.setId(position);
                TextView t=((TextView)convertView.findViewById(R.id.loc_name));
                t.setText(sheng.get(position));
                //t.setHeight();
                convertView.setOnClickListener(this);
            }

            return convertView;
        }

        @Override
        public void onClick(View v) {
            if (!allShi.containsKey(v.getId())){
                allShi.put(v.getId(),new ShiAdapter(sheng.get(v.getId())));
            }

            list_shi.setAdapter(allShi.get(v.getId()));
            window_shi.getAttributes().y=(int)v.getY()+barHeight;
            select_shi.show();
        }
    }

    private class ShiAdapter extends BaseAdapter
    {
        private String sheng;
        public ShiAdapter(String sheng)
        {
            this.sheng=sheng;
        }

        @Override
        public int getCount() {
            return location1.get(sheng).size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null||position!=convertView.getId())
            {
                convertView=li.inflate(R.layout.texx,null,false);
                TextView t=(TextView) convertView.findViewById(R.id.loc_name);
                t.setText(location1.get(sheng).get(position)[0]);
            }

            return convertView;
        }
    }
}

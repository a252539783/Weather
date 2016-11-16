package xiyou.mobile.android.weather;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

public class MainActivity extends AppCompatActivity {

    private ListView citylist;
    private RequestQueue rq;
    private HashMap<String,HashMap> location1=new HashMap<>();
    private HashMap<String,ArrayList> location0=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        citylist=(ListView)findViewById(R.id.citylist);


        rq= Volley.newRequestQueue(this);
        Dialog d=new Dialog(this);
        d.setContentView(R.layout.select_location);
        d.getWindow().getAttributes().x=-640;
        d.show();
        getLocation2();
    }

    public void getLocation() {
        rq.add(new NStringRequest("https://passport.baidu.com/js/sitedata_bas.js", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                JSONArray jsonArray= null;
                JSONObject child=null;
                try {
                    jsonArray = new JSONArray(s.substring(13));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray sheng=null,shi=null,c=null;
                JSONObject qu=null;
                ArrayList shichild=null;
                HashMap shengchild=null;
                boolean zhi=true;
                for (int i=1;i< jsonArray.length();i++)
                {
                    try {
                        child=jsonArray.getJSONObject(i);                      //第i个省/市
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {

                            if (child.getInt("type")==0)
                            {
                                zhi=true;
                                shichild=new ArrayList();
                                location0.put(child.getString("name"),shichild);
                                Log.e("shi",child.getString("name"));
                                shi=child.getJSONArray("sub");
                                c=shi;
                            }
                        else
                            {
                                zhi=false;
                                shengchild=new HashMap();
                                location1.put(child.getString("name"),shengchild);
                                sheng=child.getJSONArray("sub");
                                Log.e("sheng",child.getString("name"));
                                c=sheng;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int j=0;j<c.length();j++)
                    {
                        if (zhi)
                        {
                            try {
                                shichild.add(shi.getJSONObject(j).getString("name"));
                                Log.e("qu",shi.getJSONObject(j).getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            continue;
                        }
                        else
                        {
                            shichild=new ArrayList();
                            try {
                                child=sheng.getJSONObject(j);                        //第j个市
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                shengchild.put(child.getString("name"),shichild);
                                Log.e("shi",child.getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                shi=child.getJSONArray("sub");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int k=0;k<shi.length();k++)
                        {
                            try {
                                shichild.add(shi.getJSONObject(k).getString("name"));
                                Log.e("qu",shi.getJSONObject(k).getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
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

    public void getLocation2()
    {
        rq.add(new JsonArrayRequest("http://files.heweather.com/china-city-list.json", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject c=null;
                    try {
                        c=jsonArray.getJSONObject(i);
                    if (location1.containsKey(c.getString("provinceZh")))
                    {
                        location1.get(c.getString("provinceZh")).put(c.getString("cityZh"),c.getString("id"));
                    }else
                    {
                        HashMap<String,String> x=new HashMap<String, String>();
                        x.put(c.getString("cityZh"),c.getString("id"));
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
}

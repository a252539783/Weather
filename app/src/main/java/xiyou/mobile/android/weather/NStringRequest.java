package xiyou.mobile.android.weather;

/**
 * Created by user on 2016/11/16.
 */

import android.util.ArrayMap;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2016/9/13.
 */
public class NStringRequest extends StringRequest {

    Map<String,String> headers=null,params=null;

    public NStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public NStringRequest(int method,String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method,url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(new String (response.data,"gb2312"), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (params==null)
            params=new HashMap();

        return params;
    }

    public Map<String,String> headers()
    {
        try {
            return getHeaders();
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }

        return null;
    }

    public Map<String,String> params()
    {
        try {
            return getParams();
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }

        return null;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (headers==null)
            headers=new HashMap();

        return headers;
    }
}

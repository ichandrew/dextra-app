package br.com.dexfood.dexfood.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.dexfood.dexfood.data.Food;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 26/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class APIHandler implements APIHandlerContract {

    private Context context;
    private String url_base;

    public APIHandler(Context context) {
        this.context = context;
        this.url_base = "http://192.168.0.4:8080/api";
    }

    @Override
    public void volleyRequestPUT(String url, Response.Listener<String> onResponse, Response.ErrorListener onErrorResponse, final boolean hasParams, final Map<String, String> params) {
        String REQUEST_TAG = "br.com.dexfood.volleyStringRequest";

        StringRequest strReq = new StringRequest(
            Request.Method.PUT,
            this.url_base + url,
            onResponse,
            onErrorResponse
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (hasParams)
                    return params;
                return super.getParams();
            }
        };

        AppSingleton.getInstance(this.context).addToRequestQueue(strReq, REQUEST_TAG);
    }

    @Override
    public void volleyJsonObjectRequest(String url, Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse) {
        String REQUEST_TAG = "br.com.dexfood.volleyJsonObjectRequest";

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(
            this.url_base + url,
            null,
            onResponse,
            onErrorResponse
        );

        AppSingleton.getInstance(this.context).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }

    @Override
    public void volleyJsonArrayRequest(String url, Response.Listener<JSONArray> onResponse, Response.ErrorListener onErrorResponse) {
        String REQUEST_TAG = "br.com.dexfood.volleyJsonArrayRequest";

        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(
            this.url_base + url,
            onResponse,
            onErrorResponse
        );

        AppSingleton.getInstance(this.context).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
    }
}
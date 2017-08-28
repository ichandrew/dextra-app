package br.com.dexfood.dexfood.utils;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 26/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public interface APIHandlerContract {
    void volleyRequestPUT(String url, Response.Listener<String> onResponse, Response.ErrorListener onErrorResponse, final boolean hasParams, final Map<String, String> params);
    void volleyJsonObjectRequest(String url, Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse);
    void volleyJsonArrayRequest(String url, Response.Listener<JSONArray> onResponse, Response.ErrorListener onErrorResponse);
}

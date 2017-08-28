package br.com.dexfood.dexfood.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 27/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class Promotion {

    private int mId;
    private String mName;
    private String mDescription;

    public Promotion(int id, String name, String description) {
        this.mId          = id;
        this.mName        = name;
        this.mDescription = description;
    }

    public Promotion(JSONObject promotion) {
        try {
            this.mId          = promotion.getInt("id");
            this.mName        = promotion.getString("name");
            this.mDescription = promotion.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }
}

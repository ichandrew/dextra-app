package br.com.dexfood.dexfood.data;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.dexfood.dexfood.utils.APIHandler;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 27/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class FoodOrder extends Food {

    private int mIdOrder;
    private List<String> mExtras;

    public FoodOrder() {
        // Default
    }

    public FoodOrder(JSONObject order) {
        this.mExtras = new ArrayList<>();
        try {
            this.mIdOrder = order.getInt("id");
            this.mId      = order.getInt("id_sandwich");

            JSONArray array = order.getJSONArray("extras");
            for (int i = 0; i < array.length(); i++) {
                this.mExtras.add(array.getString(i));
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public int getIdOrder() {
        return mIdOrder;
    }

    public List<String> getExtras() {
        return mExtras;
    }

    public void setFood(JSONObject food) {
        try {
            this.mId          = food.getInt("id");
            this.mImage       = food.getString("image");
            this.mName        = food.getString("name");
            this.mIngredients = new ArrayList<>();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.mIngredients = ingredients;
    }
}

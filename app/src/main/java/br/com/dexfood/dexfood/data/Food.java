package br.com.dexfood.dexfood.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 26/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class Food {

    protected int mId;
    protected String mName;
    protected List<Ingredient> mIngredients;
    protected String mImage;

    public Food() {
        this.mIngredients = new ArrayList<>();
    }

    public Food(int id, String name, ArrayList<Ingredient> ingredients, String image) {
        this.mId          = id;
        this.mName        = name;
        this.mIngredients = ingredients;
        this.mImage       = image;
    }

    public Food(JSONObject food, List<Ingredient> ingredients) {
        try {
            this.mId          = food.getInt("id");
            this.mName        = food.getString("name");
            this.mIngredients = ingredients;
            this.mImage       = food.getString("image");
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

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public String getImage() {
        return mImage;
    }

    public String getListIngredients() {
        String list_ingredients = "";

        for (int i = 0; i < this.mIngredients.size(); i++) {
            list_ingredients += this.mIngredients.get(i).getName();

            if (i < (this.mIngredients.size() - 1))
                list_ingredients += ", ";
        }
        return list_ingredients;
    }

    public String getFoodPrice() {
        double price = this.getFoodPriceDouble();

        String[] parts = String.valueOf(price).split("\\.");
        String final_price = parts[0] + "," + parts[1];
        if (parts[1].length() == 1)
            final_price += "0";

        return "R$ " + final_price;
    }

    public double getFoodPriceDouble() {
        double price = 0.0;

        for (int i = 0; i < this.mIngredients.size(); i++) {
            price += this.mIngredients.get(i).getPrice() * this.mIngredients.get(i).getQuantity();
        }

        return price;
    }
}
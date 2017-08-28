package br.com.dexfood.dexfood.fooddetail;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.dexfood.dexfood.R;
import br.com.dexfood.dexfood.data.Food;
import br.com.dexfood.dexfood.data.Ingredient;
import br.com.dexfood.dexfood.utils.APIHandler;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 27/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class FoodDetailsPresenter implements FoodDetailsContract.Presenter {

    private FoodDetailsActivity mView;
    private APIHandler mApi;

    public FoodDetailsPresenter(FoodDetailsActivity view) {
        this.mView = view;
        this.mApi  = new APIHandler(view.getApplicationContext());
    }

    @Override
    public void getFoodDetails(final int id) {
        this.mApi.volleyJsonArrayRequest(
                "/ingrediente/de/" + id,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            List<Ingredient> ingredients = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                Ingredient ingredient = new Ingredient(response.getJSONObject(i));
                                ingredients.add(ingredient);
                            }

                            getFoodInfo(ingredients, id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
    };

    private void getFoodInfo(final List<Ingredient> ingredients, int id) {
        this.mApi.volleyJsonObjectRequest(
            "/lanche/" + id,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, response.toString());

                    Food food = new Food(response, ingredients);
                    mView.showFoodDetail(food);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            }
        );
    }

    @Override
    public void addFoodToCart(int id, List<Ingredient> ingredients) {
        if (this.isCustomized(ingredients)) {
            JSONArray array = new JSONArray();
            for (Ingredient ingredient : ingredients) {
                array.put(String.valueOf(ingredient.getId()));
            }

            Map<String, String> params = new HashMap<>();
            params.put("extras", array.toString());

            this.mApi.volleyRequestPUT(
                "/pedido/" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mView.foodAddedToCart();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                },
                true,
                params
            );
        } else {
            this.mApi.volleyRequestPUT(
                "/pedido/" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mView.foodAddedToCart();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                },
                false,
                null
            );
        }
    }

    @Override
    public boolean isCustomized(List<Ingredient> ingredients) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getQuantity() != 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clone(List<Ingredient> a, List<Ingredient> b) {
        a.clear();
        for (Ingredient ingredient : b) {
            a.add(ingredient);
        }
    }

    @Override
    public String getPrice(List<Ingredient> ingredients) {
        double price = 0.0;
        double new_price = 0.0;

        double discount = isPromoMeat(ingredients);

        for (int i = 0; i < ingredients.size(); i++) {
            price += ingredients.get(i).getPrice() * ingredients.get(i).getQuantity();

            if (discount > 0.0)
                price -= discount;
        }

        if (this.isPromoLight(ingredients)) {
            this.mView.setPromotion(true, this.mView.getApplicationContext().getString(R.string.promo_light));
            new_price = price - ((price * 10)/100);
        }

        if (new_price == 0.0)
            return "R$ " + this.formatPrice(price);
        else
            return "R$ " + this.formatPrice(new_price) + " (10% desc. em R$ " + this.formatPrice(price) + ")";
    }

    private String formatPrice(double price) {
        String[] parts = String.valueOf(price).split("\\.");
        String final_price = parts[0] + "," + parts[1];
        if (parts[1].length() == 1)
            final_price += "0";
        return final_price;
    }

    public boolean isPromoLight(List<Ingredient> ingredients) {
        boolean cond1 = false;
        boolean cond2 = false;
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getId() == 1 && ingredients.get(i).getQuantity() > 0)
                cond1 = true;

            if (ingredients.get(i).getId() == 2 && ingredients.get(i).getQuantity() == 0)
                cond2 = true;
        }

        return cond1 && cond2;
    }

    public double isPromoMeat(List<Ingredient> ingredients) {
        double discount = 0.0;

        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getId() == 3) {
                for (int k = 3; k <= ingredients.get(i).getQuantity(); k+=3) {
                    discount += ingredients.get(i).getPrice();
                }
                break;
            }
        }

        return discount;
    }
}

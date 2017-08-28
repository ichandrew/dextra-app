package br.com.dexfood.dexfood.cart;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.dexfood.dexfood.data.FoodOrder;
import br.com.dexfood.dexfood.data.Ingredient;
import br.com.dexfood.dexfood.utils.APIHandler;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 26/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class CartPresenter implements CartContract.Presenter {

    CartFragment mView;
    APIHandler mApi;
    List<FoodOrder> orders;

    public CartPresenter(CartFragment view) {
        this.mView = view;
        this.mApi   = new APIHandler(view.getContext());
        orders = new ArrayList<>();
    }

    @Override
    public void loadOrders() {
        this.mView.setProgressIndicator(true);

        this.mApi.volleyJsonArrayRequest(
                "/pedido",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                FoodOrder order = new FoodOrder(response.getJSONObject(i));
                                orders.add(order);
                                loadFoodInfo(order.getId(), i);
                            } catch(JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (response.length() > 0)
                            mView.showOrders(orders);
                        else
                            mView.setEmptyMessage(true);
                        mView.setProgressIndicator(false);
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
    public void loadFoodInfo(final int id, final int position) {
        this.mApi.volleyJsonObjectRequest(
            "/lanche/" + id,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    FoodOrder order = orders.get(position);
                    order.setFood(response);
                    orders.set(position, order);
                    mView.notifyRecycler(position);

                    getIngredients(id, position, order.getExtras());
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
    public void getIngredients(int id, final int position, final List<String> extras) {
        this.mApi.volleyJsonArrayRequest(
        "/ingrediente/de/" + id,
        new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<Ingredient> ingredients = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        Ingredient ingredient = new Ingredient(response.getJSONObject(i));

                        for (int j = 0; j < extras.size(); j++) {
                            if (String.valueOf(ingredient.getId()).equals(extras.get(j)))
                                ingredient.increseQuantity();
                        }

                        ingredients.add(ingredient);
                    }

                    FoodOrder order = orders.get(position);
                    order.setIngredients(ingredients);
                    orders.set(position, order);
                    mView.notifyRecycler(position);
                    getTotalPrice();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    public void getTotalPrice() {
        double price = 0.0;
        for (int i = 0; i < this.orders.size(); i++) {
            price += orders.get(i).getFoodPriceDouble();
        }

        String[] parts = String.valueOf(price).split("\\.");
        String final_price = parts[0] + "," + parts[1];
        if (parts[1].length() == 1)
            final_price += "0";

        mView.setTotalPrice(" R$ " + final_price);
    }
}

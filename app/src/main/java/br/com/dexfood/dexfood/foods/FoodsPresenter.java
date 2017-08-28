package br.com.dexfood.dexfood.foods;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.dexfood.dexfood.data.Food;
import br.com.dexfood.dexfood.data.Ingredient;
import br.com.dexfood.dexfood.utils.APIHandler;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 26/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class FoodsPresenter implements FoodsContract.UserActionsListener {

    private FoodsFragment mView;
    private APIHandler api;

    public FoodsPresenter(FoodsFragment view) {
        this.mView = view;
        this.api = new APIHandler(view.getContext());
    }

    @Override
    public void loadFoods() {
        if (mView != null)
            mView.setProgressIndicator(true);

        api.volleyJsonArrayRequest(
            "/ingrediente",
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        ArrayList<Ingredient> ingredients = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            Ingredient ingredient = new Ingredient(obj.getInt("id"), obj.getString("name"), obj.getDouble("price"), obj.getString("image"));
                            ingredients.add(ingredient);

                            getFoods(ingredients);
                        }
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

    private void getFoods(final ArrayList<Ingredient> ingredients) {
        api.volleyJsonArrayRequest(
            "/lanche",
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        ArrayList<Food> foods = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            JSONArray array = obj.getJSONArray("ingredients");
                            ArrayList<Ingredient> foodIngredients = new ArrayList<>();

                            for (int k = 0; k < array.length(); k++) {
                                for (int x = 0; x < ingredients.size(); x++) {
                                    if (ingredients.get(x).getId() == array.getInt(k)) {
                                        foodIngredients.add(ingredients.get(x));
                                        break;
                                    }
                                }
                            }

                            Food food = new Food(obj.getInt("id"), obj.getString("name"), foodIngredients, obj.getString("image"));

                            foods.add(food);
                        }

                        if (foods.size() > 0)
                            mView.showFoods(foods);
                        else
                            mView.setEmptyMessage(true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mView.setProgressIndicator(false);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    mView.setProgressIndicator(false);
                }
            }
        );
    }

    @Override
    public void openFoodDetails(Food requestedFood) {

    }
}

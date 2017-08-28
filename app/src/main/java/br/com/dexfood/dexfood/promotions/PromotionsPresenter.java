package br.com.dexfood.dexfood.promotions;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import br.com.dexfood.dexfood.data.Promotion;
import br.com.dexfood.dexfood.utils.APIHandler;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 26/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class PromotionsPresenter implements PromotionsContract.Presenter {

    private PromotionsFragment mView;
    private APIHandler mApi;

    public PromotionsPresenter(PromotionsFragment view) {
        this.mView = view;
        this.mApi  = new APIHandler(view.getContext());
    }

    @Override
    public void loadPromotions() {
        this.mView.setProgressIndicator(true);

        this.mApi.volleyJsonArrayRequest(
            "/promocao",
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    List<Promotion> promotions = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            Promotion promotion = new Promotion(response.getJSONObject(i));
                            promotions.add(promotion);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if (promotions.size() > 0)
                        mView.showPromotions(promotions);
                    else
                        mView.setEmptyMessage(true);
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
}

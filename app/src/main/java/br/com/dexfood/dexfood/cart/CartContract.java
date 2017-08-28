package br.com.dexfood.dexfood.cart;

import org.json.JSONObject;

import java.util.List;

import br.com.dexfood.dexfood.data.FoodOrder;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 26/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public interface CartContract {
    interface View {
        void setProgressIndicator(boolean active);
        void setEmptyMessage(boolean active);
        void showOrders(List<FoodOrder> orders);
        void notifyRecycler(int position);
        void setTotalPrice(String price);
    }

    interface Presenter {
        void loadOrders();
        void loadFoodInfo(int id, int position);
        void getIngredients(int id, int position, final List<String> extras);
    }
}

package br.com.dexfood.dexfood.foods;

import java.util.ArrayList;

import br.com.dexfood.dexfood.data.Food;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 26/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public interface FoodsContract {
    interface View {
        void setProgressIndicator(boolean active);
        void showFoods(ArrayList<Food> foods);
        void showFoodDetail(Food food);
        void setEmptyMessage(boolean active);
    }

    interface UserActionsListener {
        void loadFoods();
        void openFoodDetails(Food requestedFood);
    }
}

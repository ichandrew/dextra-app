package br.com.dexfood.dexfood.fooddetail;

import java.util.List;

import br.com.dexfood.dexfood.data.Food;
import br.com.dexfood.dexfood.data.Ingredient;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 27/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public interface FoodDetailsContract {
    interface View {
        void showFoodDetail(Food food);
        void setCustomizedLabel(boolean active, String name);
        void foodAddedToCart();
        void setPromotion(boolean active, String message);
    }

    interface Presenter {
        void getFoodDetails(int id);
        void addFoodToCart(int id, List<Ingredient> ingredients);
        boolean isCustomized(List<Ingredient> ingredients);
        void clone(List<Ingredient> a, List<Ingredient> b);
        String getPrice(List<Ingredient> ingredients);
    }
}

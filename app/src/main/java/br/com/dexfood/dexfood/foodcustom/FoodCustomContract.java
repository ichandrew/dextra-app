package br.com.dexfood.dexfood.foodcustom;

import java.util.List;

import br.com.dexfood.dexfood.data.Ingredient;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 27/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public interface FoodCustomContract {
    interface View {
        void setProgressIndicator(boolean active);
        void setEmptyMessage(boolean active);
        void showIngredients(List<Ingredient> ingredients);
    }
}

package br.com.dexfood.dexfood;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import br.com.dexfood.dexfood.data.Food;
import br.com.dexfood.dexfood.data.Ingredient;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 27/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class FoodTest {

    private ArrayList<Ingredient> ingredients;
    private Food food;

    @Before
    public void setUp() throws Exception {
        this.ingredients = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            double price = 0.5 * i;
            Ingredient ingredient = new Ingredient(1, "Ingrediente", price, "https://tinyurl.com/ya2ka87m");
            ingredients.add(ingredient);
        }

        this.food = new Food(1, "Comida", ingredients, "https://tinyurl.com/ya2ka87m");
    }

    @Test
    public void getFoodPrice() {
        Assert.assertEquals("Preço deve ser R$ X", "R$ 7,50", this.food.getFoodPrice());
    }
}

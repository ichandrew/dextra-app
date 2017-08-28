package br.com.dexfood.dexfood.fooddetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.dexfood.dexfood.R;
import br.com.dexfood.dexfood.data.Food;
import br.com.dexfood.dexfood.data.Ingredient;
import br.com.dexfood.dexfood.foods.FoodsFragment;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 26/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class FoodDetailsAdapter extends RecyclerView.Adapter<FoodDetailsHolder> {

    private final List<Ingredient> mIngredients;
    private Context context;
    private RecyclerView mRecyclerView;
    private FoodDetailsActivity mView;

    public FoodDetailsAdapter(List<Ingredient> ingredients, Context context, FoodDetailsActivity view, RecyclerView recyclerView) {
        this.mIngredients  = ingredients;
        this.context       = context;
        this.mRecyclerView = recyclerView;
        this.mView         = view;
    }

    @Override
    public FoodDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_details_list_view, parent, false);
        return new FoodDetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(final FoodDetailsHolder holder, int position) {
        String ingredient = this.mIngredients.get(position).getQuantity() + "x " + this.mIngredients.get(position).getName();
        holder.ingredient.setText(ingredient);
    }

    @Override
    public int getItemCount() {
        return (this.mIngredients != null) ? this.mIngredients.size() : 0;
    }
}

class FoodDetailsHolder extends RecyclerView.ViewHolder {

    public TextView ingredient;

    public FoodDetailsHolder(View itemView) {
        super(itemView);

        this.ingredient = (TextView) itemView.findViewById(R.id.food_details_list_view_ingredient);
    }
}
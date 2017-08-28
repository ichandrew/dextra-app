package br.com.dexfood.dexfood.foods;

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

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 26/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class FoodsAdapter extends RecyclerView.Adapter<FoodsHolder> {

    private final List<Food> mFoods;
    private Context context;
    private RecyclerView mRecyclerView;
    private FoodsFragment mView;

    public FoodsAdapter(ArrayList<Food> foods, Context context, FoodsFragment view, RecyclerView recyclerView) {
        this.mFoods  = foods;
        this.context = context;
        this.mRecyclerView = recyclerView;
        this.mView = view;
    }

    @Override
    public FoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_view, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mRecyclerView.getChildAdapterPosition(v);
                mView.showFoodDetail(mFoods.get(pos));
            }
        });
        return new FoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(final FoodsHolder holder, int position) {
        holder.name.setText(this.mFoods.get(position).getName());
        holder.ingredients.setText(this.mFoods.get(position).getListIngredients());
        holder.price.setText(this.mFoods.get(position).getFoodPrice());
        Glide.with(this.context).load(this.mFoods.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return (this.mFoods != null) ? this.mFoods.size() : 0;
    }
}

class FoodsHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView ingredients;
    public TextView price;
    public ImageView image;

    public FoodsHolder(View itemView) {
        super(itemView);

        this.name        = (TextView) itemView.findViewById(R.id.food_list_view_name);
        this.ingredients = (TextView) itemView.findViewById(R.id.food_list_view_ingredients);
        this.price       = (TextView) itemView.findViewById(R.id.food_list_view_price);
        this.image       = (ImageView) itemView.findViewById(R.id.food_list_view_image);
    }
}
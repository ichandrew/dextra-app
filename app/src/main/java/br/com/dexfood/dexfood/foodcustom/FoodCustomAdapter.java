package br.com.dexfood.dexfood.foodcustom;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.dexfood.dexfood.R;
import br.com.dexfood.dexfood.data.Ingredient;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 28/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class FoodCustomAdapter extends RecyclerView.Adapter<FoodCustomHolder> {

    private final List<Ingredient> mIngredients;
    private Context context;
    private RecyclerView mRecyclerView;
    private FoodCustomActivity mView;

    public FoodCustomAdapter(List<Ingredient> ingredients, Context context, FoodCustomActivity view, RecyclerView recyclerView) {
        this.mIngredients  = ingredients;
        this.context       = context;
        this.mRecyclerView = recyclerView;
        this.mView         = view;
    }

    @Override
    public FoodCustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_custom_list_view, parent, false);
        return new FoodCustomHolder(view);
    }

    @Override
    public void onBindViewHolder(final FoodCustomHolder holder, final int position) {
        String quant = this.mIngredients.get(position).getQuantity() + "x";
        holder.number.setText(quant);
        holder.name.setText(this.mIngredients.get(position).getName());
        Glide.with(this.context).load(this.mIngredients.get(position).getImage()).into(holder.image);

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIngredients.get(position).increseQuantity();
                notifyItemChanged(position);
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIngredients.get(position).decreaseQuantity();
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (this.mIngredients != null) ? this.mIngredients.size() : 0;
    }
}

class FoodCustomHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView number;
    public TextView name;
    public Button plus;
    public Button minus;

    public FoodCustomHolder(View itemView) {
        super(itemView);

        this.image  = (ImageView) itemView.findViewById(R.id.food_custom_list_view_image);
        this.number = (TextView) itemView.findViewById(R.id.food_custom_list_view_number);
        this.name   = (TextView) itemView.findViewById(R.id.food_custom_list_view_name);
        this.plus   = (Button) itemView.findViewById(R.id.food_custom_plus);
        this.minus  = (Button) itemView.findViewById(R.id.food_custom_minus);
    }
}

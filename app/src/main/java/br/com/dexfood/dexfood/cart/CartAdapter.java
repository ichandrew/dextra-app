package br.com.dexfood.dexfood.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.dexfood.dexfood.R;
import br.com.dexfood.dexfood.data.FoodOrder;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 27/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class CartAdapter extends RecyclerView.Adapter<CartHolder> {

    private final List<FoodOrder> mOrders;
    private Context context;
    private RecyclerView mRecyclerView;
    private CartFragment mView;

    public CartAdapter(List<FoodOrder> orders, Context context, CartFragment view, RecyclerView recyclerView) {
        this.mOrders       = orders;
        this.context       = context;
        this.mRecyclerView = recyclerView;
        this.mView         = view;
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_view, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mRecyclerView.getChildAdapterPosition(v);
//                mIngredients.get(pos);
            }
        });
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, int position) {
        if (this.mOrders.get(position).getExtras().size() > 0)
            holder.name.setText(this.mOrders.get(position).getName() + " " + this.context.getString(R.string.my_way));
        else
            holder.name.setText(this.mOrders.get(position).getName());
        Glide.with(this.context).load(this.mOrders.get(position).getImage()).into(holder.image);
        if (this.mOrders.get(position).getIngredients().size() > 0)
            holder.price.setText(this.mOrders.get(position).getFoodPrice());
    }

    @Override
    public int getItemCount() {
        return (this.mOrders != null) ? this.mOrders.size() : 0;
    }
}

class CartHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView name;
    public TextView ingredients;
    public TextView price;

    public CartHolder(View itemView) {
        super(itemView);

        this.image       = (ImageView) itemView.findViewById(R.id.cart_list_view_image);
        this.name        = (TextView) itemView.findViewById(R.id.food_list_view_name);
        this.ingredients = (TextView) itemView.findViewById(R.id.food_list_view_ingredients);
        this.price       = (TextView) itemView.findViewById(R.id.food_list_view_price);
    }
}

package br.com.dexfood.dexfood.promotions;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.dexfood.dexfood.R;
import br.com.dexfood.dexfood.data.Promotion;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 27/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class PromotionsAdapter extends RecyclerView.Adapter<PromotionsHolder> {

    private final List<Promotion> mPromotions;

    public PromotionsAdapter(List<Promotion> promotions) {
        this.mPromotions   = promotions;
    }

    @Override
    public PromotionsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promotion_list_view, parent, false);
        return new PromotionsHolder(view);
    }

    @Override
    public void onBindViewHolder(final PromotionsHolder holder, int position) {
        holder.title.setText(this.mPromotions.get(position).getName());
        holder.description.setText(this.mPromotions.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return (this.mPromotions != null) ? this.mPromotions.size() : 0;
    }
}

class PromotionsHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView description;

    public PromotionsHolder(View itemView) {
        super(itemView);

        this.title       = (TextView) itemView.findViewById(R.id.promotion_list_view_title);
        this.description = (TextView) itemView.findViewById(R.id.promotion_list_view_description);
    }
}
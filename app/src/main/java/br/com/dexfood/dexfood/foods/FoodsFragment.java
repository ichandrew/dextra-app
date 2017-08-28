package br.com.dexfood.dexfood.foods;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.dexfood.dexfood.R;
import br.com.dexfood.dexfood.data.Food;
import br.com.dexfood.dexfood.fooddetail.FoodDetailsActivity;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 26/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class FoodsFragment extends Fragment implements FoodsContract.View {

    private FoodsPresenter mPresenter;

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private TextView mEmpty;
    private FoodsAdapter mAdapter;

    public FoodsFragment() {
        // Constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_foods, container, false);

        mPresenter   = new FoodsPresenter(this);
        mProgressBar = (ProgressBar) view.findViewById(R.id.food_progressBar);
        mEmpty       = (TextView) view.findViewById(R.id.food_empty);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.food_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mPresenter.loadFoods();

        return view;
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active)
            this.mProgressBar.setVisibility(View.VISIBLE);
        else
            this.mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setEmptyMessage(boolean active) {
        if (active)
            this.mEmpty.setVisibility(View.VISIBLE);
        else
            this.mEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showFoods(ArrayList<Food> foods) {
        mAdapter = new FoodsAdapter(foods, getContext(), this, this.mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void showFoodDetail(Food food) {
        Intent intent = new Intent(getActivity().getApplicationContext(), FoodDetailsActivity.class);
        intent.putExtra("food_id", food.getId());
        startActivity(intent);
    }
}
package br.com.dexfood.dexfood.cart;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.com.dexfood.dexfood.R;
import br.com.dexfood.dexfood.data.FoodOrder;
import br.com.dexfood.dexfood.foods.FoodsAdapter;

public class CartFragment extends Fragment implements CartContract.View {

    private CartPresenter mPresenter;
    private ProgressBar mProgressBar;
    private TextView mEmpty;
    private TextView mTotalPrice;
    private RecyclerView mRecycler;
    private CartAdapter mAdapter;

    public CartFragment() {
        // Constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        this.mPresenter   = new CartPresenter(this);
        this.mProgressBar = (ProgressBar) view.findViewById(R.id.cart_progressBar);
        this.mEmpty       = (TextView) view.findViewById(R.id.cart_empty);
        this.mTotalPrice  = (TextView) view.findViewById(R.id.cart_totalPrice);
        this.mRecycler    = (RecyclerView) view.findViewById(R.id.cart_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        this.mRecycler.setLayoutManager(layoutManager);

        this.mPresenter.loadOrders();

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
    public void showOrders(List<FoodOrder> orders) {
        mAdapter = new CartAdapter(orders, getContext(), this, this.mRecycler);
        mRecycler.setAdapter(mAdapter);
        mRecycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void notifyRecycler(int position) {
        this.mAdapter.notifyItemChanged(position);
    }

    @Override
    public void setTotalPrice(String price) {
        this.mTotalPrice.setText(price);
    }
}

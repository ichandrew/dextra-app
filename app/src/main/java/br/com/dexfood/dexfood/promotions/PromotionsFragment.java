package br.com.dexfood.dexfood.promotions;

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
import br.com.dexfood.dexfood.data.Promotion;

public class PromotionsFragment extends Fragment implements PromotionsContract.View {

    private PromotionsPresenter mPresenter;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private TextView mEmpty;
    private PromotionsAdapter mAdapter;

    public PromotionsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_promotions, container, false);

        this.mPresenter   = new PromotionsPresenter(this);
        this.mProgressBar = (ProgressBar) view.findViewById(R.id.promotion_progressBar);
        mEmpty            = (TextView) view.findViewById(R.id.promotion_empty);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.promotion_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mPresenter.loadPromotions();

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
    public void showPromotions(List<Promotion> promotions) {
        mAdapter = new PromotionsAdapter(promotions);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }
}

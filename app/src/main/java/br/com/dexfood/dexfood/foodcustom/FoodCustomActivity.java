package br.com.dexfood.dexfood.foodcustom;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.dexfood.dexfood.R;
import br.com.dexfood.dexfood.data.Ingredient;

public class FoodCustomActivity extends AppCompatActivity implements FoodCustomContract.View {

    private Toolbar mToolbar;
    private RecyclerView mRecycler;
    private FoodCustomAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mEmpty;
    private Button mConfirm;
    private List<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_custom);

        this.mToolbar = (Toolbar) findViewById(R.id.food_custom_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.food_custom_title));

        this.mProgressBar = (ProgressBar) findViewById(R.id.food_custom_progressBar);
        this.mEmpty       = (TextView) findViewById(R.id.food_custom_empty);
        this.mConfirm     = (Button) findViewById(R.id.food_custom_confirm_button);

        this.mRecycler = (RecyclerView) findViewById(R.id.food_custom_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycler.setLayoutManager(layoutManager);

        ingredients = (List) getIntent().getParcelableArrayListExtra("ingredients");
        this.showIngredients(ingredients);

        this.mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("newIngredients", (ArrayList<? extends Parcelable>) ingredients);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {
        mAdapter = new FoodCustomAdapter(ingredients, getApplicationContext(), this, this.mRecycler);
        mRecycler.setAdapter(mAdapter);
        mRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}

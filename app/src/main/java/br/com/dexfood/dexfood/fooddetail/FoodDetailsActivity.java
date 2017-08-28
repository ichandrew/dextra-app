package br.com.dexfood.dexfood.fooddetail;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.dexfood.dexfood.R;
import br.com.dexfood.dexfood.data.Food;
import br.com.dexfood.dexfood.data.Ingredient;
import br.com.dexfood.dexfood.foodcustom.FoodCustomActivity;
import br.com.dexfood.dexfood.foods.FoodsAdapter;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 26/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class FoodDetailsActivity extends AppCompatActivity implements FoodDetailsContract.View {

    private ImageView mImage;
    private TextView mName, mPrice, mPromotion;
    private Button mCustomize, mAddToCart;
    private FoodDetailsPresenter mPresenter;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private FoodDetailsAdapter mAdapter;
    private List<Ingredient> ingredients;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        this.mToolbar = (Toolbar) findViewById(R.id.food_details_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.food_detail_title));

        this.mPresenter = new FoodDetailsPresenter(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.food_details_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);

        this.mImage     = (ImageView) findViewById(R.id.food_details_image);
        this.mName      = (TextView)  findViewById(R.id.food_details_name);
        this.mPrice     = (TextView)  findViewById(R.id.food_details_price);
        this.mPromotion = (TextView)  findViewById(R.id.food_details_promotion);
        this.mCustomize = (Button)    findViewById(R.id.food_details_custom);
        this.mAddToCart = (Button)    findViewById(R.id.food_details_add_cart);

        final int food_id = getIntent().getIntExtra("food_id", 0);

        this.mCustomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodDetailsActivity.this, FoodCustomActivity.class);
                intent.putParcelableArrayListExtra("ingredients", (ArrayList<? extends Parcelable>) ingredients);
                startActivityForResult(intent, 1);
            }
        });

        this.mAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addFoodToCart(food_id, ingredients);
            }
        });

        this.mPresenter.getFoodDetails(food_id);
    }

    @Override
    public void showFoodDetail(Food food) {
        this.name = food.getName();
        this.mName.setText(food.getName());
        Glide.with(this).load(food.getImage()).into(this.mImage);
        this.ingredients = food.getIngredients();
        this.mPrice.setText(this.mPresenter.getPrice(this.ingredients));

        mAdapter = new FoodDetailsAdapter(food.getIngredients(), getApplicationContext(), this, this.mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void setCustomizedLabel(boolean active, String name) {
        if (active)
            this.mName.setText(name + " " + getString(R.string.my_way));
        else
            this.mName.setText(name);
    }

    @Override
    public void setPromotion(boolean active, String message) {
        if (active)
            this.mPromotion.setVisibility(View.VISIBLE);
        else
            this.mPromotion.setVisibility(View.VISIBLE);
        this.mPromotion.setText(message);
    }

    @Override
    public void foodAddedToCart() {
        Toast.makeText(getApplicationContext(), getString(R.string.success_add_cart) + " " + this.mName.getText(), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                List<Ingredient> newIngredients = (List) bundle.getParcelableArrayList("newIngredients");

                this.mPresenter.clone(this.ingredients, newIngredients);
                this.mAdapter.notifyDataSetChanged();
                this.setCustomizedLabel(this.mPresenter.isCustomized(this.ingredients), this.name);
                this.mPrice.setText(this.mPresenter.getPrice(this.ingredients));
            }
        }
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
        finish();
    }
}

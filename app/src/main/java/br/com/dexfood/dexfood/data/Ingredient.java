package br.com.dexfood.dexfood.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 26/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public class Ingredient implements Parcelable {
    private int mId;
    private String mName;
    private double mPrice;
    private String mImage;
    private int quantity;

    public Ingredient (int id, String name, double price, String image) {
        this.mId    = id;
        this.mName  = name;
        this.mPrice = price;
        this.mImage = image;
        this.quantity = 1;
    }

    public Ingredient (JSONObject ingredient) {
        try {
            this.mId = ingredient.getInt("id");
            this.mName = ingredient.getString("name");
            this.mPrice = ingredient.getDouble("price");
            this.mImage = ingredient.getString("image");
            this.quantity = 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public double getPrice() {
        return mPrice;
    }

    public String getImage() {
        return mImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        if (this.quantity > 0)
            this.quantity--;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
        dest.writeDouble(this.mPrice);
        dest.writeString(this.mImage);
        dest.writeInt(this.quantity);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public Ingredient(Parcel in) {
        this.mId      = in.readInt();
        this.mName    = in.readString();
        this.mPrice   = in.readDouble();
        this.mImage   = in.readString();
        this.quantity = in.readInt();
    }
}
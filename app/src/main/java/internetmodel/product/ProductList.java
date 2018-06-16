package internetmodel.product;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class ProductList implements Parcelable {
    private Product[] mProducts;

    public Product[] getProducts() {
        return mProducts;
    }

    public void setProducts(Product[] products) {
        mProducts = products;
    }

    public static Creator<ProductList> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(this.mProducts, flags);
    }

    public ProductList() {
    }

    protected ProductList(Parcel in) {
        this.mProducts = in.createTypedArray(Product.CREATOR);
    }

    public static final Creator<ProductList> CREATOR = new Creator<ProductList>() {
        @Override
        public ProductList createFromParcel(Parcel source) {
            return new ProductList(source);
        }

        @Override
        public ProductList[] newArray(int size) {
            return new ProductList[size];
        }
    };

    @Override
    public String toString() {
        return "ProductList{" +
                "mProducts=" + Arrays.toString(mProducts) +
                '}';
    }
}

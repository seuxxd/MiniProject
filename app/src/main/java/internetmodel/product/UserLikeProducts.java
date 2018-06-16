package internetmodel.product;

import android.os.Parcel;
import android.os.Parcelable;

public class UserLikeProducts implements Parcelable{
    private Product[] products;

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(this.products, flags);
    }

    public UserLikeProducts() {
    }

    protected UserLikeProducts(Parcel in) {
        this.products = in.createTypedArray(Product.CREATOR);
    }

    public static final Creator<UserLikeProducts> CREATOR = new Creator<UserLikeProducts>() {
        @Override
        public UserLikeProducts createFromParcel(Parcel source) {
            return new UserLikeProducts(source);
        }

        @Override
        public UserLikeProducts[] newArray(int size) {
            return new UserLikeProducts[size];
        }
    };
}

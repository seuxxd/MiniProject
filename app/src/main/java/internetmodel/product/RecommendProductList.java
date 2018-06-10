package internetmodel.product;

import android.os.Parcel;
import android.os.Parcelable;

public class RecommendProductList implements Parcelable {
    private RecommendProduct[] mProducts;

    public RecommendProduct[] getProducts() {
        return mProducts;
    }

    public void setProducts(RecommendProduct[] products) {
        mProducts = products;
    }

    public static Creator<RecommendProductList> getCREATOR() {
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

    public RecommendProductList() {
    }

    protected RecommendProductList(Parcel in) {
        this.mProducts = in.createTypedArray(RecommendProduct.CREATOR);
    }

    public static final Creator<RecommendProductList> CREATOR = new Creator<RecommendProductList>() {
        @Override
        public RecommendProductList createFromParcel(Parcel source) {
            return new RecommendProductList(source);
        }

        @Override
        public RecommendProductList[] newArray(int size) {
            return new RecommendProductList[size];
        }
    };
}

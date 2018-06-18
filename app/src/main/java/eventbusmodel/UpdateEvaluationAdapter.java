package eventbusmodel;

import android.os.Parcel;
import android.os.Parcelable;

import internetmodel.product.Product;

/**
 * Created by SEUXXD on 2018/6/18.
 */

public class UpdateEvaluationAdapter implements Parcelable {
    Product mProduct;

    public UpdateEvaluationAdapter(Product product) {
        mProduct = product;
    }

    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product product) {
        mProduct = product;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mProduct, flags);
    }

    public UpdateEvaluationAdapter() {
    }

    protected UpdateEvaluationAdapter(Parcel in) {
        this.mProduct = in.readParcelable(Product.class.getClassLoader());
    }

    public static final Creator<UpdateEvaluationAdapter> CREATOR = new Creator<UpdateEvaluationAdapter>() {
        @Override
        public UpdateEvaluationAdapter createFromParcel(Parcel source) {
            return new UpdateEvaluationAdapter(source);
        }

        @Override
        public UpdateEvaluationAdapter[] newArray(int size) {
            return new UpdateEvaluationAdapter[size];
        }
    };

    @Override
    public String toString() {
        return "UpdateEvaluationAdapter{" +
                "mProduct=" + mProduct +
                '}';
    }
}

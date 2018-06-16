package internetmodel.product;

import android.os.Parcel;
import android.os.Parcelable;

public class UserLikeProduct implements Parcelable {
    private String id;
    private String name;
    private String price;
    private String volume;
    private String effect;
    private String category;
    private String imgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.volume);
        dest.writeString(this.effect);
        dest.writeString(this.category);
        dest.writeString(this.imgUrl);
    }

    public UserLikeProduct() {
    }

    protected UserLikeProduct(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.volume = in.readString();
        this.effect = in.readString();
        this.category = in.readString();
        this.imgUrl = in.readString();
    }

    public static final Creator<UserLikeProduct> CREATOR = new Creator<UserLikeProduct>() {
        @Override
        public UserLikeProduct createFromParcel(Parcel source) {
            return new UserLikeProduct(source);
        }

        @Override
        public UserLikeProduct[] newArray(int size) {
            return new UserLikeProduct[size];
        }
    };
}

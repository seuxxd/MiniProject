package internetmodel.product;

import android.os.Parcel;
import android.os.Parcelable;

public class RecommendProduct implements Parcelable{
    private int id;
    private String name;
    private String price;
    private String volume;
    private String effect;
    private String clazz;
    private String isFavorited;
    private String imgUrl;


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(String isFavorited) {
        this.isFavorited = isFavorited;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static Creator<RecommendProduct> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.volume);
        dest.writeString(this.effect);
        dest.writeString(this.clazz);
        dest.writeString(this.isFavorited);
        dest.writeString(this.imgUrl);
    }

    public RecommendProduct() {
    }

    protected RecommendProduct(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.price = in.readString();
        this.volume = in.readString();
        this.effect = in.readString();
        this.clazz = in.readString();
        this.isFavorited = in.readString();
        this.imgUrl = in.readString();
    }

    public static final Creator<RecommendProduct> CREATOR = new Creator<RecommendProduct>() {
        @Override
        public RecommendProduct createFromParcel(Parcel source) {
            return new RecommendProduct(source);
        }

        @Override
        public RecommendProduct[] newArray(int size) {
            return new RecommendProduct[size];
        }
    };
}

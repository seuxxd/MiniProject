package internetmodel.product;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable{
    private String id;
    private String name;
    private String price;
    private String description;
    private String volume;
    private String effect;
    private String category;
    private String hot;
    private String isMeFavorite;
    private String imgUrl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", volume='" + volume + '\'' +
                ", effect='" + effect + '\'' +
                ", category='" + category + '\'' +
                ", hot='" + hot + '\'' +
                ", isMeFavorite='" + isMeFavorite + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getIsMeFavorite() {
        return isMeFavorite;
    }

    public void setIsMeFavorite(String isMeFavorite) {
        this.isMeFavorite = isMeFavorite;
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
        dest.writeString(this.description);
        dest.writeString(this.volume);
        dest.writeString(this.effect);
        dest.writeString(this.category);
        dest.writeString(this.hot);
        dest.writeString(this.isMeFavorite);
        dest.writeString(this.imgUrl);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.description = in.readString();
        this.volume = in.readString();
        this.effect = in.readString();
        this.category = in.readString();
        this.hot = in.readString();
        this.isMeFavorite = in.readString();
        this.imgUrl = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

}

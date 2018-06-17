package commonmodels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2018/6/18.
 */

public class SkinInfo implements Parcelable {
    private String dark_circle;
    private String stain;
    private String acne;
    private String health;

    public String getDark_circle() {
        return dark_circle;
    }

    public void setDark_circle(String dark_circle) {
        this.dark_circle = dark_circle;
    }

    public String getStain() {
        return stain;
    }

    public void setStain(String stain) {
        this.stain = stain;
    }

    public String getAcne() {
        return acne;
    }

    public void setAcne(String acne) {
        this.acne = acne;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dark_circle);
        dest.writeString(this.stain);
        dest.writeString(this.acne);
        dest.writeString(this.health);
    }

    public SkinInfo() {
    }

    protected SkinInfo(Parcel in) {
        this.dark_circle = in.readString();
        this.stain = in.readString();
        this.acne = in.readString();
        this.health = in.readString();
    }

    public static final Creator<SkinInfo> CREATOR = new Creator<SkinInfo>() {
        @Override
        public SkinInfo createFromParcel(Parcel source) {
            return new SkinInfo(source);
        }

        @Override
        public SkinInfo[] newArray(int size) {
            return new SkinInfo[size];
        }
    };
}

package internetmodel;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by SEUXXD on 2018/6/8.
 */

public class User implements Parcelable {

    private String username;
    private String password;
    private String phone;

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.phone);
    }

    protected User(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
        this.phone = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}

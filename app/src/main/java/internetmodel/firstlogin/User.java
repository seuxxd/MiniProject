package internetmodel.firstlogin;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by SEUXXD on 2018/6/8.
 */

public class User implements Parcelable {

    private String openID;
    private String nickname;
    private String sex;
    private String birthday;

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    public User() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.openID);
        dest.writeString(this.nickname);
        dest.writeString(this.sex);
        dest.writeString(this.birthday);
    }

    protected User(Parcel in) {
        this.openID = in.readString();
        this.nickname = in.readString();
        this.sex = in.readString();
        this.birthday = in.readString();
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

package internetmodel.login;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginResult implements Parcelable{
    private String nickname;
    private String sex;
    private String birthday;

    private String statusCode;

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

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nickname);
        dest.writeString(this.sex);
        dest.writeString(this.birthday);
        dest.writeString(this.statusCode);
    }

    public LoginResult() {
    }

    protected LoginResult(Parcel in) {
        this.nickname = in.readString();
        this.sex = in.readString();
        this.birthday = in.readString();
        this.statusCode = in.readString();
    }

    public static final Creator<LoginResult> CREATOR = new Creator<LoginResult>() {
        @Override
        public LoginResult createFromParcel(Parcel source) {
            return new LoginResult(source);
        }

        @Override
        public LoginResult[] newArray(int size) {
            return new LoginResult[size];
        }
    };
}

package internetmodel.register;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2018/6/8.
 */

public class RegisterResult implements Parcelable{
    private String statusCode;

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
        dest.writeString(this.statusCode);
    }

    public RegisterResult() {
    }

    protected RegisterResult(Parcel in) {
        this.statusCode = in.readString();
    }

    public static final Creator<RegisterResult> CREATOR = new Creator<RegisterResult>() {
        @Override
        public RegisterResult createFromParcel(Parcel source) {
            return new RegisterResult(source);
        }

        @Override
        public RegisterResult[] newArray(int size) {
            return new RegisterResult[size];
        }
    };
}

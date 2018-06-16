package internetmodel.mycomment;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentResult implements Parcelable{
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

    public CommentResult() {
    }

    protected CommentResult(Parcel in) {
        this.statusCode = in.readString();
    }

    public static final Creator<CommentResult> CREATOR = new Creator<CommentResult>() {
        @Override
        public CommentResult createFromParcel(Parcel source) {
            return new CommentResult(source);
        }

        @Override
        public CommentResult[] newArray(int size) {
            return new CommentResult[size];
        }
    };
}

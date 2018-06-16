package internetmodel.mycomment;

import android.os.Parcel;
import android.os.Parcelable;

public class UploadComment implements Parcelable {
    private String refereeNickName;
    private String refereeRecommendLevel;
    private String refereeCommentContent;

    public String getRefereeNickName() {
        return refereeNickName;
    }

    @Override
    public String toString() {
        return "UploadComment{" +
                "refereeNickName='" + refereeNickName + '\'' +
                ", refereeRecommendLevel='" + refereeRecommendLevel + '\'' +
                ", refereeCommentContent='" + refereeCommentContent + '\'' +
                '}';
    }

    public void setRefereeNickName(String refereeNickName) {
        this.refereeNickName = refereeNickName;
    }

    public String getRefereeRecommendLevel() {
        return refereeRecommendLevel;
    }

    public void setRefereeRecommendLevel(String refereeRecommandLevel) {
        this.refereeRecommendLevel = refereeRecommandLevel;
    }

    public String getRefereeCommentContent() {
        return refereeCommentContent;
    }

    public void setRefereeCommentContent(String refereeCommentContent) {
        this.refereeCommentContent = refereeCommentContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.refereeNickName);
        dest.writeString(this.refereeRecommendLevel);
        dest.writeString(this.refereeCommentContent);
    }

    public UploadComment() {
    }

    protected UploadComment(Parcel in) {
        this.refereeNickName = in.readString();
        this.refereeRecommendLevel = in.readString();
        this.refereeCommentContent = in.readString();
    }

    public static final Creator<UploadComment> CREATOR = new Creator<UploadComment>() {
        @Override
        public UploadComment createFromParcel(Parcel source) {
            return new UploadComment(source);
        }

        @Override
        public UploadComment[] newArray(int size) {
            return new UploadComment[size];
        }
    };
}

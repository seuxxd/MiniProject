package internetmodel.poem;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2018/6/18.
 */

public class Poem implements Parcelable {
    String id;
    String sex;
    String content;

    @Override
    public String toString() {
        return "Poem{" +
                "id='" + id + '\'' +
                ", sex='" + sex + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.sex);
        dest.writeString(this.content);
    }

    public Poem() {
    }

    protected Poem(Parcel in) {
        this.id = in.readString();
        this.sex = in.readString();
        this.content = in.readString();
    }

    public static final Creator<Poem> CREATOR = new Creator<Poem>() {
        @Override
        public Poem createFromParcel(Parcel source) {
            return new Poem(source);
        }

        @Override
        public Poem[] newArray(int size) {
            return new Poem[size];
        }
    };
}

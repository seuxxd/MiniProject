package internetmodel.diary;

import android.os.Parcel;
import android.os.Parcelable;

public class DiaryInfo implements Parcelable{
     private String food;
     private String sport;
     private String others;
     private String emotion;
     private String feeling;
     private String date;
     private String cbre;
     private String cp;
     private String cs;
     private String skinState;

    @Override
    public String toString() {
        return "DiaryInfo{" +
                "food='" + food + '\'' +
                ", sport='" + sport + '\'' +
                ", others='" + others + '\'' +
                ", emotion='" + emotion + '\'' +
                ", feeling='" + feeling + '\'' +
                ", date='" + date + '\'' +
                ", cbre='" + cbre + '\'' +
                ", cp='" + cp + '\'' +
                ", cs='" + cs + '\'' +
                ", skinState='" + skinState + '\'' +
                '}';
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCbre() {
        return cbre;
    }

    public void setCbre(String cbre) {
        this.cbre = cbre;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getSkinState() {
        return skinState;
    }

    public void setSkinState(String skinState) {
        this.skinState = skinState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.food);
        dest.writeString(this.sport);
        dest.writeString(this.others);
        dest.writeString(this.emotion);
        dest.writeString(this.feeling);
        dest.writeString(this.date);
        dest.writeString(this.cbre);
        dest.writeString(this.cp);
        dest.writeString(this.cs);
        dest.writeString(this.skinState);
    }

    public DiaryInfo() {
    }

    protected DiaryInfo(Parcel in) {
        this.food = in.readString();
        this.sport = in.readString();
        this.others = in.readString();
        this.emotion = in.readString();
        this.feeling = in.readString();
        this.date = in.readString();
        this.cbre = in.readString();
        this.cp = in.readString();
        this.cs = in.readString();
        this.skinState = in.readString();
    }

    public static final Creator<DiaryInfo> CREATOR = new Creator<DiaryInfo>() {
        @Override
        public DiaryInfo createFromParcel(Parcel source) {
            return new DiaryInfo(source);
        }

        @Override
        public DiaryInfo[] newArray(int size) {
            return new DiaryInfo[size];
        }
    };
}

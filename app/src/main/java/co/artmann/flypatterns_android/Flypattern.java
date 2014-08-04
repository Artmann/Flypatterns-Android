package co.artmann.flypatterns_android;

import android.os.Parcel;
import android.os.Parcelable;

public class Flypattern implements Parcelable {
    private int id = 0;
    private String name = "";
    private String thumbnail = "";

    private String baseURL = "http://www.flypatterns.co";

    public Flypattern(int id, String name, String thumbnail) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getThumbnail() {
        return this.baseURL + this.thumbnail;
    }

    protected Flypattern(Parcel in) {
        id = in.readInt();
        name = in.readString();
        thumbnail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(thumbnail);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Flypattern> CREATOR = new Parcelable.Creator<Flypattern>() {
        @Override
        public Flypattern createFromParcel(Parcel in) {
            return new Flypattern(in);
        }

        @Override
        public Flypattern[] newArray(int size) {
            return new Flypattern[size];
        }
    };
}

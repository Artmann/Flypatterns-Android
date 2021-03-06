package co.artmann.flypatterns_android;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Flypattern implements Parcelable {
    private int id = 0;
    private String name = "";
    private String thumbnail = "";
    private String text = "";
    private String image = "";
    private ArrayList<PatternSpec> specs = null;

    public static String baseURL = "http://www.flypatterns.co";

    public Flypattern(int id, String name, String thumbnail) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.specs = new ArrayList<PatternSpec>();
    }

    public void addSpec(PatternSpec ps) {
        if(this.specs == null) {
            specs = new ArrayList<PatternSpec>();
        }
        this.specs.add(ps);
    }

    public ArrayList<PatternSpec> getSpecs() {
        return this.specs;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getThumbnail() {
        return Flypattern.baseURL + thumbnail;
    }

    public String getText() {
        return this.text;
    }

    public String getImage() {
        return this.image.length() > 0 ? Flypattern.baseURL + this.image : this.getThumbnail();
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImage(String image) {
        this.image = image;
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

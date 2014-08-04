package co.artmann.flypatterns_android;

import android.app.Activity;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Flypattern> patterns;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;

    public LazyAdapter(Activity a, ArrayList<Flypattern> patterns) {
        this.activity = a;
        this.patterns = patterns;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return this.patterns.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public void setData(ArrayList<Flypattern> patterns) {
        this.patterns = patterns;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView == null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView nameView = (TextView) vi.findViewById(R.id.name);
        ImageView thumbView = (ImageView) vi.findViewById(R.id.list_image);

        Flypattern pattern = this.patterns.get(position);

        // Setting all values in listview
        nameView.setText(pattern.getName());
        imageLoader.DisplayImage(pattern.getThumbnail(), thumbView);
        return vi;
    }
}

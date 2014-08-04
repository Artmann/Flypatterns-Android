package co.artmann.flypatterns_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class PatternActivity extends Activity {

    private Flypattern pattern = null;

    private TextView nameView = null;
    private ImageView imageView = null;
    private ImageLoader imageLoader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);

        imageLoader = new ImageLoader(getApplicationContext());
        Intent i = getIntent();
        nameView = (TextView) findViewById(R.id.name);
        imageView = (ImageView) findViewById(R.id.image);

        pattern = (Flypattern) i.getExtras().getParcelable("pattern");
        Log.d("foo", pattern.getName());
        if(this.pattern != null) {
            this.buildInterface();
        }

    }

    public void buildInterface() {
        Log.d("foo", pattern.getName());
        nameView.setText(pattern.getName());
        imageLoader.DisplayImage(pattern.getThumbnail(), imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pattern, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

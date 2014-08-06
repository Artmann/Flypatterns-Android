package co.artmann.flypatterns_android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class PatternActivity extends Activity {

    private Flypattern pattern = null;

    private TextView nameView = null;
    private TextView textView = null;
    private ImageView imageView = null;
    private ImageLoader imageLoader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);

        imageLoader = new ImageLoader(getApplicationContext());
        Intent i = getIntent();
        nameView = (TextView) findViewById(R.id.name);
        textView = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.image);

        pattern = (Flypattern) i.getExtras().getParcelable("pattern");
        Log.d("foo", pattern.getName());
        if(this.pattern != null) {
            this.buildInterface();
        }

        new GetPatternData().execute();
    }

    public void buildInterface() {
        Log.d("foo", pattern.getName());
        nameView.setText(pattern.getName());
        textView.setText(Html.fromHtml(pattern.getText()));
        String image = pattern.getImage();
        Log.d("foo", image);
        imageLoader.DisplayImage(image, imageView);
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

    private class GetPatternData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.d("foo", "GetPatternData");
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall("http://www.flypatterns.co/pattern/"+pattern.getID()+".json", ServiceHandler.GET);
            try {
                JSONObject obj = new JSONObject(jsonStr);
                String text = obj.getString("text");
                String image = obj.getString("photo_file_name");
                pattern.setText(text);
                pattern.setImage(image);
            }
            catch(JSONException e) {
                Log.e("Exception", e.getLocalizedMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            buildInterface();
        }
    }
}

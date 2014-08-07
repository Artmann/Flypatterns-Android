package co.artmann.flypatterns_android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PatternActivity extends Activity {

    private Flypattern pattern = null;

    private TextView nameView = null;
    private TextView textView = null;
    private ImageView imageView = null;
    private ImageLoader imageLoader = null;
    private ListView specList = null;
    private ArrayAdapter<PatternSpec> specAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);

        imageLoader = new ImageLoader(getApplicationContext());
        Intent i = getIntent();
        nameView = (TextView) findViewById(R.id.name);
        textView = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.image);
        specList = (ListView) findViewById(R.id.specList);
        specAdapter = new ArrayAdapter<PatternSpec>(this, android.R.layout.simple_list_item_1);
        specList.setAdapter(specAdapter);

        pattern = (Flypattern) i.getExtras().getParcelable("pattern");
        Log.d("foo", pattern.getName());
        if(this.pattern != null) {
            this.buildInterface();
        }

        new GetPatternData().execute();
    }

    public void buildInterface() {
        Log.d("foo", "BuildInterface");
        nameView.setText(pattern.getName());
        textView.setText(Html.fromHtml(pattern.getText()));
        String image = pattern.getImage();
        imageLoader.DisplayImage(image, imageView);

        if(pattern.getSpecs() != null) {
            for(PatternSpec ps : pattern.getSpecs()) {
                specAdapter.add(ps);
            }
            specAdapter.notifyDataSetChanged();
        }
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
                String image = obj.getString("image");
                JSONArray specs = obj.getJSONArray("specs");
                Log.d("foo", "Specs: "+specs.length());

                for(int i = 0; i < specs.length(); i++) {
                    JSONObject sobj = specs.getJSONObject(i);
                    String placement = sobj.getString("placement");
                    String color     = sobj.getString("color");
                    String material  = sobj.getString("material");
                    Log.d("foo", material);
                    PatternSpec spec = new PatternSpec(placement, color, material);
                    pattern.addSpec(spec);
                }

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

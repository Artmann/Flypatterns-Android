package co.artmann.flypatterns_android;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private ArrayList<Flypattern> patterns = null;
    private ArrayList<Flypattern> filteredPatterns = null;
    private LazyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("foo", "Start");

        patterns = new ArrayList<Flypattern>();
        new GetPatterns().execute();

/*
        patterns.add(new Flypattern(1, "Adams", "/assets/patterns/1/small/Adams-side.jpg?1369647019"));
        patterns.add(new Flypattern(2, "Abbey", "/assets/patterns/49/small/abbey.jpg?1369601000"));
        patterns.add(new Flypattern(3, "Alexandra", "/assets/patterns/57/small/alexandra.png?1390907755"));
        patterns.add(new Flypattern(4, "Black and Green", "/assets/patterns/3/small/Black-and-Green-2d16ad1968844a4300e9a490588ff9f8.jpg?1368450969"));
*/
        filteredPatterns = patterns;

        ListView list = (ListView) findViewById(R.id.list);
        adapter = new LazyAdapter(this, filteredPatterns);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

        EditText searchField = (EditText) findViewById(R.id.search_field);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    filteredPatterns = new ArrayList<Flypattern>();
                    for(Flypattern pattern : patterns) {
                        if(pattern.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                            filteredPatterns.add(pattern);

                        }
                    }
                    adapter.setData(filteredPatterns);
                    adapter.notifyDataSetChanged();
                }
                else {
                    filteredPatterns = patterns;
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Flypattern pattern = (Flypattern) this.filteredPatterns.get(position);
        Intent nextScreen = new Intent(getApplicationContext(), PatternActivity.class);

        //Sending data to another Activity
        try {
            nextScreen.putExtra("patternID", pattern.getID());
            nextScreen.putExtra("pattern", pattern);
            startActivity(nextScreen);
        }
        catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
    }

    private class GetPatterns extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.d("foo", "DoInBackground");
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall("http://www.flypatterns.co/pattern.json", ServiceHandler.GET);
            Log.d("foo", jsonStr);

            try {
                JSONArray array = new JSONArray(jsonStr);
                for(int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String name = jsonObject.getString("name");
                    String thumbnail = jsonObject.getString("thumb");

                    Flypattern pattern = new Flypattern(id, name, thumbnail);
                    patterns.add(pattern);

                }
            } catch(JSONException e) {
                Log.e("Exception", e.getLocalizedMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            adapter.setData(filteredPatterns);
            adapter.notifyDataSetChanged();
        }
    }
}


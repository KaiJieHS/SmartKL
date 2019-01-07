package my.edu.tarc.testsmartkl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class FeedbackActivity extends AppCompatActivity {
    public static final String TAG = "my.edu.tarc.testsmartkl";
    ListView listViewFeedback;
    List<Feedback> fbList;
    private ProgressDialog pDialog;

    //TODO: Please update the URL to point to your own server
    private static String SEARCH_URL = "https://circumgyratory-gove.000webhostapp.com/search_feedback.php";
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewFeedback = (ListView) findViewById(R.id.listViewFeedback);
        pDialog = new ProgressDialog(this);
        fbList = new ArrayList<>();

        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_LONG).show();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddFeedbackActivity.class);
                startActivity(intent);
            }
        });

        searchFeedback(getApplicationContext(), 1);

        listViewFeedback.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fbID = String.valueOf(fbList.get(position).getFeedbackID());
                Intent intent = new Intent(FeedbackActivity.this, ViewResponseRecordActivity.class);
                intent.putExtra("currentFeedbackID", fbID);
                startActivity(intent);

            }
        });

    }
    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);

        // Associate searchable configuration with the SearchView


            return false;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_sycn) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void searchFeedback(Context context, int id) {
        queue = Volley.newRequestQueue(context);
        String url = SEARCH_URL + "?CitizenID=" + id;

        if (!pDialog.isShowing())
            pDialog.setMessage("Searching...");
        pDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            fbList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject feedbackResponse = (JSONObject) response.get(i);

                                int fbid = feedbackResponse.getInt("FeedbackID");
                                String type = feedbackResponse.getString("FeedbackType");
                                String subj = feedbackResponse.getString("FeedbackTitle");
                                String desc = feedbackResponse.getString("FeedbackDesc");
                                String date = feedbackResponse.getString("FeedbackDate");
                                int citizenid = feedbackResponse.getInt("CitizenID");
                                Feedback feedback = new Feedback(fbid,type,subj,desc,date,citizenid);
                                fbList.add(feedback);
                            }
                            loadFeedback();
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                    }
                });

        // Set the tag on the request.
        jsonObjectRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void loadFeedback() {
        final FeedbackAdapter adapter = new FeedbackAdapter(this, R.layout.activity_feedback, fbList);
        listViewFeedback.setAdapter(adapter);
        if(fbList != null){
            int size = fbList.size();
            if(size > 0)
                Toast.makeText(getApplicationContext(), "No. of record : " + size + ".", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "No record found.", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //int currentCitizenid= Integer.parseInt(getIntent().getStringExtra("currentCitizenID"));
        searchFeedback(getApplicationContext(), 1);
    }
}

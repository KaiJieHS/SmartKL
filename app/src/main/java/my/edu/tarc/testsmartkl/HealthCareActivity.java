package my.edu.tarc.testsmartkl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HealthCareActivity extends AppCompatActivity {

    public static final String TAG = "my.edu.tarc.testsmartkl";
    ListView listViewHealthCare;
    List<HealthCare> hcList;
    private ProgressDialog pDialog;
    //TODO: Please update the URL to point to your own server
    private static String GET_URL = "https://circumgyratory-gove.000webhostapp.com/search_healthcare.php";
    private static String SEARCH_URL;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_care);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewHealthCare = (ListView) findViewById(R.id.listViewHealthCare);
        pDialog = new ProgressDialog(this);
        hcList = new ArrayList<>();

        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        downloadHealthCare(getApplicationContext(), GET_URL);

        listViewHealthCare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String uri = hcList.get(position).getHcBranchLocation();
                Intent mapIntent = new Intent(Intent.ACTION_VIEW);
                mapIntent.setData(Uri.parse("geo:0,0?q="+uri));
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }


            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    private void searchHealthCare(Context context, int HcID) {
        queue = Volley.newRequestQueue(context);
        String url = SEARCH_URL + "?HcID=" + HcID;

        if (!pDialog.isShowing())
            pDialog.setMessage("Searching...");
        pDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            hcList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject healthCareResponse = (JSONObject) response.get(i);
                                int hcID = Integer.parseInt(healthCareResponse.getString("HcID"));
                                String hcBranchName = healthCareResponse.getString("HcBranchName");
                                String hcBranchLocation = healthCareResponse.getString("HcBranchLocation");
                                String hcContactNumber = healthCareResponse.getString("HcContactNum");
                                HealthCare healthcare = new HealthCare(hcID,hcBranchName,hcBranchLocation,hcContactNumber);
                                hcList.add(healthcare);
                            }
                            loadHealthCare();
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
    private void downloadHealthCare(Context context, String url) {
        // Instantiate the RequestQueue
        queue = Volley.newRequestQueue(context);

        if (!pDialog.isShowing())
            pDialog.setMessage("Sync with server...");
        pDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            hcList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject healthCareResponse = (JSONObject) response.get(i);
                                int hcID = Integer.parseInt(healthCareResponse.getString("HcID"));
                                String hcBranchName = healthCareResponse.getString("HcBranchName");
                                String hcBranchLocation = healthCareResponse.getString("HcBranchLocation");
                                String hcContactNumber = healthCareResponse.getString("HcContactNum");
                                HealthCare healthcare = new HealthCare(hcID,hcBranchName,hcBranchLocation,hcContactNumber);
                                hcList.add(healthcare);
                            }
                            loadHealthCare();
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

    private void loadHealthCare() {
        final HealthCareAdapter adapter = new HealthCareAdapter(this, R.layout.activity_health_care, hcList);
        listViewHealthCare.setAdapter(adapter);
        if(hcList != null){
            int size = hcList.size();
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

}

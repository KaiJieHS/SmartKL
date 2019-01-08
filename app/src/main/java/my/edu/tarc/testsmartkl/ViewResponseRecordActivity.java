package my.edu.tarc.testsmartkl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewResponseRecordActivity extends AppCompatActivity {

    public static final String TAG = "my.edu.tarc.testsmartkl";
    ListView listViewResponse;
    List<FeedbackResponses> resList;
    private ProgressDialog pDialog;
    private EditText editTextRepDesc;
    //TODO: Please update the URL to point to your own server
    private static String SEARCH_URL = "https://circumgyratory-gove.000webhostapp.com/search_feedbackresponse.php";
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_response_record);



        listViewResponse = (ListView) findViewById(R.id.listViewResponse);
        pDialog = new ProgressDialog(this);
        resList = new ArrayList<>();

        editTextRepDesc=findViewById(R.id.editTextrepDesc);
        int searchfeedbackid= Integer.parseInt(getIntent().getStringExtra("currentFeedbackID"));
        searchResponse(getApplicationContext(), searchfeedbackid);

    }


    public void saveRecord(View v) {
        FeedbackResponses feedbackres = new FeedbackResponses();
        //Calendar calender = Calendar.getInstance();
        //String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(((Calendar) calender).getTime());
        Date currentTime = Calendar.getInstance().getTime();

        String searchusertype= getIntent().getStringExtra("currentUserType");
        if(searchusertype.equals("admin")){

        }
        feedbackres.setFeedbackID(3);
        feedbackres.setOfficerID(1);
        feedbackres.setResponseDesc(editTextRepDesc.getText().toString());
        feedbackres.setResponseDate(currentTime.toString());
        //feedbackres.setResponseDate(currentDate);

        try {
            //TODO: Please update the URL to point to your own server
            addResponse(this, "https://circumgyratory-gove.000webhostapp.com/insert_response.php", feedbackres);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void addResponse(Context context, String url, final FeedbackResponses feedbackres) {
        //mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);

        //Send data
        try {
            StringRequest postRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("success");
                                String message = jsonObject.getString("message");
                                if (success==0) {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error. " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("FeedbackID", String.valueOf(feedbackres.getFeedbackID()));
                    params.put("RespDesc", feedbackres.getResponseDesc());
                    params.put("OfficerID", String.valueOf(feedbackres.getOfficerID()));
                    params.put("RespDate", feedbackres.getResponseDate());
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(postRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);

        // Associate searchable configuration with the SearchView


        return false;
    }



    private void searchResponse(Context context, int id) {
        queue = Volley.newRequestQueue(context);
        String url = SEARCH_URL + "?FeedbackID=" + id;

        if (!pDialog.isShowing())
            pDialog.setMessage("Searching...");
        pDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            resList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject respResponse = (JSONObject) response.get(i);
                                int fbid = respResponse.getInt("FeedbackID");
                                int ofid = respResponse.getInt("OfficerID");
                                int resid = respResponse.getInt("RespID");
                                String desc = respResponse.getString("RespDesc");
                                String date = respResponse.getString("RespDate");
                                FeedbackResponses resp = new FeedbackResponses(resid,desc,date,ofid,fbid);

                                resList.add(resp);
                            }
                            loadResponse();
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

    private void loadResponse() {
        final ResponseAdapter adapter = new ResponseAdapter(this, R.layout.activity_view_response_record, resList);
        listViewResponse.setAdapter(adapter);
        if(resList != null){
            int size = resList.size();
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
package my.edu.tarc.testsmartkl;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReplyResponseActivity extends AppCompatActivity {

    private EditText editTextRepDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_response);

        editTextRepDesc=findViewById(R.id.editTextrepDesc);
    }


    public void saveRecord(View v) {
        FeedbackResponses feedbackres = new FeedbackResponses();
        Date currentTime = Calendar.getInstance().getTime();


        feedbackres.setFeedbackID(1);
        feedbackres.setOfficerID(0);
        feedbackres.setResponseDesc(editTextRepDesc.toString());
        feedbackres.setResponseDate(currentTime.toString());

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
                    params.put("ResponseDesc", feedbackres.getResponseDesc());
                    params.put("OfficerID", String.valueOf(feedbackres.getOfficerID()));
                    params.put("ResponseDate", feedbackres.getResponseDate());
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
}

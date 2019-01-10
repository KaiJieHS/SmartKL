package my.edu.tarc.testsmartkl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class CitizenRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername, editTextPassword , editTextConfirmPassword, editTextName,editTextPhoneNo,editTextEmail;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private TextView textViewLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_register);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();;
            startActivity(new Intent(this,ProfileActivity.class));
            return;
        }
        editTextUsername = (EditText)findViewById(R.id.editTextUserName);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextConfirmPassword = (EditText)findViewById(R.id.editTextConfirmPassword);
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextPhoneNo = (EditText)findViewById(R.id.editTextPhoneNo);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);

        textViewLogin = (TextView)findViewById(R.id.textViewLogin);
        buttonRegister = (Button)findViewById(R.id.btnRegister);

        progressDialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
        String text = "Already Register? Click here to Login";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(CitizenRegisterActivity.this,CitizenLoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE );
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan,32,37,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewLogin.setText(ss);
        textViewLogin.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void onClick(View view){
        if(view == buttonRegister) {
            if(editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())) {
                registerCitizen();
            }else{
                Toast.makeText(getApplicationContext(),"Sorry 2 password must be same",Toast.LENGTH_LONG).show();
                editTextPassword.setText("");
                editTextConfirmPassword.setText("");
            }
        }
    }

    public void registerCitizen() {
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        final String phoneNo = editTextPhoneNo.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Citizen.url_register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("CUserName", username);
                params.put("CPassword", password);
                params.put("CName", name);
                params.put("CPhoneNo", phoneNo);
                params.put("CEmail", email);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}

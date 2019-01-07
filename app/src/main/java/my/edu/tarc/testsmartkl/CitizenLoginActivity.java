package my.edu.tarc.testsmartkl;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class CitizenLoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };



    // UI references.

    private EditText mPasswordView,mUserName;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_login);

        // Set up the login form.
        mUserName = findViewById(R.id.txtUserName);
        mPasswordView = findViewById(R.id.txtPassword);
        TextView textView = findViewById(R.id.textViewResponseDesc);
        String text = "Register | Forget Password";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
               Intent intent = new Intent(CitizenLoginActivity.this,CitizenRegisterActivity.class);
                startActivity(intent);

            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE );
                ds.setUnderlineText(false);
            }
        };

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(CitizenLoginActivity.this,"Two",Toast.LENGTH_SHORT);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE );
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan1,0,8,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2,11,26,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUserName.getText().toString();
                String pwd = mPasswordView.getText().toString();
            if(username.equalsIgnoreCase("Bell") && pwd.equals("bell")){
                Intent intent = new Intent(CitizenLoginActivity.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(CitizenLoginActivity.this,"Sign In Successfully",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(CitizenLoginActivity.this,"Username or password incorrect.",Toast.LENGTH_LONG).show();
            }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

}


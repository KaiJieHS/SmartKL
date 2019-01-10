
package my.edu.tarc.testsmartkl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewName,textViewPhoneNo,textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,CitizenLoginActivity.class));
        }

        textViewName = (TextView)findViewById(R.id.textViewName);
        textViewPhoneNo =(TextView)findViewById(R.id.textViewPhoneNo);
        textViewEmail = (TextView)findViewById(R.id.textViewEmail);

        textViewName.setText(SharedPrefManager.getInstance(this).getName());
        textViewPhoneNo.setText(SharedPrefManager.getInstance(this).getPhoneNo());
        textViewEmail.setText(SharedPrefManager.getInstance(this).getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_me,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this,CitizenLoginActivity.class));
            break;
        }
        return true;
    }
}

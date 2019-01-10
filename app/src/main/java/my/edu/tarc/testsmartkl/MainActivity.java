package my.edu.tarc.testsmartkl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent2 = new Intent(MainActivity.this,MainActivity.class);
                    //intent.putExtra("CurrentCitizenID", "1");
                    startActivity(intent2);
                    return true;
                case R.id.navigation_feedback:
                    Intent intent = new Intent(MainActivity.this,FeedbackActivity.class);
                    intent.putExtra("userID", "1");
                    intent.putExtra("userType", "Admin");
                    startActivity(intent);
                    return true;
                case R.id.navigation_me:
                    Intent intent1 = new Intent(MainActivity.this,CitizenRegisterActivity.class);
                    //intent.putExtra("CurrentCitizenID", "1");
                    startActivity(intent1);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void Transport(View v){
        Intent intent = new Intent(this, TransportActivity.class);
        startActivity(intent);
    }

    public void HealthCare(View v){
        Intent intent = new Intent(this, HealthCareActivity.class);
        startActivity(intent);
    }

    public void Organization(View v){
        Intent intent = new Intent(this, OrganizationActivity.class);
        startActivity(intent);
    }

}

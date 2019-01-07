package my.edu.tarc.testsmartkl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TransportSchedule extends AppCompatActivity {

    TextView textViewLine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_schedule);

        textViewLine = findViewById(R.id.textViewSchedule);

        String transportLine = getIntent().getStringExtra("TransportLine");
        textViewLine.setText(transportLine);
    }
}

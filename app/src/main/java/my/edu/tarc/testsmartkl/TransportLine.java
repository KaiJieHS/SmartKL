package my.edu.tarc.testsmartkl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TransportLine extends AppCompatActivity {

    TextView textViewMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_line);

        textViewMessage = findViewById(R.id.textViewTest1);

        String stringMsg = getIntent().getStringExtra("TransportType");
        textViewMessage.setText(stringMsg);
    }
}

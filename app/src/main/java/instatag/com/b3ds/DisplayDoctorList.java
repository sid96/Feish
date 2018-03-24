package instatag.com.b3ds;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayDoctorList extends AppCompatActivity {
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
       // ArrayList<Datum> ar= (ArrayList<Datum>) bundle.getSerializable("data");
        setContentView(R.layout.activity_display_doctor_list);
        tv=(TextView)findViewById(R.id.showresult);
        //tv.setText(ar.get(0).getService().getPhone());
    }
}

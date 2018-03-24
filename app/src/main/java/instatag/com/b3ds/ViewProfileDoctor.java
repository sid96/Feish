package instatag.com.b3ds;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ViewProfileDoctor extends AppCompatActivity {
    TextView viewname;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_doctor);
        viewname=(TextView)findViewById(R.id.firstNameis);

        bundle=getIntent().getExtras();
        viewname.setText(bundle.getString("firstname"));

    }
}

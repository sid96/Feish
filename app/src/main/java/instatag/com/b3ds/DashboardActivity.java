package instatag.com.b3ds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static instatag.com.b3ds.MainActivity.JSON;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout profilelayout,visitReportlayout;
    TextView consultdoctorTV;
    private ContactDetails detailsResponse;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        consultdoctorTV=(TextView)findViewById(R.id.consultdoctorid);
        visitReportlayout=(LinearLayout)findViewById(R.id.visitreportlayoutId);
        profilelayout=(LinearLayout)findViewById(R.id.dashboardprofile);
        pb = (ProgressBar) findViewById(R.id.progress);
        pb.setVisibility(View.GONE);
        visitReportlayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(DashboardActivity.this,VisitReportActivity.class);
        startActivity(intent);
    }
});
        profilelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeApiCall();
                //Log.i("Dashboard", "onClick: Make api call");

            }
        });
        consultdoctorTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this,ConsultDoctorActivity.class);
                startActivity(intent);
            }
        });
    }

    private Request details_request(){
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id",MainActivity.userid);
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON,postdata.toString());
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key","AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url("http://dev.feish.online/apis/getPatientDetails")
                .post(body)
                .build();
        return request;
    }

    public void detailsResponse(String response) {
        Gson gson = new GsonBuilder().create();
        detailsResponse = gson.fromJson(response,ContactDetails.class);
    }

    public void makeApiCall(){

        pb.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        OkHttpClient client = new OkHttpClient();
        Request validation_request=details_request();
        client.newCall(validation_request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("Activity", "onFailure: Fail");
            }
            @Override
            public void onResponse(final Response response) throws IOException {
                detailsResponse(response.body().string());
                final boolean isSuccessful=detailsResponse.getStatus();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isSuccessful){
                            pb.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(getApplicationContext(),"Fetched Details",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(DashboardActivity.this,ViewProfilePatient.class);
                            intent.putExtra("details",detailsResponse);
                            startActivity(intent);
                           // firstname=detailsResponse.getData().getFirstName();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"OOPS!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

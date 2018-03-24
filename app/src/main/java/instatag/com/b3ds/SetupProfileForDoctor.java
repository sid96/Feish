package instatag.com.b3ds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SetupProfileForDoctor extends AppCompatActivity {
    private EditText identityno, firstname,lastname,email,mobile,qualification,consultationTime;
    private Spinner identitytype;
    private int identitytype_val;
    Bundle bundle;
    Button updateProfilebtn;
    public  static  final MediaType JSON= MediaType.parse("application/json:charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_setup_profile_activity);
        firstname=(EditText)findViewById(R.id.setupfirstname);
        lastname=(EditText)findViewById(R.id.setuplastname);
        email=(EditText)findViewById(R.id.setupemail);
        mobile=(EditText)findViewById(R.id.setupmobile);
        qualification=(EditText)findViewById(R.id.enterqualificationID);
        consultationTime=(EditText)findViewById(R.id.consultationtimeID);
        updateProfilebtn=(Button)findViewById(R.id.submitprofileDoctorID);
        identityno=(EditText)findViewById(R.id.enteridenetitynumID);

        bundle= getIntent().getExtras();

        firstname.setText(bundle.getString("firstname"));

        lastname.setText(bundle.getString("lastname"));

        email.setText(bundle.getString("email"));

        mobile.setText(bundle.getString("mobile"));

        identitytype=(Spinner)findViewById(R.id.setupidentityproof);

        identitytype.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        identitytype_val=pos+1;
                        //Log.i(TAG, "onItemSelected: "+pos);
                        //Log.i(TAG, "onItemSelected: "+id);


                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                        identitytype_val=1;
                    }
                });

        updateProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                OkHttpClient client = new OkHttpClient();
                Request validation_request=profile_request();
                client.newCall(validation_request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Request request, IOException e) {

                        // Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                        Log.i("Activity", "onFailure: Fail");
                    }
                    @Override
                    public void onResponse(final Response response) throws IOException {

                        final boolean isSuccessful=profileresponse(response.body().string());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(isSuccessful){

                                    Toast.makeText(getApplicationContext(),"Update successful",Toast.LENGTH_LONG).show();


                                    Intent intent=new Intent(SetupProfileForDoctor.this,ViewProfileDoctor.class);
                                    intent.putExtra("firstname",bundle.getString("firstname"));
                                   // intent.putExtra("userid",userid);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"OOPS!!",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });




            }
        });


    }
    private Request profile_request(){
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("id",MainActivity.userid);

            //postdata.put("bmi",bmi.getText().toString());

            postdata.put("first_name",firstname.getText().toString());
            postdata.put("last_name",lastname.getText().toString());
            postdata.put("email",email.getText().toString());
            postdata.put("mobile",mobile.getText().toString());
            postdata.put("qualification",qualification.getText().toString());
            postdata.put("consultation_time",consultationTime.getText().toString());

            postdata.put("identity_type",Integer.toString(identitytype_val));
            postdata.put("identity_id",identityno.getText().toString());
            //postdata.put("height",height.getText().toString());
            // postdata.put("address",address.getText().toString());
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON,postdata.toString());
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key","AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url("http://dev.feish.online/apis/set_up_profile")
                .post(body)
                .build();
        return request;
    }

    public boolean profileresponse(String response) {
        Gson gson = new GsonBuilder().create();
        ContactService profileResponse = gson.fromJson(response,ContactService.class);
        return profileResponse.getStatus();
    }
}

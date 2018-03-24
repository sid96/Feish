package instatag.com.b3ds;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
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

/**
 * Created by This Pc on 3/8/2018.
 */

public class SetupProfile extends Activity {


  ImageView imageView;


    private EditText identityno,firstname,lastname,email,mobile,address,height,weight;
    private int gender=1,marrital=1,occupation_val,ethinicity_val,bloodgroup_val,identitytype_val;
    private TextView bmi;
    private RadioButton radioButtonMale,radioButtonFemale,radioButtonMarried,radioButtonUnmarried;
    private Spinner ethinicity,bloodgroup,occupation,identitytype;
    private Button submit;
    public  static  final MediaType JSON= MediaType.parse("application/json:charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsetupprofileactivity);
        Bundle bundle = getIntent().getExtras();

        imageView = findViewById(R.id.imageViewforsetupprofile);
        firstname=findViewById(R.id.setupfirstname);
        lastname=findViewById(R.id.setuplastname);
        email=findViewById(R.id.setupemail);
        mobile=findViewById(R.id.setupmobile);
        submit=findViewById(R.id.submitprofile);
        address =findViewById(R.id.setupaddress);
        identityno =findViewById(R.id.identityno);
        height=findViewById(R.id.setupheight);
        weight=findViewById(R.id.setupweight);
        bmi=findViewById(R.id.setupbmi);

        radioButtonMale =  findViewById(R.id.radioMale);
        radioButtonFemale =  findViewById(R.id.radioFemale);

        radioButtonMarried =  findViewById(R.id.marriedid);
        radioButtonUnmarried =  findViewById(R.id.unmarriedid);

        occupation=findViewById(R.id.setupoccupation);






        occupation.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        occupation_val=pos+1;
                        //Log.i(TAG, "onItemSelected: "+pos);
                        //Log.i(TAG, "onItemSelected: "+id);


                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                        occupation_val=1;
                    }
                });


        bloodgroup=(Spinner)findViewById(R.id.setupbloodgroup);

        bloodgroup.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        bloodgroup_val=pos+1;
                        //Log.i(TAG, "onItemSelected: "+pos);
                        //Log.i(TAG, "onItemSelected: "+id);


                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                        bloodgroup_val=1;
                    }
                });



        ethinicity=(Spinner)findViewById(R.id.setupoccupation);

        ethinicity.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        ethinicity_val=pos+1;
                        //Log.i(TAG, "onItemSelected: "+pos);
                        //Log.i(TAG, "onItemSelected: "+id);


                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                        ethinicity_val=1;
                    }
                });


        identitytype=(Spinner)findViewById(R.id.setupoccupation);

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

        firstname.setText(bundle.getString("firstname"));

        lastname.setText(bundle.getString("lastname"));

        email.setText(bundle.getString("email"));

        mobile.setText(bundle.getString("mobile"));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radioButtonMale.isChecked())
                {
                    gender=1;
                }
                else if (radioButtonFemale.isChecked())
                {
                    gender=2;
                }

                if(radioButtonMarried.isChecked())
                {
                    marrital=1;
                }
                else if (radioButtonUnmarried.isChecked())
                {
                    marrital=2;
                }



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
                                    double wgt=Double.parseDouble(weight.getText().toString());
                                    double hgt=Double.parseDouble(height.getText().toString());
                                    double hgtsq=Math.pow(hgt,2);
                                    bmi.setText(Double.toString(wgt/hgtsq));
                                    Toast.makeText(getApplicationContext(),"Update successful",Toast.LENGTH_LONG).show();

                                    //Intent intent=new Intent(VerificationChoiceActivity.this,SetPasswordActivity.class);
                                    //intent.putExtra("userid",userid);
                                    //startActivity(intent);
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
            postdata.put("weight",weight.getText().toString());
            postdata.put("height",height.getText().toString());
            postdata.put("gender",Integer.toString(gender));
            postdata.put("marital_status",Integer.toString(marrital));
            //postdata.put("bmi",bmi.getText().toString());
            postdata.put("address",address.getText().toString());
            postdata.put("first_name",firstname.getText().toString());
            postdata.put("last_name",lastname.getText().toString());
            postdata.put("email",email.getText().toString());
            postdata.put("mobile",mobile.getText().toString());
            postdata.put("occupation_id",Integer.toString(occupation_val));
            postdata.put("ethinicity_id",Integer.toString(ethinicity_val));
            postdata.put("blood_group",Integer.toString(bloodgroup_val));
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

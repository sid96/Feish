package instatag.com.b3ds;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class EditProfile extends AppCompatActivity {

    private static final String TAG ="EditProfile" ;
    Button update;
    EditText firstname,lastname,email,mobile,address,height,weight,identityno;
    String first_name,last_name,addressEt,ht,wt,idnum,rgender,rmarital,occ,eth,idtype,bldgrp,sal;
    RadioButton male,female,married,unmarried;
    Spinner occupation,ethinicity,bloodgroup,salutation,identitytype;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_patient);
        update=(Button)findViewById(R.id.editprofile);
        update.setText("UPDATE");
        ContactDetails cd=(ContactDetails) getIntent().getSerializableExtra("details");
        ImageView imageView =(ImageView)findViewById(R.id.viewimageViewforsetupprofile);
        salutation=(Spinner)findViewById(R.id.viewsalutation);
        if(cd.getData().getIdentityType()!=null)
        {
            salutation.setSelection(Integer.parseInt(cd.getData().getSalutation())-1);
        }

        firstname=(EditText) findViewById(R.id.viewfirstname);

        lastname=(EditText) findViewById(R.id.viewlastname);

        email=(EditText) findViewById(R.id.viewemail);
        email.setEnabled(false);

        mobile=(EditText) findViewById(R.id.viewmobile);
        mobile.setEnabled(false);

        male=(RadioButton)findViewById(R.id.radioMale);

        female=(RadioButton)findViewById(R.id.radioFemale);


        if(cd.getData().getGender()!=null)
        {

            if(cd.getData().getGender()=="1")
            {
                male.setChecked(true);
            }
            else
            {
                female.setChecked(true);
            }

        }
        else
        {
            male.setChecked(false);
            female.setChecked(false);
        }



        married=(RadioButton)findViewById(R.id.marriedid);
        unmarried=(RadioButton)findViewById(R.id.unmarriedid);


        if(cd.getData().getMaritalStatus()!=null)
        {

            if(cd.getData().getMaritalStatus()=="1")
            {
                (married).setChecked(true);
            }
            else
            {
                (unmarried).setChecked(true);
            }

        }
        else
        {
            (unmarried).setChecked(false);
            (married).setChecked(false);
        }


        address =(EditText)findViewById(R.id.viewaddress);

        identityno =(EditText)findViewById(R.id.viewidentityno);

        height=(EditText)findViewById(R.id.viewheight);

        weight=(EditText)findViewById(R.id.viewweight);

        TextView bmi=(TextView)findViewById(R.id.setupbmi);
        occupation=(Spinner)findViewById(R.id.viewoccupation);
        if(cd.getData().getOccupationId()!=null) {
            occupation.setSelection(Integer.parseInt((String)cd.getData().getOccupationId())-13);
        }

        bloodgroup=(Spinner)findViewById(R.id.viewbloodgroup);
        if(cd.getData().getBloodGroup()!=null)
        {
            bloodgroup.setSelection(Integer.parseInt((String)cd.getData().getBloodGroup())-1);
        }


        ethinicity=(Spinner)findViewById(R.id.viewethnicity);
        if(cd.getData().getEthnicityId()!=null)
        {
            ethinicity.setSelection(Integer.parseInt((String)cd.getData().getEthnicityId())-18);
        }


        identitytype=(Spinner)findViewById(R.id.viewidentityproof);
        if(cd.getData().getIdentityType()!=null)
        {
           // identitytype.setSelection(Integer.parseInt((String)cd.getData().getIdentityId())-1);
        }

        if(cd.getData().getFirstName()!=null)
            firstname.setText(cd.getData().getFirstName());
        if(cd.getData().getLastName()!=null)
            lastname.setText(cd.getData().getLastName());
        if(cd.getData().getEmail()!=null)
            email.setText(cd.getData().getEmail());
        if(cd.getData().getMobile()!=null)
            mobile.setText(cd.getData().getMobile());
        if(cd.getData().getAddress()!=null)
            address.setText((String)cd.getData().getAddress());
        if(cd.getData().getIdentityId()!=null)
            identityno.setText((String)cd.getData().getIdentityId());
        if(cd.getData().getHeight()!=null)
            height.setText(cd.getData().getHeight());
        if(cd.getData().getWeight()!=null)
            weight.setText(cd.getData().getWeight());


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name=firstname.getText().toString();
                last_name=lastname.getText().toString();
                ht=height.getText().toString();
                wt=weight.getText().toString();
                addressEt=address.getText().toString();
                idnum=identityno.getText().toString();
                occ=Long.toString(occupation.getSelectedItemId()+13);
                bldgrp=Long.toString(bloodgroup.getSelectedItemId()+1);
                eth=Long.toString(ethinicity.getSelectedItemId()+18);
                sal=Long.toString(salutation.getSelectedItemId()+1);
                if(male.isChecked()){
                    rgender="1";
                }
                else {
                    rgender="2";
                }
                if(married.isChecked()){
                    rmarital="1";
                }
                else {
                    rmarital="2";
                }

               idtype=Long.toString(identitytype.getSelectedItemId()+10);
               finish();
                //makeApiCall();
            }
        });

    }

    private Request setup_request(){
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id",MainActivity.userid);
            //postdata.put("salutation",sal);
            //postdata.put("first_name",first_name);
            //postdata.put("last_name",last_name);
            //postdata.put("gender",rgender);
            //postdata.put("occupation_id",occ);
            //postdata.put("ethinicity_id",eth);
           // postdata.put("address",addressEt);
            /*postdata.put("blood_group",bldgrp);
            postdata.put("identity_id",idnum);
            postdata.put("identity_type",idtype);
            postdata.put("height",ht);
            postdata.put("weight",wt);
            postdata.put("marital_status",rmarital);*/
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
        //Log.i("Edit Profile", "setup_request: "+request.toString());
        return request;
    }



    public void makeApiCall(){

        OkHttpClient client = new OkHttpClient();
        Request validation_request=setup_request();
        client.newCall(validation_request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("Activity", "onFailure: Fail");
            }
            @Override
            public void onResponse(final Response response) throws IOException {
                final boolean isSuccessful=setup(response.body().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isSuccessful){
                            Toast.makeText(getApplicationContext(),"Update Successfully",Toast.LENGTH_SHORT).show();
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

    public boolean setup(String response) {
        Gson gson = new GsonBuilder().create();
        ContactSetup setupresponse = gson.fromJson(response,ContactSetup.class);
        return  setupresponse.getStatus();
    }
}

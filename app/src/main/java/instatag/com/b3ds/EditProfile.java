package instatag.com.b3ds;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class EditProfile extends AppCompatActivity {

    Button update;
    EditText firstname,lastname,email,mobile,address,height,weight,identityno;
    RadioButton male,female,married,unmarried;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_patient);
        update=(Button)findViewById(R.id.editprofile);
        update.setText("UPDATE");
        ContactDetails cd=(ContactDetails) getIntent().getSerializableExtra("details");
        ImageView imageView =(ImageView)findViewById(R.id.viewimageViewforsetupprofile);
        Spinner salutation=(Spinner)findViewById(R.id.viewsalutation);
        if(cd.getData().getIdentityType()!=null)
        {
            salutation.setSelection(Integer.parseInt(cd.getData().getSalutation())-1);
        }

        firstname=(EditText) findViewById(R.id.viewfirstname);
        firstname.setEnabled(false);

        lastname=(EditText) findViewById(R.id.viewlastname);
        lastname.setEnabled(false);

        email=(EditText) findViewById(R.id.viewemail);
        email.setEnabled(false);

        mobile=(EditText) findViewById(R.id.viewmobile);
        mobile.setEnabled(false);

        male=(RadioButton)findViewById(R.id.radioMale);

        female=(RadioButton)findViewById(R.id.radioFemale);


        if(cd.getData().getGender()!=null)
        {

            if(cd.getData().getGender()=="0")
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
        Spinner occupation=(Spinner)findViewById(R.id.viewoccupation);
        if(cd.getData().getOccupationId()!=null) {
            occupation.setSelection(Integer.parseInt((String)cd.getData().getOccupationId()));
        }
        occupation.setEnabled(false);

        Spinner bloodgroup=(Spinner)findViewById(R.id.viewbloodgroup);
        if(cd.getData().getBloodGroup()!=null)
        {
            bloodgroup.setSelection(Integer.parseInt((String)cd.getData().getBloodGroup()));
        }


        Spinner ethinicity=(Spinner)findViewById(R.id.viewethnicity);
        if(cd.getData().getEthnicityId()!=null)
        {
            ethinicity.setSelection(Integer.parseInt((String)cd.getData().getEthnicityId()));
        }


        Spinner identitytype=(Spinner)findViewById(R.id.viewidentityproof);
        if(cd.getData().getIdentityType()!=null)
        {
            identitytype.setSelection(Integer.parseInt((String)cd.getData().getIdentityId())-1);
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


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                finish();
            }
        });

    }
}

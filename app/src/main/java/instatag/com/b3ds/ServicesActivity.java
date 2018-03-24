package instatag.com.b3ds;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzagz.runOnUiThread;

public class ServicesActivity extends AppCompatActivity {

    TabLayout MyTabs;
    ViewPager MyPage;
    private AddServices addServices;
    private ListServices listServices;
    public static final MediaType JSON = MediaType.parse("application/json:charset=utf-8");
    private ListServicesContact serviceResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MyTabs = (TabLayout)findViewById(R.id.MyTabs);
        MyPage = (ViewPager)findViewById(R.id.MyPage);

        MyTabs.setupWithViewPager(MyPage);
        SetUpViewPager(MyPage);

        //Thanks For Watching and Keep Learning With ClipCodes on YouTube
    }

    public void SetUpViewPager (final ViewPager viewpage){
        MyViewPageAdapter Adapter = new MyViewPageAdapter(getSupportFragmentManager());

        addServices=new AddServices();
        listServices=new ListServices();
        Adapter.AddFragmentPage(addServices, "ADD SERVICES");
        Adapter.AddFragmentPage(listServices, "LIST SERVICES");

        //We Need Fragment class now

        viewpage.setAdapter(Adapter);
        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int i;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                i=viewpage.getCurrentItem();
                if(i==1)
                {
                    OkHttpClient client = new OkHttpClient();
                    Request validation_request = listservices();
                    client.newCall(validation_request).enqueue(new Callback() {

                        @Override
                        public void onFailure(Request request, IOException e) {

                            // Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                            Log.i("Activity", "onFailure: Fail");
                        }

                        @Override
                        public void onResponse(final Response response) throws IOException {
                            listServiceResponse(response.body().string());
                            final boolean isSuccessful=serviceResponse.getStatus();
                            final List l=serviceResponse.getData();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (isSuccessful) {

                                        Toast.makeText(getApplication(), "List of services ", Toast.LENGTH_LONG).show();
                                        //l.toArray();
                                        /*for (int i=0;i<l.size();i++){
                                            Datum2 d=(Datum2) l.get(i);
                                            d.getService().
                                        }*/
                                        //new ListServices().serviceAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,
                                        //       android.R.id.text1,l.toArray());
                                        //Intent intent=new Intent(VerificationChoiceActivity.this,SetPasswordActivity.class);
                                        //intent.putExtra("userid",userid);
                                        //startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplication(), "Could not load the list!!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    });

                }
                //Log.i("ServicesActivity", "onPageScrollStateChanged: ");
               // Log.i("ServicesActivity", "onPageScrolled: "+viewpage.getCurrentItem());
            }
        });


    }
    private Request listservices() {
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id", MainActivity.userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url("http://dev.feish.online/apis/listService")
                .post(body)
                .build();
        return request;
    }

    public void listServiceResponse(String response) {
        Gson gson = new GsonBuilder().create();
        serviceResponse = gson.fromJson(response, ListServicesContact.class);
    }

    public class MyViewPageAdapter extends FragmentPagerAdapter{
        private List<Fragment> MyFragment = new ArrayList<>();
        private List<String> MyPageTittle = new ArrayList<>();

        public MyViewPageAdapter(FragmentManager manager){
            super(manager);
        }

        public void AddFragmentPage(Fragment Frag, String Title){
            MyFragment.add(Frag);
            MyPageTittle.add(Title);
        }

        @Override
        public Fragment getItem(int position) {
            return MyFragment.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return MyPageTittle.get(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


}
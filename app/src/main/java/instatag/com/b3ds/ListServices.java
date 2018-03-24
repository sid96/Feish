package instatag.com.b3ds;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static android.content.ContentValues.TAG;

/**
 * Created by haade on 13/09/2017.
 */

public class ListServices extends Fragment {

    //Constructor default
    protected ArrayAdapter<String> serviceAdapter;
    protected String[] serviceList;
    protected  ListView ls;
    public ListServices(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View PageTwo = inflater.inflate(R.layout.ist_services_fragment, container, false);
        ls=PageTwo.findViewById(R.id.servicelist);
        //serviceAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,serviceList);
        //ls.setAdapter(serviceAdapter);
        Log.i(TAG, "onCreateView: ");
        return PageTwo;
    }



}

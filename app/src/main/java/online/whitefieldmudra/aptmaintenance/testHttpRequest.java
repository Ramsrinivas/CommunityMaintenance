package online.whitefieldmudra.aptmaintenance;

/**
 * Created by welcome on 16-10-2017.
 */


import android.app.DownloadManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.util.ExceptionCatchingInputStream;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class testHttpRequest extends AsyncTask<String, Void, Boolean>{

    // private OkHttpClient okHttpClient;
    public static final MediaType FORM_DATA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public static int responseCode = 0;


    @Override
    protected Boolean doInBackground(String... params) {

        String OWNERNAME_KEY = "entry.2066481574";
        String OWNEREMAIL_KEY = "entry.566989299";
        String OWNERPHONE_KEY = "entry.1498217885";
        String DOB_KEY = "entry.857039178";
        Boolean result = true;

        String URLformsubmit = params[0];
        String ownerName = params[1];
        String ownerEmail = params[2];
        String ownerPhoneNo = params[3];
        String dob = params[4];


        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

            Date dobDate = dateFormat.parse(dob);

            OkHttpClient client = new OkHttpClient();

            //FormBody body = new FormBody.Builder()
            //                    .add(OWNERNAME_KEY,ownerName)
           //                     .add(OWNEREMAIL_KEY,ownerEmail)
            //                    .add(OWNERPHONE_KEY,ownerPhoneNo)
            //                    .build();

             String post_data = OWNERNAME_KEY +"="+ URLEncoder.encode(ownerName,"UTF-8")
                     + "&" + OWNEREMAIL_KEY + "=" + URLEncoder.encode(ownerEmail,"UTF-8")
                     + "&" + OWNERPHONE_KEY + "=" + URLEncoder.encode(ownerPhoneNo,"UTF-8")
                     + "&" + DOB_KEY + "=" + URLEncoder.encode(dob,"UTF-8")
                     + "&pageHistory=0,1&submit=Submit";


             RequestBody body = RequestBody.create(FORM_DATA_TYPE,post_data);

            Request request = new Request.Builder()
                                .url(URLformsubmit)
                                .post(body)
                                .build();
            responseCode = 0;
            Response response = client.newCall(request).execute();

            if((responseCode = response.code()) == 200)
            {
                String jasonData = response.body().string();
                String Htmlvalue = jasonData.toString();
                JSONObject json = new JSONObject(jasonData);

                String token = json.getString("token");
            }
        }
        catch (Exception ex)
        {
           // ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean result){
        //Print Success or failure message accordingly
        // Toast.makeText(context,result?"Message successfully sent!":"There was some error in sending message. Please try again after some time.",Toast.LENGTH_LONG).show();
    }

}

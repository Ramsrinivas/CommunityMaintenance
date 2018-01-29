package online.whitefieldmudra.aptmaintenance;

/**
 * Created by welcome on 18-01-2018.
 */
import android.os.AsyncTask;
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

 public class ResidentRegistrationHttpRequest extends AsyncTask<String, Void, Boolean>{

     public static final MediaType FORM_DATA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
     public static int responseCode = 0;

     @Override
     protected Boolean doInBackground(String... params) {

         String FLATNO_KEY = "entry.477992208";
         String RESIDENTNAME_KEY = "entry.900845194";
         String RESIDENTEMAIL_KEY = "entry.2063769459";
         String RESIDENTGROUP_KEY = "entry.1053385478";
         String RESIDENTTYPE_KEY = "entry.188186544";
         String RESIDENTPHONENUMBER_KEY = "entry.2013291699";
         String RESIDENTWHATUPNUMBER_KEY = "entry.189157720";
         String ISMC_KEY = "entry.2013008390";
         String REGISTERNAME_KEY = "entry.298818192";
         String REGISTEREMAIL_KEY = "entry.1850141090";
         String SUBMIT_KEY = "Submit";

         Boolean result = true;
         String URLformsubmit = params[0];
         String flatno = params[1];
         String residentname = params[2];
         String residentemail = params[3];
         String residentgroup = params[4].trim();
         String residenttype = params[5];
         String residentphonenumber = params[6];
         String residentwhatsupnumber = params[7];
         String ismc = params[8];
         String registername = params[9];
         String registeremail = params[10];
         String urlParams = "";
         String submit = "submit";

         try {

             urlParams = FLATNO_KEY +"="+ URLEncoder.encode(flatno,"UTF-8")
                     + "&" + RESIDENTNAME_KEY + "=" + URLEncoder.encode(residentname,"UTF-8")
                     + "&" + RESIDENTEMAIL_KEY + "=" + URLEncoder.encode(residentemail,"UTF-8")
                     + "&" + RESIDENTGROUP_KEY + "=" + URLEncoder.encode(residentgroup,"UTF-8")
                     + "&" + RESIDENTTYPE_KEY + "=" + URLEncoder.encode(residenttype,"UTF-8")
                     + "&" + RESIDENTPHONENUMBER_KEY + "=" + URLEncoder.encode(residentphonenumber,"UTF-8")
                     + "&" + RESIDENTWHATUPNUMBER_KEY + "=" + URLEncoder.encode(residentwhatsupnumber,"UTF-8")
                     + "&" + ISMC_KEY + "=" + URLEncoder.encode(ismc,"UTF-8")
                     + "&" + REGISTERNAME_KEY + "=" + URLEncoder.encode(registername,"UTF-8")
                     + "&" + REGISTEREMAIL_KEY + "=" + URLEncoder.encode(registeremail,"UTF-8")
                     + "&" + SUBMIT_KEY + "=" + URLEncoder.encode(submit,"UTF-8");


             OkHttpClient clientMaintenance = new OkHttpClient();

             RequestBody body = RequestBody.create(FORM_DATA_TYPE,urlParams);

             Request requestMaintenance = new Request.Builder()
                     .url(URLformsubmit)
                     .post(body)
                     .build();

             responseCode = 0;

             Response responseMaintenance = clientMaintenance.newCall(requestMaintenance).execute();


             if((responseCode = responseMaintenance.code()) == 200)
             {
                 String jasonDataMaintenance = responseMaintenance.body().string();
                 String HtmlvalueMaintenance = jasonDataMaintenance.toString();
                 JSONObject jsonMaintenance = new JSONObject(jasonDataMaintenance);

                 String token = jsonMaintenance.getString("token");
             }
             else
             {
                 result = false;
             }
         }
         catch (Exception ex)
         {
            // ex.printStackTrace();
         }
         return result;
     }
 }

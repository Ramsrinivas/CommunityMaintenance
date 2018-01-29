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
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TenantHttpRequest extends AsyncTask<String, Void, Boolean>{

   // private OkHttpClient okHttpClient;
    public static final MediaType FORM_DATA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public static int responseCode = 0;

    public TenantHttpRequest(){
       }

    @Override
    protected Boolean doInBackground(String... params) {

        String PASSWORD_KEY = "entry_1345743666";
        String BLOCKNUMBER_KEY = "entry_129841939";
        String HOUSENUMBER_KEY = "entry_1387986307";
        String OWNERNAME_KEY = "entry_620139325";
        String OWNEREMAIL_KEY = "entry_1278237615";
        String OWNERPHONENO_KEY = "entry_1497113697";
        String TENANTNAME_KEY = "entry_129841939";
        String TENANTPHONENO_KEY = "entry_1117967015";
        String TENANTWHATSAPPNO_KEY = "entry_846178262";
        String TENANTEMAIL_KEY = "entry_1613156843";
        String TENANTPARMANANTADDRS_KEY = "entry_810763720";
        String TENANTDATEOFOCCUPATION_KEY = "entry_1709843746";
        String TENANTIDTYPE_KEY = "entry_696445028";
        String TENANTIDNUMBER_KEY = "entry_2124026498";
        String TENANTPERMANANTADDRESS_KEY = "entry_436816596";
        String TENANTPREVIOUSVACATIONDATE_KEY = "entry_858120707";

        Boolean result = true;
        String URLformsubmit = params[0];
        String password = params[1];
        String blockNumber = params[2];
        String houseNumber = params[3];
        String ownerName = params[4];
        String ownerEmail = params[5];
       // String ownerPhoneNumber = params[6];
       // String tenantName = params[7];
       // String tenantPhoneNumber = params[8];
      //  String tenantWhatsAppNumber = params[9];
       // String tenantEmailID = params[10];
       // String tenantPermanentAddress = params[11];
      //  String tenantOccupationDate = params[12];
      //  String tenantIDType = params[13];
      //  String tenantIDNumber = params[14];
      //  String tenantPermanetAddress = params[15];
      //  String tenantPreviousVecationDate = params[16];
     //  String urlParams = "";

        try {

            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                      .add(PASSWORD_KEY,password)
                      .add(BLOCKNUMBER_KEY,blockNumber)
                      .add(HOUSENUMBER_KEY,houseNumber)
                      .add(OWNERNAME_KEY,ownerName)
                      .add(OWNEREMAIL_KEY,ownerEmail)
             //       .add(OWNERPHONENO_KEY,ownerPhoneNumber)
            //        .add(TENANTNAME_KEY,tenantName)
            //        .add(TENANTPHONENO_KEY,tenantPhoneNumber)
             //       .add(TENANTWHATSAPPNO_KEY,tenantWhatsAppNumber)
             //       .add(TENANTEMAIL_KEY,tenantEmailID)
            //        .add(TENANTPARMANANTADDRS_KEY,tenantPermanentAddress)
            //        .add(TENANTDATEOFOCCUPATION_KEY,tenantOccupationDate)
            //        .add(TENANTIDTYPE_KEY,tenantIDType)
            //        .add(TENANTIDNUMBER_KEY,tenantIDNumber)
            //        .add(TENANTPERMANANTADDRESS_KEY,tenantPermanetAddress)
            //        .add(TENANTPREVIOUSVACATIONDATE_KEY,tenantPreviousVecationDate)
                    .build();

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

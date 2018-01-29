package online.whitefieldmudra.aptmaintenance;

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

/**
 * Created by welcome on 23-10-2017.
 */

public class MaintenanceHttpRequest extends AsyncTask<String, Void, Boolean> {

    public static final MediaType FORM_DATA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public static int responseCode = 0;

    @Override
    protected Boolean doInBackground(String... params) {

        String PASSWORD_KEY = "entry.626750970";
        String BLOCKNUMBER_KEY = "entry.1260624191";
        String HOUSENUMBER_KEY = "entry.736385788";
        String SUBMITTEREMAIL_KEY = "entry.2133683235";
        String PAYMENTAMOUNT_KEY = "entry.1940446733";
        String PAYMENTMODE_KEY = "entry.1769448416";
        String PAYMENTREFERENCE_KEY = "entry.861038333";
        String PAYMENTPURPOSE_KEY = "entry.594788399";
        String PAYMENTMONTH_KEY = "entry.1873908387";
        String PAYMENTYEAR_KEY = "entry.1646830355";
        String PAYMENTDATE_KEY = "entry.470776897";
        String COMMENTS_KEY = "entry.507914581";
        String MULTIPAGEHISTORY_KEY = "pageHistory";
        String SUBMIT_KEY = "Submit";

        Boolean result = true;
        String URLformsubmit = params[0];
        String password = params[1];
        String blockNumber = params[2];
        String houseNumber = params[3];
        String submitterEmail = params[4];
        String amountPaid = params[5];
        String paymentMode = params[6];
        String paymentReference = params[7];
        String paymentPurpose = params[8];
        String paymentMonth = params[9];
        String paymentYear = params[10];
        String paymentDate = params[11].toString();
        String comments = params[12];
        String urlParams = "";
        String multiPageFormAppend = "0,1";
        String submit = "submit";
        try {

         Date dateConvert=new SimpleDateFormat("MM/dd/yyyy").parse(paymentDate);
         SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
         String transferDate = dateFormat.format(dateConvert);

            urlParams = PASSWORD_KEY +"="+ URLEncoder.encode(password,"UTF-8")
                    + "&" + BLOCKNUMBER_KEY + "=" + URLEncoder.encode(blockNumber,"UTF-8")
                    + "&" + HOUSENUMBER_KEY + "=" + URLEncoder.encode(houseNumber,"UTF-8")
                    + "&" + SUBMITTEREMAIL_KEY + "=" + URLEncoder.encode(submitterEmail,"UTF-8")
                    + "&" + PAYMENTAMOUNT_KEY + "=" + URLEncoder.encode(amountPaid,"UTF-8")
                    + "&" + PAYMENTMODE_KEY + "=" + URLEncoder.encode(paymentMode,"UTF-8")
                    + "&" + PAYMENTREFERENCE_KEY + "=" + URLEncoder.encode(paymentReference,"UTF-8")
                    + "&" + PAYMENTPURPOSE_KEY + "=" + URLEncoder.encode(paymentPurpose,"UTF-8")
                    + "&" + PAYMENTMONTH_KEY + "=" + URLEncoder.encode(paymentMonth,"UTF-8")
                    + "&" + PAYMENTYEAR_KEY + "=" + URLEncoder.encode(paymentYear,"UTF-8")
                    + "&" + PAYMENTDATE_KEY + "=" + URLEncoder.encode(transferDate,"UTF-8")
                    + "&" + COMMENTS_KEY + "=" + URLEncoder.encode(comments,"UTF-8")
                    + "&" + MULTIPAGEHISTORY_KEY + "=" + URLEncoder.encode(multiPageFormAppend,"UTF-8")
                    + "&" + SUBMIT_KEY + "=" + URLEncoder.encode(submit,"UTF-8");


            OkHttpClient clientMaintenance = new OkHttpClient();

            // RequestBody bodyMaintenance = new FormBody.Builder()
     /*       FormBody bodyMaintenance = new FormBody.Builder()
                    .add(PASSWORD_KEY,password)
                    .add(BLOCKNUMBER_KEY,blockNumber)
                    .add(HOUSENUMBER_KEY,houseNumber)
                    .add(SUBMITTEREMAIL_KEY,submitterEmail)
                    .add(PAYMENTAMOUNT_KEY,amountPaid)
                    .add(PAYMENTMODE_KEY,paymentMode)
                    .add(PAYMENTREFERENCE_KEY,paymentReference)
                    .add(PAYMENTPURPOSE_KEY,paymentPurpose)
                    .add(PAYMENTMONTH_KEY,paymentMonth)
                    .add(PAYMENTYEAR_KEY,paymentYear)
                    .add(PAYMENTDATE_KEY,paymentDate)
                    .add(COMMENTS_KEY,comments)
                    .add(MULTIPAGEHISTORY_KEY,multiPageFormAppend)
                    .build();
*/
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
        }
        catch (Exception ex)
        {
            //ex.printStackTrace();
        }

        return null;
    }
}

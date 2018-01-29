package online.whitefieldmudra.aptmaintenance;

/**
 * Created by welcome on 12-12-2017.
 */
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.app.ProgressDialog;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResidentsPaymentInfoHttpResponse extends AsyncTask<String, Void, List<UserPaymentInfo>> {
  //  private ProgressDialog progressBar;
  //  private Context context;
   /* public ResidentsPaymentInfoHttpResponse(Context _context)
    {
        context = _context;
    }
    */
@Override
protected List<UserPaymentInfo> doInBackground(String... params){
    String flatNo = params[0];
    String urls = "https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=1cESWvyfqm7M3RDikZnQR0G_fHj6OmFsB-XRxo0jaTAg&sheet=ResidentsInfo";
    List<UserPaymentInfo> residentsMonthlyPayments = new ArrayList<>();

    try {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urls)
                .build();

        Response responses = null;

        try
        {
            responses = client.newCall(request).execute();
            String jsonData = responses.body().string();
            JSONObject jobject = new JSONObject(jsonData);
            JSONArray jarray = jobject.getJSONArray("ResidentsInfo");

            int limit = jarray.length();

            for(int i=0;i<limit; i++)
            {
                JSONObject object = jarray.getJSONObject(i);
                if(object.getString("FlatNo").equals(flatNo) && object.getString("PaymentStatus").equals("notpaid")) {
                    UserPaymentInfo residentMaintePayment = new UserPaymentInfo();
                    UserInfo residentInfo = new UserInfo();
                    residentInfo.setUserFlatNo(object.getString("FlatNo"));
                    residentInfo.setUserName(object.getString("Name"));
                    residentInfo.setUserEamil(object.getString("OwnerEmail"));
                    residentMaintePayment.setResidentData(residentInfo);
                    residentMaintePayment.setactualAmount(object.getLong("Actualamountneedtopay"));
                    residentMaintePayment.setPaymentYear(object.getInt("Year"));
                    residentMaintePayment.setPaymentMonth(object.getString("Month"));
                    residentsMonthlyPayments.add(residentMaintePayment);
                }
            }

        }

        catch (IOException e)
        {
          //  e.printStackTrace();
        }

    }
    catch (Exception ex)
    {
       // ex.printStackTrace();
    }
    return residentsMonthlyPayments;

        }

  /*  @Override
    protected void onPreExecute() {
-
        //progressBar = new ProgressDialog(v.getContext());
        progressBar = new ProgressDialog(this.context);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please Wait.......");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        //progressBarStatus = 0;
        new Thread(new Runnable() {
            public void run() {
                try {
                  //  Select_PaymentMonths(userFlatNumber.getText().toString());
                    Thread.sleep(1000);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    } */

 }

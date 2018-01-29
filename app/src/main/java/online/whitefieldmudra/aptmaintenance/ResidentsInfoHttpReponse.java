package online.whitefieldmudra.aptmaintenance;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

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

/**
 * Created by welcome on 25-10-2017.
 */

public class ResidentsInfoHttpReponse extends AsyncTask<Void, Void, List<UserInfo>> {

    @Override
    protected List<UserInfo> doInBackground(Void... params) {
        String urls = "https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=1EdKRA-w0ckFz3nO0UJ9vO5qLwFnkY0YDwmLSY00dfG4&sheet=Form responses 1";
        List<UserInfo> ResidentsInfo = new ArrayList<>();
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
                JSONArray jarray = jobject.getJSONArray("Form responses 1");



                int limit = jarray.length();
                for(int i=0;i<limit; i++)
                {
                    JSONObject object = jarray.getJSONObject(i);
                    UserInfo residentDetails = new UserInfo();


                    String residentGroup = object.getString("ResidentGroup");
                    String residentType = object.getString("ResidentType");
                    boolean IsOwner = false;
                    boolean IsOwnerFamilyMember = false;

                    if(residentGroup.toString().toUpperCase() == "OWNER" && residentType.toString().toUpperCase() == "SELF")
                    {
                        IsOwner = true;
                    }
                    else
                    {
                        IsOwner = false;
                    }

                    if(residentGroup.toString().toUpperCase() == "OWNER" && residentType.toString().toUpperCase() == "FAMILYMEMBER")
                    {
                        IsOwnerFamilyMember = true;
                    }
                    else
                    {
                        IsOwnerFamilyMember = false;
                    }


                    boolean IsMC = Boolean.parseBoolean(object.getString("IsMC"));
                    residentDetails.setUserEamil(object.getString("ResidentEmail"));
                    residentDetails.setUserFlatNo(object.getString("FlatNo"));
                    residentDetails.setUserName(object.getString("ResidentName"));
                    residentDetails.setUserPhoneNo(object.getString("phonenumber"));
                    residentDetails.setUserWhatsAppNo(object.getString("whatsupnumber"));
                    residentDetails.setUserIsMc(IsMC);
                    residentDetails.setUserIsOwner(IsOwner);
                    residentDetails.setUserIsOwner(IsOwnerFamilyMember);
                    ResidentsInfo.add(residentDetails);
                }

            }

             catch (IOException e)
            {
              //  e.printStackTrace();
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return ResidentsInfo;
    }

 /*   @Override
    protected void onPostExecute(List<UserInfo> userInfos) {
        super.onPostExecute(userInfos);
    }
    */
}

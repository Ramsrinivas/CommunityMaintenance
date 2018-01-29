package online.whitefieldmudra.aptmaintenance;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by welcome on 06-11-2017.
 */

public class ResidentsInformation {

    public List<UserInfo> getResidentsDetails()
    {
        String urls = "https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=1EdKRA-w0ckFz3nO0UJ9vO5qLwFnkY0YDwmLSY00dfG4&sheet=OwnersDetails";
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
                JSONArray jarray = jobject.getJSONArray("OwnersDetails");



                int limit = jarray.length();
                for(int i=0;i<limit; i++)
                {
                    JSONObject object = jarray.getJSONObject(i);
                    UserInfo residentDetails = new UserInfo();

                    boolean IsMC = Boolean.parseBoolean(object.getString("IsMC"));
                    boolean IsOwner = Boolean.parseBoolean(object.getString("IsOwner"));
                    boolean IsOwnerFamilyMember = Boolean.parseBoolean(object.getString("IsOwnerFamilyMember"));

                    residentDetails.setUserEamil(object.getString("OwnerEmail"));
                    residentDetails.setUserFlatNo(object.getString("FlatNo"));
                    residentDetails.setUserName(object.getString("Name"));
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
}

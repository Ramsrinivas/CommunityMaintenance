package online.whitefieldmudra.aptmaintenance;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Created by welcome on 20-10-2017.
 */

public class SubmitForm {

    public void tntFormSubmit(String urlString, String[] perameterName, String[] perameterValue){
    try
    {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                        .add(perameterName[0],perameterValue[0])
                        .add(perameterName[1],perameterValue[1])
                        .add(perameterName[2],perameterValue[2])
                        .add(perameterName[3],perameterValue[3])
                        .add(perameterName[4],perameterValue[4])
                        .add(perameterName[5],perameterValue[5])
                        .add(perameterName[6],perameterValue[6])
                        .add(perameterName[7],perameterValue[7])
                        .add(perameterName[8],perameterValue[8])
                        .add(perameterName[9],perameterValue[9])
                        .add(perameterName[10],perameterValue[10])
                        .add(perameterName[11],perameterValue[11])
                        .add(perameterName[12],perameterValue[12])
                        .add(perameterName[13],perameterValue[13])
                        .add(perameterName[14],perameterValue[14])
                        .add(perameterName[15],perameterValue[15])
                        .build();
        Request request = new Request.Builder()
                        .url(urlString)
                        .post(body)
                        .build();
        Response response = client.newCall(request).execute();


    }
    catch (Exception ex)
    {
       // ex.printStackTrace();
    }
    }

  }

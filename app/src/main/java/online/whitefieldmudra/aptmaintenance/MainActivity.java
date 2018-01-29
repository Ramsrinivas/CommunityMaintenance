package online.whitefieldmudra.aptmaintenance;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;

public class MainActivity extends BaseActivity //implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
{
    SessionManagement loginSession;
    private LinearLayout registraionLayout, submitItemsLayout;
    String LoginUsername,LoginUserEmail,LoginUserFlatNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        getLayoutInflater().inflate(R.layout.activity_main,frameLayout);
        loginSession = new SessionManagement(getApplicationContext());
        //loginSession.createLoginSession("Manoj","Ramsrinivaskumar.p@gmail.com");
        loginSession.checkLogin();
        //TextView sNameLabel = (TextView) findViewById(R.id.sName_lable);
        registraionLayout = (LinearLayout) findViewById(R.id.layout_register);
        submitItemsLayout = (LinearLayout) findViewById(R.id.maintenenace_submit);

        //Start Get the Residents Information from Login session
        HashMap<String, String> user = loginSession.getUserDetails();
        LoginUsername = user.get("UserName");
        LoginUserEmail = user.get("UserEmail");
        LoginUserFlatNo = user.get("UserFlatNo");
//End Get the Residents Information from Login session
        if (LoginUserFlatNo == null)
        {
            registraionLayout.setVisibility(LinearLayout.VISIBLE);
            submitItemsLayout.setVisibility(LinearLayout.GONE);
        }
        else
        {
            registraionLayout.setVisibility(LinearLayout.GONE);
            submitItemsLayout.setVisibility(LinearLayout.VISIBLE);
        }
       }

        @Override
        protected void onStart()
             {
                super.onStart();
                loginSession = new SessionManagement(getApplicationContext());
                loginSession.checkLogin();

            }

    //Redirecting Activity to TenantDetails Submit page
    public void Redirect_Register(View view){
        Intent register_intent = new Intent(this,ResidentRegistration.class);
        startActivity(register_intent);
    }
    //Redirecting Activity to Monthly Maintenance Submit page
   public void Redirect_MaintenanceSubmit(View view){
           Intent mSubmit_intent = new Intent(this,MaintenanceSubmitActivity.class);
           startActivity(mSubmit_intent);

        }

//Redirecting Activity to TenantDetails Submit page
   public void Redirect_TendetailsSubmit(View view){
       Intent tenantSubmit_intent = new Intent(this,TenantVerificationActivity.class);
       startActivity(tenantSubmit_intent);
        }



/*
    public void Redirect_TestSubmit(View view) {

 //       String URLformsubmit = "https://docs.google.com/forms/d/e/1FAIpQLSeHlROie2SVoELoSZ4XTdLM5XrX_00WMtFIj5m0rK7l2naafw/formResponse";

 //       String submitterName = "Ramsrinivaskumar.p";
 //       String submitterEmail = "Ramsrinivaskumar.p@gmail.com";
//        String submitterPhoneNo = "9952949886";
 //       String dob = "2017-10-25";

 //       testHttpRequest TestSubmit = new testHttpRequest();

 //       TestSubmit.execute(URLformsubmit,submitterName,submitterEmail,submitterPhoneNo,dob);

        ResidentsInfoHttpReponse residentsDetails = new ResidentsInfoHttpReponse();
        residentsDetails.execute();
    }
*/
}

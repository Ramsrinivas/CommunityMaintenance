package online.whitefieldmudra.aptmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    private SignInButton Signin;
    private GoogleApiClient googleApiClient;
    private UserInfo loggedInUserInfo;
    private SessionManagement userLoginSession;
    private static final int REQ_CODE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Signin = (SignInButton)findViewById(R.id.btn_signin);
        Signin.setOnClickListener(this);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();
    };

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onClick(View v) {
            signin();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signin()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient);
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }

    private void handleresult(GoogleSignInResult result)
    {
        try {
            if (result.isSuccess()) {
                loggedInUserInfo = new UserInfo();
                userLoginSession = new SessionManagement(getApplicationContext());
                GoogleSignInAccount account = result.getSignInAccount();
                loggedInUserInfo.setUserName(account.getDisplayName());
                loggedInUserInfo.setUserEamil(account.getEmail());

                /*
                if (!account.getPhotoUrl().toString().isEmpty()) {
                    loggedInUserInfo.setUserPhotoUrl(account.getPhotoUrl().toString());
                } else {
                    loggedInUserInfo.setUserPhotoUrl("https://lh4.googleusercontent.com/mP4xY5XusB0xedTFKI5MXjDUzvbCKQy5EuhHGZzLzi-7tH92rvsSzPzRA60LXOy6a3UNJBlxOw=w170");
                }
                */
                //userLoginSession.createLoginSession(loggedInUserInfo.getUserName().toString(), loggedInUserInfo.getUserEmail().toString(), loggedInUserInfo.getUserPhotoUrl().toString());

                userLoginSession.createLoginSession(loggedInUserInfo.getUserName().toString(), loggedInUserInfo.getUserEmail().toString());
                Intent main_intent = new Intent(this, MainActivity.class);
                startActivity(main_intent);


            }
        }
        catch (Exception ex)
        {
          //  ex.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleresult(result);
        }

    }

    public void googleSignOut()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
    }
}

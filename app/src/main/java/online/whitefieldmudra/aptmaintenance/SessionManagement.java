package online.whitefieldmudra.aptmaintenance;


import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.SystemClock;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ram on 06-10-2017.
 */

public class SessionManagement {
    SharedPreferences sPref;
    Editor editor;
    Context _context;
    List<UserInfo> ResidentsInfo;

    int Private_Mode = 0;
    private static final String Pref_Name = "UserProfilePref";
    private static final String Is_Login = "IsLoggedIn";
    private static final String Key_UserName = "UserName";
    private static final String Key_Email = "UserEmail";
    private static final String Key_UserFlatNo = "UserFlatNo";
    private static final String Key_UserPhoneNo = "UserPhoneNo";
    private static final String Key_UserWhatsUpNo = "UserWhatsUpNo";
    private static final String Key_IsMC = "UserIsMC";
    private static final String Key_IsOwner = "UserIsOwner";
    private static final String Key_IsOwnerFamilyMember = "UserIsOwnerFamilyMember";

    public SessionManagement(Context context)
    {
        this._context = context;
        sPref = _context.getSharedPreferences(Pref_Name,Private_Mode);
        editor = sPref.edit();
    }

    /*Create Login session*/
    public void createLoginSession(String userName,String email)
    {
        editor.putBoolean(Is_Login,true);
        editor.putString(Key_UserName,userName);
        editor.putString(Key_Email,email);
       // editor.putString(Key_UserImageUrl,userImageUrl);
        completeOtherUsersInfo(email);
        editor.commit();
    }

    public void completeOtherUsersInfo(String email) {
         ResidentsInfo = new ArrayList<>();
        UserInfo residentInfo = new UserInfo();

       /* ResidentsInfoHttpReponse getAllResidentsInfo = new ResidentsInfoHttpReponse();

        new ResidentsInfoHttpReponse(){
            @Override
            protected void onPostExecute(List<UserInfo> userInfos) {
                //super.onPostExecute(userInfos);
                ResidentsInfo = userInfos;
            }
        }.execute();


        ResidentsInformation residentsInforma = new ResidentsInformation();
       // ResidentsInfo = new ResidentsInfoHttpReponse.get();
        ResidentsInfo = residentsInforma.getResidentsDetails();
*/
       try {
           ResidentsInfo = new ResidentsInfoHttpReponse().execute().get();

        int usersCount = ResidentsInfo.size();

        for(int i = 0; i < usersCount; i++)
        {
            residentInfo = ResidentsInfo.get(i);
            String email1 = residentInfo.getUserEmail().toString().toUpperCase().trim();
            String email2 = email.toString().toUpperCase().trim();
        //    if(residentInfo.getUserEmail().toString().toUpperCase().trim() == email.toString().toUpperCase().trim())
            if(email1.equals(email2))
            {
            //Needs to updated user all other perameters
                editor.putString(Key_UserFlatNo,residentInfo.getUserFlatNo());
                editor.putString(Key_UserPhoneNo,residentInfo.getUserPhoneNo());
                editor.putString(Key_UserWhatsUpNo,residentInfo.getUserPhoneNo());
                editor.putBoolean(Key_IsMC,residentInfo.getUserIsMc());
                editor.putBoolean(Key_IsOwner,residentInfo.getUserIsOwner());
                editor.putBoolean(Key_IsOwnerFamilyMember,residentInfo.getUserIsOwnerFamilyMember());

            }
        }
       }
       catch (Exception ex)
       {
          // ex.printStackTrace();
       }
    }


    /*Check user login status*/

    public void checkLogin()
    {
        if(!this.isLoggedIn())
        {
            this.redirectToLoginActivity();
        }
    }

    /*Check Login State*/

    public boolean isLoggedIn()
    {

        return sPref.getBoolean(Is_Login,false);
    }

    /*Clear Session details*/
    public void logOutUser()
    {
        editor.clear();
        editor.commit();
        this.redirectToLoginActivity();
    }

    /*Redirect to Login Activity*/
    public void redirectToLoginActivity()
    {
        //Afterlogout redirect user to login Activity
        Intent intent = new Intent(_context,SigninActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(intent);
    }

    /* Get stored session data */
    public HashMap<String, String> getUserDetails(){

        HashMap<String, String> user = new HashMap<String, String>();
// user name
        user.put(Key_UserName, sPref.getString(Key_UserName, null));
// user email id
        user.put(Key_Email, sPref.getString(Key_Email, null));
// user flat Number
        user.put(Key_UserFlatNo, sPref.getString(Key_UserFlatNo, null));
// Is logged in User
 //       user.put(Is_Login, sPref.getString(Is_Login, null));
// UserPhoneNo
        user.put(Key_UserPhoneNo, sPref.getString(Key_UserPhoneNo, null));
// User whatsupNumber
       user.put(Key_UserWhatsUpNo, sPref.getString(Key_UserWhatsUpNo, null));
// Is logged in User MC Member
    //   user.put(Key_IsMC, sPref.getString(Key_IsMC, null));
// Is logged in User is Owner
 //      user.put(Key_IsOwner, sPref.getString(Key_IsOwner, null));
// Is logged in User OwnerfamilyMember
  //      user.put(Key_IsOwnerFamilyMember, sPref.getString(Key_IsOwnerFamilyMember, null));

        // user Image URL
        //user.put(Key_UserImageUrl, sPref.getString(Key_UserImageUrl, null));

        // return user
        return user;
    }
}

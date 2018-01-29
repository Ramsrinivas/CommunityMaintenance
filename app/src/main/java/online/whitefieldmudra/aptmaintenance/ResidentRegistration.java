package online.whitefieldmudra.aptmaintenance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ResidentRegistration extends BaseActivity {

    private EditText flatNumber,userName,userEmail,residentGroup,residentType, residentPhoneNumber,residentWhatsupNumber;
    private Button btnFlatNumber, btnResidentGroup, btnResidentType, btnSubmit;
    SessionManagement loginSession;
    private String LoginUsername,LoginUserEmail,LoginUserFlatNo;
    private List<String> ResidentGruop,ResidentType;
    AlertDialog levelDialog;
    private TextInputLayout inputFlatNoLayout,inputNameLayout,inputEmailLayout,inputResidentGroupLayout,inputResidentTypeLayout,inputPhoneNoLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_registration);
        loginSession = new SessionManagement(getApplicationContext());

//Start Get the Residents Information from Login session
        HashMap<String, String> user = loginSession.getUserDetails();
        LoginUsername = user.get("UserName");
        LoginUserEmail = user.get("UserEmail");
        LoginUserFlatNo = user.get("UserFlatNo");
//End Get the Residents Information from Login session

//Start Field Layouts Initilization
        inputFlatNoLayout = (TextInputLayout) findViewById(R.id.input_layout_flatno);
        inputNameLayout = (TextInputLayout) findViewById(R.id.reg_input_layout_name);
        inputEmailLayout = (TextInputLayout) findViewById(R.id.reg_input_layout_email);
        inputResidentGroupLayout = (TextInputLayout) findViewById(R.id.reg_layout_ResiGroup);
        inputResidentTypeLayout = (TextInputLayout) findViewById(R.id.reg_layout_ResiType);
        inputPhoneNoLayout = (TextInputLayout) findViewById(R.id.input_layout_phoneNumber);
//End Field Layouts Initilization


        flatNumber = (EditText) findViewById(R.id.reg_flat_no);
        flatNumber.setFocusable(false);

        residentGroup = (EditText) findViewById(R.id.reg_resi_group);
        residentGroup.setFocusable(false);

        residentType = (EditText) findViewById(R.id.reg_resi_Type);
        residentType.setFocusable(false);

        userName = (EditText) findViewById(R.id.reg_user_name);
        userName.setText(LoginUsername);

        userEmail = (EditText) findViewById(R.id.reg_user_email);
        userEmail.setText(LoginUserEmail);

        residentPhoneNumber = (EditText) findViewById(R.id.reg_flat_phoneNumber);
        residentWhatsupNumber = (EditText) findViewById(R.id.reg_flat_whatsupNumber);



        btnFlatNumber = (Button) findViewById((R.id.select_FlatNo));
        btnFlatNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Select_FlatName();
            }
        });


        btnResidentGroup = (Button) findViewById((R.id.reg_ResiGroup));
        btnResidentGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Select_ResidentGroup();
            }
        });


        btnResidentType = (Button) findViewById((R.id.reg_ResiType));
        btnResidentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Select_ResidentType();
            }
        });

        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validRegistrationDetails()){
                    return;
                }
                Submit_Registration();
            }
        });
    }


    //Method to select the Flat Name
    public void Select_FlatName() {
        final CharSequence[] flatNames = getResources().getStringArray(R.array.flat_Numbers);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Flat Number");
        builder.setSingleChoiceItems(flatNames, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                flatNumber.setText(flatNames[item].toString());

                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    //Method to select the Resident Group
    public void Select_ResidentGroup() {
        final CharSequence[] residentGroups = {" Owner ","Tenant"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Resident Group");
        builder.setSingleChoiceItems(residentGroups, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                residentGroup.setText(residentGroups[item].toString());

                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    //Method to select the Resident Type
    public void Select_ResidentType() {
        final CharSequence[] residentTypes = {"Self","FamilyMember"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Resident Type");
        builder.setSingleChoiceItems(residentTypes, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                residentType.setText(residentTypes[item].toString());

                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    //Method to Submit to Registration form
    public void Submit_Registration()
    {
        String URLformsubmit = "https://docs.google.com/forms/d/e/1FAIpQLSc_mpudcff05DP9iZQDlmfd8C6wbozDJZ5BCUiUA0rSUqBSkw/formResponse";
        String flatno = flatNumber.getText().toString();
        String residentname = userName.getText().toString();
        String residentemail = userEmail.getText().toString();
        String residentgroup = residentGroup.getText().toString();
        String residenttype = residentType.getText().toString();
        String residentphonenumber = residentPhoneNumber.getText().toString();
        String residentwhatsupnumber = residentWhatsupNumber.getText().toString();
        String ismc = "False";
        String registername = LoginUsername;
        String registeremail = LoginUserEmail;
        ResidentRegistrationHttpRequest ResidentRegistration = new ResidentRegistrationHttpRequest();
        Boolean submitStatus = false;
        try {
                submitStatus = ResidentRegistration.execute(URLformsubmit, flatno, residentname, residentemail, residentgroup, residenttype, residentphonenumber, residentwhatsupnumber, ismc, registername, registeremail).get();
            }
        catch (Exception ex)
            {
            ex.printStackTrace();
                Toast.makeText(getApplicationContext(),"Registration Failed. Please Contact Management Commitee...",Toast.LENGTH_LONG).show();
            }
            if(submitStatus == true)
            {
                Toast.makeText(getApplicationContext(),"Registration form was submitted Successfully.....",Toast.LENGTH_LONG).show();
                //redirect_MainActivity();
                loginSession.logOutUser();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Registration Failed. Please Contact Management Commitee...",Toast.LENGTH_LONG).show();
            }
    }

    private void redirect_MainActivity()
    {
        Intent mSubmit_intent = new Intent(this,MainActivity.class);
        startActivity(mSubmit_intent);
    }

   private boolean validRegistrationDetails() {

       if(!validateFlatNumber()) {
           return false;
       }

       if(!validateName()) {
           return false;
       }

       if(!validateEmail()) {
           return false;
       }

       if(!validateResidentGroup()) {
           return false;
       }

       if(!validateResidentType()) {
           return false;
       }

       if(!validateResidentPhoneNumber()) {
           return false;
       }

       return true;
   }



    // Validate FlatNumber field. It should not be empty
    private boolean validateFlatNumber() {
        if (CommonFunctionality.ISstringEmpty(flatNumber.getText().toString())) {
            inputFlatNoLayout.setError(getString(R.string.err_msg_flatNumber));
            requestFocus(flatNumber);
            return false;
        } else {
            inputFlatNoLayout.setErrorEnabled(false);
        }
        return true;
    }

    // Validate Name field. It should not be empty
    private boolean validateName() {
        if (CommonFunctionality.ISstringEmpty(userName.getText().toString())) {
            inputNameLayout.setError(getString(R.string.err_msg_name));
            requestFocus(userName);
            return false;
        } else {
            inputNameLayout.setErrorEnabled(false);
        }
        return true;
    }

    // Validate Email field. It should not be empty
    private boolean validateEmail() {
        if (!CommonFunctionality.EmailValidate(userEmail.getText().toString())) {
            inputEmailLayout.setError(getString(R.string.err_msg_email));
            requestFocus(userEmail);
            return false;
        } else {
            inputEmailLayout.setErrorEnabled(false);
        }
        return true;
    }

    // Validate ResidentGroup field. It should not be empty
    private boolean validateResidentGroup() {
        if (CommonFunctionality.ISstringEmpty(residentGroup.getText().toString())) {
            inputResidentGroupLayout.setError(getString(R.string.err_msg_ResidentGroup));
            requestFocus(residentGroup);
            return false;
        } else {
            inputResidentGroupLayout.setErrorEnabled(false);
        }
        return true;
    }

    // Validate ResidentGroup field. It should not be empty
    private boolean validateResidentType() {
        if (CommonFunctionality.ISstringEmpty(residentType.getText().toString())) {
            inputResidentTypeLayout.setError(getString(R.string.err_msg_ResidentType));
            requestFocus(residentType);
            return false;
        } else {
            inputResidentTypeLayout.setErrorEnabled(false);
        }
        return true;
    }

    // Validate ResidentPhoneNumber field. It should not be empty
    private boolean validateResidentPhoneNumber() {
        if (CommonFunctionality.ISstringEmpty(residentPhoneNumber.getText().toString())) {
            inputPhoneNoLayout.setError(getString(R.string.err_msg_PhoneNumber));
            requestFocus(residentPhoneNumber);
            return false;
        } else {
            inputPhoneNoLayout.setErrorEnabled(false);
        }
        return true;
    }


    // Method to set foocus on Error Field
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}


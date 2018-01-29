package online.whitefieldmudra.aptmaintenance;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ProgressBar;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Handler;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.ProgressDialog;

/**
 * Created by Ram on 08-10-2017.
 */

public class MaintenanceSubmitActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{
    private ProgressDialog progressBarLoadData;
    private LinearLayout paymentLayoutUserInfo, paymentInfo;
    private Button btn_BanktransferDetails,btn_BackLayout,btn_paymentGateway,btn_SubmitMaintenance,btn_selectmonth,btn_paymentpurpose,btn_selectflatno;
    private TextInputLayout inputLayoutPurposeofPayment, inputLayoutName,inputLayoutEmail,inputLayoutAmountPaid;
    private EditText userName,userEmail,amountPaid,paymentDate,purposeofPayment,paymentMonth,userFlatNumber,submitComments,ref_Number;
    private RadioGroup paymentType;
   // AutoCompleteTextView selectFlatNumber;
    String[] flat_Names;
    AlertDialog levelDialog,flatNoDialog;
    //private SimpleDateFormat dateFormatter;
    private DatePickerDialog.OnDateSetListener transDateListener;
    //private int year,month,day;
    private List<String> paymentPurposeItems,residentFlatNames;
    private List<UserPaymentInfo> residentsPaymentInfo;
    private List<UserInfo> residentRegisterData;
    SessionManagement loginSession;
    String LoginUsername,LoginUserEmail,LoginUserFlatNo,payment_Method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maintenancesubmit);
//Start Get the Residents Information from Login session
        loginSession = new SessionManagement(getApplicationContext());
        loginSession.checkLogin();
        HashMap<String, String> user = loginSession.getUserDetails();
        LoginUsername = user.get("UserName");
        LoginUserEmail = user.get("UserEmail");
        LoginUserFlatNo = user.get("UserFlatNo");
//End Get the Residents Information from Login session

        getLayoutInflater().inflate(R.layout.activity_maintenancesubmit,frameLayout);

        paymentLayoutUserInfo = (LinearLayout) findViewById(R.id.payment_initialinfo);
        paymentInfo = (LinearLayout) findViewById(R.id.payment_BanktransferInfo);

        paymentInfo.setVisibility(LinearLayout.GONE);

       // selectFlatNumber = (AutoCompleteTextView)findViewById(R.id.text_flatName);
       // flat_Names = getResources().getStringArray(R.array.flat_Numbers);
       // ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,flat_Names);
       // selectFlatNumber.setAdapter(stringAdapter);
       // selectFlatNumber.setText(LoginUserFlatNo);

        paymentPurposeItems = new ArrayList<String>();
        paymentPurposeItems.add("MonthlyMaintenance");
        paymentPurposeItems.add("CommunityHall");
        paymentPurposeItems.add("GuestRoom");
        paymentPurposeItems.add("Others");

//Button event for to choose the purpose of payment
        purposeofPayment = (EditText) findViewById(R.id.payment_purpose);
        purposeofPayment.setFocusable(false);

        btn_paymentpurpose = (Button) findViewById((R.id.select_Paymentpurpose));
        btn_paymentpurpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Submit_Maintenance();
                Select_PurposeofPayment();
            }
        });

        //Spinner paymentPurposeSpinner = (Spinner) findViewById(R.id.payment_purpose);
        //paymentPurposeSpinner.setOnItemSelectedListener(this);

        //ArrayAdapter<String> paymentPurposeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,paymentPurposeItems);
        //paymentPurposeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //paymentPurposeSpinner.setAdapter(paymentPurposeAdapter);

// Button to redirect to Paymentgateway screen
        Button mnt_Button =(Button) findViewById(R.id.btn_paymentGateway);
        mnt_Button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View org)
            {
                Redirect_Paymentgateway();
            }
            });


 //Validate textbox fields in the first Layoutpage
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutAmountPaid = (TextInputLayout) findViewById(R.id.input_layout_amountPaid);
        inputLayoutPurposeofPayment = (TextInputLayout) findViewById(R.id.input_layout_purposeofPayment);

        userName = (EditText) findViewById(R.id.user_name);
        userEmail = (EditText) findViewById(R.id.user_email);
        amountPaid = (EditText) findViewById(R.id.amount_paid);
        userName.setText(LoginUsername);
        userName.setFocusable(false);
        userEmail.setText(LoginUserEmail);
        userEmail.setFocusable(false);
        userFlatNumber = (EditText) findViewById(R.id.text_flatName);
        paymentMonth = (EditText) findViewById(R.id.month_paid);
        paymentMonth.setFocusable(false);

        // select Payment Months and Amount
        btn_selectmonth = (Button) findViewById((R.id.select_month));
      //  progressBarLoadData = new ProgressDialog(this);
        btn_selectmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userFlatNumber != null)
                {
                //    progressBarLoadData.setMessage("Please Wait....");
                //    progressBarLoadData.show();

                   // btn_selectmonth.setClickable(false);
                   // btn_selectmonth.setEnabled(false);
              /*     progressBar = new ProgressDialog(v.getContext());
                   progressBar.setCancelable(true);
                   progressBar.setMessage("Please Wait to open Months Details.......");
                   progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                   progressBar.setProgress(0);
                   progressBar.setMax(100);
                   progressBar.show();
                   progressBarStatus = 0;
                   new Thread(new Runnable() {
                   public void run() {
                            try {
                                //Select_PaymentMonths(userFlatNumber.getText().toString());
                                Thread.sleep(1000);
                                progressBar.dismiss();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    */
                   // progressBarLoadData.dismiss();
                    Select_PaymentMonths(userFlatNumber.getText().toString());
                  //  btn_selectmonth.setClickable(true);
                  //  btn_selectmonth.setEnabled(true);
                }
                else
                    {
                       // progressBarLoadData.dismiss();
                        Toast.makeText(getApplicationContext(),"Please Select the Flat Nmber",Toast.LENGTH_LONG).show();
                }
            }
        });

//Button Event for to Banktransfer screen and validate the first screen fields
        btn_BanktransferDetails = (Button) findViewById(R.id.btn_Banktransfer);
        btn_BanktransferDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validPaymentPurpose()){
                    return;
                }
               if(!validateName()) {
                    return;
                }
                if(!validateEmail()) {
                    return;
                }

                Banktransfer_Information();
            }
        });


//Button Event for to redirect to Paymentgateway
        btn_BackLayout = (Button) findViewById(R.id.btn_Back);
        btn_BackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backlayout_Information();
            }
        });

//Button Event for to Submit Maintenance request
        btn_SubmitMaintenance = (Button) findViewById(R.id.btn_Submit);
        btn_SubmitMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit_Maintenance();
                //Redirect_Paymentgateway();
            }
        });

//Text box Event for to select Date of Amount Trasfer
        paymentDate = (EditText) findViewById(R.id.transfer_Date);
        paymentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTrasferDate();
            }
                });

        transDateListener = new DatePickerDialog.OnDateSetListener(){
//Override method to set set selected date month year
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                paymentDate.setText(date);
            }
        };

        paymentType = (RadioGroup) findViewById(R.id.payment_method);

        paymentType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.payment_banktransfter:
                        payment_Method = "BankTransfer";
                        break;
                    case R.id.payment_byCash:
                        payment_Method = "ByCash";
                        break;
                }
            }
        });

        ref_Number =  (EditText)findViewById(R.id.reference_Number);
        submitComments = (EditText) findViewById(R.id.input_comments);

        btn_selectflatno = (Button)findViewById(R.id.select_FlatNo);
        btn_selectflatno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Select_RegisterUserFlatNumber(userEmail.getText().toString());
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        loginSession = new SessionManagement(getApplicationContext());
        loginSession.checkLogin();

    }


//  Method to popup Date popup dialog
    private void setTrasferDate() {
        Calendar newCalendar = Calendar.getInstance();
        int year = newCalendar.get(Calendar.YEAR);
        int month = newCalendar.get(Calendar.MONTH);
        int day = newCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(MaintenanceSubmitActivity.this,android.R.style.Theme_Holo_Dialog_MinWidth,transDateListener,year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


// Method to Submit monthly Maintenance Request
    public void Submit_Maintenance() {
/*
        String URLformsubmit = "https://docs.google.com/forms/d/e/1FAIpQLSedXOsyQQOUtSMAHBwWLdxKs_GqVfUgS3HZBe3DQOT0nr6s0A/formResponse";
        String password = "101";
        String blockNumber = "B";
        String houseNumber = "303";
        String submitterEmail = "Ramsrinivaskumar.p@gmail.com";
        String amountPaid = "2890";
        String paymentMode = "Bycash";
        String paymentReference = "Bycash";
        String paymentPurpose = "Bycash";
        String paymentMonth = "November";
        String paymentYear = "2017";
        String paymentDate = "2017-10-25";
        String comments = "TestNoneedtotakeanyaction";
*/
        ArrayList<String> blockAndFlatNo = new ArrayList<String>();
        blockAndFlatNo = seperateBlockFlatNo(userFlatNumber.getText().toString().trim());

        String[] monthNames = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        Calendar calendar = Calendar.getInstance();
        int thisMonth = calendar.get(Calendar.MONTH);
        String currentMonth = monthNames[thisMonth];
        int currentYear = calendar.get(Calendar.YEAR);
        String amount_Paid;
        String[] paymentMonths = paymentMonth.getText().toString().split(",");
        for(int mont=0; mont<paymentMonths.length;mont++) {
            String[] payMonthYear = paymentMonths[mont].toString().split("-");
            String payMonth = payMonthYear[0];
            int payYear = Integer.parseInt(payMonthYear[1].toString());

            if (currentYear - payYear == 0) {
                if(payMonth.toUpperCase() == currentMonth.toUpperCase()) {

                    amount_Paid = amountPaid.getText().toString();
                }
                else {
                    amount_Paid = Integer.toString(0);
                }
            }
            else
            {
                amount_Paid = Integer.toString(0);
            }

            String URLformsubmit = "https://docs.google.com/forms/d/e/1FAIpQLSedXOsyQQOUtSMAHBwWLdxKs_GqVfUgS3HZBe3DQOT0nr6s0A/formResponse";
            String password = "101";
            String blockNumber = blockAndFlatNo.get(0).toString();
            String houseNumber = blockAndFlatNo.get(1).toString();
            String submitterEmail = userEmail.getText().toString();//"Ramsrinivaskumar.p@gmail.com";
            //String amount_Paid = amountPaid.getText().toString();
            String paymentMode = payment_Method.toString();
            String paymentReference = ref_Number.getText().toString();
            String paymentPurpose = purposeofPayment.getText().toString().trim();
            String paymentMonth = payMonth;
            String paymentYear = Integer.toString(payYear);//String.valueOf(currentYear);
            String payment_Date = paymentDate.getText().toString();
            String comments = "App" + submitComments.getText().toString();

            MaintenanceHttpRequest maintenanceRequestSubmit = new MaintenanceHttpRequest();

            maintenanceRequestSubmit.execute(URLformsubmit, password, blockNumber, houseNumber, submitterEmail, amount_Paid, paymentMode, paymentReference, paymentPurpose, paymentMonth, paymentYear, payment_Date, comments);
        }
            Toast.makeText(getApplicationContext(), "Monthly Maintenance Form Submitted", Toast.LENGTH_LONG).show();
            Log.i("MaintenanceSubmit", "Monthly Maintenance Form Submitted");
            //Thread.sleep(1000);
            redirect_MainActivity();

        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


//Method to select the purpose of payment
    public void Select_PurposeofPayment(){
        final CharSequence[] items = {" MonthlyMaintenance ","CommunityHall","GuestRoom","Other"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Purpose Of Payment");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                purposeofPayment.setText(items[item].toString());

                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }


//Method to select User Register FlatNumber
   public void Select_RegisterUserFlatNumber(String residentEmailId)
   {
       residentRegisterData = new ArrayList<UserInfo>();
       residentFlatNames = new ArrayList<String>();

        ResidentsInfoHttpReponse getResidentFlatNumbers = new ResidentsInfoHttpReponse();
       try {
           residentRegisterData = getResidentFlatNumbers.execute().get();
            String residentEmail = residentEmailId.trim();
           for(int j=0; j<residentRegisterData.size();j++ )
           {
               UserInfo userInformation = new UserInfo();
               userInformation = residentRegisterData.get(j);
               if(residentEmail.equals(userInformation.getUserEmail().trim()))
               {
                   residentFlatNames.add(userInformation.getUserFlatNo());
               }
           }
           final CharSequence[] flatNoItems = residentFlatNames.toArray(new CharSequence[residentFlatNames.size()]);
           AlertDialog.Builder builderAlert = new AlertDialog.Builder(this);
           builderAlert.setTitle("Select FlatNumber");
           builderAlert.setSingleChoiceItems(flatNoItems, -1, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int item) {

                   userFlatNumber.setText(flatNoItems[item].toString());

                   flatNoDialog.dismiss();
               }
           });
           flatNoDialog = builderAlert.create();
           flatNoDialog.show();
       }
       catch (Exception e)
       {
          // e.printStackTrace();
       }
   }

//Method to select the payment Months
    public void Select_PaymentMonths(String selectFlatNumber)
    {
        /*
        progressBarLoadData = new ProgressDialog(getApplicationContext());
        progressBarLoadData.setMessage("Please Wait....");
        progressBarLoadData.show();
        */
      //  ProgressDialog progressDialog = new ProgressDialog(this);
      //  progressDialog.setMessage("Please Wait......");
     //   progressDialog.show();

        residentsPaymentInfo = new ArrayList<UserPaymentInfo>();
        ResidentsPaymentInfoHttpResponse getResidentsPaymentMonthDetails = new ResidentsPaymentInfoHttpResponse();
        try {
            residentsPaymentInfo = getResidentsPaymentMonthDetails.execute(selectFlatNumber).get();

            int notPaidMonthsCount = residentsPaymentInfo.size();
            List<String> listItems = new ArrayList<String>();


            for(int i=0; i<notPaidMonthsCount; i++ )
            {
                UserPaymentInfo userNotPaidMonthInfo = new UserPaymentInfo();
                userNotPaidMonthInfo = residentsPaymentInfo.get(i);

                //listItems.add(userNotPaidMonthInfo.getPaymentMonth() +","+ userNotPaidMonthInfo.getPaymentoYear() +" - ₹" + userNotPaidMonthInfo.getactualAmount() + "/-");
                listItems.add(userNotPaidMonthInfo.getPaymentMonth() +" "+ userNotPaidMonthInfo.getPaymentoYear() +" ₹" + userNotPaidMonthInfo.getactualAmount() + "/-");
            }

            final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
// arraylist to keep the selected items
            final ArrayList seletedItems=new ArrayList();
            //progressBarLoadData.dismiss();
            //progressDialog.dismiss();
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Select Payment Month Year Amount")
                    .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                            if (isChecked) {
                                // If the user checked the item, add it to the selected items
                                //seletedItems.add(indexSelected);
                                seletedItems.add(indexSelected);
                            } else if (seletedItems.contains(indexSelected)) {
                                // Else, if the item is already in the array, remove it
                                seletedItems.remove(Integer.valueOf(indexSelected));
                            }
                        }
                    }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Collections.sort(seletedItems);

                            String selectedMonths = "";
                            float totalAmount=0;
                            for(int i = 0 ; i < seletedItems.size(); i++) {
                                //selectedMonths = selectedMonths + items[(int)(seletedItems.get(i))];
                                selectedMonths = selectedMonths + residentsPaymentInfo.get((int)seletedItems.get(i)).getPaymentMonth() +"-"+residentsPaymentInfo.get((int)seletedItems.get(i)).getPaymentoYear()+",";
                                totalAmount = totalAmount + residentsPaymentInfo.get((int)seletedItems.get(i)).getactualAmount();
                            }

                            paymentMonth.setText(selectedMonths);
                            amountPaid.setText(String.valueOf(totalAmount));
                            //selectedMonths.split(",");
                            //  Your code when user clicked on OK
                            //  You can write the code  to save the selected item here

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //  Your code when user clicked on Cancel
                        }
                    }).create();
            //return 50;
            dialog.show();

        }
        catch (Exception e)
        {
         //   e.printStackTrace();
        }

    }

//Method to make BankTransferinformation screen visible
    public void Banktransfer_Information(){

        paymentLayoutUserInfo.setVisibility(LinearLayout.GONE);
        paymentInfo.setVisibility(LinearLayout.VISIBLE);
    }

//Method to make first screen visible
    public void Backlayout_Information(){

        paymentLayoutUserInfo.setVisibility(LinearLayout.VISIBLE);
        paymentInfo.setVisibility(LinearLayout.GONE);
    }

    public void Redirect_Paymentgateway(){
        Toast.makeText(getApplicationContext(),"PaymentGateway Process is under Process.....",Toast.LENGTH_LONG).show();
    }


    // Validate Payment Purpose slection
    private boolean validPaymentPurpose()
    {
        if (CommonFunctionality.ISstringEmpty(purposeofPayment.getText().toString())) {
            inputLayoutPurposeofPayment.setError(getString(R.string.err_msg_paymentpurpose));
            requestFocus(purposeofPayment);
            return false;
        } else {
            inputLayoutPurposeofPayment.setErrorEnabled(false);
        }
        return true;
    }
// Validate Name field. It should not be empty
    private boolean validateName() {
        if (CommonFunctionality.ISstringEmpty(userName.getText().toString())) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(userName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }
        return true;
    }

// Validate Email field. It should not be empty
    private boolean validateEmail() {
        String email = userEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(userEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }


// Method to set foocus on Error Field
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //Method to validate emailid matches
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //Split string into block and flatnmber
    private ArrayList<String> seperateBlockFlatNo(String hoouseNumber)
    {
        ArrayList<String> blockFlatNo = new ArrayList<String>();
        Matcher splitPatternMatch = Pattern.compile("[0-9]+|[a-z]+|[A-Z]+").matcher(hoouseNumber);

        while (splitPatternMatch.find()){
            blockFlatNo.add(splitPatternMatch.group());
        }

        return blockFlatNo;
    }

    private void redirect_MainActivity()
    {
        Intent mSubmit_intent = new Intent(this,MainActivity.class);
        startActivity(mSubmit_intent);
    }

}


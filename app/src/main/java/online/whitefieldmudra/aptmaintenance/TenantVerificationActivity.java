package online.whitefieldmudra.aptmaintenance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Ram on 08-10-2017.
 */

public class TenantVerificationActivity extends BaseActivity {

 /*   String url = "https://docs.google.com/forms/d/e/1FAIpQLSeZe3KzDyl_C3GZRwOb0-0TeRMNV9p6INzr04MzHyyuR_edDg/formResponse";
    String password = "101";
    String blockNumber = "B";
    String houseNumber = "303";
    String ownerName = "TestOwner";
    String ownerEmail = "Test@gmail.com";
 //   String ownerPhoneNumber = "TestPhoneNumber";
 //   String tenantName = "TestTntName";
 //   String tenantPhoneNumber = "TntPhoneNumber";
 //   String tenantWhatsAppNumber = "TntWhatsAppNumber";
 //   String tenantEmailID = "tenenat@gmail.com";
//    String tenantPermanentAddress = "TenantAddress1";
 //   String tenantOccupationDate = "30/08/2017";
 //   String tenantIDType = "TestPancard";
 //   String tenantIDNumber = "Test12345";
 //   String tenantPermanetAddress = "TntPermanetaddress";
//    String tenantPreviousVecationDate = "29/08/2017";
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_tenantverification);
        getLayoutInflater().inflate(R.layout.activity_tenantverification,frameLayout);

        Button tnt_Button =(Button) findViewById(R.id.btn_tntSubmit);

        tnt_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String URLSUBMIT = "https://docs.google.com/forms/d/e/1FAIpQLSeZe3KzDyl_C3GZRwOb0-0TeRMNV9p6INzr04MzHyyuR_edDg/formResponse";
                String password = "101";
                String blockNumber = "B";
                String houseNumber = "303";
                String ownerName = "TestOwner";
                String ownerEmail = "Rspolavarapu44@yahoo.com";
                String ownerPhoneNumber = "TestPhoneNumber";
                String tenantName = "TestTntName";
                String tenantPhoneNumber = "TntPhoneNumber";
                String tenantWhatsAppNumber = "TntWhatsAppNumber";
                String tenantEmailID = "tenenat@gmail.com";
                String tenantPermanentAddress = "TenantAddress1";
                String tenantOccupationDate = "30/08/2017";
                String tenantIDType = "TestPancard";
                String tenantIDNumber = "Test12345";
                String tenantPermanetAddress = "TntPermanetaddress";
                String tenantPreviousVecationDate = "29/08/2017";



                 TenantHttpRequest tntRequestSubmit = new TenantHttpRequest();
                tntRequestSubmit.execute(URLSUBMIT,password,blockNumber,houseNumber,ownerName,ownerEmail);

                Toast.makeText(getApplicationContext(),"Tenant Form Submitted",Toast.LENGTH_LONG).show();
                Log.i("TenantSubmit","TenantInformation was submitted");
            }
        });
    }
}

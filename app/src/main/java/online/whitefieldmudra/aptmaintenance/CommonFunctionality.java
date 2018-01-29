package online.whitefieldmudra.aptmaintenance;

import android.text.TextUtils;

/**
 * Created by welcome on 31-10-2017.
 */

public class CommonFunctionality {
    public static boolean ISstringEmpty(String inputString)
    {
        if(inputString.toString().trim().isEmpty())
            return true;
        else
            return false;
    }
    public static boolean EmailValidate(String emailInputString)
    {
        boolean emailMatches = android.util.Patterns.EMAIL_ADDRESS.matcher(emailInputString).matches();
        boolean isValueEmpty = emailInputString.toString().trim().isEmpty();

        if(!isValueEmpty & emailMatches) {
                return true;
        }
        else {
            return false;
        }
    }

}

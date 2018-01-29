package online.whitefieldmudra.aptmaintenance;

/**
 * Created by welcome on 19-10-2017.
 */

public class UserInfo {

    private String userName;
    private String userEmail;
    private String userPhotoUrl;
    private String userBlockNo;
    private String userHouseNo;
    private String userFlatNo;
    private String userPhoneNo;
    private String userWhatsAppNo;
    private Boolean isMc;
    private Boolean isOwner;
    private Boolean isOwnerFamilyMember;

    //User Name
    public void setUserName(String value) {
        userName = value;
    }
    public String getUserName()
    {
        return userName;
    }

    //User Email
    public void setUserEamil(String value) {
        userEmail = value;
    }
    public String getUserEmail()
    {
        return userEmail;
    }

    //User Photo URL
    public void setUserPhotoUrl(String value){userPhotoUrl = value;};
    public String getUserPhotoUrl()
    {
        return userPhotoUrl;
    }

    //UserBlockNumber
    public void setUserBlockNo(String value) {
        userBlockNo = value;
    }
    public String getUserBlockNo()
    {
        return userBlockNo;
    }

    //UserHouseNumber
    public void setUserHouseNo(String value) {
        userHouseNo = value;
    }
    public String getUserHouseNo()
    {
        return userHouseNo;
    }

    //UserFlatNumber
    public void setUserFlatNo(String value) {
        userFlatNo = value;
    }
    public String getUserFlatNo()
    {
        return userFlatNo;
    }

    //UserFlatNumberJoinfromBlockNumberandFlatnumber
    public String getUserFlatNoJoin()
    {

        return userBlockNo + userHouseNo;
    }

    //UserPhoneNumber
    public void setUserPhoneNo(String value) {
        userPhoneNo = value;
    }
    public String getUserPhoneNo()
    {
        return userPhoneNo;
    }

    //UserWhatsAppNumber
    public void setUserWhatsAppNo(String value) {
        userWhatsAppNo = value;
    }
    public String getUserWhatsAppNo()
    {
        return userWhatsAppNo;
    }

    //UserisOwner
    public void setUserIsOwner(boolean value) {
        isOwner = value;
    }
    public boolean getUserIsOwner()
    {
        return isOwner;
    }

    //UserisOwnerFamilyMember
    public void setUserIsOwnerFamilyMember(boolean value) {
        isOwnerFamilyMember = value;
    }
    public boolean getUserIsOwnerFamilyMember()
    {
        return isOwnerFamilyMember;
    }

    //UserisMC
    public void setUserIsMc(boolean value) {
        isMc = value;
    }
    public boolean getUserIsMc()
    {
        return isMc;
    }
}

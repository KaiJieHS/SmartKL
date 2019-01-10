package my.edu.tarc.testsmartkl;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    public static final String SHARE_PREF_NAME = "shareprefCitizen";
    public static final String KEY_CITIZEN_ID = "citizenid";
    public static final String KEY_CUSERNAME = "username";
    public static final String KEY_CITIZEN_NAME = "name";
    public static final String KEY_CITIZEN_PHONE_NO = "phoneNo";
    public static final String KEY_CITIZENEMAIL = "email";

    private SharedPrefManager(Context context){
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance ==null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id,String username,String name,int phoneNo,String email){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARE_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_CITIZEN_ID,id);
        editor.putString(KEY_CUSERNAME,username);
        editor.putString(KEY_CITIZEN_NAME,name);
        editor.putInt(KEY_CITIZEN_PHONE_NO,phoneNo);
        editor.putString(KEY_CITIZENEMAIL,email);

        editor.apply();
        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARE_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_CUSERNAME,null)!=null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARE_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARE_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CITIZEN_NAME,null);
    }

    public String getPhoneNo(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARE_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CITIZEN_PHONE_NO,null);
    }
    public String getEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARE_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CITIZENEMAIL,null);
    }
}

package vn.viviu.produk.managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by h on 4/6/2018.
 */

public class PrefManager {
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private static int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "produk_app";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static PrefManager mInstance;

    public static PrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PrefManager(context);
        }
        return mInstance;
    }

    public PrefManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mPref.edit();
    }

    public void setFirstTimeLaunch() {
        mEditor.putBoolean(IS_FIRST_TIME_LAUNCH, true);
        mEditor.apply();
    }

    public boolean isFirstTimeLaunch() {
        return mPref.getBoolean(IS_FIRST_TIME_LAUNCH, false);
    }
}

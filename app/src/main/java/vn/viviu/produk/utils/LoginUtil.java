package vn.viviu.produk.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by h on 4/6/2018.
 */

public class LoginUtil {
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final String PASSWORD_REGEX = "^(?=.*\\d).{8,}$";

    public static boolean isEmail(String email) {
        boolean valid;

        if (TextUtils.isEmpty(email))
            valid = false;
        else {
            Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            valid = matcher.find();
        }
        return valid;
    }

    public static boolean isPassword(String password) {
        if (TextUtils.isEmpty(password))
            return false;
        else {
            Matcher matcher = Pattern.compile(PASSWORD_REGEX).matcher(password);
            return matcher.find();
        }
    }
}

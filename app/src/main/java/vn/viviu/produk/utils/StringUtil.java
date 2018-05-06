package vn.viviu.produk.utils;

public class StringUtil {
    public static String splitEmail(String email) {
        String[] s = email.split("@gmail.com");
        return s[0];
    }
}

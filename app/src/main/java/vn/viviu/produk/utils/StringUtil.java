package vn.viviu.produk.utils;

import java.text.DecimalFormat;

public class StringUtil {
    public static String splitEmail(String email) {
        String[] s = email.split("@gmail.com");
        return s[0];
    }

    public static String formatCurrency(int money) {
        DecimalFormat format = new DecimalFormat("#,###,###");
        return format.format(money) + " VNĐ";
    }
}

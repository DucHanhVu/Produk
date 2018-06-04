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

    public static String toString(int number) {
        return number + "";
    }

    public static int convertDate(String date) {
        String[] a = date.trim().split("/");
        return Integer.parseInt(a[1]);
    }
}

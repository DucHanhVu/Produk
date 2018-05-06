package vn.viviu.produk.callbacks;

import android.support.v4.app.Fragment;

public interface OnFragmentChangedListener {
    void onFragmentChanged(Fragment fragment, String tag);

    void onItemSelected(int index);
}

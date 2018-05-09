package vn.viviu.produk.callbacks;

import android.support.v4.app.Fragment;

public interface OnFragmentChangedListener {
    void onFragmentChanged(Fragment fragment, String tag, boolean backStack);

    void onItemChanged(int index);
}

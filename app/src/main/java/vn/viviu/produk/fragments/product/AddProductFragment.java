package vn.viviu.produk.fragments.product;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.viviu.produk.R;
import vn.viviu.produk.callbacks.OnFragmentChangedListener;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.utils.Key;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends BaseFragment {

    private OnFragmentChangedListener listener;

    public AddProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentChangedListener)
            listener = (OnFragmentChangedListener) context;
        else
            throw new RuntimeException(context.toString() + "must implement OnFragmentChangedListener");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager()
                .popBackStack(Key.KEY_ADD_PRODUCT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}

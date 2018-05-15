package vn.viviu.produk.fragments.order;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.viviu.produk.R;
import vn.viviu.produk.callbacks.OnFragmentChangedListener;
import vn.viviu.produk.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends BaseFragment implements OrderView{

    private OnFragmentChangedListener listener;

    public OrdersFragment() {
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
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showFab();
        showBackButton(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.title_orders);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @Override
    public void setOrders() {

    }
}

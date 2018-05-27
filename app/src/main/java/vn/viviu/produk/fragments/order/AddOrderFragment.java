package vn.viviu.produk.fragments.order;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.viviu.produk.R;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.utils.Key;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOrderFragment extends BaseFragment {
    @BindView(R.id.edt_order_id)
    EditText edtOrderId;
    @BindView(R.id.edt_order_customer)
    EditText edtOrderCustomer;
    @BindView(R.id.edt_order_ngdat)
    EditText edtOrderNgdat;
    @BindView(R.id.edt_order_ngban)
    EditText edtOrderNgban;
    @BindView(R.id.edt_ngay_dat)
    EditText edtNgayDat;
    @BindView(R.id.edt_ngay_giao)
    EditText edtNgayGiao;
    @BindView(R.id.spin_order_tuyen)
    Spinner spinOrderTuyen;
    @BindView(R.id.rg_nhom)
    RadioGroup rgNhom;
    Unbinder unbinder;

    public AddOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.add_order);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager()
                .popBackStackImmediate(Key.KEY_ADD_ORDER, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

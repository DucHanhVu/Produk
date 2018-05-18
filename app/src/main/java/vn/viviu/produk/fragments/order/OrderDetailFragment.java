package vn.viviu.produk.fragments.order;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.viviu.produk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends Fragment {


    @BindView(R.id.tv_customer_detail)
    TextView tvCustomerDetail;
    @BindView(R.id.tv_nglh_detail)
    TextView tvNglhDetail;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.tv_phone_detail)
    TextView tvPhoneDetail;
    @BindView(R.id.tv_payed_detail)
    TextView tvPayedDetail;
    @BindView(R.id.add_product_btn)
    Button addProductBtn;
    @BindView(R.id.rv_ctb)
    RecyclerView rvCtb;
    Unbinder unbinder;

    public OrderDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

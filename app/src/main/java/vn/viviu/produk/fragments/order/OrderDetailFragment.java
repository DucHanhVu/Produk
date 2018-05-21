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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.viviu.produk.R;
import vn.viviu.produk.adapters.OrderDetailAdapter;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.models.ChiTietBan;
import vn.viviu.produk.models.Customer;
import vn.viviu.produk.models.Order;
import vn.viviu.produk.utils.Key;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends BaseFragment implements OrderDetailView {
    @BindView(R.id.tv_customer_detail)
    TextView tvCustomerDetail;
    @BindView(R.id.tv_ngdat_detail)
    TextView tvNgdatDetail;
    @BindView(R.id.tv_ngban_detail)
    TextView tvNgbanDetail;
    @BindView(R.id.tv_sale_group_detail)
    TextView tvSaleGroupDetail;
    @BindView(R.id.tv_route_detail)
    TextView tvRouteDetail;
    @BindView(R.id.tv_order_date_detail)
    TextView tvOrderDateDetail;
    @BindView(R.id.tv_delivery_date_detail)
    TextView tvDeliveryDateDetail;
    @BindView(R.id.tv_payed_detail)
    TextView tvPayedDetail;
    @BindView(R.id.tv_total_detail)
    TextView tvTotalDetail;
    @BindView(R.id.add_product_btn)
    Button addProductBtn;
    @BindView(R.id.rv_ctb)
    RecyclerView rvCtb;
    Unbinder unbinder;

    /**
     * Adapter
     */
    private OrderDetailAdapter adapter;

    private OrderDetailPresenter orderDetailPre;
    private List<ChiTietBan> chiTietBans;
    private Customer customer;
    private Order order;

    public OrderDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            order = (Order) getArguments().getSerializable(Key.KEY_ORDER_DETAIL);
            orderDetailPre = new OrderDetailPresenterImpl(this);
            orderDetailPre.getData(order.getMaPhieuBan());
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setData(List<ChiTietBan> chiTietBans, Customer customer) {
        this.chiTietBans = chiTietBans;
        this.customer = customer;

        tvCustomerDetail.setText(customer.getTenKH());


        String payed = "Thanh toán trước/Tổng tiền : " + order.getThanhToanTruoc() + "/"
                + order.getTongTien() + " VNĐ";
        tvPayedDetail.setText(payed);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
    }
}

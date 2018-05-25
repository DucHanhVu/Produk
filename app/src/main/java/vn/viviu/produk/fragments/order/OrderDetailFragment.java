package vn.viviu.produk.fragments.order;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.viviu.produk.R;
import vn.viviu.produk.adapters.OrderDetailAdapter;
import vn.viviu.produk.callbacks.OnPassDataListener;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.models.ChiTietBan;
import vn.viviu.produk.models.Customer;
import vn.viviu.produk.models.Order;
import vn.viviu.produk.utils.Key;
import vn.viviu.produk.utils.StorageUtil;
import vn.viviu.produk.utils.StringUtil;

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
    @BindView(R.id.avt_customer_order_detail)
    CircleImageView avtCustomerOrderDetail;

    /**
     * Adapter
     */
    private OrderDetailAdapter adapter;

    private StorageUtil storageUtil;
    private StorageReference storageRef;

    private OrderDetailPresenter orderDetailPre;
    private List<ChiTietBan> chiTietBans;
    private Customer customer;
    private Order order;

    public OrderDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageUtil = new StorageUtil();
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
            orderDetailPre.getData(order.getMaPhieuBan(), order.getMaKH());

            String ngBan = "Người Bán: " + order.getNguoiBan();
            String ngDat = "Người Đặt: " + order.getNguoiDat();
            String maNhom = "Nhóm: " + order.getMaNhom();
            String maTuyen = "Mã Tuyến: " + order.getMaTuyen();
            String ngayDat = "Ngày Đặt: " + order.getNgayDat();
            String ngayGiao = "Ngày Giao: " + order.getNgayGiao();
            String thanhtoan;
            if (order.getThanhToanTruoc() > 0)
                thanhtoan = "Thanh toán trước: " + StringUtil.formatCurrency(order.getThanhToanTruoc());
            else
                thanhtoan = "Thanh toán ngay";
            String tongtien = "Tổng tiền: " + StringUtil.formatCurrency(order.getTongTien());

            tvNgbanDetail.setText(ngBan);
            tvNgdatDetail.setText(ngDat);
            tvSaleGroupDetail.setText(maNhom);
            tvRouteDetail.setText(maTuyen);
            tvOrderDateDetail.setText(ngayDat);
            tvDeliveryDateDetail.setText(ngayGiao);
            tvPayedDetail.setText(thanhtoan);
            tvTotalDetail.setText(tongtien);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showBackButton(true);
        hideFab();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setList(List<ChiTietBan> chiTietBans) {
        this.chiTietBans = chiTietBans;
        adapter = new OrderDetailAdapter(getContext(), chiTietBans, passDataListener);
        rvCtb.setAdapter(adapter);
        rvCtb.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false));
    }

    @Override
    public void setCustomer(Customer customer) {
        this.customer = customer;
        tvCustomerDetail.setText(customer.getTenKH());
        storageRef = storageUtil.getImage(customer.getHinhAnh(), StorageUtil.TYPE_AVATAR);
        storageRef.getDownloadUrl().addOnSuccessListener(uri ->
                Glide.with(getContext()).load(uri).into(avtCustomerOrderDetail));
    }

    OnPassDataListener passDataListener = (position, type) -> {

    };

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack(Key.KEY_ORDER_DETAIL,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}

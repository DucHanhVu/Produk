package vn.viviu.produk.fragments.order;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.viviu.produk.R;
import vn.viviu.produk.adapters.OrderDetailAdapter;
import vn.viviu.produk.callbacks.OnFragmentChangedListener;
import vn.viviu.produk.callbacks.OnPassDataListener;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.fragments.product.AddProductFragment;
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
    @BindView(R.id.rv_ctb)
    RecyclerView rvCtb;
    Unbinder unbinder;
    @BindView(R.id.avt_customer_order_detail)
    CircleImageView avtCustomerOrderDetail;
    @BindView(R.id.tv_debt_order_detail)
    TextView tvDebtOrderDetail;
    @BindView(R.id.banner_order)
    ImageView bannerOrder;

    /**
     * Adapter
     */
    private OrderDetailAdapter adapter;

    private StorageUtil storageUtil;
    private StorageReference storageRef;

    private OrderDetailPresenter orderDetailPre;
    private List<ChiTietBan> chiTietBans;
    private Order order;
    private Customer customer;
    private OnFragmentChangedListener listener;

    public OrderDetailFragment() {
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageUtil = new StorageUtil();
        orderDetailPre = new OrderDetailPresenterImpl(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        chiTietBans = new ArrayList<>(0);
        adapter = new OrderDetailAdapter(getContext(), chiTietBans, passDataListener);
        rvCtb.setAdapter(adapter);
        rvCtb.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showBackButton(true);
        hideFab();
        if (getArguments() != null) {
            String orderId = getArguments().getString(Key.KEY_ORDER_DETAIL);
            orderDetailPre.getData(orderId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.title_order_detail);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setOrder(Order order) {
        this.order = order;
        String ngBan = "Người Bán: " + order.getNguoiBan();
        String ngDat = order.getNguoiDat();
        String maNhom = "Nhóm: " + order.getMaNhom();
        String maTuyen = "Mã Tuyến: " + order.getMaTuyen();
        String ngayDat = "Ngày Đặt: " + order.getNgayDat();
        String ngayGiao = "Ngày Giao: " + order.getNgayGiao();
        String thanhtoan;
        if (order.getThanhToanTruoc() == 0) {
            thanhtoan = "Chưa thanh toán...";
        } else if (order.getThanhToanTruoc().equals(order.getTongTien()))
            thanhtoan = "Thanh toán ngay!";
        else
            thanhtoan = "Thanh toán trước: " + StringUtil.formatCurrency(order.getThanhToanTruoc());
        String tongTien = "Tổng tiền: " + StringUtil.formatCurrency(order.getTongTien());

        tvNgbanDetail.setText(ngBan);
        tvNgdatDetail.setText(ngDat);
        tvSaleGroupDetail.setText(maNhom);
        tvRouteDetail.setText(maTuyen);
        tvOrderDateDetail.setText(ngayDat);
        tvDeliveryDateDetail.setText(ngayGiao);
        tvPayedDetail.setText(thanhtoan);
        tvTotalDetail.setText(tongTien);
    }

    @Override
    public void setList(List<ChiTietBan> chiTietBans) {
        this.chiTietBans = chiTietBans;
        adapter.update(chiTietBans);
    }

    @Override
    public void setCustomer(Customer customer) {
        this.customer = customer;
        tvCustomerDetail.setText(customer.getTenKH());
        String debt = "Hạn mức công nợ: " + StringUtil.formatCurrency(customer.getHanMucCN());
        tvDebtOrderDetail.setText(debt);
        storageRef = storageUtil.getImage(customer.getHinhAnh(), StorageUtil.TYPE_AVATAR);
        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            Glide.with(getContext()).load(uri).into(avtCustomerOrderDetail);
            Glide.with(getContext()).load(uri).into(bannerOrder);
        });
    }

    OnPassDataListener passDataListener = (position, type) -> {

    };

    @OnClick(R.id.add_product_btn)
    @Override
    public void onClick(View v) {
        AddProductFragment addProductFragment = new AddProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Key.KEY_ADD_PRODUCT, order.getMaPhieuBan());
        addProductFragment.setArguments(bundle);
        listener.onFragmentChanged(addProductFragment, Key.KEY_ADD_PRODUCT, true);
    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager()
                .popBackStack(Key.KEY_ORDER_DETAIL, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            AddOrderFragment addOrderFragment = new AddOrderFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Key.KEY_ORDER, order);
            bundle.putString(Key.KEY_CUSTOMER, customer.getTenKH());
            addOrderFragment.setArguments(bundle);
            listener.onFragmentChanged(addOrderFragment, Key.KEY_ADD_ORDER, true);
        }
        return super.onOptionsItemSelected(item);
    }
}

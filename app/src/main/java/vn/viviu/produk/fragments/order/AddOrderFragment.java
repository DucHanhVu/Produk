package vn.viviu.produk.fragments.order;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.viviu.produk.R;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.models.Order;
import vn.viviu.produk.models.SaleGroup;
import vn.viviu.produk.models.Stream;
import vn.viviu.produk.utils.Key;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOrderFragment extends BaseFragment implements AddOrderView {
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
    @BindView(R.id.spin_order_nhom)
    Spinner spinOrderNhom;
    Unbinder unbinder;

    private AddOrderPresenter addOrderPre;
    private Order order;

    private List<SaleGroup> saleGroups;
    private List<Stream> routes;
    private ArrayAdapter<String> spinAdapter;

    public AddOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        addOrderPre = new AddOrderPresenterImpl(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        addOrderPre.getData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideFab();
        showBackButton(true);
        if (getArguments() != null) {
            order = (Order) getArguments().getSerializable(Key.KEY_ORDER);
            edtOrderId.setText(order.getMaPhieuBan());
            edtOrderId.setEnabled(false);
            edtOrderCustomer.setText(order.getMaKH());
            edtNgayDat.setText(order.getNgayDat());
            edtNgayGiao.setText(order.getNgayGiao());
            edtOrderNgban.setText(order.getNguoiBan());
            edtOrderNgdat.setText(order.getNguoiDat());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.add_order);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setRouteSpin(List<Stream> routes) {
        this.routes = routes;
        List<String> listRoutes = new ArrayList<>();
        for (Stream r : routes) {
            listRoutes.add(r.getTenTuyen());
        }
        spinAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listRoutes
        );
        spinOrderTuyen.setAdapter(spinAdapter);
    }

    @Override
    public void setGroupSpin(List<SaleGroup> saleGroups) {
        this.saleGroups = saleGroups;
        List<String> listSales = new ArrayList<>();
        for (SaleGroup s : saleGroups) {
            listSales.add(s.getTenNhom());
        }
        spinAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listSales
        );
        spinOrderNhom.setAdapter(spinAdapter);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {

        }
        return super.onOptionsItemSelected(item);
    }
}

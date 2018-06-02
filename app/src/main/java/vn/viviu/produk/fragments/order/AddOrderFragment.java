package vn.viviu.produk.fragments.order;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vn.viviu.produk.R;
import vn.viviu.produk.adapters.AddProductAdapter;
import vn.viviu.produk.callbacks.OnFragmentChangedListener;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.fragments.product.ProductFragment;
import vn.viviu.produk.models.ChiTietBan;
import vn.viviu.produk.models.Customer;
import vn.viviu.produk.models.Order;
import vn.viviu.produk.models.Provider;
import vn.viviu.produk.models.SaleGroup;
import vn.viviu.produk.models.Stream;
import vn.viviu.produk.utils.Key;
import vn.viviu.produk.utils.StringUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOrderFragment extends BaseFragment implements AddOrderView {
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
    @BindView(R.id.ngdat_img_btn)
    ImageButton ngdatImgBtn;
    @BindView(R.id.nggiao_img_btn)
    ImageButton nggiaoImgBtn;
    @BindView(R.id.ngban_img_btn)
    ImageButton ngbanImgBtn;
    @BindView(R.id.customer_img_btn)
    ImageButton customerImgBtn;
    Unbinder unbinder;
    @BindView(R.id.edt_order_customer_name)
    EditText edtOrderCustomerName;
    @BindView(R.id.edt_order_payed)
    EditText edtOrderPayed;
    @BindView(R.id.rv_add_order)
    RecyclerView rvAddOrder;

    private AlertDialog.Builder mBuilder;
    private Spinner spinDialog;
    private Calendar calendar;

    /**
     * Tool Util
     */
    private OnFragmentChangedListener listener;
    private AddOrderPresenter addOrderPre;
    private Order order;
    private List<ChiTietBan> chiTietBans;
    private AddProductAdapter addProductAdapter;
    private ArrayAdapter<String> spinAdapter;

    private static final String TAG = "Add_Order";

    public AddOrderFragment() {
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
        setHasOptionsMenu(true);
        addOrderPre = new AddOrderPresenterImpl(this);
        calendar = Calendar.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        chiTietBans = new ArrayList<>(0);
        addProductAdapter = new AddProductAdapter(getContext(), chiTietBans);
        rvAddOrder.setAdapter(addProductAdapter);
        rvAddOrder.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        addOrderPre.getDataSpin();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideFab();
        showBackButton(true);
        if (getArguments() != null) {
            order = (Order) getArguments().getSerializable(Key.KEY_ORDER);
            addOrderPre.getOrderDetail(order.getMaPhieuBan());
            edtOrderCustomer.setText(order.getMaKH());
            edtNgayDat.setText(order.getNgayDat());
            edtNgayGiao.setText(order.getNgayGiao());
            edtOrderNgban.setText(order.getNguoiBan());
            edtOrderNgdat.setText(order.getNguoiDat());
            edtOrderPayed.setText(order.getThanhToanTruoc() + "");

            String customerName = getArguments().getString(Key.KEY_CUSTOMER);
            edtOrderCustomerName.setText(customerName);
        } else {
            Log.d(TAG, "Call");
            order = new Order();
            addOrderPre.getOrderCount();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            String total = "Tổng tiền: " + StringUtil.formatCurrency(order.getTongTien());
            setTitle(total);
        } else
            setTitle(R.string.add_order);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.customer_img_btn, R.id.ngban_img_btn, R.id.ngdat_img_btn, R.id.nggiao_img_btn, R.id.add_product_order_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ngdat_img_btn:
                showDatePickerDialog(edtNgayDat);
                break;
            case R.id.nggiao_img_btn:
                showDatePickerDialog(edtNgayGiao);
                break;
            case R.id.customer_img_btn:
                showSpinnerDialog(customerImgBtn);
                break;
            case R.id.ngban_img_btn:
                showSpinnerDialog(ngbanImgBtn);
                break;
            case R.id.add_product_order_btn:
                listener.onFragmentChanged(new ProductFragment(), Key.KEY_PRODUCT, true);
        }
    }

    private void showDatePickerDialog(EditText view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener dateSetListener = (v, y, m, d) -> {
            String date = d + "/" + (m + 1) + "/" + y;
            view.setText(date);
        };
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getContext(), dateSetListener, year, month, day);
        datePickerDialog.setTitle(R.string.choose_date);
        datePickerDialog.show();
    }

    private void showSpinnerDialog(View v) {
        mBuilder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
        spinDialog = view.findViewById(R.id.spin_dialog);
        if (v == customerImgBtn)
            addOrderPre.getCustomer();
        else if (v == ngbanImgBtn)
            addOrderPre.getNCC();
        mBuilder.setTitle(R.string.dialog);
        mBuilder.setView(view);
        mBuilder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.cancel());
        mBuilder.create().show();
    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager()
                .popBackStackImmediate(Key.KEY_ADD_ORDER, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void setOrderDetail(List<ChiTietBan> chiTietBans) {
        this.chiTietBans = chiTietBans;
        addProductAdapter.update(chiTietBans);
    }

    @Override
    public void setCount(long count) {
        Log.d(TAG, count + "");
        String idOrder;
        if (count < 10)
            idOrder = "PB0" + (count + 1);
        else
            idOrder = "PB" + (count + 1);
        order.setMaPhieuBan(idOrder);
    }

    @Override
    public void setRouteSpin(List<Stream> routes) {
        List<String> listRoutes = new ArrayList<>();
        listRoutes.add(getString(R.string.choose_route));
        for (Stream r : routes) {
            listRoutes.add(r.getTenTuyen());
        }
        spinAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listRoutes
        );
        spinOrderTuyen.setAdapter(spinAdapter);
        spinOrderTuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    order.setMaTuyen(routes.get(position - 1).getMaTuyen());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (order.getMaTuyen() != null)
            spinOrderTuyen.setSelection(addOrderPre.itemSelectRoute(order.getMaTuyen()));
    }

    @Override
    public void setGroupSpin(List<SaleGroup> saleGroups) {
        List<String> listSales = new ArrayList<>();
        listSales.add(getString(R.string.choose_sales));
        for (SaleGroup s : saleGroups) {
            listSales.add(s.getTenNhom());
        }
        spinAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listSales
        );
        spinOrderNhom.setAdapter(spinAdapter);
        spinOrderNhom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    order.setMaNhom(saleGroups.get(position - 1).getMaNhom());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (order.getMaNhom() != null)
            spinOrderNhom.setSelection(addOrderPre.itemSelectGroup(order.getMaNhom()));
    }

    @Override
    public void setCustomerDialog(List<Customer> customers) {
        List<String> customerString = new ArrayList<>();
        customerString.add(getString(R.string.choose_customer));
        for (Customer c : customers) {
            customerString.add(c.getTenKH());
        }

        spinAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                customerString
        );
        spinDialog.setAdapter(spinAdapter);
        spinDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String cId = customers.get(position - 1).getMaKH();
                    String ngLh = customers.get(position - 1).getNguoiLienHe();
                    String name = customers.get(position - 1).getTenKH();
                    edtOrderCustomer.setText(cId);
                    edtOrderCustomerName.setText(name);
                    edtOrderNgdat.setText(ngLh);
                } else {
                    edtOrderCustomer.setText("");
                    edtOrderNgdat.setText("");
                    edtOrderCustomerName.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setNccDialog(List<Provider> providers) {
        List<String> pString = new ArrayList<>();
        pString.add(getString(R.string.choose_ncc));
        for (Provider p : providers) {
            pString.add(p.getTenNCC());
        }

        spinAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                pString
        );
        spinDialog.setAdapter(spinAdapter);
        spinDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String cId = providers.get(position - 1).getMaNCC();
                    edtOrderNgban.setText(cId);

                } else {
                    edtOrderNgban.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(getContext(), "Success!!!", Toast.LENGTH_SHORT).show();
        onBackPressed();
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
        if (id == R.id.action_save) {
            saveData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        order.setMaKH(edtOrderCustomer.getText().toString());
        order.setNguoiDat(edtOrderNgdat.getText().toString());
        order.setNguoiBan(edtOrderNgban.getText().toString());
        order.setNgayDat(edtNgayDat.getText().toString());
        order.setNgayGiao(edtNgayGiao.getText().toString());
        order.setStatus(0);
        order.setThanhToanTruoc(0);
        order.setTongTien(0);
        //Put data
        addOrderPre.putData(order);
    }
}

package vn.viviu.produk.fragments.customer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.viviu.produk.R;
import vn.viviu.produk.callbacks.OnFragmentChangedListener;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.fragments.CameraFragment;
import vn.viviu.produk.models.Area;
import vn.viviu.produk.models.Customer;
import vn.viviu.produk.models.CustomerGroup;
import vn.viviu.produk.models.Stream;
import vn.viviu.produk.utils.Key;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCustomerFragment extends BaseFragment implements AddCustomerView {
    /**
     * View
     */
    @BindView(R.id.edt_customer_id)
    TextInputEditText edtCustomerId;
    @BindView(R.id.edt_customer_name)
    TextInputEditText edtCustomerName;
    @BindView(R.id.edt_customer_address)
    TextInputEditText edtCustomerAddress;
    @BindView(R.id.spin_customer_type)
    Spinner spinCustomerType;
    @BindView(R.id.spin_customer_area)
    Spinner spinCustomerArea;
    @BindView(R.id.spin_customer_route)
    Spinner spinCustomerRoute;
    @BindView(R.id.edt_customer_contact)
    TextInputEditText edtCustomerContact;
    @BindView(R.id.edt_customer_position)
    TextInputEditText edtCustomerPosition;
    @BindView(R.id.edt_customer_phone)
    TextInputEditText edtCustomerPhone;
    @BindView(R.id.edt_customer_email)
    TextInputEditText edtCustomerEmail;
    @BindView(R.id.edt_customer_website)
    TextInputEditText edtCustomerWebsite;
    @BindView(R.id.edt_customer_note)
    TextInputEditText edtCustomerNote;
    @BindView(R.id.edt_debt_limit)
    TextInputEditText edtDebtLimit;
    Unbinder unbinder;
    @BindView(R.id.add_avatar)
    ImageView addAvatar;

    private AddCustomerPre addCustomerPreListener;
    private OnFragmentChangedListener fragmentChangedListener;
    private Customer customer;

    /**
     * List
     */
    private List<Area> areas;
    private List<CustomerGroup> groups;
    private List<Stream> routes;
    private ArrayAdapter<String> spinAdapter;

    private String pathImg;
    private String imgName;
    private static final String TAG = "Add_Customer_Fragment";

    public AddCustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentChangedListener)
            fragmentChangedListener = (OnFragmentChangedListener) context;
        else
            throw new RuntimeException(context.toString() + "must implement OnFragmentChangedListener");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        addCustomerPreListener = new AddCustomerPreImpl(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_customer, container, false);
        unbinder = ButterKnife.bind(this, v);
        addCustomerPreListener.getData();
        Log.d(TAG, "onCreateView Call");
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
        if (getArguments() != null) {
            customer = (Customer) getArguments().getSerializable(Key.KEY_CUSTOMER);
            showData();
        } else {
            customer = new Customer();
        }
        hideFab();
        showBackButton(true);
        addAvatar.setOnClickListener(this);
        if (pathImg != null) {
            Glide.with(getContext()).load(new File(pathImg)).into(addAvatar);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume Call");
        if (getArguments() != null)
            setTitle(R.string.edit_customer);
        else
            setTitle(R.string.add_customer);
    }

    @Override
    public void setListGroup(List<CustomerGroup> groups) {
        Log.d(TAG, String.valueOf(groups.size()));
        this.groups = groups;
        List<String> listGroup = new ArrayList<>();
        for (CustomerGroup group : groups) {
            listGroup.add(group.getTenLoaiKH());
        }
        spinAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listGroup
        );
        spinCustomerType.setAdapter(spinAdapter);
        spinCustomerType.setOnItemSelectedListener(itemSelectedListener);

        if (customer.getMaLoaiKH() != null)
            spinCustomerType.setSelection(
                    addCustomerPreListener.itemSelectedType(customer.getMaLoaiKH())
            );
    }

    @Override
    public void setListArea(List<Area> areaList) {
        Log.d(TAG, String.valueOf(areaList.size()));
        this.areas = areaList;
        List<String> listArea = new ArrayList<>();
        for (Area area : areaList) {
            listArea.add(area.getTenKV());
        }
        spinAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listArea
        );
        spinCustomerArea.setAdapter(spinAdapter);
        spinCustomerArea.setOnItemSelectedListener(itemSelectedListener);
        if (customer.getMaKV() != null)
            spinCustomerArea.setSelection(
                    addCustomerPreListener.itemSelectedArea(customer.getMaKV())
            );
    }

    @Override
    public void setListRoute(List<Stream> routes) {
        Log.d(TAG, String.valueOf(routes.size()));
        this.routes = routes;
        List<String> listRoute = new ArrayList<>();
        for (Stream route : routes) {
            String st = route.getTenTuyen() + " (" + route.getMoTa() + ")";
            listRoute.add(st);
        }
        spinAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listRoute
        );
        spinCustomerRoute.setAdapter(spinAdapter);
        spinCustomerRoute.setOnItemSelectedListener(itemSelectedListener);
        if (customer.getMaTuyen() != null)
            spinCustomerRoute.setSelection(
                    addCustomerPreListener.itemSelectedRoute(customer.getMaTuyen())
            );
    }

    @Override
    public void onSuccess() {
        Toast.makeText(getContext(), "Save Complete!!!", Toast.LENGTH_SHORT).show();
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

    /**
     * show data
     */
    private void showData() {
        edtCustomerId.setText(customer.getMaKH());
        edtCustomerName.setText(customer.getTenKH());
        edtCustomerAddress.setText(customer.getDiaChi());
        edtCustomerContact.setText(customer.getNguoiLienHe());
        edtCustomerPosition.setText(customer.getChucVu());
        edtCustomerPhone.setText("0" + customer.getSDT());
        edtCustomerEmail.setText(customer.getEmail());
        edtCustomerWebsite.setText(customer.getWebsite());
        edtCustomerNote.setText(customer.getGhiChu());
        edtDebtLimit.setText(customer.getHanMucCN() + "");
    }

    /**
     * Save Customer
     */
    private void saveData() {
        //Get data
        customer.setMaKH(edtCustomerId.getText() + "");
        customer.setTenKH(edtCustomerName.getText() + "");
        customer.setDiaChi(edtCustomerAddress.getText() + "");
        customer.setNguoiLienHe(edtCustomerContact.getText() + "");
        customer.setChucVu(edtCustomerPosition.getText() + "");
        customer.setSDT(Integer.parseInt(edtCustomerPhone.getText() + ""));
        customer.setEmail(edtCustomerEmail.getText() + "");
        customer.setWebsite(edtCustomerWebsite.getText() + "");
        customer.setGhiChu(edtCustomerNote.getText() + "");
        customer.setHanMucCN(Integer.parseInt(edtDebtLimit.getText() + ""));
        customer.setHinhAnh(edtCustomerId.getText() + ".jpg");
        customer.setTrangThai(true);

        addCustomerPreListener.putData(customer);
    }

    AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (view.getId()) {
                case R.id.spin_customer_type: {
                    customer.setMaLoaiKH(groups.get(position).getMaLoaiKH());
                    break;
                }
                case R.id.spin_customer_area: {
                    customer.setMaKV(areas.get(position).getMaKV());
                    break;
                }
                case R.id.spin_customer_route: {
                    customer.setMaTuyen(routes.get(position).getMaTuyen());
                    break;
                }
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.add_avatar) {
            CameraFragment cameraFragment = new CameraFragment();
            cameraFragment.setTargetFragment(AddCustomerFragment.this, Key.ADD_CUSTOMER_CODE);
            //Set image name
            if (customer.getHinhAnh() != null) {
                imgName = customer.getHinhAnh();
            } else if (edtCustomerId.getText() != null) {
                imgName = edtCustomerId.getText() + ".jpg";
            } else {
                imgName = "pic.jpg";
            }
            Bundle bundle = new Bundle();
            bundle.putString("ImageFile", imgName);
            cameraFragment.setArguments(bundle);
            fragmentChangedListener.onFragmentChanged(cameraFragment, Key.KEY_CAMERA, true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Key.ADD_CUSTOMER_CODE) {
                pathImg = data.getStringExtra("pathImage");
                Log.d(TAG, pathImg);
            }
        }
    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.d(TAG, "onDestroyView Call");
    }
}

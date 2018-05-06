package vn.viviu.produk.fragments.customer;


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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.viviu.produk.R;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.fragments.CameraFragment;
import vn.viviu.produk.models.Area;
import vn.viviu.produk.models.CustomerGroup;
import vn.viviu.produk.models.Stream;
import vn.viviu.produk.utils.Key;

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

    private AddCustomerPre addCustomerPre;

    private ArrayAdapter<String> spinAdapter;
    private static final String TAG = "Add_Customer_Fragment";

    public AddCustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        addCustomerPre = new AddCustomerPreImpl(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_customer, container, false);
        unbinder = ButterKnife.bind(this, v);
        addCustomerPre.getData();
        Log.d(TAG, "onCreateView Call");
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
        hideFab();
        showBackButton(true);
        addAvatar.setOnClickListener(this);
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

    }

    @Override
    public void setListArea(List<Area> areaList) {
        Log.d(TAG, String.valueOf(areaList.size()));
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
    }

    @Override
    public void setListRoute(List<Stream> routes) {
        Log.d(TAG, String.valueOf(routes.size()));
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
            //Code here
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.add_avatar) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_main, new CameraFragment(), Key.KEY_CAMERA)
                    .addToBackStack(Key.KEY_CAMERA)
                    .commit();
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

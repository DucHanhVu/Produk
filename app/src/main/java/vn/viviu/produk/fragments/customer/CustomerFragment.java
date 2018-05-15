package vn.viviu.produk.fragments.customer;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.viviu.produk.R;
import vn.viviu.produk.adapters.CustomerAdapter;
import vn.viviu.produk.callbacks.OnFragmentChangedListener;
import vn.viviu.produk.callbacks.OnPassDataListener;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.models.Customer;
import vn.viviu.produk.utils.Key;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFragment extends BaseFragment implements CustomerView {
    @BindView(R.id.search_view)
    FloatingSearchView search;
    @BindView(R.id.rv_customer)
    RecyclerView rvCustomer;
    Unbinder unbinder;

    /**
     * Control, Adapter {@link CustomerAdapter}
     */
    private CustomerAdapter adapter;
    private CustomerPresenter customerPre;
    private List<Customer> customers;

    private OnFragmentChangedListener listener;

    public CustomerFragment() {
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
        View v = inflater.inflate(R.layout.fragment_customer, container, false);
        unbinder = ButterKnife.bind(this, v);
        customerPre = new CustomerPresenterImpl(this);
        customerPre.getCustomers();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showFab();
        showBackButton(false);
        fab.setOnClickListener(this);
        search.setOnQueryChangeListener((oldQuery, newQuery) -> customerPre.onQueryChanged(newQuery));
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.customer);
    }

    @Override
    public void setData(List<Customer> customers) {
        Log.d("CustomerSize", String.valueOf(customers.size()));
        this.customers = customers;
        adapter = new CustomerAdapter(getContext(), customers, dataListener);
        rvCustomer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvCustomer.setAdapter(adapter);
    }

    @Override
    public void onSuccess(String msg) {
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Event Listener
     */
    OnPassDataListener dataListener = (position, type) -> {
        switch (type) {
            case 0: {
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                BaseFragment newFragment = new AddCustomerFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Key.KEY_CUSTOMER, customers.get(position));
                newFragment.setArguments(bundle);
                listener.onFragmentChanged(newFragment, null, true);
                break;
            }
            case 4: {
                AlertDialog.Builder al = new AlertDialog.Builder(getContext());
                al.setTitle("Delete Customer")
                        .setMessage("Are you sure delete customer?")
                        .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                        .setPositiveButton(R.string.ok, (dialog, which) ->
                                customerPre.onDelete(customers.get(position)));
                al.create().show();
                break;
            }
        }

    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fab) {
            listener.onFragmentChanged(new AddCustomerFragment(), Key.KEY_ADD_CUSTOMER, true);
        }
    }

    @Override
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

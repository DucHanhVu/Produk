package vn.viviu.produk.fragments.check_in;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.viviu.produk.R;
import vn.viviu.produk.adapters.CheckInAdapter;
import vn.viviu.produk.callbacks.OnFragmentChangedListener;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.models.Customer;

public class CheckInFragment extends BaseFragment implements
        FloatingSearchView.OnQueryChangeListener, CheckInView {
    @BindView(R.id.search_view)
    FloatingSearchView searchView;
    @BindView(R.id.rv_check_in)
    RecyclerView rvCheckIn;
    Unbinder unbinder;

    private CheckInAdapter adapter;
    /**
     * Listener
     */
    private CheckInPresenter checkInPre;
    private OnFragmentChangedListener listener;

    private List<Customer> customers;

    public CheckInFragment() {
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
        checkInPre = new CheckInPresenterImpl(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_check_in, container, false);
        unbinder = ButterKnife.bind(this, v);
        checkInPre.getCustomers();
        customers = new ArrayList<>(0);
        adapter = new CheckInAdapter(getContext(), customers);
        rvCheckIn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvCheckIn.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideFab();
        showBackButton(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setData(List<Customer> customers) {
        this.customers = customers;
        adapter.update(customers);
    }

    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {

    }
}

package vn.viviu.produk.fragments.order;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vn.viviu.produk.R;
import vn.viviu.produk.adapters.OrderAdapter;
import vn.viviu.produk.callbacks.OnFragmentChangedListener;
import vn.viviu.produk.callbacks.OnPassDataListener;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.models.Order;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends BaseFragment implements OrderView,
        FloatingSearchView.OnQueryChangeListener {

    @BindView(R.id.search_view)
    FloatingSearchView searchView;
    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;
    Unbinder unbinder;

    //Adapter
    private OrderAdapter adapter;

    /**
     * Listener
     */
    private OrderPresenter orderPre;
    private OnFragmentChangedListener listener;

    private List<Order> orderList;

    public OrdersFragment() {
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
        orderPre = new OrderPresenterImpl(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        //RecyclerView set LayoutManager
        rvOrders.setLayoutManager(new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false));

        if (getArguments() != null) {

        } else {
            orderPre.getOrder();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showFab();
        showBackButton(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.title_orders);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @Override
    public void setOrders(List<Order> orderList) {
        this.orderList = orderList;
        adapter = new OrderAdapter(getContext(), orderList, passDataListener);
        rvOrders.setAdapter(adapter);
    }

    OnPassDataListener passDataListener = (position, type) -> {

    };

    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}

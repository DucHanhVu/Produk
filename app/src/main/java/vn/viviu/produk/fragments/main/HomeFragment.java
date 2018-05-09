package vn.viviu.produk.fragments.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import vn.viviu.produk.R;
import vn.viviu.produk.adapters.HomeMenuAdapter;
import vn.viviu.produk.callbacks.OnFragmentChangedListener;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.fragments.check_in.CheckInFragment;
import vn.viviu.produk.fragments.customer.CustomerFragment;
import vn.viviu.produk.fragments.order.OrdersFragment;
import vn.viviu.produk.fragments.staff.StaffFragment;
import vn.viviu.produk.fragments.statistical.StatisticFragment;
import vn.viviu.produk.fragments.work.WorkFragment;
import vn.viviu.produk.utils.Key;

public class HomeFragment extends BaseFragment {
    private OnFragmentChangedListener listener;
    private static int[] images = {
            R.drawable.ic_menu_visit,
            R.drawable.ic_menu_orders,
            R.drawable.ic_menu_customer,
            R.drawable.ic_menu_job,
            R.drawable.ic_menu_staff,
            R.drawable.ic_menu_statistical
    };

    private static int[] titles = {
            R.string.title_visit,
            R.string.title_orders,
            R.string.customer,
            R.string.work,
            R.string.staff,
            R.string.statistical
    };

    private static int[] colors = {
            R.color.colorAccent,
            R.color.light_green,
            R.color.indigo,
            R.color.teal,
            R.color.deep_orange,
            R.color.green_a400,
    };

    public HomeFragment() {
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        GridView gridMenu = v.findViewById(R.id.grid_menu);
        HomeMenuAdapter adapter = new HomeMenuAdapter(getContext(), images, titles, colors);
        gridMenu.setAdapter(adapter);
        gridMenu.setOnItemClickListener((adapterView, view, position, id) -> selectItemChanged(position));
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideFab();
        showBackButton(false);
    }

    private void selectItemChanged(int p) {
        switch (p) {
            case 0: {
                listener.onFragmentChanged(new CheckInFragment(), Key.KEY_CHECK_IN, false);
                listener.onItemChanged(R.id.nav_check_in);
                break;
            }
            case 1: {
                listener.onFragmentChanged(new OrdersFragment(), Key.KEY_ORDER, false);
                listener.onItemChanged(R.id.nav_orders);
                break;
            }
            case 2: {
                listener.onFragmentChanged(new CustomerFragment(), Key.KEY_CUSTOMER, false);
                listener.onItemChanged(R.id.nav_customer);
                break;
            }
            case 3: {
                listener.onFragmentChanged(new WorkFragment(), Key.KEY_WORK, false);
                listener.onItemChanged(R.id.nav_work);
                break;
            }
            case 4: {
                listener.onFragmentChanged(new StaffFragment(), Key.KEY_STAFF, false);
                listener.onItemChanged(R.id.nav_staff);
                break;
            }
            case 5: {
                listener.onFragmentChanged(new StatisticFragment(), Key.KEY_STATISTIC, false);
                listener.onItemChanged(R.id.nav_statistical);
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}

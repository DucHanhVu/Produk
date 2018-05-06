package vn.viviu.produk.fragments.staff;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.viviu.produk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffFragment extends Fragment {


    public StaffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_staff, container, false);
    }

}

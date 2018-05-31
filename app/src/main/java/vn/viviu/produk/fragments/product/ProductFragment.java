package vn.viviu.produk.fragments.product;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import vn.viviu.produk.R;
import vn.viviu.produk.adapters.IndustryAdapter;
import vn.viviu.produk.models.Industry;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {
    private RecyclerView listIndustry;
    private IndustryAdapter industryAdapter;
    private DatabaseReference dataRef;
    private ArrayList<Industry> industries;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);
        listIndustry = v.findViewById(R.id.list_product_manager);
        listIndustry.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        dataRef = FirebaseDatabase.getInstance().getReference("Industry");
        industries = new ArrayList<>();
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Industry industry = post.getValue(Industry.class);
                    industries.add(industry);
                }
                industryAdapter = new IndustryAdapter(getContext(), industries);
                listIndustry.setAdapter(industryAdapter);
                industryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }

}

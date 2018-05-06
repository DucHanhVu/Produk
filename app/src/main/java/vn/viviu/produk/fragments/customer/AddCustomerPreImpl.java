package vn.viviu.produk.fragments.customer;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.viviu.produk.models.Area;
import vn.viviu.produk.models.Customer;
import vn.viviu.produk.models.CustomerGroup;
import vn.viviu.produk.models.Stream;

public class AddCustomerPreImpl implements AddCustomerPre {
    private AddCustomerView addCustomerView;
    private FirebaseDatabase mDatabase;

    private List<CustomerGroup> groups;
    private List<Area> areas;
    private List<Stream> routes;

    private static final String TAG = "Add Customer";

    public AddCustomerPreImpl(AddCustomerView addCustomerView) {
        this.addCustomerView = addCustomerView;
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void getData() {
        groups = new ArrayList<>();
        areas = new ArrayList<>();
        routes = new ArrayList<>();

        mDatabase.getReference("CustomerGroup").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    groups.add(post.getValue(CustomerGroup.class));
                }

                addCustomerView.setListGroup(groups);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

        mDatabase.getReference("Area").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    areas.add(post.getValue(Area.class));
                }

                addCustomerView.setListArea(areas);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

        mDatabase.getReference("Stream").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    routes.add(post.getValue(Stream.class));
                }

                addCustomerView.setListRoute(routes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

    }

    @Override
    public void putData(Customer customer) {

    }
}

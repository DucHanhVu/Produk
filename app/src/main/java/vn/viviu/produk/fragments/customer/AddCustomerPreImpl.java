package vn.viviu.produk.fragments.customer;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private int i;
    private int n;
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
        Map<String, Object> postData = customer.toMap();
        Map<String, Object> child = new HashMap<>();
        child.put(customer.getMaKH(), postData);
        mDatabase.getReference("Customer").updateChildren(child);
        addCustomerView.onSuccess();
    }

    @Override
    public int itemSelectedType(String value) {
        n = groups.size();
        if (n > 0) {
            for (i = 0; i < n; i++)
                if (groups.get(i).getMaLoaiKH().equals(value)) {
                    break;
                }
        }
        return i;
    }

    @Override
    public int itemSelectedArea(String value) {
        n = areas.size();
        if (n > 0) {
            for (i = 0; i < n; i++)
                if (areas.get(i).getMaKV().equals(value)) {
                    break;
                }
        }
        return i;
    }

    @Override
    public int itemSelectedRoute(String value) {
        n = routes.size();
        if (n > 0) {
            for (i = 0; i < n; i++)
                if (routes.get(i).getMaTuyen().equals(value)) {
                    break;
                }
        }
        return i;
    }
}

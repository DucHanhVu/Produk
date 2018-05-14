package vn.viviu.produk.fragments.customer;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.viviu.produk.models.Customer;

public class CustomerPresenterImpl implements CustomerPresenter {
    private CustomerView customerView;
    private DatabaseReference mDatabase;
    private List<Customer> customers;

    private static final String TAG = "Customer";

    public CustomerPresenterImpl(CustomerView customerView) {
        this.customerView = customerView;
        mDatabase = FirebaseDatabase.getInstance().getReference("Customer");
    }

    @Override
    public void getCustomers() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                customers = new ArrayList<>();
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    customers.add(post.getValue(Customer.class));
                }
                customerView.setData(customers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void onQueryChanged(String query) {
        List<Customer> newCustomer = new ArrayList<>();
        for (Customer c : customers) {
            if (c.getMaKH().contains(query) || c.getTenKH().contains(query))
                newCustomer.add(c);
        }
        customerView.setData(newCustomer);
    }
}

package vn.viviu.produk.fragments.check_in;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.viviu.produk.models.Customer;

public class CheckInPresenterImpl implements CheckInPresenter {
    private CheckInView checkInView;
    private DatabaseReference mDatabase;
    private List<Customer> customers;

    private static final String TAG = "Check In";

    CheckInPresenterImpl(CheckInView checkInView) {
        this.checkInView = checkInView;
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
                checkInView.setData(customers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }
}

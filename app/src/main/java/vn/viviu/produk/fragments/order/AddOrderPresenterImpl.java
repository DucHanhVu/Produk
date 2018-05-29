package vn.viviu.produk.fragments.order;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.viviu.produk.models.Customer;
import vn.viviu.produk.models.Provider;
import vn.viviu.produk.models.SaleGroup;
import vn.viviu.produk.models.Stream;

public class AddOrderPresenterImpl implements AddOrderPresenter {
    private AddOrderView addOrderView;
    private FirebaseDatabase mDatabase;

    private List<Stream> routes;
    private List<SaleGroup> saleGroups;
    private List<Customer> customers;
    private List<Provider> providers;

    private final static String TAG = "Add_Order";

    AddOrderPresenterImpl(AddOrderView addOrderView) {
        this.addOrderView = addOrderView;
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void getData() {
        routes = new ArrayList<>();
        saleGroups = new ArrayList<>();

        mDatabase.getReference("SalesGroup").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    SaleGroup saleGroup = post.getValue(SaleGroup.class);
                    saleGroups.add(saleGroup);
                }
                addOrderView.setGroupSpin(saleGroups);
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
                addOrderView.setRouteSpin(routes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void getCustomer() {
        customers = new ArrayList<>();
        mDatabase.getReference("Customer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    customers.add(post.getValue(Customer.class));
                }
                addOrderView.setCustomerDialog(customers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void getNCC() {
        providers = new ArrayList<>();
        mDatabase.getReference("Provider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    providers.add(post.getValue(Provider.class));
                }
                addOrderView.setNccDialog(providers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }
}

package vn.viviu.produk.fragments.order;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.viviu.produk.models.ChiTietBan;
import vn.viviu.produk.models.Customer;

public class OrderDetailPresenterImpl implements OrderDetailPresenter {
    private OrderDetailView detailView;
    private FirebaseDatabase mDatabase;
    private List<ChiTietBan> chiTietBans;
    private final static String TAG = "OrderDetail";

    public OrderDetailPresenterImpl(OrderDetailView detailView) {
        this.detailView = detailView;
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void getData(String orderId, String customerid) {
        mDatabase.getReference("ChiTietBan").orderByChild("MaPhieuBan").equalTo(orderId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        chiTietBans = new ArrayList<>();
                        for (DataSnapshot post : dataSnapshot.getChildren()) {
                            ChiTietBan ctb = post.getValue(ChiTietBan.class);
                            chiTietBans.add(ctb);
                        }
                        detailView.setList(chiTietBans);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });

        mDatabase.getReference("Customer").child(customerid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Customer c = dataSnapshot.getValue(Customer.class);
                        detailView.setCustomer(c);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
    }
}

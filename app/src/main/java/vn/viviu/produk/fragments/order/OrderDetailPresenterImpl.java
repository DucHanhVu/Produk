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
import vn.viviu.produk.models.Order;
import vn.viviu.produk.models.Provider;

public class OrderDetailPresenterImpl implements OrderDetailPresenter {
    private OrderDetailView detailView;
    private FirebaseDatabase mDatabase;
    private List<ChiTietBan> chiTietBans;
    private final static String TAG = "OrderDetail";

    OrderDetailPresenterImpl(OrderDetailView detailView) {
        this.detailView = detailView;
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void getData(String orderId) {
        mDatabase.getReference("PhieuBanHang").child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);
                detailView.setOrder(order);
                getCustomer(order.getMaKH());
                getNCC(order.getNguoiBan());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

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
    }

    private void getCustomer(String customerId) {
        mDatabase.getReference("Customer").child(customerId)
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

    private void getNCC(String nccId) {
        mDatabase.getReference("Provider").child(nccId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Provider provider = dataSnapshot.getValue(Provider.class);
                        if (provider != null)
                            detailView.setNCC(provider);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
    }
}

package vn.viviu.produk.fragments.order;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.viviu.produk.models.Order;

public class OrderPresenterImpl implements OrderPresenter {
    private OrderView orderView;
    private DatabaseReference mDatabase;
    private List<Order> orderList;
    private final static String TAG = "Order";

    public OrderPresenterImpl(OrderView orderView) {
        this.orderView = orderView;
        mDatabase = FirebaseDatabase.getInstance().getReference("PhieuBanHang");
    }

    @Override
    public void getOrder() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderList = new ArrayList<>();
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Order order = post.getValue(Order.class);
                    orderList.add(order);
                }
                orderView.setOrders(orderList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void getOrderByCustomer(String customerId) {
        Query query = mDatabase.orderByChild("MaKH").equalTo(customerId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderList = new ArrayList<>();
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Order order = post.getValue(Order.class);
                    orderList.add(order);
                }
                orderView.setOrders(orderList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }
}

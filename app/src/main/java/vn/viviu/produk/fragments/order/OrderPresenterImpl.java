package vn.viviu.produk.fragments.order;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import vn.viviu.produk.models.Order;

public class OrderPresenterImpl implements OrderPresenter {
    private OrderView orderView;
    private DatabaseReference mDatabase;
    private List<Order> orderList;


    public OrderPresenterImpl(OrderView orderView) {
        this.orderView = orderView;
        mDatabase = FirebaseDatabase.getInstance().getReference("PhieuBanHang");
    }

    @Override
    public void getOrder() {

    }

    @Override
    public void getOrderByCustomer(String customerId) {

    }
}

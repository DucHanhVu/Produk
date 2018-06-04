package vn.viviu.produk.fragments.statistical;

import android.util.Log;

import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.viviu.produk.models.Order;
import vn.viviu.produk.utils.StringUtil;

public class AnalyticsPresenterImpl implements AnalyticsPresenter {
    private AnalyticsView analyticsView;
    private FirebaseDatabase mDatabase;

    private List<Order> orderList;

    private static final String TAG = "Analytics";

    public AnalyticsPresenterImpl(AnalyticsView analyticsView) {
        this.analyticsView = analyticsView;
        mDatabase = FirebaseDatabase.getInstance();
        orderList = new ArrayList<>();
    }

    @Override
    public void getData() {
        mDatabase.getReference("PhieuBanHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderList.clear();
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Order order = post.getValue(Order.class);
                    orderList.add(order);
                }
                getMoneyWithMonth();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.toException().toString());
            }
        });
    }

    @Override
    public void getOrderInMonth(int month) {
        mDatabase.getReference("PhieuBanHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderList.clear();
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Order order = post.getValue(Order.class);
                    if (StringUtil.convertDate(order.getNgayDat()) == month)
                        orderList.add(order);
                }
                analyticsView.setOrderInMonth(orderList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.toException().toString());
            }
        });
    }

    private void getMoneyWithMonth() {
        List<BarEntry> entries = new ArrayList<>();
        int i = 1;
        long m;
        while (i <= 12) {
            m = getMoneyInMonth(i);
            entries.add(new BarEntry(i, m));
            i++;
        }
        analyticsView.setChart(entries);
    }

    private long getMoneyInMonth(int month) {
        long money = 0;
        for (Order order : orderList) {
            if (StringUtil.convertDate(order.getNgayDat()) == month)
                money += order.getTongTien();
        }
        return money;
    }
}

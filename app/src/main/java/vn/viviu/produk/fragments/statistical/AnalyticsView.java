package vn.viviu.produk.fragments.statistical;

import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

import vn.viviu.produk.models.Order;

public interface AnalyticsView {
    void setChart(List<BarEntry> entries);

    void setOrderInMonth(List<Order> orderInMonth);
}

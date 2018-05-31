package vn.viviu.produk.fragments.order;

import java.util.List;

import vn.viviu.produk.models.ChiTietBan;
import vn.viviu.produk.models.Customer;
import vn.viviu.produk.models.Order;

public interface OrderDetailView {
    void setOrder(Order order);

    void setList(List<ChiTietBan> chiTietBans);

    void setCustomer(Customer customer);
}

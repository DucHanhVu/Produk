package vn.viviu.produk.fragments.order;

import java.util.List;

import vn.viviu.produk.models.ChiTietBan;
import vn.viviu.produk.models.Customer;

public interface OrderDetailView {
    void setList(List<ChiTietBan> chiTietBans);

    void setCustomer(Customer customer);
}

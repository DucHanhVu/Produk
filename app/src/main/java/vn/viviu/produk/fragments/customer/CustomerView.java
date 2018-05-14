package vn.viviu.produk.fragments.customer;

import java.util.List;

import vn.viviu.produk.models.Customer;

public interface CustomerView {
    void setData(List<Customer> customers);

    void onSuccess(String msg);

    void onFailed(String msg);
}

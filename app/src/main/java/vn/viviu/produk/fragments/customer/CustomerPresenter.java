package vn.viviu.produk.fragments.customer;

import vn.viviu.produk.models.Customer;

public interface CustomerPresenter {
    void getCustomers();

    void onQueryChanged(String query);

    void onDelete(Customer customer);
}

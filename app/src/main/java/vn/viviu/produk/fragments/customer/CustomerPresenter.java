package vn.viviu.produk.fragments.customer;

public interface CustomerPresenter {
    void getCustomers();

    void onQueryChanged(String query);
}

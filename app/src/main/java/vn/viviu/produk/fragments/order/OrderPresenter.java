package vn.viviu.produk.fragments.order;

public interface OrderPresenter {
    void getOrder();

    void getOrderByCustomer(String customerId);
}

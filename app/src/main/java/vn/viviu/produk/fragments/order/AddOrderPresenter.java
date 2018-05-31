package vn.viviu.produk.fragments.order;


import vn.viviu.produk.models.Order;

public interface AddOrderPresenter {
    void getData();

    void getCustomer();

    void getNCC();

    int itemSelectRoute(String value);

    int itemSelectGroup(int value);

    void putData(Order order);
}

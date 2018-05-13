package vn.viviu.produk.fragments.customer;

import vn.viviu.produk.models.Customer;

public interface AddCustomerPre {
    void getData();

    void putData(Customer customer);

    void uploadImage(String pathImage, String name);

    int itemSelectedType(String value);

    int itemSelectedArea(String value);

    int itemSelectedRoute(String value);
}

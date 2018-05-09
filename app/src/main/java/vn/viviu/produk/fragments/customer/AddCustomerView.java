package vn.viviu.produk.fragments.customer;

import java.util.List;

import vn.viviu.produk.models.Area;
import vn.viviu.produk.models.CustomerGroup;
import vn.viviu.produk.models.Stream;

public interface AddCustomerView {
    void setListGroup(List<CustomerGroup> groups);

    void setListArea(List<Area> areaList);

    void setListRoute(List<Stream> routes);

    void onSuccess();
}

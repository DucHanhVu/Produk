package vn.viviu.produk.fragments.order;

import java.util.List;

import vn.viviu.produk.models.Customer;
import vn.viviu.produk.models.Provider;
import vn.viviu.produk.models.SaleGroup;
import vn.viviu.produk.models.Stream;

public interface AddOrderView {
    void setRouteSpin(List<Stream> routes);

    void setGroupSpin(List<SaleGroup> saleGroups);

    void setCustomerDialog(List<Customer> customers);

    void setNccDialog(List<Provider> providers);
}

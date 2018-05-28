package vn.viviu.produk.fragments.order;

import java.util.List;

import vn.viviu.produk.models.SaleGroup;
import vn.viviu.produk.models.Stream;

public interface AddOrderView {
    void setRouteSpin(List<Stream> routes);

    void setGroupSpin(List<SaleGroup> saleGroups);
}

package vn.viviu.produk.fragments.customer;

import java.util.List;

import vn.viviu.produk.models.Area;
import vn.viviu.produk.models.CustomerGroup;
import vn.viviu.produk.models.Stream;

public interface AddCustomerView {
    void setListGroup(List<CustomerGroup> groups);

    void setListArea(List<Area> areaList);

    void setListRoute(List<Stream> routes);

    /**
     * @param code    mã request
     *                code = 0: Customer ID not null
     *                code = 1: Image not null
     * @param message error
     */
    void onError(int code, String message);

    void onProgress(int progress);

    void onSuccess();

    void onFailed(String msg);
}

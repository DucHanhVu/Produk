package vn.viviu.produk.fragments.product;

import java.util.List;

import vn.viviu.produk.models.Industry;
import vn.viviu.produk.models.Product;

public interface ProductView {
    void setProductList(List<Product> products);

    void setIndustries(List<Industry> industries);

    void setCount(long count);
}

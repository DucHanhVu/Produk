package vn.viviu.produk.fragments.product;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.viviu.produk.models.Industry;
import vn.viviu.produk.models.Product;

public class ProductPresenterImpl implements ProductPresenter {
    private ProductView productView;
    private FirebaseDatabase mDatabase;

    private List<Industry> industries;
    private List<Product> products;

    private final static String TAG = "Product Presenter";

    public ProductPresenterImpl(ProductView productView) {
        this.productView = productView;
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void getProduct(String industryId) {
        products = new ArrayList<>();
        mDatabase.getReference("Product").orderByChild("industryId").equalTo(industryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot post : dataSnapshot.getChildren()) {
                            Product product = post.getValue(Product.class);
                            products.add(product);
                        }
                        productView.setProductList(products);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, databaseError.toException().toString());
                    }
                });
    }

    @Override
    public void getIndustry() {
        industries = new ArrayList<>();
        mDatabase.getReference("Industry").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Industry industry = post.getValue(Industry.class);
                    industries.add(industry);
                }
                productView.setIndustries(industries);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.toException().toString());
            }
        });
    }

    @Override
    public void getCount() {

    }
}

package vn.viviu.produk.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.viviu.produk.R;
import vn.viviu.produk.models.Product;
import vn.viviu.produk.utils.StorageUtil;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private Context context;
    private ArrayList<Product> products;
    private StorageUtil storage;
    private StorageReference storeRef;
    private DecimalFormat formatter;

    public ProductAdapter(Context context, ArrayList<Product> industries) {
        this.context = context;
        this.products = industries;
        storage = new StorageUtil();
        formatter = new DecimalFormat("#,###,###");
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        final Product product = products.get(position);
        final ProductHolder productHol = holder;
        storeRef = storage.getImage(product.getProductId(), StorageUtil.TYPE_IMAGE);
        storeRef.getDownloadUrl().addOnSuccessListener(uri ->
                Glide.with(context).load(uri.toString()).into(productHol.imgProduct));

        productHol.tvProductName.setText(product.getProductName());
        String price = formatter.format(product.getPrice()) + " VNĐ";
        productHol.tvProductPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView tvProductName;
        private TextView tvProductPrice;

        public ProductHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
        }
    }
}

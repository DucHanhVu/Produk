package vn.viviu.produk.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import vn.viviu.produk.R;
import vn.viviu.produk.callbacks.OnCheckChangedListener;
import vn.viviu.produk.callbacks.OnPassDataListener;
import vn.viviu.produk.models.Product;
import vn.viviu.produk.utils.StorageUtil;
import vn.viviu.produk.utils.StringUtil;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private Context context;
    private List<Product> products;
    private OnCheckChangedListener checkChangedListener;
    private OnPassDataListener passDataListener;

    private StorageUtil storage;
    private StorageReference storeRef;

    public ProductAdapter(Context context, List<Product> products,
                          OnCheckChangedListener checkChangedListener,
                          OnPassDataListener passDataListener) {
        this.context = context;
        this.products = products;
        this.passDataListener = passDataListener;
        this.checkChangedListener = checkChangedListener;
        storage = new StorageUtil();
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

        storeRef = storage.getImage(product.getProductId(), StorageUtil.TYPE_IMAGE);
        storeRef.getDownloadUrl().addOnSuccessListener(uri ->
                Glide.with(context).load(uri.toString()).into(holder.imgProduct));

        holder.tvProductName.setText(product.getProductName());
        holder.tvProductName.setSelected(true);
        String price = StringUtil.formatCurrency(product.getPrice()) + " VNĐ";
        holder.tvProductPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void update(List<Product> newList) {
        products = newList;
        notifyDataSetChanged();
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView tvProductName;
        private TextView tvProductPrice;
        private CheckBox checkProduct;

        ProductHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            checkProduct = itemView.findViewById(R.id.check_product);
            itemView.setOnClickListener(v ->
                    passDataListener.onDataPassed(getAdapterPosition(), 0));
            checkProduct.setOnCheckedChangeListener((buttonView, isChecked) ->
                    checkChangedListener.onCheckChanged(getAdapterPosition(), isChecked));
        }
    }
}

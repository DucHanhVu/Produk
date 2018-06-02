package vn.viviu.produk.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.viviu.produk.R;
import vn.viviu.produk.models.ChiTietBan;
import vn.viviu.produk.models.Product;
import vn.viviu.produk.utils.StorageUtil;
import vn.viviu.produk.utils.StringUtil;

public class AddProductAdapter extends RecyclerView.Adapter<AddProductAdapter.AddProductHolder> {
    private Context context;
    private List<ChiTietBan> chiTietBans;
    private StorageUtil storage;
    private StorageReference storeRef;
    private DatabaseReference dataRef;

    public AddProductAdapter(Context context, List<ChiTietBan> chiTietBans) {
        this.context = context;
        this.chiTietBans = chiTietBans;
        storage = new StorageUtil();
        dataRef = FirebaseDatabase.getInstance().getReference("Product");
    }

    @NonNull
    @Override
    public AddProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_add_product, parent, false);
        return new AddProductHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddProductHolder holder, int position) {
        final ChiTietBan chiTietBan = chiTietBans.get(position);

        storeRef = storage.getImage(chiTietBan.getMaSP(), StorageUtil.TYPE_IMAGE);
        storeRef.getDownloadUrl().addOnSuccessListener(uri ->
                Glide.with(context).load(uri.toString()).into(holder.imgAddProduct));

        dataRef.orderByChild("productId").equalTo(chiTietBan.getMaSP())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Product product = null;
                        for (DataSnapshot post : dataSnapshot.getChildren()) {
                            product = post.getValue(Product.class);
                        }
                        if (product != null) {
                            holder.edtNameAddProduct.setText(product.getProductName());
                            holder.tvVatAddProduct.setText(product.getVat() + "%");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        if (chiTietBan.getDVT().equals("Lô"))
            holder.rbLo.setChecked(true);
        else
            holder.rbThung.setChecked(true);
        holder.tvPriceAddProduct.setText(StringUtil.formatCurrency(chiTietBan.getDonGia()));
        holder.edtDiscountAddProduct.setText(chiTietBan.getChietKhau() + "");
        holder.edtAmountsAddProduct.setText(chiTietBan.getSoLuong() + "");
    }

    @Override
    public int getItemCount() {
        return chiTietBans.size();
    }

    public void update(List<ChiTietBan> newData) {
        chiTietBans = newData;
        notifyDataSetChanged();
    }

    protected class AddProductHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_add_product)
        ImageView imgAddProduct;
        @BindView(R.id.edt_name_add_product)
        EditText edtNameAddProduct;
        @BindView(R.id.rb_thung)
        RadioButton rbThung;
        @BindView(R.id.rb_lo)
        RadioButton rbLo;
        @BindView(R.id.rg_dvt_add_product)
        RadioGroup rgDvtAddProduct;
        @BindView(R.id.tv_price_add_product)
        TextView tvPriceAddProduct;
        @BindView(R.id.tv_vat_add_product)
        TextView tvVatAddProduct;
        @BindView(R.id.edt_discount_add_product)
        EditText edtDiscountAddProduct;
        @BindView(R.id.edt_amounts_add_product)
        EditText edtAmountsAddProduct;
        @BindView(R.id.decrease_btn)
        ImageButton decreaseBtn;
        @BindView(R.id.increase_btn)
        ImageButton increaseBtn;
        @BindView(R.id.del_add_product_btn)
        ImageButton delAddProductBtn;

        AddProductHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

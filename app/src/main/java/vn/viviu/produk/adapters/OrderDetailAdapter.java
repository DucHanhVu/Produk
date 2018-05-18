package vn.viviu.produk.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import vn.viviu.produk.callbacks.OnPassDataListener;
import vn.viviu.produk.models.ChiTietBan;
import vn.viviu.produk.models.Product;
import vn.viviu.produk.utils.StorageUtil;
import vn.viviu.produk.utils.StringUtil;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailHolder> {
    private Context context;
    private List<ChiTietBan> chiTietBans;
    private OnPassDataListener listener;
    private StorageUtil storage;
    private StringUtil stringUtil;

    private DatabaseReference mDatabase;
    private StorageReference storeRef;

    public OrderDetailAdapter(Context context, List<ChiTietBan> chiTietBans, OnPassDataListener listener) {
        this.context = context;
        this.chiTietBans = chiTietBans;
        this.listener = listener;
        storage = new StorageUtil();
        mDatabase = FirebaseDatabase.getInstance().getReference("Product");
    }

    @NonNull
    @Override
    public OrderDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_order, parent, false);
        return new OrderDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailHolder holder, int position) {
        final ChiTietBan chiTietBan = chiTietBans.get(position);

        //Load Image
        storeRef = storage.getImage(chiTietBan.getMaSP(), StorageUtil.TYPE_IMAGE);
        storeRef.getDownloadUrl().addOnSuccessListener(uri ->
                Glide.with(context).load(uri.toString()).into(holder.imgProductDetail));
        //Load product name
        mDatabase.child(chiTietBan.getMaSP()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                holder.tvProductDetail.setText(product.getProductName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String staff = "Mã Kho: " + chiTietBan.getMaKho();
        String dvt = "Đơn vị tính: " + chiTietBan.getDVT();
        String amounts = "Số lượng: " + chiTietBan.getSoLuong();
        String price = "Đơn giá: " + chiTietBan.getDonGia() + " VNĐ";
        String discount = "Chiết Khấu: " + chiTietBan.getChietKhau() + "%";
        String totalMoney = "Thành tiền: " + chiTietBan.getThanhTien() + " VNĐ";

        holder.tvStaffDetail.setText(staff);
        holder.tvDvtDetail.setText(dvt);
        holder.tvAmountsDetail.setText(amounts);
        holder.tvPriceDetail.setText(price);
        holder.tvDiscountDetail.setText(discount);
        holder.tvTotalMoneyDetail.setText(totalMoney);
    }

    @Override
    public int getItemCount() {
        return chiTietBans.size();
    }

    protected class OrderDetailHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_product_detail)
        ImageView imgProductDetail;
        @BindView(R.id.tv_product_detail)
        TextView tvProductDetail;
        @BindView(R.id.tv_staff_detail)
        TextView tvStaffDetail;
        @BindView(R.id.tv_dvt_detail)
        TextView tvDvtDetail;
        @BindView(R.id.tv_amounts_detail)
        TextView tvAmountsDetail;
        @BindView(R.id.tv_price_detail)
        TextView tvPriceDetail;
        @BindView(R.id.tv_discount_detail)
        TextView tvDiscountDetail;
        @BindView(R.id.tv_total_money_detail)
        TextView tvTotalMoneyDetail;
        @BindView(R.id.del_product_order_btn)
        ImageButton delBtn;

        OrderDetailHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            delBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}

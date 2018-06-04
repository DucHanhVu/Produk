package vn.viviu.produk.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.viviu.produk.R;
import vn.viviu.produk.models.ChiTietBan;
import vn.viviu.produk.models.Product;
import vn.viviu.produk.utils.StringUtil;

public class SingleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ChiTietBan> chiTietBanList;
    private int totalMoney;

    private FirebaseDatabase mDatabase;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    public SingleItemAdapter(Context context, List<ChiTietBan> chiTietBanList, int totalMoney) {
        this.context = context;
        this.chiTietBanList = chiTietBanList;
        this.totalMoney = totalMoney;
        mDatabase = FirebaseDatabase.getInstance();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_HEADER) {
            v = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
            return new HeaderHolder(v);
        } else if (viewType == TYPE_FOOTER) {
            v = LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false);
            return new FooterHolder(v);
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.item_center, parent, false);
            return new ItemHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            final ItemHolder itemHolder = (ItemHolder) holder;
            ChiTietBan chiTietBan = chiTietBanList.get(position - 1);
            mDatabase.getReference("Product").orderByChild("productId").equalTo(chiTietBan.getMaSP())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot post : dataSnapshot.getChildren()) {
                                Product product = post.getValue(Product.class);
                                itemHolder.productName.setText(product.getProductName());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
            itemHolder.slDat.setText(StringUtil.toString(chiTietBan.getSoLuong()));
            itemHolder.totalMoney.setText(StringUtil.formatCurrency(chiTietBan.getThanhTien()));
        }else if (holder instanceof FooterHolder) {
            String tong = "Tổng tiền: " + StringUtil.formatCurrency(totalMoney);
            ((FooterHolder) holder).total.setText(tong);
        }
    }

    @Override
    public int getItemCount() {
        return chiTietBanList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else if (position == chiTietBanList.size())
            return TYPE_FOOTER;
        else
            return TYPE_ITEM;
    }

    protected class HeaderHolder extends RecyclerView.ViewHolder {

        HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    protected class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_name)
        TextView productName;
        @BindView(R.id.sl_dat)
        TextView slDat;
        @BindView(R.id.total_money)
        TextView totalMoney;

        ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    protected class FooterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.total_footer)
        TextView total;

        FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

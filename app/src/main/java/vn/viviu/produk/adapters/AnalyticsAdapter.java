package vn.viviu.produk.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.viviu.produk.R;
import vn.viviu.produk.models.ChiTietBan;
import vn.viviu.produk.models.Order;

public class AnalyticsAdapter extends RecyclerView.Adapter<AnalyticsAdapter.AnalyticsHolder> {
    private Context context;
    private List<Order> orderList;
    private Order order;
    private List<ChiTietBan> chiTietBanList;

    private FirebaseDatabase mDatabase;
    private SingleItemAdapter singleItemAdapter;

    public AnalyticsAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
        mDatabase = FirebaseDatabase.getInstance();
    }

    @NonNull
    @Override
    public AnalyticsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_analytics, parent, false);
        return new AnalyticsHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull AnalyticsHolder holder, int position) {
        order = orderList.get(position);
        holder.tvTitleTime.setText(order.getNgayDat());
        String maDonHang = "Mã đơn hàng: " + order.getMaPhieuBan();
        holder.tvOrderId.setText(maDonHang);

        holder.rvOrderDetail.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        mDatabase.getReference("ChiTietBan").orderByChild("MaPhieuBan")
                .equalTo(order.getMaPhieuBan())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        chiTietBanList = new ArrayList<>();
                        for (DataSnapshot post : dataSnapshot.getChildren()) {
                            ChiTietBan chiTietBan = post.getValue(ChiTietBan.class);
                            chiTietBanList.add(chiTietBan);
                        }
                        Log.d("AnalyticsAdapter", chiTietBanList.size() + "");
                        singleItemAdapter = new SingleItemAdapter(context, chiTietBanList, order.getTongTien());
                        holder.rvOrderDetail.setAdapter(singleItemAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void update(List<Order> newList) {
        orderList = newList;
        notifyDataSetChanged();
    }

    protected class AnalyticsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title_time)
        TextView tvTitleTime;
        @BindView(R.id.time_line_view)
        TimelineView timeLineView;
        @BindView(R.id.tv_order_id)
        TextView tvOrderId;
        @BindView(R.id.rv_order_detail)
        RecyclerView rvOrderDetail;

        public AnalyticsHolder(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            timeLineView.initLine(viewType);
        }
    }
}

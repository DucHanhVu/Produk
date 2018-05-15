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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.viviu.produk.R;
import vn.viviu.produk.callbacks.OnPassDataListener;
import vn.viviu.produk.models.Customer;
import vn.viviu.produk.models.Order;
import vn.viviu.produk.utils.StringUtil;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
    private Context context;
    private List<Order> orderList;
    private DatabaseReference mDatabase;
    private OnPassDataListener listener;

    public OrderAdapter(Context context, List<Order> orderList, OnPassDataListener passDataListener) {
        this.context = context;
        this.orderList = orderList;
        this.listener = passDataListener;
        mDatabase = FirebaseDatabase.getInstance().getReference("Customer");
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        final Order order = orderList.get(position);
        mDatabase.child(order.getMaKH()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                holder.tvOrderCustomer.setText(customer.getTenKH());
                holder.tvOrderAddress.setText(customer.getDiaChi());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.tvOrderTime.setText(order.getNgayGiao());
        holder.tvOrderTotal.setText(StringUtil.formatCurrency(order.getTongTien()));

        String text;
        int res;
        int color;

        switch (order.getStatus()) {
            case 0:
                text = "Chờ Duyệt";
                res = R.drawable.bkg_orange;
                color = R.color.deep_orange;
                break;
            case 1:
                text = "Đã Xác Nhận";
                res = R.drawable.bkg_green;
                color = R.color.green_a400;
                break;
            case 2:
                text = "Đã Bán Hàng";
                res = R.drawable.bkg_blue;
                color = R.color.indigo;
                break;
            default:
                text = null;
                res = 0;
                color = 0;
                break;

        }
        if (text != null && res != 0 && color != 0) {
            holder.tvOrderStatus.setText(text);
            holder.tvOrderStatus.setTextColor(context.getResources().getColor(color));
            holder.tvOrderStatus.setBackgroundResource(res);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_order_customer)
        TextView tvOrderCustomer;
        @BindView(R.id.tv_order_address)
        TextView tvOrderAddress;
        @BindView(R.id.tv_order_time)
        TextView tvOrderTime;
        @BindView(R.id.tv_order_total)
        TextView tvOrderTotal;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;

        OrderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onDataPassed(getAdapterPosition(), 0);
        }
    }
}

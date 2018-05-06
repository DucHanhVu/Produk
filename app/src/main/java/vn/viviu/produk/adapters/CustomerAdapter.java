package vn.viviu.produk.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.viviu.produk.R;
import vn.viviu.produk.callbacks.OnPassDataListener;
import vn.viviu.produk.models.Customer;
import vn.viviu.produk.utils.StorageUtil;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerHolder> {
    private Context context;
    private List<Customer> customers;
    private StorageUtil storageUtil;
    private StorageReference storageRef;
    private OnPassDataListener listener;

    public CustomerAdapter(Context context, List<Customer> customers, OnPassDataListener listener) {
        this.context = context;
        this.customers = customers;
        storageUtil = new StorageUtil();
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false);
        return new CustomerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerHolder holder, int position) {
        final Customer customer = customers.get(position);
        storageRef = storageUtil.getImage(customer.getHinhAnh(), StorageUtil.TYPE_AVATAR);
        storageRef.getDownloadUrl().addOnSuccessListener(
                uri -> Glide.with(context).load(uri).into(holder.avatar)
        );

        holder.customerName.setText(customer.getTenKH());
        holder.tvContact.setText(customer.getNguoiLienHe());
        holder.tvAddress.setText(customer.getDiaChi());
        //convert phone
        String sdt = "0" + customer.getSDT();
        holder.tvPhone.setText(sdt);

    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public class CustomerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView avatar;
        private TextView customerName;
        private TextView tvContact;
        private TextView tvDistance;
        private TextView tvAddress;
        private TextView tvPhone;
        private Button btnOrder;
        private Button btnReport;
        private Button btnCheckIn;
        private ImageButton btnMore;

        public CustomerHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar_customer);
            customerName = itemView.findViewById(R.id.title_customer_name);
            tvContact = itemView.findViewById(R.id.tv_contact);
            tvDistance = itemView.findViewById(R.id.tv_distance);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            btnOrder = itemView.findViewById(R.id.order_customer_btn);
            btnReport = itemView.findViewById(R.id.report_customer_btn);
            btnCheckIn = itemView.findViewById(R.id.checkin_customer_btn);
            btnMore = itemView.findViewById(R.id.more_customer_btn);

            btnOrder.setOnClickListener(CustomerHolder.this);
            btnCheckIn.setOnClickListener(CustomerHolder.this);
            btnReport.setOnClickListener(CustomerHolder.this);
            btnMore.setOnClickListener(CustomerHolder.this);
        }

        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            if (viewId == R.id.more_customer_btn)
                showPopupMenu();
            else if (viewId == R.id.order_customer_btn)
                listener.onDataPassed(getAdapterPosition(), 0);
            else if (viewId == R.id.report_customer_btn)
                listener.onDataPassed(getAdapterPosition(), 1);
            else if (viewId == R.id.checkin_customer_btn)
                listener.onDataPassed(getAdapterPosition(), 2);
        }

        private void showPopupMenu() {

        }
    }
}

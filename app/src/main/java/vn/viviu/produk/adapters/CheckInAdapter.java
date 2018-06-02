package vn.viviu.produk.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.viviu.produk.R;
import vn.viviu.produk.models.Customer;
import vn.viviu.produk.utils.StorageUtil;

public class CheckInAdapter extends RecyclerView.Adapter<CheckInAdapter.CheckInHolder> {
    private Context context;
    private List<Customer> customers;
    private StorageUtil storageUtil;
    private StorageReference storageRef;

    public CheckInAdapter(Context context, List<Customer> customers) {
        this.context = context;
        this.customers = customers;
        storageUtil = new StorageUtil();
    }

    @NonNull
    @Override
    public CheckInHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_check_in, parent, false);
        return new CheckInHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckInHolder holder, int position) {
        final Customer customer = customers.get(position);
        storageRef = storageUtil.getImage(customer.getHinhAnh(), StorageUtil.TYPE_AVATAR);
        storageRef.getDownloadUrl().addOnSuccessListener(
                uri -> Glide.with(context).load(uri).into(holder.avtCustomerCheck)
        );

        holder.tvCustomerNameCheck.setText(customer.getTenKH());
        holder.tvContactCheck.setText(customer.getNguoiLienHe());
        holder.tvAddressCheck.setText(customer.getDiaChi());
        //convert phone
        String sdt = "0" + customer.getSDT();
        holder.tvPhoneCheck.setText(sdt);
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public void update(List<Customer> newList) {
        customers = newList;
        notifyDataSetChanged();
    }

    class CheckInHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avt_customer_check)
        CircleImageView avtCustomerCheck;
        @BindView(R.id.tv_customer_name_check)
        TextView tvCustomerNameCheck;
        @BindView(R.id.tv_contact_check)
        TextView tvContactCheck;
        @BindView(R.id.tv_address_check)
        TextView tvAddressCheck;
        @BindView(R.id.tv_phone_check)
        TextView tvPhoneCheck;
        @BindView(R.id.start_check_in_btn)
        Button startCheckInBtn;
        @BindView(R.id.get_direction_btn)
        Button getDirectionBtn;

        CheckInHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

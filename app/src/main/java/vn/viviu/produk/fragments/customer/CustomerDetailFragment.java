package vn.viviu.produk.fragments.customer;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.viviu.produk.R;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.models.Customer;
import vn.viviu.produk.utils.Key;
import vn.viviu.produk.utils.StorageUtil;
import vn.viviu.produk.utils.StringUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerDetailFragment extends BaseFragment {
    @BindView(R.id.banner_customer_detail)
    ImageView bannerCustomerDetail;
    @BindView(R.id.avatar_customer_detail)
    CircleImageView avatarCustomerDetail;

    @BindView(R.id.tv_name_customer_detail)
    TextView tvNameCustomerDetail;
    @BindView(R.id.tv_nglh_customer_detail)
    TextView tvNglhCustomerDetail;
    @BindView(R.id.tv_address_customer_detail)
    TextView tvAddressCustomerDetail;
    @BindView(R.id.tv_phone_customer_detail)
    TextView tvPhoneCustomerDetail;
    @BindView(R.id.tv_email_customer_detail)
    TextView tvEmailCustomerDetail;
    @BindView(R.id.tv_note_customer_detail)
    TextView tvNoteCustomerDetail;
    Unbinder unbinder;
    @BindView(R.id.tv_debt_customer_detail)
    TextView tvDebtCustomerDetail;

    private StorageUtil storageUtil;
    private StorageReference storageRef;

    private static final String TAG = "Customer_detail";

    public CustomerDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageUtil = new StorageUtil();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showBackButton(true);
        hideFab();
        if (getArguments() != null) {
            Customer customer = (Customer) getArguments().getSerializable(Key.KEY_CUSTOMER_DETAIL);
            //Load Image
            storageRef = storageUtil.getImage(customer.getHinhAnh(), StorageUtil.TYPE_AVATAR);
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                Glide.with(getContext()).load(uri).into(bannerCustomerDetail);
                Glide.with(getContext()).load(uri).into(avatarCustomerDetail);
            }).addOnFailureListener(e -> Log.e(TAG, e.getMessage()));

            String text;
            text = customer.getTenKH();
            setTitle(text);
            tvNameCustomerDetail.setText(text);
            text = customer.getNguoiLienHe();
            tvNglhCustomerDetail.setText(text);
            text = "Địa chỉ: " + customer.getDiaChi();
            tvAddressCustomerDetail.setText(text);
            text = "Điện thoại: +84" + customer.getSDT();
            tvPhoneCustomerDetail.setText(text);
            text = "Email: " + customer.getEmail();
            tvEmailCustomerDetail.setText(text);
            text = "Ghi chú: " + customer.getGhiChu();
            tvNoteCustomerDetail.setText(text);
            text = "Hạn mức công nợ: " + StringUtil.formatCurrency(customer.getHanMucCN());
            tvDebtCustomerDetail.setText(text);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack(Key.KEY_CUSTOMER_DETAIL,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

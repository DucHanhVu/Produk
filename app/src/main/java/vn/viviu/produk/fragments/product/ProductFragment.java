package vn.viviu.produk.fragments.product;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.viviu.produk.R;
import vn.viviu.produk.adapters.ProductAdapter;
import vn.viviu.produk.callbacks.OnCheckChangedListener;
import vn.viviu.produk.callbacks.OnPassDataListener;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.models.ChiTietBan;
import vn.viviu.produk.models.Industry;
import vn.viviu.produk.models.Product;
import vn.viviu.produk.utils.Key;
import vn.viviu.produk.utils.StorageUtil;
import vn.viviu.produk.utils.StringUtil;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class ProductFragment extends BaseFragment implements ProductView {
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    Unbinder unbinder;
    private Spinner spinner;

    /**
     * Adapter for rvProduct {@link ProductAdapter}.
     */
    private ProductAdapter adapter;

    /**
     * Presenter {@link ProductPresenter}.
     */
    private ProductPresenter productPre;

    /**
     * Dialog {@link AlertDialog.Builder}.
     */
    private AlertDialog.Builder builder;

    private List<Industry> industries;
    private List<Product> products;
    private List<ChiTietBan> chiTietBanList;

    private ChiTietBan chiTietBan;

    private StorageUtil storage;
    private StorageReference storeRef;

    /**
     * Listener {@link OnCheckChangedListener}.
     */
    private OnCheckChangedListener checkChangedListener = (position, checked) -> {
        Product product = products.get(position);
        if (checked) {
            chiTietBan = new ChiTietBan();

        } else {

        }
    };

    /**
     * Listener {@link OnPassDataListener}.
     */
    private OnPassDataListener passDataListener = (position, type) -> showProductDetail(position);

    private int p;  //Position of industry
    private long count = 0;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        productPre = new ProductPresenterImpl(this);
        storage = new StorageUtil();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);
        unbinder = ButterKnife.bind(this, v);
        products = new ArrayList<>(0);
        adapter = new ProductAdapter(getContext(), products, checkChangedListener, passDataListener);
        rvProduct.setAdapter(adapter);
        rvProduct.setLayoutManager(new GridLayoutManager(getContext(), 2,
                LinearLayoutManager.VERTICAL, false));
        showDialog();
        productPre.getCount();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showBackButton(true);
        hideFab();
        chiTietBanList = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.product);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager()
                .popBackStackImmediate(Key.KEY_PRODUCT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setProductList(List<Product> products) {
        this.products = products;
        adapter.update(products);
    }

    @Override
    public void setIndustries(List<Industry> industries) {
        this.industries = industries;
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.choose_industry));
        for (Industry industry : industries) {
            String titleSpin = industry.getIndustryId() + " - " + industry.getIndustryName();
            list.add(titleSpin);
        }

        ArrayAdapter<String> arr = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                list
        );
        spinner.setAdapter(arr);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                p = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_filter, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {
            showDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("InflateParams")
    private void showDialog() {
        builder = new AlertDialog.Builder(getContext());
        View v = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
        spinner = v.findViewById(R.id.spin_dialog);
        builder.setTitle(R.string.title_filter);
        builder.setView(v);
        productPre.getIndustry();
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel())
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    String id = industries.get(p - 1).getIndustryId();
                    productPre.getProduct(id);
                    dialog.dismiss();
                });
        builder.create().show();
    }

    @SuppressLint("InflateParams")
    private void showProductDetail(int position) {
        final Product productDetail = products.get(position);
        builder = new AlertDialog.Builder(getContext());
        View v2 = getLayoutInflater().inflate(R.layout.dialog_product_detail, null);
        ImageView imgProduct = v2.findViewById(R.id.img_product_dialog);
        TextView titleProduct = v2.findViewById(R.id.title_product_dialog);
        TextView priceProduct = v2.findViewById(R.id.price_product_dialog);
        TextView vatProduct = v2.findViewById(R.id.vat_product_dialog);
        TextView description = v2.findViewById(R.id.description_product);

        storeRef = storage.getImage(productDetail.getProductId(), StorageUtil.TYPE_IMAGE);
        storeRef.getDownloadUrl().addOnSuccessListener(uri ->
                Glide.with(getContext()).load(uri.toString()).into(imgProduct));
        titleProduct.setText(productDetail.getProductName());
        priceProduct.setText(StringUtil.formatCurrency(productDetail.getPrice()));
        String vat = "VAT: " + productDetail.getVat() + "%";
        vatProduct.setText(vat);
        String des = "Description: \n" + productDetail.getDescription();
        description.setText(des);
        builder.setView(v2);
        builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}

package vn.viviu.produk.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import vn.viviu.produk.R;
import vn.viviu.produk.models.Industry;
import vn.viviu.produk.models.Product;

public class IndustryAdapter extends RecyclerView.Adapter<IndustryAdapter.IndustryHolder> {
    private Context context;
    private ArrayList<Industry> industries;
    private DatabaseReference dataRef;
    private ProductAdapter productAdapter;
    private ArrayList<Product> products;

    public IndustryAdapter(Context context, ArrayList<Industry> industries) {
        this.context = context;
        this.industries = industries;
        dataRef = FirebaseDatabase.getInstance().getReference("Product");
    }

    @NonNull
    @Override
    public IndustryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_industry, parent, false);
        return new IndustryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IndustryHolder holder, int position) {
        final Industry industry = industries.get(position);

        holder.industryTitle.setText(industry.getIndustryName());
        holder.listProduct.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        products = new ArrayList<>();
        Query query = dataRef.orderByChild("industryId").equalTo(industry.getIndustryId());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Product product = postSnapShot.getValue(Product.class);
                    products.add(product);
                }
                productAdapter = new ProductAdapter(context, products);
                holder.listProduct.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return industries.size();
    }

    public class IndustryHolder extends RecyclerView.ViewHolder {
        private TextView industryTitle;
        private Button btnMore;
        private RecyclerView listProduct;

        public IndustryHolder(View itemView) {
            super(itemView);
            industryTitle = itemView.findViewById(R.id.industry_title);
            btnMore = itemView.findViewById(R.id.more_btn);
            listProduct = itemView.findViewById(R.id.list_product);
        }
    }
}

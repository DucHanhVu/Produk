package vn.viviu.produk.fragments.order;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.viviu.produk.models.SaleGroup;
import vn.viviu.produk.models.Stream;

public class AddOrderPresenterImpl implements AddOrderPresenter {
    private AddOrderView addOrderView;
    private FirebaseDatabase mDatabase;

    private List<Stream> routes;
    private List<SaleGroup> saleGroups;

    private final static String TAG = "Add_Order";

    public AddOrderPresenterImpl(AddOrderView addOrderView) {
        this.addOrderView = addOrderView;
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void getData() {
        routes = new ArrayList<>();
        saleGroups = new ArrayList<>();

        mDatabase.getReference("SalesGroup").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    SaleGroup saleGroup = post.getValue(SaleGroup.class);
                    saleGroups.add(saleGroup);
                }
                addOrderView.setGroupSpin(saleGroups);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

        mDatabase.getReference("Stream").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    routes.add(post.getValue(Stream.class));
                }
                addOrderView.setRouteSpin(routes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }
}

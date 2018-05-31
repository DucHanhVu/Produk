package vn.viviu.produk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import vn.viviu.produk.R;
import vn.viviu.produk.activities.login.LoginActivity;
import vn.viviu.produk.callbacks.OnBackPressListener;
import vn.viviu.produk.callbacks.OnFragmentChangedListener;
import vn.viviu.produk.fragments.HomeFragment;
import vn.viviu.produk.fragments.check_in.CheckInFragment;
import vn.viviu.produk.fragments.customer.CustomerFragment;
import vn.viviu.produk.fragments.order.OrdersFragment;
import vn.viviu.produk.fragments.statistical.StatisticFragment;
import vn.viviu.produk.fragments.work.WorkFragment;
import vn.viviu.produk.models.User;
import vn.viviu.produk.utils.Key;
import vn.viviu.produk.utils.StorageUtil;
import vn.viviu.produk.utils.StringUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentChangedListener {
    /**
     * View
     */
    private NavigationView nav;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private View headerView;
    private TextView tvUser, tvEmail;
    private ImageView avtUser;

    /**
     * Fragment Manager
     */
    private FragmentManager fm;
    private FragmentTransaction transaction;

    /**
     * Fire base
     */
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference dataRef;
    private StorageUtil storage = new StorageUtil();

    /**
     * var static
     */
    private static final String TAG = "MainActivity";

    static {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Mapping
        nav = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        headerView = nav.getHeaderView(0);
        tvUser = headerView.findViewById(R.id.title_username);
        tvEmail = headerView.findViewById(R.id.title_email_user);
        avtUser = headerView.findViewById(R.id.avt_user);

        //Navigation Drawer
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(this);

        //Fire base
        mAuth = FirebaseAuth.getInstance();
        dataRef = FirebaseDatabase.getInstance().getReference("User");

        //Fragment Manager
        if (savedInstanceState == null) {
            fm = getSupportFragmentManager();
            transaction = fm.beginTransaction();
            transaction.replace(R.id.container_main, new OrdersFragment()).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            setConfig();
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    /**
     * Get Toggle Navigation Drawer
     * Using in Base Fragment
     */
    public ActionBarDrawerToggle getToggle() {
        return toggle;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = fm.findFragmentById(R.id.container_main);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragment instanceof OnBackPressListener) {
            ((OnBackPressListener) fragment).onBackPressed();
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (fm.getBackStackEntryCount() > 0)
            fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        switch (id) {
            case R.id.nav_main:
                onFragmentChanged(new HomeFragment(), Key.KEY_HOME, false);
                break;
            case R.id.nav_notifications:
                break;
            case R.id.nav_check_in:
                onFragmentChanged(new CheckInFragment(), Key.KEY_CHECK_IN, false);
                break;
            case R.id.nav_orders:
                onFragmentChanged(new OrdersFragment(), Key.KEY_ORDER, false);
                break;
            case R.id.nav_customer:
                onFragmentChanged(new CustomerFragment(), Key.KEY_CUSTOMER, false);
                break;
            case R.id.nav_work:
                onFragmentChanged(new WorkFragment(), Key.KEY_WORK, false);
                break;
            case R.id.nav_statistical:
                onFragmentChanged(new StatisticFragment(), Key.KEY_STATISTIC, false);
                break;
            case R.id.nav_logout: {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentChanged(Fragment fragment, String tag, boolean backStack) {
        transaction = fm.beginTransaction();
        transaction.replace(R.id.container_main, fragment, tag);
        if (backStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    @Override
    public void onItemChanged(int index) {
        nav.setCheckedItem(index);
    }

    /**
     * Config
     */
    private void setConfig() {
        String email = mUser.getEmail();
        //Show email of user
        tvEmail.setText(email);
        assert email != null;
        final String subEmail = StringUtil.splitEmail(email);
        Query query = dataRef.orderByChild(subEmail);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            User user = null;
            for (DataSnapshot post : dataSnapshot.getChildren()) {
                user = post.getValue(User.class);
                Log.d(TAG, post.getKey());
            }
            assert user != null;
            tvUser.setText(user.getName());
            loadImage(user.getAvatar());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "onCancelled", databaseError.toException());
        }
    };

    private void loadImage(String image) {
        Log.d(TAG, image);
        StorageReference ref = storage.getImage(image, StorageUtil.TYPE_AVATAR);
        ref.getDownloadUrl().addOnSuccessListener(uri ->
                Glide.with(MainActivity.this).load(uri.toString()).into(avtUser));
    }
}

package vn.viviu.produk.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import vn.viviu.produk.R;
import vn.viviu.produk.activities.MainActivity;
import vn.viviu.produk.callbacks.OnBackPressListener;

public abstract class BaseFragment extends Fragment implements View.OnClickListener,
        OnBackPressListener {
    protected FloatingActionButton fab;
    protected Toolbar toolbar;
    protected ActionBar actionBar;
    protected ActionBarDrawerToggle toggle;
    protected DrawerLayout drawer;
    protected boolean mToolBarNavigationListenerIsRegistered = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = ((MainActivity) getActivity()).findViewById(R.id.fab);
        toolbar = ((MainActivity) getActivity()).findViewById(R.id.toolbar);
        actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        drawer = ((MainActivity) getActivity()).findViewById(R.id.drawer_layout);
        toggle = ((MainActivity) getActivity()).getToggle();
    }

    // hide FAB button
    protected void hideFab() {
        fab.hide();
    }

    //show FAB button
    protected void showFab() {
        fab.show();
    }


    /**
     * Shows Home button as Back button
     * Took from here {@link}https://stackoverflow.com/a/36677279/9381524
     * <p>
     * To keep states of ActionBar and ActionBarDrawerToggle synchronized,
     * when you enable on one, you disable on the other.
     * And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT!!!
     *
     * @param show = true to show <showHomeAsUp> or show = false to show <Hamburger> button
     */
    protected void showBackButton(boolean show) {
        if (show) {
            // Remove hamburger
            toggle.setDrawerIndicatorEnabled(false);
            // Show back button
            actionBar.setDisplayHomeAsUpEnabled(true);
            // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
            // clicks are disabled i.e. the UP button will not work.
            // We need to add a listener, as in below, so DrawerToggle will forward
            // click events to this listener.
            if (!mToolBarNavigationListenerIsRegistered) {
                toggle.setToolbarNavigationClickListener(v -> onBackPressed());
                mToolBarNavigationListenerIsRegistered = true;
            }
        } else {
            // Remove back button
            actionBar.setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            toggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            toggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }
        // So, one may think "Hmm why not simplify to:
        // .....
        // getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        // mDrawer.setDrawerIndicatorEnabled(!enable);
        // ......
        // To re-iterate, the order in which you enable and disable views IS important #dontSimplify.
    }

    /**
     * Simplify setTitle in child fragments
     */
    protected void setTitle(int resId) {
        getActivity().setTitle(getResources().getString(resId));
    }

    protected void setTitle(CharSequence title) {
        getActivity().setTitle(title);
    }

    @Override
    public abstract void onClick(View v);

    @Override
    public abstract void onBackPressed();
}

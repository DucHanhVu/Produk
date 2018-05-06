package vn.viviu.produk.activities.splash;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import me.relex.circleindicator.CircleIndicator;
import vn.viviu.produk.R;
import vn.viviu.produk.managers.PrefManager;

public class WelcomeFrag extends Fragment {
    private ViewPager pager;
    private CircleIndicator indicator;
    private IntroPagerAdapter adapter;

    private PrefManager prefManager;
    private OnFinishedWelcomeListener listener;
    private int[] mLayout = {R.layout.slide_1, R.layout.slide_2, R.layout.slide_3};

    public WelcomeFrag() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFinishedWelcomeListener)
            listener = (OnFinishedWelcomeListener) context;
        else
            throw new RuntimeException(context.toString() + "must implement OnFinishedWelcomeListener");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        pager = v.findViewById(R.id.pager_welcome);
        indicator = v.findViewById(R.id.view_dot);

        adapter = new IntroPagerAdapter();
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        return v;
    }

    private class IntroPagerAdapter extends PagerAdapter {
        private LayoutInflater inflater;

        public IntroPagerAdapter() {
            super();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            View v = inflater.inflate(mLayout[position], container, false);
            if (position == 2) {
                Button btnStart = v.findViewById(R.id.start_now_btn);
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prefManager = new PrefManager(getContext());
                        prefManager.setFirstTimeLaunch();
                        listener.onFinished();
                    }
                });
            }
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return mLayout.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}

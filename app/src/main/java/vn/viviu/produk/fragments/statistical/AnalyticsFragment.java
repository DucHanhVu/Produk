package vn.viviu.produk.fragments.statistical;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.viviu.produk.R;
import vn.viviu.produk.adapters.AnalyticsAdapter;
import vn.viviu.produk.fragments.BaseFragment;
import vn.viviu.produk.models.Order;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnalyticsFragment extends BaseFragment implements AnalyticsView {
    @BindView(R.id.bar_chart)
    BarChart barChart;
    @BindView(R.id.spin_month)
    Spinner spinMonth;
    @BindView(R.id.rv_time_line)
    RecyclerView rvTimeLine;
    Unbinder unbinder;

    private AnalyticsPresenter analyticsPre;
    private AnalyticsAdapter analyticsAdapter;
    private List<Order> orderList;

    private int[] colors = {
            R.color.colorAccent,
            R.color.light_green,
            R.color.indigo,
            R.color.teal,
            R.color.green_a400,
    };

    private String[] months = {
            "Choose a month...",
            "Tháng 1",
            "Tháng 2",
            "Tháng 3",
            "Tháng 4",
            "Tháng 5",
            "Tháng 6",
            "Tháng 7",
            "Tháng 8",
            "Tháng 9",
            "Tháng 10",
            "Tháng 11",
            "Tháng 12",
    };

    public AnalyticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analyticsPre = new AnalyticsPresenterImpl(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);
        unbinder = ButterKnife.bind(this, view);
        orderList = new ArrayList<>(0);
        analyticsAdapter = new AnalyticsAdapter(getContext(), orderList);
        rvTimeLine.setAdapter(analyticsAdapter);
        rvTimeLine.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        barChart.setScaleEnabled(true);

        ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                months
        );
        spinMonth.setAdapter(spinAdapter);
        spinMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                analyticsPre.getOrderInMonth(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideFab();
        showBackButton(false);
        analyticsPre.getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.title_analytics);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setChart(List<BarEntry> entries) {
        BarDataSet dataSet = new BarDataSet(entries, "label");
        dataSet.setColors(colors, getContext());
        dataSet.setValueFormatter(new LargeValueFormatter());
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(1f);
        barData.setValueFormatter(new LargeValueFormatter());
        barChart.setData(barData);
        barChart.invalidate();
    }

    @Override
    public void setOrderInMonth(List<Order> orderInMonth) {
        analyticsAdapter.update(orderInMonth);
    }
}

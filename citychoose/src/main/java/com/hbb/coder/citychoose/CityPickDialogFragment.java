package com.hbb.coder.citychoose;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbb.coder.citychoose.adapter.CityListAdapter;
import com.hbb.coder.citychoose.bean.City;
import com.hbb.coder.citychoose.bean.HotCity;
import com.hbb.coder.citychoose.bean.LocateState;
import com.hbb.coder.citychoose.bean.LocatedCity;
import com.hbb.coder.citychoose.customerview.SideIndexBar;
import com.hbb.coder.citychoose.database.CityBean;
import com.hbb.coder.citychoose.database.CityLabel;
import com.hbb.coder.citychoose.decoration.DividerItemDecoration;
import com.hbb.coder.citychoose.decoration.SectionItemDecoration;
import com.hbb.coder.citychoose.listener.InnerListener;
import com.hbb.coder.citychoose.listener.OnPickListener;
import com.hbb.coder.citychoose.utils.SharePerferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class CityPickDialogFragment extends AppCompatDialogFragment implements InnerListener,
        TextWatcher, SideIndexBar.OnIndexTouchedChangedListener, View.OnClickListener {

    public static final String CITY_REQUEST_ACTION = "location";
    public static final String MAIN_REQUEST_ACTION = "main_request_action";
    private boolean enableAnim;
    private CityLabel mCityLable;
    private List<City> mAllCities;
    private List<HotCity> mHotCities;
    private List<City> mResults;
    private LocatedCity mLocatedCity;
    private int locateState;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private View mContentView;
    private CityListAdapter mAdapter;
    private LinearLayout mEmptyView;
    private TextView mOverlayTextView;
    private SideIndexBar mIndexBar;
    private EditText mSearchBox;
    private TextView mCancelBtn;
    private ImageView mClearAllBtn;
    private int mAnimStyle;
    private OnPickListener mOnPickListener;
    private BroadcastReceiver mLocationBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == CITY_REQUEST_ACTION) {

                String city = intent.getStringExtra(Intent.EXTRA_TEXT);
                SharePerferenceUtils.setString(getActivity(), SharePerferenceUtils.locateCity, city);
                CityPicker.getInstance().locateComplete(new LocatedCity(city, "", ""), LocateState.SUCCESS);

            }
        }
    };

    /**
     * 获取实例
     *
     * @param enable 是否启用动画效果
     * @return
     */
    public static CityPickDialogFragment newInstance(boolean enable) {
        final CityPickDialogFragment fragment = new CityPickDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean("cp_enable_anim", enable);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registBroadcast();

        setStyle(STYLE_NO_TITLE, R.style.CityPickerStyle);

        Bundle args = getArguments();
        if (args != null) {
            enableAnim = args.getBoolean("cp_enable_anim");
        }

        initHotCities();
        initLocatedCity();

        mCityLable = CityLabel.getCrimeLabel(getContext());
        mAllCities = mCityLable.getAllCityList();
        mAllCities.add(0, mLocatedCity);
        mAllCities.add(1, new HotCity("热门城市", getResources().getString(R.string.unknow), "0"));
        mResults = mAllCities;
    }

    private void registBroadcast() {
        IntentFilter intentFilter = new IntentFilter(CITY_REQUEST_ACTION);
        getActivity().registerReceiver(mLocationBroadcast, intentFilter);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContentView = View.inflate(getActivity(), R.layout.cp_dialog_city_picker, null);

        mRecyclerView = mContentView.findViewById(R.id.cp_city_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SectionItemDecoration(getActivity(), mAllCities), 0);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()), 1);
        mAdapter = new CityListAdapter(getActivity(), mAllCities, mHotCities, locateState);
        mAdapter.setInnerListener(this);
        mAdapter.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //确保定位城市能正常刷新
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mAdapter.refreshLocationItem();
                }
            }
        });

        mEmptyView = mContentView.findViewById(R.id.cp_empty_view);
        mOverlayTextView = mContentView.findViewById(R.id.cp_overlay);

        mIndexBar = mContentView.findViewById(R.id.cp_side_index_bar);
        mIndexBar.setOverlayTextView(mOverlayTextView)
                .setOnIndexChangedListener(this);

        mSearchBox = mContentView.findViewById(R.id.cp_search_box);
        mSearchBox.addTextChangedListener(this);

        mCancelBtn = mContentView.findViewById(R.id.cp_cancel);
        mClearAllBtn = mContentView.findViewById(R.id.cp_clear_all);
        mCancelBtn.setOnClickListener(this);
        mClearAllBtn.setOnClickListener(this);

        return mContentView;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            if (enableAnim) {
                window.setWindowAnimations(mAnimStyle);
            }
        }
        return dialog;
    }


    public void setOnPickListener(OnPickListener listener) {
        this.mOnPickListener = listener;
    }

    public void setLocatedCity(LocatedCity location) {
        mLocatedCity = location;
    }

    public void setHotCities(List<HotCity> data) {
        if (data != null && !data.isEmpty()) {
            this.mHotCities = data;
        }
    }

    public void setAnimationStyle(@StyleRes int style) {
        this.mAnimStyle = style <= 0 ? R.style.DefaultCityPickerAnimation : style;
    }

    public void locationChanged(LocatedCity location, int state) {
        mAdapter.updateLocateState(location, state);
    }

    private void initHotCities() {
        if (mHotCities == null || mHotCities.isEmpty()) {
            mHotCities = new ArrayList<>();
            mHotCities.add(new HotCity("北京", "北京", "101010100"));
            mHotCities.add(new HotCity("上海", "上海", "101020100"));
            mHotCities.add(new HotCity("广州", "广东", "101280101"));
            mHotCities.add(new HotCity("深圳", "广东", "101280601"));
            mHotCities.add(new HotCity("天津", "天津", "101030100"));
            mHotCities.add(new HotCity("杭州", "浙江", "101210101"));
            mHotCities.add(new HotCity("南京", "江苏", "101190101"));
            mHotCities.add(new HotCity("成都", "四川", "101270101"));
            mHotCities.add(new HotCity("武汉", "湖北", "101200101"));
        }
    }

    private void initLocatedCity() {
        if (mLocatedCity == null) {
            String city = SharePerferenceUtils.getString(getActivity(), SharePerferenceUtils.locateCity,
                    getActivity().getResources().getString(R.string.unknow));
            if(getActivity().getResources().getString(R.string.unknow).equals(city)){
                mLocatedCity = new LocatedCity(getString(R.string.unknow), "", "0");
                locateState = LocateState.FAILURE;
            }else{
                mLocatedCity = new LocatedCity(city, "", "0");
                locateState = LocateState.SUCCESS;
            }


        } else {
            locateState = LocateState.SUCCESS;
        }
    }


    @Override
    public void dismiss(int position, City data) {
        dismiss();
        if (mOnPickListener != null) {
            mOnPickListener.onPick(position, data);
        }
    }

    @Override
    public void locate() {
        if (mOnPickListener != null) {
            mOnPickListener.onLocate();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String keyword = s.toString();
        if (TextUtils.isEmpty(keyword)) {
            mClearAllBtn.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mResults = mAllCities;
            ((SectionItemDecoration) (mRecyclerView.getItemDecorationAt(0))).setData(mResults);
            mAdapter.updateData(mResults);
        } else {
            mClearAllBtn.setVisibility(View.VISIBLE);
            //开始数据库查找
            mResults = mCityLable.getSearchCityList(keyword);
            ((SectionItemDecoration) (mRecyclerView.getItemDecorationAt(0))).setData(mResults);
            if (mResults == null || mResults.isEmpty()) {
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.GONE);
                mAdapter.updateData(mResults);
            }
        }
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cp_cancel) {
            dismiss(-1, null);
        } else if (id == R.id.cp_clear_all) {
            mSearchBox.setText("");
        }
    }

    @Override
    public void onIndexChanged(String index, int position) {
        //滚动RecyclerView到索引位置
        mAdapter.scrollToSection(index);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mLocationBroadcast);

    }
}

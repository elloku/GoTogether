package com.xlman.gotogether.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.xlman.common.app.Application;
import com.xlman.common.app.PresenterToolbarActivity;
import com.xlman.common.widget.PortraitView;
import com.xlman.common.widget.recycler.RecyclerAdapter;
import com.xlman.factory.presenter.group.GroupCreateContract;
import com.xlman.factory.presenter.group.GroupCreatePresenter;
import com.xlman.gotogether.R;

import butterknife.BindView;
import butterknife.OnCheckedChanged;

/**
 * 拼车信息创建界面 Activity
 * <p>
 * create by xlman on 10:30 2018/3/1
 */
public class InfoCreateActivity extends PresenterToolbarActivity<GroupCreateContract.Presenter>
        implements GroupCreateContract.View {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.edit_target)
    EditText mTarget;

    @BindView(R.id.edit_current)
    EditText mCurrent;

    @BindView(R.id.edit_time)
    EditText mTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLocation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationClient.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stop();
    }

    // 定位
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;


    private void initLocation() {

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);

        LocationClientOption option = new LocationClientOption();

        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }

    private Adapter mAdapter;

    public static void show(Context context) {
        context.startActivity(new Intent(context, InfoCreateActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_info_create;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("");

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new Adapter());
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.group_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_create) {
            //  进行创建
            onCreateClick();
        }
        return super.onOptionsItemSelected(item);
    }

    // 进行创建操作
    private void onCreateClick() {
        hideSoftKeyboard();
        String name = mTarget.getText().toString().trim();
        String desc = mCurrent.getText().toString().trim();
        String time = mTime.getText().toString().trim();
        mPresenter.create(name, desc, time);
    }

    // 隐藏软件盘
    private void hideSoftKeyboard() {
        // 当前焦点的View
        View view = getCurrentFocus();
        if (view == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onCreateSucceed() {
        // 提示成功
        hideLoading();
        Application.showToast(R.string.label_group_create_succeed);
        finish();
    }

    @Override
    protected GroupCreateContract.Presenter initPresenter() {
        return new GroupCreatePresenter(this);
    }

    @Override
    public RecyclerAdapter<GroupCreateContract.ViewModel> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        hideLoading();
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息

            mCurrent.setText(addr);
        }
    }

    // 底部拼友列表适配器
    private class Adapter extends RecyclerAdapter<GroupCreateContract.ViewModel> {
        @Override
        protected int getItemViewType(int position, GroupCreateContract.ViewModel viewModel) {
            return R.layout.cell_group_create_contact;
        }

        @Override
        protected ViewHolder<GroupCreateContract.ViewModel> onCreateViewHolder(View root, int viewType) {
            return new InfoCreateActivity.ViewHolder(root);
        }
    }

    // 底部拼友列表
    class ViewHolder extends RecyclerAdapter.ViewHolder<GroupCreateContract.ViewModel> {
        @BindView(R.id.im_portrait)
        PortraitView mPortrait;
        @BindView(R.id.txt_name)
        TextView mName;
        @BindView(R.id.cb_select)
        CheckBox mSelect;

        ViewHolder(View itemView) {
            super(itemView);
        }

        @OnCheckedChanged(R.id.cb_select)
        void onCheckedChanged(boolean checked) {
            // 进行状态更改
            mPresenter.changeSelect(mData, checked);
        }

        @Override
        protected void onBind(GroupCreateContract.ViewModel viewModel) {
            mPortrait.setup(Glide.with(InfoCreateActivity.this), viewModel.author);
            mName.setText(viewModel.author.getName());
            mSelect.setChecked(viewModel.isSelected);
        }
    }
}

package com.xlman.gotogether.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.xlman.factory.map.MyOrientationListener;
import com.xlman.factory.map.OnOrientationListener;
import com.xlman.gotogether.R;


/**
 * 百度地图 Activity
 * <p>
 * Created by xlman on 22:13 2018/3/7.
 */
public class MapActivity extends Activity {

    private MapView mMapView = null;
    private BaiduMap mBaiduMap;

    private Context context;

    // 定位
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;

    // 判断是否是第一次进入百度地图
    private boolean isFirstIn = true;

    // 用来保存经度和纬度信息
    private double mLatitude;
    private double mLongitude;

    // 自定义定位图标
    private BitmapDescriptor mIconLocation;

    // 方向传感器
    private MyOrientationListener myOrientationListener;
    private float mCurrentX;
    private LocationMode mLocationMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        this.context = this;
        initView();
        initLocation();
        final ImageView button = findViewById(R.id.btn_menu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(button);
            }
        });
    }

    private void initLocation() {

        mLocationMode = LocationMode.NORMAL;

        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);

        LocationClientOption option = new LocationClientOption();
        // 可选，设置返回经纬度坐标类型，默认gcj02
        // gcj02：国测局坐标；
        // bd09ll：百度经纬度坐标；
        // bd09：百度墨卡托坐标；
        // 海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        // 可选，设置发起定位请求的间隔，int类型，单位ms
        // 如果设置为0，则代表单次定位，即仅定位一次，默认为0
        // 如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);

        // 初始化定位图标
        mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
        myOrientationListener = new MyOrientationListener(context);

        myOrientationListener.setOnOrientationListener(new OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });
    }

    // 显示菜单
    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.map_menu, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onOptionsItemSelected(item);
                return true;
            }
        });
        popupMenu.show();
    }

    // 对菜单进行点击操作时出触发
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_common_map:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                showToast(item);
                break;
            case R.id.id_site_map:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                showToast(item);
                break;
            case R.id.id_traffic_map:
                // 如果交通图属于打开状态，则把交通图关闭
                if (mBaiduMap.isTrafficEnabled()) {
                    mBaiduMap.setTrafficEnabled(false);
                    item.setTitle("实时交通(OFF)");
                    showToast(item);
                } else {
                    // 否则开启交通图
                    mBaiduMap.setTrafficEnabled(true);
                    item.setTitle("实时交通(ON)");
                    showToast(item);
                }
                break;
            case R.id.id_my_location:
                centerToMyLocation(mLatitude, mLongitude);
                break;
            case R.id.id_map_LocationMode_compass:
                mLocationMode = LocationMode.COMPASS;
                showToast(item);
                break;
            case R.id.id_map_LocationMode_following:
                mLocationMode = LocationMode.FOLLOWING;
                showToast(item);
                break;
            case R.id.id_map_LocationMode_normal:
                mLocationMode = LocationMode.NORMAL;
                showToast(item);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToast(MenuItem item) {
        Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 定位到我的位置
     *
     * @param mLatitude  我所在位置的经度
     * @param mLongitude 我所在位置的纬度
     */
    private void centerToMyLocation(double mLatitude, double mLongitude) {
        LatLng latlng = new LatLng(mLatitude, mLongitude);
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latlng);
        mBaiduMap.animateMapStatus(mapStatusUpdate);
    }

    //初始化界面
    private void initView() {
        mMapView = findViewById(R.id.id_mapView);
        mBaiduMap = mMapView.getMap();

        //设置地图比例为500米
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(mapStatusUpdate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    /**
     * 当开始Activity时开启定位
     * 定位到自己所在的位置
     */
    @Override
    protected void onStart() {
        super.onStart();
        // 允许开启定位
        mBaiduMap.setMyLocationEnabled(true);
        // 开启定位
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
            // 开启方向传感器
            myOrientationListener.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    /**
     * 当Activity 停止时关闭定位
     * 避免出现此APP已停止后还在定位还在进行中
     */
    @Override
    protected void onStop() {
        super.onStop();
        // 停止定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        // 停止方向传感器
        myOrientationListener.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    public static void show(Context context) {
        context.startActivity(new Intent(context, MapActivity.class));
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // 开启定位图层
            mBaiduMap.setMyLocationEnabled(true);
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置获取到的方向信息，顺时针0-360
                    .direction(mCurrentX)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);

            // 设置自定义图标
            MyLocationConfiguration config = new MyLocationConfiguration(mLocationMode, true, mIconLocation);
            mBaiduMap.setMyLocationConfiguration(config);

            // 更新经纬度
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

            if (isFirstIn) {
                // 获得经度和纬度
                centerToMyLocation(location.getLatitude(), location.getLongitude());
                isFirstIn = false;
                Toast.makeText(context, location.getAddrStr(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}



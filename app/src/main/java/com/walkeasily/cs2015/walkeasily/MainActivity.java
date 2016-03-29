package com.walkeasily.cs2015.walkeasily;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;


public class MainActivity extends Activity {
    private boolean isShow = true;
    private boolean mapisNormal;
    private boolean flag;
    private FloatingActionButton mapMode;
    private FloatingActionButton locate;
    private FloatingActionButton imageMenu;
    private FloatingActionMenu map;
    private FloatingActionMenu activities;
    private FloatingActionMenu member;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private InfoWindow mInfoWindow = null;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        init();

        //菜单的初始与设置
        initFABMenu();
        setImageMenu();

        //定位键的初始与设置
        setLocation();

        //地图模式的初始与设置
        initandsetmapMode();

        //判断是否网络连接
        Util.isNetworkConnected(MainActivity.this);

        //Mark的初始
        initMark();

        //气泡的设置
        initinfowindow();

        //菜单1的设置
        initMenu1();

        //菜单3的设置
        initMenu3();

    }

    private void initMenu3() {
        FloatingActionButton btn_account = (FloatingActionButton) findViewById(R.id.account);
        FloatingActionButton btn_notice = (FloatingActionButton) findViewById(R.id.notice);

        //账号按钮的点击事件
        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initMenu1() {
        FloatingActionButton empty_classroom = (FloatingActionButton) findViewById(R.id.empty_classroom);
        FloatingActionButton loaction = (FloatingActionButton) findViewById(R.id.location);
        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.search);

        //空教室

        //定位

        loaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    locate.setVisibility(View.INVISIBLE);
                    isShow = false;
                } else {
                    locate.setVisibility(View.VISIBLE);
                    isShow = true;
                }
            }
        });
        //检索
    }


    private void initinfowindow() {
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                double latitude, longitude;
                latitude = marker.getPosition().latitude;
                longitude = marker.getPosition().longitude;

                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.info_window, null); //自定义气泡形状
                TextView textView = (TextView) view.findViewById(R.id.textView);
                TextView title = (TextView) view.findViewById(R.id.title);
                TextView click = (TextView) view.findViewById(R.id.click);
                //标题内容
                title.setText(marker.getTitle());
                LatLng pt = new LatLng(latitude, longitude);
                //正文内容
                textView.setText("lalalala");

                //查看详情
                click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, com.walkeasily.cs2015.walkeasily.List.class);
                        startActivity(intent);
                    }
                });


                // 定义用于显示该InfoWindow的坐标点
                // 创建InfoWindow
                mInfoWindow = new InfoWindow(view, pt, -30);

                mBaiduMap.showInfoWindow(mInfoWindow); //显示气泡

                return false;
            }
        });
    }

    private void initMark() {
        //教十一
        LatLng latLng11 = new LatLng(38.89482449994686, 115.52477449681984);
        //教十
        LatLng latLng10 = new LatLng(38.892128625498785, 115.52070517285965);
        //教九
        LatLng latLng9 = new LatLng(38.89214968741778, 115.51942059598038);
        //教八
        LatLng latLng8 = new LatLng(38.89560375693293, 115.52154059698393);
        //教七
        LatLng latLng7 = new LatLng(38.892992159013566, 115.52061534230864);
        //教六
        LatLng latLng6 = new LatLng(38.892816644928615, 115.51986974873536);
        //操场
        LatLng latLng0 = new LatLng(38.89273239801251, 115.5191600873825);


//准备 marker 的图片
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.building_mark);
//准备 marker option 添加 marker 使用
        MarkerOptions markerOptions11 = new MarkerOptions().icon(bitmap).position(latLng11).title("教十一");
        MarkerOptions markerOptions10 = new MarkerOptions().icon(bitmap).position(latLng10).title("教十");
        MarkerOptions markerOptions9 = new MarkerOptions().icon(bitmap).position(latLng9).title("教九");
        MarkerOptions markerOptions8 = new MarkerOptions().icon(bitmap).position(latLng8).title("教八");
        MarkerOptions markerOptions7 = new MarkerOptions().icon(bitmap).position(latLng7).title("教七");
        MarkerOptions markerOptions6 = new MarkerOptions().icon(bitmap).position(latLng6).title("教六");
        MarkerOptions markerOptions0 = new MarkerOptions().icon(bitmap).position(latLng0).title("运动场");


//获取添加的 marker 这样便于后续的操作
        Marker marker11 = (Marker) mBaiduMap.addOverlay(markerOptions11);
        Marker marker10 = (Marker) mBaiduMap.addOverlay(markerOptions10);
        Marker marker9 = (Marker) mBaiduMap.addOverlay(markerOptions9);
        Marker marker8 = (Marker) mBaiduMap.addOverlay(markerOptions8);
        Marker marker7 = (Marker) mBaiduMap.addOverlay(markerOptions7);
        Marker marker6 = (Marker) mBaiduMap.addOverlay(markerOptions6);
        Marker marker0 = (Marker) mBaiduMap.addOverlay(markerOptions0);


    }


    private void init() {

        mMapView = (MapView) findViewById(R.id.bmapview);


        // 隐藏logo
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }

        //隐藏缩放按钮
        mMapView.showZoomControls(false);


        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);


        //地图中心默认为二校
        initMapCenter();


        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });


    }

    private void setImageMenu() {
        flag = false;
        imageMenu = (FloatingActionButton) findViewById(R.id.imageMenu);
        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    map.showMenu(true);
                    activities.showMenu(true);
                    member.showMenu(true);
                    imageMenu.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.image_menu_2));
                    flag = true;

                } else {
                    map.hideMenu(true);
                    activities.hideMenu(true);
                    member.hideMenu(true);
                    imageMenu.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.image_menu_1));
                    flag = false;

                }

            }
        });
    }

    private void initFABMenu() {
        map = (FloatingActionMenu) findViewById(R.id.Map);
        activities = (FloatingActionMenu) findViewById(R.id.Activities);
        member = (FloatingActionMenu) findViewById(R.id.Member);


        map.hideMenu(false);
        activities.hideMenu(false);
        member.hideMenu(false);
    }


    private void initMapCenter() {
        double x = 38.896186;
        double y = 115.523255;
        LatLng cenpt = new LatLng(x, y);
//定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(18)
                .build();
//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);


    }

    private void setLocation() {
        locate = (FloatingActionButton) findViewById(R.id.locate);
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定位当前位置
                mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
                mLocationClient.registerLocationListener(myListener);    //注册监听函数
                initLocation();
                mLocationClient.start();
                locate.setEnabled(false);
            }
        });


    }

    private void initandsetmapMode() {
        mapisNormal = true;
        mapMode = (FloatingActionButton) findViewById(R.id.mapMode);
        mapMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapMode.setEnabled(false);
                if (mapisNormal) {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    mapisNormal = false;
                    init();
                    mapMode.setEnabled(true);
                } else {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    mapisNormal = true;
                    init();
                    mapMode.setEnabled(true);
                }

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        //退出时销毁定位
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        // TODO Auto-generated method stub
        super.onDestroy();
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            double x = location.getLatitude();
            double y = location.getLongitude();
            if (x >= 38.891 && x <= 38.898 && y >= 115.51 && y <= 115.53) {

                LatLng cenpt = new LatLng(x, y);
//定义地图状态
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(cenpt)
                        .zoom(18)
                        .build();
//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//改变地图状态
                mBaiduMap.setMapStatus(mMapStatusUpdate);


//我的位置点
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);    //设置定位数据
                mLocationClient.stop();
                locate.setEnabled(true);
            } else {
                Dialog alertDialog = new AlertDialog.Builder(MainActivity.this).
                        setTitle("提示").
                        setMessage("本应用是针对华北电力大学保定二校区开发，请在其附近区域使用定位功能。").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                alertDialog.show();
                mLocationClient.stop();
                locate.setEnabled(true);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mInfoWindow != null) {
            mBaiduMap.hideInfoWindow();
        }
        return super.onTouchEvent(event);
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 3000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setNeedDeviceDirect(true);//设置机头朝向
        mLocationClient.setLocOption(option);
    }
}

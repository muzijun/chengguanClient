package com.pactera.chengguan.municipal.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.model.municipal.CaseInfo;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.ItemizedOverlay;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.OverlayItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 天地图显示
 * Created by huang hua
 * 2016/5/24.
 */
public class MapActivity extends BaseActivity {

    private MapView mMapView;
    private CaseInfo caseInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mMapView = (MapView) findViewById(R.id.mapview);
        mMapView.displayZoomControls(true);
        getIntentContent();
        showGeoPoint();
    }

    private void getIntentContent(){
        caseInfo = (CaseInfo) getIntent().getSerializableExtra("case");
        if(caseInfo == null){
            finish();
        }
    }

    private void showGeoPoint(){
        Drawable marker = getResources().getDrawable(R.mipmap.poiresult);
        OverItemT overlay = new OverItemT(marker, mContext);
        int latE6 = (int) (caseInfo.getLatitude() * 1000000);
        int lonE6 = (int) (caseInfo.getLongitude() * 1000000);
        GeoPoint geoPoint = new GeoPoint(latE6, lonE6);
        OverlayItem item = new OverlayItem(geoPoint, caseInfo.getLocation(), "");
        overlay.addItem(item);
        item.getMarker(OverlayItem.ITEM_STATE_FOCUSED_MASK);
        mMapView.getController().animateTo(geoPoint);
        overlay.Populate();
        mMapView.getOverlays().add(overlay);
        mMapView.postInvalidate();
    }

    /**
     * 自定义覆盖物
     */
    static class OverItemT extends ItemizedOverlay<OverlayItem> implements Overlay.Snappable{

        private List<OverlayItem> GeoList = new ArrayList<OverlayItem>();
        private static Drawable mMaker = null;
        private Context mContext;

        public OverItemT(Drawable marker, Context mContext) {
            super((mMaker = boundCenterBottom(marker)));
            this.mContext = mContext;
        }


        @Override
        protected OverlayItem createItem(int i) {
            return GeoList.get(i);
        }

        @Override
        public int size() {
            return GeoList.size();
        }

        public void addItem(OverlayItem item)
        {
            item.setMarker(mMaker);
            GeoList.add(item);
        }

        @Override
        public void draw(Canvas canvas, MapView mapView, boolean shadow) {
            super.draw(canvas, mapView, shadow);
        }

        // 处理当点击事件
        @Override
        protected boolean onTap(int i) {
            Toast.makeText(mContext, GeoList.get(i).getTitle(), Toast.LENGTH_SHORT).show();
            return super.onTap(i);
        }

        public void Populate()
        {
            populate();
        }

    }
}

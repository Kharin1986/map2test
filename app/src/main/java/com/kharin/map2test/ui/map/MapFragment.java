package com.kharin.map2test.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.kharin.map2test.R;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

public class MapFragment extends Fragment implements UserLocationObjectListener {

    private MapViewModel mapViewModel;
    private UserLocationLayer userLocationLayer;
    private MapView mapview;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel =
                ViewModelProviders.of(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);


            mapview =  root.findViewById(R.id.mapview);
            mapview.getMap().setRotateGesturesEnabled(false);
            mapview.getMap().move(new CameraPosition(new Point(0, 0), 14, 0, 0));
            MapKit mapKit = MapKitFactory.getInstance();
            userLocationLayer = mapKit.createUserLocationLayer(mapview.getMapWindow());
            userLocationLayer.setVisible(true);
            userLocationLayer.setHeadingEnabled(true);
            userLocationLayer.setObjectListener(this);
        return root;
    }



    @Override
    public void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float)(mapview.getWidth() * 0.5), (float)(mapview.getHeight() * 0.5)),
                new PointF((float)(mapview.getWidth() * 0.5), (float)(mapview.getHeight() * 0.83)));
        if(getActivity()!=null) {
            userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                    getActivity(), R.drawable.user_arrow));

            CompositeIcon pinIcon = userLocationView.getPin().useCompositeIcon();

//            pinIcon.setIcon(
//                    "icon",
//                    ImageProvider.fromResource(getActivity(), R.drawable.icon),
//                    new IconStyle().setAnchor(new PointF(0f, 0f))
//                            .setRotationType(RotationType.ROTATE)
//                            .setZIndex(0f)
//                            .setScale(1f)
//            );

            pinIcon.setIcon(
                    "pin",
                    ImageProvider.fromResource(getActivity(), R.drawable.search_result),
                    new IconStyle().setAnchor(new PointF(0.5f, 0.5f))
                            .setRotationType(RotationType.ROTATE)
                            .setZIndex(1f)
                            .setScale(0.5f)
            );

       //   userLocationView.getAccuracyCircle().setFillColor(Color.BLUE);
        }
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {

    }
}
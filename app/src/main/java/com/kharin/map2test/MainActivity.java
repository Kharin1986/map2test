package com.kharin.map2test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.transport.TransportFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_FINE_PERMISSION = 20;
    private final String APIKEY = "8b473f18-1ae9-4111-bcf6-b0e48927546b";
    private MapView mapview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_map, R.id.navigation_notifications)
                .build();
        if(receivePermissions()) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
            MapKitFactory.setApiKey(APIKEY);
            MapKitFactory.initialize(this);
            TransportFactory.initialize(this);// для общественного транспорта

        }
    }
    private boolean receivePermissions() {
        boolean result = false;
            int permissionFineLocationStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            int permissionCoarseLocationStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (permissionFineLocationStatus == PackageManager.PERMISSION_GRANTED&&permissionCoarseLocationStatus ==PackageManager.PERMISSION_GRANTED) {
                result  = true;
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_FINE_PERMISSION);
                this.recreate(); //////////////Иначе выпадает с ошибкой
              result = (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) >=0)&&
                      (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) >=0);
            }
        return result;
    }
}
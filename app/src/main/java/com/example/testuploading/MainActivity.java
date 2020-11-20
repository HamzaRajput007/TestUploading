package com.example.testuploading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.testuploading.Fragments.FragmentOne;
import com.example.testuploading.Fragments.FragmentTwo;
import com.example.testuploading.Fragments.NetworkLostFragment;

public class MainActivity extends AppCompatActivity {

    Button fragmentOneBtn, fragmentTwoBtn;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentOneBtn = findViewById(R.id.buttonFragmentOne);
        fragmentTwoBtn = findViewById(R.id.buttonFragmentTwo);
        frameLayout = findViewById(R.id.frameLayout);
        frameLayout.removeAllViews();

        fragmentOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new FragmentOne());
            }
        });
        fragmentTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new FragmentTwo());
            }
        });

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            loadFragment(new NetworkLostFragment());
        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.removeView();
        ((ViewGroup) frameLayout.getParent()).removeView(frameLayout.getRootView());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
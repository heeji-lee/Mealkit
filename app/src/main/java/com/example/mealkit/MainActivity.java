package com.example.mealkit;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mealkit.ui.account.AccountFragment;
import com.example.mealkit.ui.favorite.FavoriteFragment;
import com.example.mealkit.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
//        View headView = navigationView.getHeaderView(0);
//        TextView nickname = headView.findViewById(R.id.nickname);
//        nickname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FrameLayout FragmentContainer = findViewById(R.id.fragment_container);
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                Fragment AccountFragment = new AccountFragment();
//                fragmentTransaction.replace(R.id.fragment_container,AccountFragment);
//                fragmentTransaction.commit();
//            }
//        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        HomeFragment homeFragment = new HomeFragment();
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        AccountFragment accountFragment = new AccountFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.tab1: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment,homeFragment)
                                .commit();
                        return true;
                    } case R.id.tab2: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment,favoriteFragment)
                                .commit();
                        return true;
                    } case R.id.tab3: {

                        return true;
                    } case R.id.tab4: case R.id.tab5: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment,accountFragment)
                                .commit();
                        return true;
                    } default:
                        return false;
                }
            }
        });
        //        Log.d("GET_KEYHASH",getKeyHash());
    }

//    public String getKeyHash(){
//        try{
//            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            if(packageInfo == null) return null;
//            for(Signature signature: packageInfo.signatures){
//                try{
//                    MessageDigest md = MessageDigest.getInstance("SHA");
//                    md.update(signature.toByteArray());
//                    return android.util.Base64.encodeToString(md.digest(), Base64.NO_WRAP);
//                }catch (NoSuchAlgorithmException e){
//                    e.printStackTrace();
//                }
//            }
//        }catch(PackageManager.NameNotFoundException e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "'뒤로'버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        } else if(System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        } else {
            super.onBackPressed();
        }
    }
}
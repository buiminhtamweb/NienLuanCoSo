package mycompany.com.nienluancoso;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import mycompany.com.nienluancoso.Category.Fragment2;
import mycompany.com.nienluancoso.Home.Fragment1;
import mycompany.com.nienluancoso.Order.OrderActivity;
import mycompany.com.nienluancoso.User.Fragment3;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   mViewPager.setCurrentItem(0,true);
                    return true;
                case R.id.navigation_dashboard:
                    mViewPager.setCurrentItem(1,true);
                    return true;
                case R.id.navigation_order:
                    Intent intent = new Intent(getBaseContext(), OrderActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_user:
                    mViewPager.setCurrentItem(2,true);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();


        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.add(fragment1,"Fragment 1");
        pagerAdapter.add(fragment2,"Fragment 2");
        pagerAdapter.add(fragment3,"Fragment 3");
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(pagerAdapter);
    }






}

package mycompany.com.nienluancoso;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import mycompany.com.nienluancoso.Category.Fragment2;
import mycompany.com.nienluancoso.Home.Fragment1;
import mycompany.com.nienluancoso.Order.Fragment3;
import mycompany.com.nienluancoso.User.Fragment4;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private MenuItem mPrevMenuItem;

    BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   mViewPager.setCurrentItem(0,true);
                    break;
                case R.id.navigation_dashboard:
                    mViewPager.setCurrentItem(1,true);
                    break;
                case R.id.navigation_order:
                    mViewPager.setCurrentItem(2,true);
                    break;
                case R.id.navigation_user:
                    mViewPager.setCurrentItem(3,true);
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //Chọn màn hình chính
        int position = 0;
        //Lấy dự liệu Intent từ Tạo TK
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt(Constant.MAIN_POSITION);
            mViewPager.setCurrentItem(position);
        }
    }

    private void initView(){
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        Fragment4 fragment4 = new Fragment4();


        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.add(fragment1,"Fragment 1");
        pagerAdapter.add(fragment2,"Fragment 2");
        pagerAdapter.add(fragment3,"Fragment 3");
        pagerAdapter.add(fragment4,"Fragment 4");
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(pagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mPrevMenuItem != null) {
                    mPrevMenuItem.setChecked(false);
                }
                else
                {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                navigation.getMenu().getItem(position).setChecked(true);
                mPrevMenuItem = navigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }






}

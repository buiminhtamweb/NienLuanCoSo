package mycompany.com.nienluancoso.Home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mycompany.com.nienluancoso.Data.AgriObject;
import mycompany.com.nienluancoso.Data.Api;
import mycompany.com.nienluancoso.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by buimi on 1/29/2018.
 */

public class Fragment1 extends Fragment {

    int currentPage = 0;
    int NUM_PAGES = 0;
    private List<AgriObject> mAgriObjectListHot = new ArrayList<>();
    private List<AgriObject> mAgriObjectListNew = new ArrayList<>();
    private RecylerViewAdapter recylerViewAdapter, recylerViewAdapterHot;
    private RecyclerView recyclerViewHot, recyclerViewNew, recyclerViewSale;
    private Button mBtnSearch;
    private ViewPager mBannerSlider;
    private BannerSliderAdapter mBannerSliderAdapter;

    public Fragment1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,
                container, false);

        recyclerViewNew = (RecyclerView) view.findViewById(R.id.recycler_view_new);
        recyclerViewHot = (RecyclerView) view.findViewById(R.id.recycler_view_hot);
        recyclerViewSale = (RecyclerView) view.findViewById(R.id.recycler_view_sale);
        mBtnSearch = (Button) view.findViewById(R.id.btn_search);


        mBannerSlider = (ViewPager) view.findViewById(R.id.banner_slider);
//        mBannerSliderAdapter = new BannerSliderAdapter(getContext());
//        mBannerSlider.setAdapter(mBannerSliderAdapter);

        initImageSlider(view);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNew.setLayoutManager(mLayoutManager);

        RecyclerView.LayoutManager mLayoutManagerHot = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHot.setLayoutManager(mLayoutManagerHot);

        recylerViewAdapter = new RecylerViewAdapter(getContext(), mAgriObjectListNew);
        recylerViewAdapterHot = new RecylerViewAdapter(getContext(), mAgriObjectListHot);

        recyclerViewNew.setAdapter(recylerViewAdapter);
        recyclerViewHot.setAdapter(recylerViewAdapterHot);

        getData();
        return view;
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);

        Call<List<AgriObject>> call = api.getNewAgri();

        call.enqueue(new Callback<List<AgriObject>>() {
            @Override
            public void onResponse(Call<List<AgriObject>> call, Response<List<AgriObject>> response) {

                mAgriObjectListNew.clear();
                if (response != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        mAgriObjectListNew.add(response.body().get(i));
                        Log.e("Home", mAgriObjectListNew.get(i).getNAME_AGRI());
                    }
                }
                recylerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AgriObject>> call, Throwable t) {
//                Snackbar snackbar = Snackbar
//                        .make(mBtnSearch, "Lỗi ! Không thể truy cập đến server", Snackbar.LENGTH_LONG);
//                snackbar.show();
                Toast.makeText(getContext(),"Lỗi ! Không thể truy cập đến server", Toast.LENGTH_SHORT).show();
            }
        });
        Call<List<AgriObject>> callHot = api.getHotAgri();
        callHot.enqueue(new Callback<List<AgriObject>>() {
            @Override
            public void onResponse(Call<List<AgriObject>> call, Response<List<AgriObject>> response) {
                mAgriObjectListHot.clear();
                if (response != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        mAgriObjectListHot.add(response.body().get(i));
                        Log.e("Home", mAgriObjectListHot.get(i).getNAME_AGRI());
                    }
                }
                recylerViewAdapterHot.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AgriObject>> call, Throwable t) {
                Toast.makeText(getContext(),"Lỗi ! Không thể truy cập đến server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Silder
    private List<ImageSlider> getImageList() {
        List<ImageSlider> imageList = new ArrayList<>();
        imageList.add(new ImageSlider("Logo", R.drawable.backround));
        imageList.add(new ImageSlider("Steve aoki", R.drawable.backround));
        imageList.add(new ImageSlider("Dancellenium", R.drawable.backround));
        return imageList;
    }

    private void initImageSlider(View view) {

        //Set the pager with an adapter
        mBannerSlider.setAdapter(new ImageSliderAdapter(getContext(), getImageList()));

        CirclePageIndicator indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);

        indicator.setViewPager(mBannerSlider);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = getImageList().size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mBannerSlider.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }


}

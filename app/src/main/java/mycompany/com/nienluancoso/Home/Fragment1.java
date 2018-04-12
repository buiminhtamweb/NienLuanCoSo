package mycompany.com.nienluancoso.Home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import mycompany.com.nienluancoso.Data.AgriItemObject;
import mycompany.com.nienluancoso.Data.Api;
import mycompany.com.nienluancoso.DetailAgri.ChiTietNSActivity;
import mycompany.com.nienluancoso.R;
import mycompany.com.nienluancoso.Search.SearchActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by buimi on 1/29/2018.
 */

public class Fragment1 extends Fragment {


    //Chuyển trang QC ViewPager
    int currentPage = 0;
    int NUM_PAGES = 0;


    private List<AgriItemObject> mAgriItemListCheap = new ArrayList<>();
    private List<AgriItemObject> mAgriItemObjectListHot = new ArrayList<>();
    private List<AgriItemObject> mAgriItemObjectListSale = new ArrayList<>();
    private RecyItemAgriAdapter recyItemAgriAdapterCheap, recyItemAgriAdapterHot, recyItemAgriAdapterSale;
    private RecyclerView recyclerViewHot, recyclerViewCheap, recyclerViewSale;
    private Button mBtnSearch;
    private ViewPager mBannerSlider;
    private Intent mIntent;

    private Snackbar mSnackbar;

    private BannerSliderAdapter mBannerSliderAdapter;

    public Fragment1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,
                container, false);

        recyclerViewCheap = (RecyclerView) view.findViewById(R.id.recycler_view_cheap);
        recyclerViewHot = (RecyclerView) view.findViewById(R.id.recycler_view_hot);
        recyclerViewSale = (RecyclerView) view.findViewById(R.id.recycler_view_sale);
        mBtnSearch = (Button) view.findViewById(R.id.btn_search);

        mSnackbar = Snackbar
                .make(mBtnSearch, "Lỗi ! Không thể truy cập đến server", Snackbar.LENGTH_LONG);



        mIntent = new Intent(getActivity(), ChiTietNSActivity.class);
        mBannerSlider = (ViewPager) view.findViewById(R.id.banner_slider);
//        mBannerSliderAdapter = new BannerSliderAdapter(getContext());
//        mBannerSlider.setAdapter(mBannerSliderAdapter);

        initImageSlider(view);


        //Set LayoutManager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCheap.setLayoutManager(mLayoutManager);
        RecyclerView.LayoutManager mLayoutManagerHot = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHot.setLayoutManager(mLayoutManagerHot);
        RecyclerView.LayoutManager mLayoutManagerSale = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSale.setLayoutManager(mLayoutManagerSale);

        //Set Adapter
        recyItemAgriAdapterCheap = new RecyItemAgriAdapter(getContext(), mAgriItemListCheap);
        recyItemAgriAdapterHot = new RecyItemAgriAdapter(getContext(), mAgriItemObjectListHot);
        recyItemAgriAdapterSale = new RecyItemAgriAdapter(getContext(), mAgriItemObjectListSale);

        recyclerViewCheap.setAdapter(recyItemAgriAdapterCheap);
        recyclerViewHot.setAdapter(recyItemAgriAdapterHot);
        recyclerViewSale.setAdapter(recyItemAgriAdapterSale);

        getData();
        evenClick();

        return view;
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);

        //Load Nông sản mới nhập
        Call<List<AgriItemObject>> callNew = api.getCheapAgri();
        callNew.enqueue(new Callback<List<AgriItemObject>>() {
            @Override
            public void onResponse(Call<List<AgriItemObject>> call, Response<List<AgriItemObject>> response) {

                mAgriItemListCheap.clear();
                if (response != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        mAgriItemListCheap.add(response.body().get(i));
//                        Log.e("Home", mAgriItemObjectListNew.get(i).getNAME_AGRI());
                    }
                }
                recyItemAgriAdapterCheap.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AgriItemObject>> call, Throwable t) {

                mSnackbar.show();

            }
        });

        //Load Nông sản hot
        Call<List<AgriItemObject>> callHot = api.getHotAgri();
        callHot.enqueue(new Callback<List<AgriItemObject>>() {
            @Override
            public void onResponse(Call<List<AgriItemObject>> call, Response<List<AgriItemObject>> response) {
                mAgriItemObjectListHot.clear();
                if (response != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        mAgriItemObjectListHot.add(response.body().get(i));
//                        Log.e("Home", mAgriItemObjectListHot.get(i).getNAME_AGRI());
                    }
                }
                recyItemAgriAdapterHot.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AgriItemObject>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi ! Không thể truy cập đến server", Toast.LENGTH_SHORT).show();
            }
        });


        ////Load Nông sản giảm giá
        Call<List<AgriItemObject>> callSale = api.getSaleAgri();
        callSale.enqueue(new Callback<List<AgriItemObject>>() {
            @Override
            public void onResponse(Call<List<AgriItemObject>> call, Response<List<AgriItemObject>> response) {
                mAgriItemObjectListSale.clear();
                if (response != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        mAgriItemObjectListSale.add(response.body().get(i));
//                        Log.e("Home", mAgriItemObjectListSale.get(i).getNAME_AGRI());
                    }
                }
                recyItemAgriAdapterSale.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AgriItemObject>> call, Throwable t) {
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

    private void evenClick(){
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        recyItemAgriAdapterCheap.setOnClickListener(new RecyItemAgriAdapter.onClickListener() {
            @Override
            public void onItemClick(int position, int idAgri) {
                mIntent.putExtra("ID_AGRI",idAgri+"");
                startActivity(mIntent);
            }
        });

        recyItemAgriAdapterHot.setOnClickListener(new RecyItemAgriAdapter.onClickListener() {
            @Override
            public void onItemClick(int position, int idAgri) {
                mIntent.putExtra("ID_AGRI",idAgri+"");
                startActivity(mIntent);

            }
        });

        recyItemAgriAdapterSale.setOnClickListener(new RecyItemAgriAdapter.onClickListener() {
            @Override
            public void onItemClick(int position, int idAgri) {
                mIntent.putExtra("ID_AGRI",idAgri+"");
                startActivity(mIntent);
            }
        });


    }


}

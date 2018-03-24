package mycompany.com.nienluancoso.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mycompany.com.nienluancoso.Data.AgriObject;
import mycompany.com.nienluancoso.Data.Api;
import mycompany.com.nienluancoso.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * Created by buimi on 1/29/2018.
 */

public class Fragment1 extends Fragment {

    private List<AgriObject> mAgriObjectListHot = new ArrayList<>();
    private List<AgriObject> mAgriObjectListNew = new ArrayList<>();

    public Fragment1() {
    }

    RecylerViewAdapter recylerViewAdapter, recylerViewAdapterHot;
    RecyclerView recyclerViewHot, recyclerViewNew;

    BannerSlider bannerSlider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,
                container, false);

        recyclerViewNew = (RecyclerView) view.findViewById(R.id.recycler_view_new);
        recyclerViewHot = (RecyclerView) view.findViewById(R.id.recycler_view_hot);

        bannerSlider = (BannerSlider) view.findViewById(R.id.banner_slider);
        List<Banner> banners=new ArrayList<>();
        //add banner using image url
        //banners.add(new RemoteBanner("Put banner image url here ..."));
        //add banner using resource drawable
        banners.add(new DrawableBanner(R.drawable.gao));
        banners.add(new DrawableBanner(R.drawable.gao));
        banners.add(new DrawableBanner(R.drawable.gao));
        bannerSlider.setBanners(banners);


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
                Snackbar snackbar = Snackbar
                        .make(bannerSlider, "Lỗi ! Không thể truy cập đến server", Snackbar.LENGTH_LONG);

                snackbar.show();
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
                Snackbar snackbar = Snackbar
                        .make(recyclerViewHot, "Lỗi ! Không thể truy cập đến server", Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        });
    }
}

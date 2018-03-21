package mycompany.com.nienluancoso.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import mycompany.com.nienluancoso.Category.GridViewAdapter;
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

    private GridView mGridView;
    private GridViewAdapter mGridViewAdapter;
    private List<AgriObject> mAgriObjectListHot = new ArrayList<>();
    private List<AgriObject> mAgriObjectListNew = new ArrayList<>();

    public Fragment1() {
    }

    public static Fragment newIntance() {
        return new Fragment1();
    }


    RecylerViewAdapter recylerViewAdapter;
    RecyclerView recyclerViewHot,recyclerViewNew;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,
                container, false);
        getData();

//        mGridView = (GridView) view.findViewById(R.id.gridView_frag1);
        recyclerViewHot = (RecyclerView) view.findViewById(R.id.recycler_view_hot);
        recyclerViewNew = (RecyclerView) view.findViewById(R.id.recycler_view_new);

        return view;
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);

        Call<List<AgriObject>> call = api.getHotAgri();

        call.enqueue(new Callback<List<AgriObject>>() {
            @Override
            public void onResponse(Call<List<AgriObject>> call, Response<List<AgriObject>> response) {
                mAgriObjectListHot = response.body();
                Log.e("logg", mAgriObjectListHot.get(1).getNAME_AGRI());

//                mGridViewAdapter = new GridViewAdapter(getContext(), mAgriObjectListHot);
//
//                mGridView.setAdapter(mGridViewAdapter);

                recyclerViewHot.setAdapter(new RecylerViewAdapter(getContext(), mAgriObjectListHot));
            }

            @Override
            public void onFailure(Call<List<AgriObject>> call, Throwable t) {

            }
        });

        Call<List<AgriObject>> callNew = api.getNewAgri();

        callNew.enqueue(new Callback<List<AgriObject>>() {
            @Override
            public void onResponse(Call<List<AgriObject>> call, Response<List<AgriObject>> response) {
                mAgriObjectListNew = response.body();
                Log.e("logg", mAgriObjectListNew.get(1).getNAME_AGRI());

//                mGridViewAdapter = new GridViewAdapter(getContext(), mAgriObjectListHot);
//
//                mGridView.setAdapter(mGridViewAdapter);

                recyclerViewNew.setAdapter(new RecylerViewAdapter(getContext(), mAgriObjectListNew));
            }

            @Override
            public void onFailure(Call<List<AgriObject>> call, Throwable t) {

            }
        });
    }
}

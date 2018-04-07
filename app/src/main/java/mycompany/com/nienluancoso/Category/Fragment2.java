package mycompany.com.nienluancoso.Category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import mycompany.com.nienluancoso.Data.AgriItemObject;
import mycompany.com.nienluancoso.Data.Api;
import mycompany.com.nienluancoso.Data.KindObject;
import mycompany.com.nienluancoso.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by buimi on 1/29/2018.
 */

public class Fragment2 extends Fragment {

    private final static String TAG = "Category";
    List<AgriItemObject> mAgriItemObjectList = new ArrayList<>();
    List<KindObject> kindData = new ArrayList<>();
    private ListView mKindList;
    private GridView mAgriList;
    private GridViewAdapter mGridViewAdapter;
    private LvKindAdapter mLvKindAdapter;
    private Api api;
    private Retrofit retrofit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);

        mKindList = (ListView) view.findViewById(R.id.listview_kind);
        mKindList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mKindList.setSelector(R.drawable.selector_listview);


        //Danh sách nông sản theo loại
        mAgriList = (GridView) view.findViewById(R.id.gridview_ds_sp);
        mGridViewAdapter = new GridViewAdapter(getContext(), mAgriItemObjectList);
        mAgriList.setAdapter(mGridViewAdapter);
        mAgriList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e(TAG, "onClick: ID_AGRI =  " + l);
            }
        });

        initRetrofit();
        getKind();
        getAllArgi();

        //danh sách loại nông sản
        mLvKindAdapter = new LvKindAdapter(getContext(), kindData);
        mKindList.setAdapter(mLvKindAdapter);
        mKindList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onClick: ID_KIND =  " + id);

                if (id == 0){
                    getAllArgi();
                }else {
                    getArgiWithKind(id + "");
                }


            }
        });

        return view;
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    private void getKind() {
        Call<List<KindObject>> call = api.getKind();
        call.enqueue(new Callback<List<KindObject>>() {
            @Override
            public void onResponse(Call<List<KindObject>> call, Response<List<KindObject>> response) {

                kindData.clear();
                kindData.add(new KindObject("0","Tất cả"));
                if (response != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        kindData.add(response.body().get(i));
                    }
                }

                mLvKindAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<KindObject>> call, Throwable t) {

            }
        });

    }

    private void getArgiWithKind(String kind) {


        Call<List<AgriItemObject>> call = api.getArgiWithKind(kind);

        call.enqueue(new Callback<List<AgriItemObject>>() {
            @Override
            public void onResponse(Call<List<AgriItemObject>> call, Response<List<AgriItemObject>> response) {

                mAgriItemObjectList.clear();
                if (response != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        mAgriItemObjectList.add(response.body().get(i));
                        Log.e(TAG, mAgriItemObjectList.get(i).getNAME_AGRI());
                    }
                }

                mGridViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AgriItemObject>> call, Throwable t) {

            }
        });

    }

    private void getAllArgi(){
        Call<List<AgriItemObject>> call = api.getAllAgri();

        call.enqueue(new Callback<List<AgriItemObject>>() {
            @Override
            public void onResponse(Call<List<AgriItemObject>> call, Response<List<AgriItemObject>> response) {

                mAgriItemObjectList.clear();
                if (response != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        mAgriItemObjectList.add(response.body().get(i));
//                        Log.e(TAG, mAgriItemObjectList.get(i).getNAME_AGRI());
                    }
                }

                mGridViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AgriItemObject>> call, Throwable t) {

            }
        });
    }

}

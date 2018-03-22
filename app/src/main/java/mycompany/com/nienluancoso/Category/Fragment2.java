package mycompany.com.nienluancoso.Category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import mycompany.com.nienluancoso.Data.Api;
import mycompany.com.nienluancoso.Home.AgriObject;
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

    private ListView mKindList;
    private GridView mAgriList;
    private GridViewAdapter mGridViewAdapter;

    private Api api;
    private Retrofit retrofit;

    List<AgriObject> mAgriObjectList = new ArrayList<>();

    String[] kindData = {"Gạo","Lúa", "Rau","Củ","Quả", "Trái Cây"};


    public Fragment2() {
    }

    public static Fragment newIntance(){
        return new Fragment2();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,container,false);

        mKindList = (ListView)view.findViewById(R.id.listview_kind);
        mAgriList = (GridView) view.findViewById(R.id.gridview_ds_sp);

        initRetrofit();


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, kindData);

        mKindList.setAdapter(arrayAdapter);
        mKindList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Cate_KindList", "onClick: "+ position );
                //getArgiWithKind(String.valueOf(position));
            }
        });

        return view;
    }

    private void initRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    private void getArgiWithKind(String kind){


        Call<List<AgriObject>> call = api.getArgiWithKind(kind);

        call.enqueue(new Callback<List<AgriObject>>() {
            @Override
            public void onResponse(Call<List<AgriObject>> call, Response<List<AgriObject>> response) {


                mAgriObjectList.clear();
                if (response != null) {
                    for (int i=0; i<response.body().size(); i++ ){
                        mAgriObjectList.add(response.body().get(i));
                        Log.e("Home", mAgriObjectList.get(i).getNAME_AGRI());
                    }
                }


                mGridViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AgriObject>> call, Throwable t) {

            }
        });

    }

}

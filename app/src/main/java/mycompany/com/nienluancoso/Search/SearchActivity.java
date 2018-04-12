package mycompany.com.nienluancoso.Search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import mycompany.com.nienluancoso.Data.AgriItemObject;
import mycompany.com.nienluancoso.Data.Api;
import mycompany.com.nienluancoso.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admin on 3/27/2018.
 */

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private SearchView searchView;

    private Retrofit mRetrofit;
    private Api mApi;

    private ListView mLvHint;
    private RecyclerView mRecyResult;

    private List<AgriItemObject> mResultList = new ArrayList<>();
    private List<String> mHintList = new ArrayList<>();

    private ArrayAdapter<String> mHintAdapter;

    private RecySearchResultAdapter mSearchResultAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    finish();
                }
            });
        }

        //Init Result
        mRecyResult = (RecyclerView) findViewById(R.id.recycler_view_search_result);
        mSearchResultAdapter = new RecySearchResultAdapter(this,mResultList);
        mRecyResult.setAdapter(mSearchResultAdapter);
        mRecyResult.setLayoutManager(new LinearLayoutManager(this));

        //Init Hint
        mLvHint = (ListView) findViewById(R.id.lv_search_hint);
        mHintAdapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mHintList);
        mLvHint.setAdapter(mHintAdapter);
        mLvHint.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchView.setQuery(mHintList.get(i), true);
            }
        });

       initRetrofit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // thêm search vào vào action bar
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem itemSearch = menu.findItem(R.id.search_view);
        searchView = (SearchView) itemSearch.getActionView();
        //set OnQueryTextListener cho search view để thực hiện search theo text
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        mLvHint.setVisibility(View.GONE);
        mRecyResult.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mLvHint.setVisibility(View.VISIBLE);
        mRecyResult.setVisibility(View.GONE);
        getHint(newText);
        return true;
    }

    private void initRetrofit(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);
    }

    private void getDataResult(String keyword){
        mResultList.clear();
        Call<List<AgriItemObject>> call = mApi.searchAgric(keyword);

        call.enqueue(new Callback<List<AgriItemObject>>() {
            @Override
            public void onResponse(Call<List<AgriItemObject>> call, Response<List<AgriItemObject>> response) {

                if (response.isSuccessful()){
                    mResultList = response.body();
                }

                mSearchResultAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AgriItemObject>> call, Throwable t) {

            }
        });


    }


    private void getHint(String keyword){
        Call<List<String>> call = mApi.searchHintAgric(keyword);

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {

                if (response.isSuccessful()){
                    mHintList = response.body();
                }
                mHintAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });


    }


}

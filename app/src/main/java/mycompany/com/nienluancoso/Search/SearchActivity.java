package mycompany.com.nienluancoso.Search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mycompany.com.nienluancoso.Data.AgriObjectItem;
import mycompany.com.nienluancoso.Home.RecyItemAgriAdapter;
import mycompany.com.nienluancoso.R;

/**
 * Created by Admin on 3/27/2018.
 */

public class SearchActivity extends AppCompatActivity{

    private TextView mTenNS, mLoaiNS, mGia, mChitiet;
    private RecyclerView mRecyViewNSMuaNhieu;
    private RecyItemAgriAdapter recyItemAgriAdapter;
    private List<AgriObjectItem> agriObjectItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitet_ns);

        mTenNS = (TextView) findViewById(R.id.tv_ten_ns);
        mLoaiNS = (TextView) findViewById(R.id.tv_loai_ns);
        mGia = (TextView) findViewById(R.id.tv_gia);
        mChitiet = (TextView) findViewById(R.id.tv_nd_chitiet_ns);
        mRecyViewNSMuaNhieu = (RecyclerView) findViewById(R.id.recycler_view_hot);

        //Nong san mua nhieu
        mRecyViewNSMuaNhieu.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyItemAgriAdapter = new RecyItemAgriAdapter(this, agriObjectItems);
        mRecyViewNSMuaNhieu.setAdapter(recyItemAgriAdapter);

    }
}

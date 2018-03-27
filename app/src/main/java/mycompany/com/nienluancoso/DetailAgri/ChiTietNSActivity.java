package mycompany.com.nienluancoso.DetailAgri;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mycompany.com.nienluancoso.Data.AgriObject;
import mycompany.com.nienluancoso.Home.RecylerViewAdapter;
import mycompany.com.nienluancoso.R;

/**
 * Created by Admin on 3/27/2018.
 */

public class ChiTietNSActivity extends AppCompatActivity{

    private ImageView mHinhAnh;
    private TextView mTenNS, mLoaiNS, mGia, mChitiet;
    private RecyclerView mRecyViewNSMuaNhieu;
    private RecylerViewAdapter recylerViewAdapter ;
    private List<AgriObject> agriObjects  = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitet_ns);

        mHinhAnh = (ImageView) findViewById(R.id.img_agri);
        mTenNS = (TextView) findViewById(R.id.tv_ten_ns);
        mLoaiNS = (TextView) findViewById(R.id.tv_loai_ns);
        mGia = (TextView) findViewById(R.id.tv_gia);
        mChitiet = (TextView) findViewById(R.id.tv_nd_chitiet_ns);
        mRecyViewNSMuaNhieu = (RecyclerView) findViewById(R.id.recycler_view_hot);

        mRecyViewNSMuaNhieu.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recylerViewAdapter = new RecylerViewAdapter(this, agriObjects);
        mRecyViewNSMuaNhieu.setAdapter(recylerViewAdapter);




    }
}

package mycompany.com.nienluancoso.DetailAgri;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import mycompany.com.nienluancoso.Home.RecyItemAgriAdapter;
import mycompany.com.nienluancoso.R;

/**
 * Created by Admin on 3/27/2018.
 */

public class ChiTietNSActivity extends AppCompatActivity{

    private ImageView mImgHinh;
    private TextView mTvTenNS, mTvLoaiNS,mTvGia, mTvChiTiet;

    private RecyclerView mRecyHot;
    private RecyItemAgriAdapter recyItemAgriAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitet_ns);


        mImgHinh = (ImageView) findViewById(R.id.img_agri);
        mTvTenNS = (TextView) findViewById(R.id.tv_ten_ns);
        mTvLoaiNS = (TextView) findViewById(R.id.tv_loai_ns);
        mTvGia = (TextView) findViewById(R.id.tv_gia);
        mTvChiTiet = (TextView) findViewById(R.id.tv_nd_chitiet_ns);
        mRecyHot = (RecyclerView) findViewById(R.id.recycler_view_hot);


    }
}

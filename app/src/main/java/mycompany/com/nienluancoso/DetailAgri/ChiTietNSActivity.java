package mycompany.com.nienluancoso.DetailAgri;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mycompany.com.nienluancoso.Constant;
import mycompany.com.nienluancoso.Data.Api;
import mycompany.com.nienluancoso.Home.RecyItemAgriAdapter;
import mycompany.com.nienluancoso.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admin on 3/27/2018.
 */

public class ChiTietNSActivity extends AppCompatActivity {

    private static final String TAG = "Detail_Agric";
    private ImageView mImgHinh;
    private TextView mTvTenNS, mTvLoaiNS, mTvSLConLai, mTvGia, mTvChiTiet;
    private Button mBtnThemGioHang;

    private RecyclerView mRecyHot;
    private RecyItemAgriAdapter recyItemAgriAdapter;

    private Api api;
    private Retrofit retrofit;
    private AgriObjectDetail agriObjectDetail;
    private String mIdAgric;
    private int soLuongMua;
    private int soLuongConLai;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitet_ns);


        //Lấy dự liệu Intent từ Tạo TK
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mIdAgric = extras.getString("ID_AGRI");

        }

        initView();
        initRetrofit();

        getAgriDetail(mIdAgric);


    }

    private void initView() {
        mImgHinh = (ImageView) findViewById(R.id.img_agri);
        mTvTenNS = (TextView) findViewById(R.id.tv_ten_ns);
        mTvLoaiNS = (TextView) findViewById(R.id.tv_loai_ns);
        mTvSLConLai = (TextView) findViewById(R.id.tv_sl_conlai);
        mTvGia = (TextView) findViewById(R.id.tv_gia);
        mTvChiTiet = (TextView) findViewById(R.id.tv_nd_chitiet_ns);
        mBtnThemGioHang = (Button) findViewById(R.id.btn_them_sp_giohang);
        mRecyHot = (RecyclerView) findViewById(R.id.recycler_view_hot);
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }


    private void getAgriDetail(String idAgric) {
        Call<AgriObjectDetail> call = api.getArgiDetail(idAgric);
        call.enqueue(new Callback<AgriObjectDetail>() {
            @Override
            public void onResponse(Call<AgriObjectDetail> call, Response<AgriObjectDetail> response) {


                if (response.body().equals("")) {

                    agriObjectDetail = response.body();
                    Log.e(TAG, agriObjectDetail.getNAMEAGRI());

                    Picasso.get().load(Constant.IMAGE_SOURCE + agriObjectDetail.getIMGURLAGRI()).centerCrop().fit().into(mImgHinh);
                    mTvTenNS.setText(agriObjectDetail.getNAMEAGRI());
                    mTvLoaiNS.setText("Loại: " + agriObjectDetail.getNAMEKIND());
                    mTvSLConLai.setText("Số lượng còn lại: " + agriObjectDetail.getQUANTUMAGRI());
                    mTvGia.setText("Giá: " + agriObjectDetail.getPRICEAGRI());
                    mTvChiTiet.setText(agriObjectDetail.getDETAILAGRI());

                }

            }

            @Override
            public void onFailure(Call<AgriObjectDetail> call, Throwable t) {

            }
        });

    }

    private void event() {
        mBtnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void nhapSoLuongSNMua() {

        String arr[] = {"Gam", "Kg"};

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_doi_sdt, null);
        dialogBuilder.setView(dialogView);

        EditText mEdtSoluongMua = (EditText) dialogView.findViewById(R.id.edt_soLuong_mua);
        Spinner spin = (Spinner) findViewById(R.id.spinner_donvi_tinh);
        //Gán Data source (arr) vào Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spin.setAdapter(adapter);
        spin.setSelection(0);

        //Chuyển đổi đơn vị tính theo Gam
        if (spin.getSelectedItemPosition() == 1){
            soLuongMua = Integer.parseInt(mEdtSoluongMua.getText().toString()+"000");
        }else {
            soLuongMua = Integer.parseInt(mEdtSoluongMua.getText().toString());

        }

        //Kiểm tra số lượng mua cao hơn số lượng hàng tồn kho hay không
        if (soLuongMua>soLuongConLai) mEdtSoluongMua.setError("Mặt hàng không đủ số lượng để bán");
        else mEdtSoluongMua.setError(null);


        dialogBuilder.setTitle("Nhập vào số lượng cần mua");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {



            }
        });


        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog b = dialogBuilder.create();
        b.show();

    }

    private void themVaoGioHangThanhCong() {

    }


}

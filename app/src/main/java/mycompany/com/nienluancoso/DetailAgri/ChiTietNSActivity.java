package mycompany.com.nienluancoso.DetailAgri;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mycompany.com.nienluancoso.Constant;
import mycompany.com.nienluancoso.Data.AgriItemObject;
import mycompany.com.nienluancoso.Data.Api;
import mycompany.com.nienluancoso.Data.Local.DatabaseHelper;
import mycompany.com.nienluancoso.Home.RecyItemAgriAdapter;
import mycompany.com.nienluancoso.MainActivity;
import mycompany.com.nienluancoso.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChiTietNSActivity extends AppCompatActivity {

    private static final String TAG = "Detail_Agric";
    private ImageView mImgHinh;
    private TextView mTvTenNS, mTvLoaiNS, mTvSLConLai, mTvGia, mTvChiTiet;
    private Button mBtnThemGioHang;

    private RecyclerView mRecyHot;
    private RecyItemAgriAdapter mRecyItemAgriAdapterHot;
    private List<AgriItemObject> mAgriItemObjectListHot = new ArrayList<>();

    private Api api;
    private Retrofit retrofit;
    private AgriDetailObject agriDetailObject;
    private String mIdAgric;
    private int soLuongMua;
    private int soLuongConLai;
    Intent intent;
    private DatabaseHelper dbaseHelper;
    private ProgressDialog mProcessDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitet_ns);



        mProcessDialog = new ProgressDialog(this);
        mProcessDialog.setMessage("Loading...");

        intent = new Intent(this, ChiTietNSActivity.class);
        //Lấy dự liệu Intent từ Tạo TK
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mIdAgric = extras.getString("ID_AGRI");
            Log.e(TAG, "onCreate: "+ mIdAgric );
        }

        initView();
        initRetrofit();

        //Lấy dự liệu chi tiết nông sản dựa vào ID
        getAgriDetail(mIdAgric);
        getHotAgri();

        //Khời tạo CSQL
        dbaseHelper = new DatabaseHelper(this);

        event();

    }

    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Chi tiết nông sản");
        }

        mImgHinh = (ImageView) findViewById(R.id.img_agri);
        mTvTenNS = (TextView) findViewById(R.id.tv_ten_ns);
        mTvLoaiNS = (TextView) findViewById(R.id.tv_loai_ns);
        mTvSLConLai = (TextView) findViewById(R.id.tv_sl_conlai);
        mTvGia = (TextView) findViewById(R.id.tv_gia);
        mTvChiTiet = (TextView) findViewById(R.id.tv_nd_chitiet_ns);
        mBtnThemGioHang = (Button) findViewById(R.id.btn_them_sp_giohang);

        mRecyHot = (RecyclerView) findViewById(R.id.recycler_view_hot);
        RecyclerView.LayoutManager mLayoutManagerHot = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyHot.setLayoutManager(mLayoutManagerHot);
        mRecyItemAgriAdapterHot = new RecyItemAgriAdapter(this, mAgriItemObjectListHot);
        mRecyHot.setAdapter(mRecyItemAgriAdapterHot);

    }


    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    private void getAgriDetail(String idAgric) {
        Call<AgriDetailObject> call = api.getArgiDetail(idAgric);
        call.enqueue(new Callback<AgriDetailObject>() {
            @Override
            public void onResponse(Call<AgriDetailObject> call, Response<AgriDetailObject> response) {

                if (!response.body().equals("")) {

                    agriDetailObject = response.body();
                    Log.e(TAG, agriDetailObject.getNAMEAGRI());
                    Picasso.get().load(Constant.IMAGE_SOURCE + agriDetailObject.getIMGURLAGRI()).centerCrop().fit().into(mImgHinh);
                    mTvTenNS.setText(agriDetailObject.getNAMEAGRI());
                    mTvLoaiNS.setText("Loại: " + agriDetailObject.getNAMEKIND());

                    //Set số lượng còn lại vào "soLuongConLai"
                    soLuongConLai = Integer.parseInt(agriDetailObject.getAMOUNTAGRI());
                    mTvSLConLai.setText("Số lượng còn lại: " + soLuongConLai );
                    mTvGia.setText("Giá: " + agriDetailObject.getPRICEAGRI());
                    mTvChiTiet.setText(agriDetailObject.getDETAILAGRI());

                    if (mProcessDialog.isShowing()) {
                        mProcessDialog.dismiss();

                    }

                }

            }

            @Override
            public void onFailure(Call<AgriDetailObject> call, Throwable t) {

            }
        });

    }

    private void event() {
        mBtnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nhapSoLuongSNMua();
            }
        });

        mRecyItemAgriAdapterHot.setOnClickListener(new RecyItemAgriAdapter.onClickListener() {
            @Override
            public void onItemClick(int position, int idAgri) {
                intent.putExtra("ID_AGRI",idAgri+"");
                startActivity(intent);
                finish();
            }
        });

    }

    private void nhapSoLuongSNMua() {

        String arr[] = {"Gam", "Kg"};
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_donvi_tinh, null);
        dialogBuilder.setView(dialogView);

        final EditText mEdtSoluongMua = (EditText) dialogView.findViewById(R.id.edt_soLuong_mua);
        final Spinner spin = (Spinner) dialogView.findViewById(R.id.spinner_donvi_tinh);
        Button btnOK = (Button) dialogView.findViewById(R.id.btn_ok);
        Button btnHuy = (Button) dialogView.findViewById(R.id.btn_huy);


        //Gán Data source (arr) vào Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spin.setAdapter(adapter);
        spin.setSelection(0);

        dialogBuilder.setTitle("Nhập vào số lượng cần mua");

        final AlertDialog b = dialogBuilder.create();
        b.show();

        //Sự kiện các nút trên Dialog
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chuyển đổi đơn vị tính theo Gam
                if (spin.getSelectedItemPosition() == 1) {
                    soLuongMua = Integer.parseInt(mEdtSoluongMua.getText().toString() + "000");
                } else {
                    soLuongMua = Integer.parseInt(mEdtSoluongMua.getText().toString());

                }
                //Kiểm tra số lượng mua cao hơn số lượng hàng tồn kho hay không
                if (soLuongMua > soLuongConLai)
                    mEdtSoluongMua.setError("Mặt hàng không đủ số lượng để bán");
                else {
                    mEdtSoluongMua.setError(null);
                    themVaoGioHang(String.valueOf(soLuongMua));
                    Toast.makeText(ChiTietNSActivity.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
                    b.dismiss();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra(Constant.MAIN_POSITION, 2);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

    }

    private void themVaoGioHang(String soLuongMua) {
       if (null == dbaseHelper.getOrder()){

           Date currentTime = Calendar.getInstance().getTime();
           dbaseHelper.insertOrder(currentTime.getTime()+"");
       }
        Log.e(TAG, "themVaoGioHang: "+ mIdAgric );
       dbaseHelper.insertAgriOnOrder(mIdAgric,soLuongMua);

    }

    private void getHotAgri(){
        //Load Nông sản hot
        Call<List<AgriItemObject>> callHot = api.getHotAgri();
        callHot.enqueue(new Callback<List<AgriItemObject>>() {
            @Override
            public void onResponse(Call<List<AgriItemObject>> call, Response<List<AgriItemObject>> response) {
                mAgriItemObjectListHot.clear();
                if (response != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        mAgriItemObjectListHot.add(response.body().get(i));
                    }
                }
                mRecyItemAgriAdapterHot.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AgriItemObject>> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Lỗi ! Không thể truy cập đến server", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

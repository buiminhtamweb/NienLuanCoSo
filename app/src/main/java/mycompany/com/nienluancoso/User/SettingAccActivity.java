package mycompany.com.nienluancoso.User;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import mycompany.com.nienluancoso.Constant;
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

public class SettingAccActivity extends AppCompatActivity {

    Retrofit retrofit;
    Api api;
    private CircleImageView mImgAnhDaiDien;
    private Button mBtnHoTen, mBtnDoiMK, mBtnNamSinh, mBtnGioiTinh, mBtnSDT, mBtnDiaChi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_acc);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        mImgAnhDaiDien = (CircleImageView) findViewById(R.id.img_anhdaidien);
        mBtnHoTen = (Button) findViewById(R.id.btn_td_hoten);
        mBtnDoiMK = (Button) findViewById(R.id.btn_td_matkhau);
        mBtnNamSinh = (Button) findViewById(R.id.btn_td_namsinh);
        mBtnGioiTinh = (Button) findViewById(R.id.btn_td_gioitinh);
        mBtnSDT = (Button) findViewById(R.id.btn_td_sdt);
        mBtnDiaChi = (Button) findViewById(R.id.btn_td_diachi);

        eventClick();
        initRetrofit();
        loadData();


    }

    private void eventClick() {
        mImgAnhDaiDien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        mBtnHoTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doiHoTen();
            }
        });

        mBtnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doiMatKhau();
            }
        });

        mBtnNamSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doiNamSinh();
            }
        });

        mBtnGioiTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             doiGioiTinh();
            }
        });

        mBtnSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doiSDT();
            }
        });

        mBtnDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doiDiaChi();
            }
        });
    }

    private void doiHoTen() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_doi_hoten, null);
        dialogBuilder.setView(dialogView);

        EditText mEdtHoTen = (EditText) dialogView.findViewById(R.id.edt_hoten);

        dialogBuilder.setTitle("Đổi họ tên");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
            }
        });


        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog b = dialogBuilder.create();
        b.show();

    }

    private void doiMatKhau() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_doi_matkhau, null);
        dialogBuilder.setView(dialogView);

        EditText mEdtMatKhauCu = (EditText) dialogView.findViewById(R.id.edt_mk_cu);
        EditText mEdtMatKhau = (EditText) dialogView.findViewById(R.id.edt_mk);
        EditText mEdtNhapLaiMK = (EditText) dialogView.findViewById(R.id.edt_nhaplaimk);


        dialogBuilder.setTitle("Đổi mật khẩu");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog b = dialogBuilder.create();
        b.show();

    }

    private void doiNamSinh() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_datepicker, null);
        dialogBuilder.setView(dialogView);

        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);

        dialogBuilder.setTitle("Đổi mật khẩu");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mBtnNamSinh.setText(datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear());
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void doiGioiTinh() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_datepicker, null);
        dialogBuilder.setView(dialogView);

        String sex[]={
                "Chọn giới tính",
                "Nam",
                "Nữ",
                "Khác"};
        Spinner mSpinGioiTinh = (Spinner) findViewById(R.id.spinner_gioitinh);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sex);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mSpinGioiTinh.setAdapter(adapter);
        mSpinGioiTinh.setSelection(0);

        dialogBuilder.setTitle("Giới tính");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void doiSDT() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_doi_sdt, null);
        dialogBuilder.setView(dialogView);

        EditText mEdtSDT = (EditText) dialogView.findViewById(R.id.edt_sdt);

        dialogBuilder.setTitle("Đổi Số điện thoại");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
            }
        });


        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog b = dialogBuilder.create();
        b.show();

    }

    private void doiDiaChi() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_doi_diachi, null);
        dialogBuilder.setView(dialogView);

        EditText mEdtHoTen = (EditText) dialogView.findViewById(R.id.edt_diachi);

        dialogBuilder.setTitle("Đổi địa chỉ");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
            }
        });


        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog b = dialogBuilder.create();
        b.show();

    }

    //Data
    private void loadData() {
        //Lấy ID từ SharedPreferences
        SharedPreferences mSPre;
        mSPre = getSharedPreferences(Constant.SPRE_NAME, MODE_PRIVATE);
        String idUsername = mSPre.getString(Constant.USERNAME_CUS, "");

        //Lấy thông tin từ HOST
        Call<UserCusObject> call = api.getInfoUser(idUsername);
        call.enqueue(new Callback<UserCusObject>() {
            @Override
            public void onResponse(Call<UserCusObject> call, Response<UserCusObject> response) {

                if (response.isSuccessful()) {
                    Picasso.get().load(Constant.IMAGE_SOURCE + response.body().getIMGURLCUS()).fit().centerCrop().into(mImgAnhDaiDien);
                    mBtnHoTen.setText(response.body().getFULLNAMECUS());
                    mBtnGioiTinh.setText(response.body().getSEX());
                    mBtnNamSinh.setText(response.body().getBIRTHDAY());
                    mBtnSDT.setText(response.body().getTELCUS());
                    mBtnDiaChi.setText(response.body().getADDRESSCUS());
                }
            }

            @Override
            public void onFailure(Call<UserCusObject> call, Throwable t) {

            }
        });


    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }


}

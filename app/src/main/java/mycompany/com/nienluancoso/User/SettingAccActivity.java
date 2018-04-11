package mycompany.com.nienluancoso.User;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Admin on 3/27/2018.
 */

public class SettingAccActivity extends AppCompatActivity {

    private final int FULLNAME = 1;
    private final int BIRTHDAY = 2;
    private final int SEX = 3;
    private final int TEL_CUS = 4;
    private final int ADDRESS_CUS = 5;
    private final int IMG_URL_CUS = 6;

    private Retrofit mRetrofitGetData, mRetrofitUpdate;
    private Api mApiGetData, mApiUpdate;
    private CircleImageView mImgAnhDaiDien;
    private Button mBtnHoTen, mBtnDoiMK, mBtnNamSinh, mBtnGioiTinh, mBtnSDT, mBtnDiaChi;

    private String mIdUsername;

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
                doiHoTen(mBtnHoTen.getText().toString());
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
                doiGioiTinh(mBtnGioiTinh.getText().toString());
            }
        });

        mBtnSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doiSDT(mBtnSDT.getText().toString());
            }
        });

        mBtnDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doiDiaChi(mBtnDiaChi.getText().toString());
            }
        });
    }

    private void doiHoTen(String oldFullName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_doi_hoten, null);
        dialogBuilder.setView(dialogView);

        final EditText mEdtHoTen = (EditText) dialogView.findViewById(R.id.edt_hoten);
        mEdtHoTen.setText(oldFullName);

        dialogBuilder.setTitle("Đổi họ tên");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                updateUser(FULLNAME, mEdtHoTen.getText().toString());
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

        final EditText mEdtMatKhauCu = (EditText) dialogView.findViewById(R.id.edt_mk_cu);
        final EditText mEdtMatKhau = (EditText) dialogView.findViewById(R.id.edt_mk);
        final EditText mEdtNhapLaiMK = (EditText) dialogView.findViewById(R.id.edt_nhaplaimk);

        dialogBuilder.setTitle("Đổi mật khẩu");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mEdtMatKhau.getText().toString();
                if (TextUtils.equals(mEdtMatKhau.getText().toString(), mEdtNhapLaiMK.getText().toString())) {
                    changePassWd(mEdtMatKhauCu.getText().toString(), mEdtNhapLaiMK.getText().toString());
                } else mEdtNhapLaiMK.setError("Mật khẩu mới chưa khớp");
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

        dialogBuilder.setTitle("Đổi năm sinh");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
//                mBtnNamSinh.setText(datePicker.getDayOfMonth() + "-" + datePicker.getMonth() + "-" + datePicker.getYear());
                updateUser(BIRTHDAY, datePicker.getDayOfMonth() + "-" + datePicker.getMonth() + "-" + datePicker.getYear());

            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void doiGioiTinh(String oldSex) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_doi_gioitinh, null);
        dialogBuilder.setView(dialogView);

        String sex[] = {
                "Nam",
                "Nữ",
                "Khác"};
        final Spinner spinGioiTinh = (Spinner) findViewById(R.id.spinner_gioitinh);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sex);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinGioiTinh.setAdapter(adapter);

        for (int i = 0; i < 3; i++) {
            if (sex[i].equals(oldSex)) {
                spinGioiTinh.setSelection(i);
            }
        }

        dialogBuilder.setTitle("Giới tính");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                updateUser(SEX, spinGioiTinh.getSelectedItem().toString());
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void doiSDT(String oldTel) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_doi_sdt, null);
        dialogBuilder.setView(dialogView);

        final EditText mEdtSDT = (EditText) dialogView.findViewById(R.id.edt_sdt);
        mEdtSDT.setText(oldTel);

        dialogBuilder.setTitle("Đổi Số điện thoại");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                updateUser(TEL_CUS, mEdtSDT.getText().toString());
            }
        });


        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog b = dialogBuilder.create();
        b.show();

    }

    private void doiDiaChi(String oldAddress) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_doi_diachi, null);
        dialogBuilder.setView(dialogView);

        final EditText mEdtAddress = (EditText) dialogView.findViewById(R.id.edt_diachi);
        mEdtAddress.setText(oldAddress);

        dialogBuilder.setTitle("Đổi địa chỉ");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                updateUser(ADDRESS_CUS, mEdtAddress.getText().toString());
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
        mIdUsername = mSPre.getString(Constant.USERNAME_CUS, "");

        //Lấy thông tin từ HOST
        Call<UserCusObject> call = mApiGetData.getInfoUser(mIdUsername);
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
        mRetrofitGetData = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiGetData = mRetrofitGetData.create(Api.class);


        mRetrofitUpdate = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        mApiUpdate = mRetrofitGetData.create(Api.class);
    }

    private void updateUser(int type, String data) {

        Call<String> call = mApiUpdate.updateUser(mIdUsername, type, data);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Snackbar.make(mBtnDiaChi, "Đã cập nhật thành công", Snackbar.LENGTH_LONG).show();
                } else Snackbar.make(mBtnDiaChi, "Chưa cập nhật được", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Snackbar.make(mBtnDiaChi, "Không thể kết nối đến máy chủ", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void changePassWd(String oldPass, String newConfirmPass) {


        Call<String> call = mApiUpdate.getChangePassUser(mIdUsername, oldPass, newConfirmPass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Snackbar.make(mBtnDiaChi, "Đã cập nhật thành công", Snackbar.LENGTH_LONG).show();
                } else Snackbar.make(mBtnDiaChi, "Chưa cập nhật được", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Snackbar.make(mBtnDiaChi, "Không thể kết nối đến máy chủ", Snackbar.LENGTH_LONG).show();
            }
        });

    }


}

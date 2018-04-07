package mycompany.com.nienluancoso.Signin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mycompany.com.nienluancoso.Data.Api;
import mycompany.com.nienluancoso.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Admin on 3/27/2018.
 */

public class TaoTaiKhoanMoiActivity extends AppCompatActivity {


    private EditText mEdtTenDangNhap, mEdtMatKhau, mEdtNhapLaiMK;

    private Retrofit retrofit;
    private Api api;
    Intent intent;

    private ProgressDialog mProcessDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taotaikhoanmoi);

        toolbarView();

        mProcessDialog = new ProgressDialog(TaoTaiKhoanMoiActivity.this);
        mProcessDialog.setMessage("Loading...");
        intent = new Intent(this, ThemThongTinDKActivity.class);

        mEdtTenDangNhap = (EditText) findViewById(R.id.edt_tendangnhap);
        mEdtMatKhau = (EditText) findViewById(R.id.edt_mk);
        mEdtNhapLaiMK = (EditText) findViewById(R.id.edt_nhaplaimk);
    }

    public void btnTiepTuc(View view) {

        mProcessDialog.show();
        if (isCorrecPasswd()){
            checkHaveUserName(mEdtTenDangNhap.getText().toString(),mEdtNhapLaiMK.getText().toString() );
        }
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    private void checkHaveUserName(final String userName, final String passWd) {
        initRetrofit();

        Call<String> call = api.checkUserName(userName);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                    if(response.body().equals("true")) {
                        mEdtTenDangNhap.setError("Tên đăng nhập đã có người sử dụng");

                    }else {
                        intent.putExtra("USERNAME_CUS", userName);
                        intent.putExtra("PASSWORD_CUS", passWd);
                        startActivity(intent);
                        finish();
                    }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isCorrecPasswd(){
        String password = mEdtMatKhau.getText().toString();
        if (!isPasswordValid(password)){
            mEdtMatKhau.setError("Mật khẩu chưa đúng định dạng");
            return false;
        }else if (!mEdtMatKhau.getText().toString().equals(mEdtNhapLaiMK.getText().toString())){
            mEdtMatKhau.setError(null);
            mEdtNhapLaiMK.setError("Mật khẩu nhập lại chưa đúng");
            return false;
        }
        else {
            mEdtNhapLaiMK.setError(null);
            return true;
        }
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }


    private void toolbarView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mProcessDialog.isShowing()) {
            mProcessDialog.dismiss();

        }
    }


}

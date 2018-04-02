package mycompany.com.nienluancoso.Signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import mycompany.com.nienluancoso.Data.AgriObject;
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

public class TaoTaiKhoanMoiActivity extends AppCompatActivity {

    private EditText mEdtTenDangNhap, mEdtMatKhau, mEdtNhapLaiMK;

    private Retrofit retrofit;
    private Api api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taotaikhoanmoi);

        mEdtTenDangNhap = (EditText) findViewById(R.id.edt_tendangnhap);
        mEdtMatKhau = (EditText) findViewById(R.id.edt_mk);
        mEdtNhapLaiMK = (EditText) findViewById(R.id.edt_nhaplaimk);
    }

    public void btnTiepTuc(View view) {
        if (!isHaveUserName(mEdtTenDangNhap.getText().toString()) && isCorrecPasswd()){
            Intent intent = new Intent(this, ThemThongTinDKActivity.class);
            intent.putExtra("USERNAME_CUS", mEdtTenDangNhap.getText().toString());
            intent.putExtra("PASSWORD_CUS", mEdtNhapLaiMK.getText().toString());
            startActivity(intent);
        }
    }


    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    private boolean isHaveUserName(String userName) {
        final boolean[] isHave = {true};

        initRetrofit();

        Call<String> call = api.checkUserName(userName);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("TaoTK", response.body());
                    if(response.body().equals("true")) {
                        mEdtTenDangNhap.setError("Tên đăng nhập đã có người sử dụng");
                        isHave[0] = true;
                    }else {
                        isHave[0] = false;
                    }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });

        return isHave[0];

    }

    private boolean isCorrecPasswd(){
        String password = mEdtNhapLaiMK.getText().toString();
        if (mEdtMatKhau.getText().toString().equals(mEdtNhapLaiMK.getText().toString())&&isPasswordValid(password)){
            return true;
        }else {
            mEdtNhapLaiMK.setError("Mật khẩu chưa đúng định dạng");
            return false;
        }
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }
}

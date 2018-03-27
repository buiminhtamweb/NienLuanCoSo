package mycompany.com.nienluancoso.Signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import mycompany.com.nienluancoso.R;

/**
 * Created by Admin on 3/27/2018.
 */

public class TaoTaiKhoanMoiActivity extends AppCompatActivity {

    private EditText mEdtTenDangNhap, mEdtMatKhau, mEdtNhapLaiMK;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taotaikhoanmoi);

        mEdtTenDangNhap = (EditText) findViewById(R.id.edt_tendangnhap);
        mEdtMatKhau  = (EditText) findViewById(R.id.edt_mk);
        mEdtNhapLaiMK  = (EditText) findViewById(R.id.edt_nhaplaimk);
    }

    public void btnTiepTuc(View view) {
        Intent intent = new Intent(this, ThemThongTinDKActivity.class);
        startActivity(intent);

    }
}

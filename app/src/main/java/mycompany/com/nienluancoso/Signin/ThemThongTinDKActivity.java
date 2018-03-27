package mycompany.com.nienluancoso.Signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;
import mycompany.com.nienluancoso.MainActivity;
import mycompany.com.nienluancoso.R;

/**
 * Created by Admin on 3/27/2018.
 */

public class ThemThongTinDKActivity extends AppCompatActivity {

    private CircleImageView mImgAnhDaiDien;
    private EditText mEdtHoTen,mEdtSDT, mEdtDiaChi;
    private CheckBox mCheckNam, mCheckNu;
    private Button mBtnNgaySinh;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themthongtindk);

        mImgAnhDaiDien = (CircleImageView) findViewById(R.id.img_anhdaidien);
        mEdtHoTen = (EditText) findViewById(R.id.edt_hoten);
        mEdtSDT = (EditText) findViewById(R.id.edt_sdt);
        mEdtDiaChi = (EditText) findViewById(R.id.edt_diachi);

        mCheckNam = (CheckBox) findViewById(R.id.checkbox_nam);
        mCheckNu = (CheckBox) findViewById(R.id.checkbox_nu);


    }

    public void btnDangKy(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}

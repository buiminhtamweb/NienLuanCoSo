package mycompany.com.nienluancoso.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import de.hdodenhof.circleimageview.CircleImageView;
import mycompany.com.nienluancoso.R;

/**
 * Created by Admin on 3/27/2018.
 */

public class CaiDatTKActivity extends AppCompatActivity {

    private CircleImageView mImgAnhDaiDien;
    private Button mBtnHoTen, mBtnDoiMK, mBtnNamSinh, mBtnSDT, mBtnDiaChi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caidat_tk);

        mImgAnhDaiDien = (CircleImageView) findViewById(R.id.img_anhdaidien);
        mBtnHoTen = (Button) findViewById(R.id.btn_td_hoten);
        mBtnDoiMK = (Button) findViewById(R.id.btn_td_matkhau);
        mBtnNamSinh = (Button) findViewById(R.id.btn_td_namsinh);
        mBtnSDT = (Button) findViewById(R.id.btn_td_sdt);
        mBtnDiaChi = (Button) findViewById(R.id.btn_td_diachi);

    }


}

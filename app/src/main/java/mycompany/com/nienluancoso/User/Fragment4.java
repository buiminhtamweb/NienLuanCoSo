package mycompany.com.nienluancoso.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import mycompany.com.nienluancoso.R;

/**
 * Created by buimi on 1/29/2018.
 */

public class Fragment4 extends Fragment {



    LinearLayout linearLayout;
    private Button mBtnYeuThich, mBtnLichSuDatHang, mBtnCaiDatTK, mBtnCaiDat, mBtnHuongDanSuDung, mBtnPhanHoi;

    public Fragment4() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4,container,false);

        linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout_tk);
        mBtnLichSuDatHang = (Button) view.findViewById(R.id.btn_lichsudathang);
        mBtnYeuThich = (Button) view.findViewById(R.id.btn_danhsachyeuthich);
        mBtnCaiDatTK = (Button) view.findViewById(R.id.btn_caidat_tk);
        mBtnCaiDat = (Button) view.findViewById(R.id.btn_caidat_app);
        mBtnHuongDanSuDung = (Button) view.findViewById(R.id.btn_huongdan_sudung);
        mBtnPhanHoi = (Button) view.findViewById(R.id.btn_phanhoi_ungdung);


        return view;
    }

    private void kiemTraDangNhap(){

        linearLayout.setVisibility(View.GONE);

    }






}

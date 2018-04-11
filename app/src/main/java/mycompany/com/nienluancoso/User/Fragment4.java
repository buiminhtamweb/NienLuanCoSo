package mycompany.com.nienluancoso.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import mycompany.com.nienluancoso.Constant;
import mycompany.com.nienluancoso.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by buimi on 1/29/2018.
 */

public class Fragment4 extends Fragment {


    LinearLayout linearLayout;
    private Button mBtnOrderProcessing, mBtnBill, mBtnSettingAcc, mBtnCaiDat, mBtnPoliciesAndTerms, mBtnFeedback;

    public Fragment4() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4, container, false);

        linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout_tk);
        mBtnBill = (Button) view.findViewById(R.id.btn_bill);
        mBtnOrderProcessing = (Button) view.findViewById(R.id.btn_order_processing);
        mBtnSettingAcc = (Button) view.findViewById(R.id.btn_setting_acc);
//        mBtnCaiDat = (Button) view.findViewById(R.id.btn_caidat_app);
        mBtnPoliciesAndTerms = (Button) view.findViewById(R.id.btn_policies_terms);
        mBtnFeedback = (Button) view.findViewById(R.id.btn_feed_back);


        //Sự kiện click
        mBtnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BillActivity.class));
            }
        });

        mBtnOrderProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OrderProcessingActivity.class));
            }
        });

        mBtnSettingAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingAccActivity.class));
            }
        });


        kiemTraDangNhap();

        return view;
    }


    private void kiemTraDangNhap() {
        SharedPreferences mSPre;
        mSPre = getActivity().getSharedPreferences(Constant.SPRE_NAME, MODE_PRIVATE);
        if (mSPre.getString(Constant.USERNAME_CUS, "").equals("")) {
            linearLayout.setVisibility(View.GONE);
        }
    }


}

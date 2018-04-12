package mycompany.com.nienluancoso.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import mycompany.com.nienluancoso.Constant;
import mycompany.com.nienluancoso.Data.Api;
import mycompany.com.nienluancoso.Data.Local.DatabaseHelper;
import mycompany.com.nienluancoso.R;
import mycompany.com.nienluancoso.Signin.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by buimi on 1/29/2018.
 */

public class Fragment4 extends Fragment {

    private Button mBtnOrderProcessing, mBtnBill, mBtnSettingAcc, mBtnPoliciesAndTerms, mBtnFeedback, mBtnDangXuat;

    private ImageView mImageViewHeader, mImgAnhDaiDien;

    private TextView mTvFullNameUser;

    private DatabaseHelper mDBaseHelper;

    private SharedPreferences mSPre;
    private SharedPreferences.Editor mSP_Edit;

    private Retrofit mRetrofitGetData;
    private Api mApiGetData;

    private UserCusObject userCusObject = new UserCusObject();

    public Fragment4() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4, container, false);

        mImgAnhDaiDien = (CircleImageView) view.findViewById(R.id.img_anhdaidien);
        mTvFullNameUser = (TextView) view.findViewById(R.id.tv_hoten_user);
        mBtnBill = (Button) view.findViewById(R.id.btn_bill);
        mBtnOrderProcessing = (Button) view.findViewById(R.id.btn_order_processing);
        mBtnSettingAcc = (Button) view.findViewById(R.id.btn_setting_acc);
        mBtnPoliciesAndTerms = (Button) view.findViewById(R.id.btn_policies_terms);
        mBtnFeedback = (Button) view.findViewById(R.id.btn_feed_back);
        mBtnDangXuat = (Button) view.findViewById(R.id.btn_login);

        mImageViewHeader = (ImageView) view.findViewById(R.id.img_nav_header);
        Picasso.get().load(R.drawable.backround).fit().centerCrop().into(mImageViewHeader);

        mDBaseHelper = new DatabaseHelper(getActivity());
        mSPre = getActivity().getSharedPreferences(Constant.SPRE_NAME, MODE_PRIVATE);

        //Sự kiện click
        eventClick();

        initRetrofit();
        loadData();

        return view;
    }


    private void eventClick() {
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

        mBtnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogDangXuat();
            }
        });

        mBtnPoliciesAndTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PoliciesAndTermsActivity.class));
            }
        });

        mBtnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "tamb1401088@student.ctu.edu.vn", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ứng dụng cửa hàng nông sản");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Xin chào Tâm !");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
    }


    private void loadData() {
        //Lấy ID từ SharedPreferences
        SharedPreferences mSPre;
        mSPre = getActivity().getSharedPreferences(Constant.SPRE_NAME, MODE_PRIVATE);
        String mIdUsername = mSPre.getString(Constant.USERNAME_CUS, "");

        //Lấy thông tin từ HOST
        Call<UserCusObject> call = mApiGetData.getInfoUser(mIdUsername);
        call.enqueue(new Callback<UserCusObject>() {
            @Override
            public void onResponse(Call<UserCusObject> call, Response<UserCusObject> response) {

                if (response.isSuccessful()) {

                    Log.e("USER", "onResponse: " + Constant.IMAGE_SOURCE + response.body().getIMGURLCUS());
                    Picasso.get().load(Constant.IMAGE_SOURCE + response.body().getIMGURLCUS()).fit().centerCrop().into(mImgAnhDaiDien);
                    mTvFullNameUser.setText("Xin chào " + response.body().getFULLNAMECUS() + " !");
                }


            }

            @Override
            public void onFailure(Call<UserCusObject> call, Throwable t) {

            }
        });

    }

    public void showAlertDialogDangXuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có muốn đăng xuất không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Hủy", null);
        builder.setNegativeButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Xóa các giỏ hàng và tài khoản đăng nhập
                mDBaseHelper.deleteAllOrder();
                mSP_Edit = mSPre.edit();
                mSP_Edit.clear();
                mSP_Edit.apply();

                //Chuyển sang màn hình đăng nhập
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void initRetrofit() {
        mRetrofitGetData = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiGetData = mRetrofitGetData.create(Api.class);


    }

}

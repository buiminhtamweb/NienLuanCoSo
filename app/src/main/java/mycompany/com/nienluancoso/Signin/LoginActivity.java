package mycompany.com.nienluancoso.Signin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import mycompany.com.nienluancoso.Constant;
import mycompany.com.nienluancoso.Data.Api;
import mycompany.com.nienluancoso.MainActivity;
import mycompany.com.nienluancoso.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mUserNameView;
    private EditText mPasswordView;

    private TextView mTvLogo;
    private ImageView mImgBackround;

    private Retrofit retrofit;
    private Api api;

    private SharedPreferences mSPre;
    private SharedPreferences.Editor mSP_Edit;

    private AlertDialog mAlertDialog;
    private AlertDialog.Builder mBuilderDialog;

    private Button mEmailSignInButton;


    private Intent intent;
    private ProgressDialog mProcessDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intent = new Intent(this, MainActivity.class);

        initDialog();

        mTvLogo = (TextView) findViewById(R.id.tv_logo);
        mImgBackround = (ImageView) findViewById(R.id.img_backround);

        mUserNameView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);


        event();

        //Dặt hính nền
        Picasso.get().load(R.drawable.backround).fit().centerCrop().into(mImgBackround);

        //Check Data Sign In
        mSPre = getSharedPreferences(Constant.SPRE_NAME, MODE_PRIVATE);
        if (mSPre.getBoolean(Constant.IS_SIGNIN, false)) {
            String userName = mSPre.getString(Constant.USERNAME_CUS, "");
            String passwd = mSPre.getString(Constant.PASSWORD_CUS, "");
            mProcessDialog.show();
            processLogin(userName, passwd);
        }

    }


    private boolean isNotNull() {
        boolean isNotNull = true;

        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            isNotNull = false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            isNotNull = false;
        }

        return isNotNull;

    }

    private boolean isPasswordValid(String password) {
        return password.length() > 2;
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    private void processLogin(final String userName, final String passWd) {
        initRetrofit();
        Call<String> call = api.login(userName, passWd);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("TaoTK", response.body());
                if (response.body().equals("true")) {
                    //Save Data to SharePre
                    mSP_Edit = mSPre.edit();
                    mSP_Edit.putString(Constant.USERNAME_CUS, userName);
                    mSP_Edit.putString(Constant.PASSWORD_CUS, passWd);
                    mSP_Edit.putBoolean(Constant.IS_SIGNIN, true);
                    mSP_Edit.commit();


                    //Set thời gian chờ xử lý 2s
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }

                    startActivity(intent);
                    finish();
                } else {

                    mProcessDialog.dismiss();

                    mSP_Edit = mSPre.edit();
                    mSP_Edit.clear().commit();
                    Toast.makeText(getBaseContext(), "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                mProcessDialog.dismiss();
                Toast.makeText(getBaseContext(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                mAlertDialog.show();

            }
        });


    }

    //Sự kiện click ở các nút
    public void skipSignin(View view) {
        startActivity(intent);
        finish();
    }

    public void dangKy(View view) {
        Intent intent = new Intent(this, TaoTaiKhoanMoiActivity.class);
        startActivity(intent);

    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mProcessDialog.isShowing()) {
            mProcessDialog.dismiss();

        }
    }


    private void event() {

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNotNull()) {
                    mProcessDialog.show();
                    processLogin(mUserNameView.getText().toString(), mPasswordView.getText().toString());
                }
            }
        });

//        mUserNameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    mTvLogo.setVisibility(View.GONE);
//                } else {
//                    mTvLogo.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//
//        mPasswordView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    mTvLogo.setVisibility(View.GONE);
//                } else {
//                    mTvLogo.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(mUserNameView.getWindowToken(), 0);
//        imm.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);

    }

    private void initDialog() {
        mProcessDialog = new ProgressDialog(this);
        mProcessDialog.setMessage("Đang đăng nhập...");

        mBuilderDialog = new AlertDialog.Builder(this);
        mBuilderDialog.setTitle("Cảnh báo!");
        mBuilderDialog.setCancelable(false);
        mBuilderDialog.setMessage("Bạn phải đăng nhập mới sử dụng được ứng dụng này!");
        mBuilderDialog.setPositiveButton("Cài dặt Wifi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
            }
        });
        mBuilderDialog.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        mAlertDialog = mBuilderDialog.create();
    }

    // Checks whether a hardware keyboard is available
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {


            Toast.makeText(this, "Mở Phím", Toast.LENGTH_SHORT).show();
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {

        }
    }
}


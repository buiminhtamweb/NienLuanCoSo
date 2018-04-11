package mycompany.com.nienluancoso.Signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private Retrofit retrofit;
    private Api api;

    private SharedPreferences mSPre;
    private SharedPreferences.Editor mSP_Edit;

    private String mUsername, mPasswd;
    private Intent intent;
    private ProgressDialog mProcessDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intent = new Intent(this, MainActivity.class);
        mProcessDialog = new ProgressDialog(this);
        mProcessDialog.setMessage("Đang đăng nhập...");

        //Check Data Sign In
        mSPre = getSharedPreferences(Constant.SPRE_NAME, MODE_PRIVATE);

        if (mSPre.getBoolean(Constant.IS_SIGNIN, false)) {
            mUsername = mSPre.getString(Constant.USERNAME_CUS, "");
            mPasswd = mSPre.getString(Constant.PASSWORD_CUS, "");
            //Show process Dialog
            mProcessDialog.show();

            processLogin(mUsername, mPasswd);
        }


        // Set up the login form.
        mUserNameView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {

                    return true;
                }
                return false;
            }
        });


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNotNull()) {

                    mProcessDialog.show();

                    processLogin(mUserNameView.getText().toString(), mPasswordView.getText().toString());
                }
            }
        });

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

                    mSP_Edit = mSPre.edit();
                    mSP_Edit.clear().commit();
                    Toast.makeText(getBaseContext(), "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });


    }

    //Sự kiện click ở các nút
    public void skipSignin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
}


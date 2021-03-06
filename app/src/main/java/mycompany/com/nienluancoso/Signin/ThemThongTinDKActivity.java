package mycompany.com.nienluancoso.Signin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import mycompany.com.nienluancoso.Constant;
import mycompany.com.nienluancoso.Data.Api;
import mycompany.com.nienluancoso.MainActivity;
import mycompany.com.nienluancoso.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Admin on 3/27/2018.
 */

public class ThemThongTinDKActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final String TAG = "ThemTT_DK";

    private static int RESULT_LOAD_IMG = 1;
    //UpLoad Image
    ProgressDialog prgDialog;
    String encodedString;
    RequestParams params = new RequestParams();
    String imgPath, fileName;
    Bitmap bitmap;
    String userName, passWD;
    Intent intent;
    private CircleImageView mImgAnhDaiDien;
    private EditText mEdtHoTen, mEdtSDT, mEdtDiaChi;
    private CheckBox mCheckNam, mCheckNu;
    private EditText mBtnNamSinh;
    private Spinner mSpinGioiTinh;

    private Retrofit retrofit;
    private Api api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themthongtindk);

        toolbarView();

        mImgAnhDaiDien = (CircleImageView) findViewById(R.id.img_anhdaidien);
        mEdtHoTen = (EditText) findViewById(R.id.edt_hoten);
        mEdtSDT = (EditText) findViewById(R.id.edt_sdt);
        mEdtDiaChi = (EditText) findViewById(R.id.edt_diachi);

//        mCheckNam = (CheckBox) findViewById(R.id.checkbox_nam);
//        mCheckNu = (CheckBox) findViewById(R.id.checkbox_nu);

        //Giới tính
        String sex[]={
                "Chọn giới tính",
                "Nam",
                "Nữ",
                "Khác"};
        mSpinGioiTinh=(Spinner) findViewById(R.id.spinner_gioitinh);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sex);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mSpinGioiTinh.setAdapter(adapter);
        mSpinGioiTinh.setSelection(0);

        mBtnNamSinh = (EditText) findViewById(R.id.btn_namsinh);

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Loading...");
        // Set Cancelable as False
//        prgDialog.setCancelable(false);

        intent = new Intent(this, MainActivity.class);

        checkPermission();
        initRetrofit();

        //Lấy dự liệu Intent từ Tạo TK
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString("USERNAME_CUS");
            passWD = extras.getString("PASSWORD_CUS");

        }

        mImgAnhDaiDien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImagefromGallery();
            }
        });
        mBtnNamSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNamSinh();
            }
        });


    }

    private void chonNamSinh() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_datepicker, null);
        dialogBuilder.setView(dialogView);

        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);

        dialogBuilder.setTitle("Chọn năm sinh");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mBtnNamSinh.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    //Sự kiện của nút Đăng ký
    public void btnDangKy(View view) {

        if (mEdtHoTen.getText().toString().equals("") || mBtnNamSinh.getText().toString().equals("") || mSpinGioiTinh.getSelectedItemPosition()==0 || mEdtSDT.getText().toString().equals("") || mEdtDiaChi.getText().toString().equals("")) {
            if (mEdtHoTen.getText().toString().equals("")) mEdtHoTen.setError("Bạn chưa nhập tên");
            else mBtnNamSinh.setError(null);
            if (mBtnNamSinh.getText().toString().equals("01/01/1970"))
                mBtnNamSinh.setError("Bạn chưa chọn năm sinh");
            else mBtnNamSinh.setError(null);
            if ( mSpinGioiTinh.getSelectedItemPosition()==0)
                Snackbar.make(mSpinGioiTinh, "Bạn chưa chọn giới tính", Snackbar.LENGTH_LONG ).show();
            else mBtnNamSinh.setError(null);
            if (mEdtSDT.getText().toString().equals(""))
                mEdtSDT.setError("Bạn chưa nhập số điện thoại");
            else mBtnNamSinh.setError(null);
            if (mEdtDiaChi.getText().toString().equals(""))
                mEdtDiaChi.setError("Bạn chưa nhập địa chỉ");
            else mBtnNamSinh.setError(null);
        } else {

            prgDialog.show();
            uploadImage();
        }
    }

    public void loadImagefromGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    // When Image is selected from Gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();

                // Set the Image in ImageView
                mImgAnhDaiDien.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                // Get the Image's file name

                Time today = new Time(Time.getCurrentTimezone());
//                String fileNameSegments[] = imgPath.split("/");
//                fileName = fileNameSegments[fileNameSegments.length - 1];
                Date currentTime = Calendar.getInstance().getTime();
                fileName = currentTime.getTime() + userName + ".jpg";
                Log.e(TAG, "onActivityResult: " + fileName);
                // Put file name in Async Http Post Param which will used in Php web app
                params.put("filename", fileName);

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    // When Upload button is clicked
    public void uploadImage() {
        // When Image is selected from Gallery
        if (imgPath != null && !imgPath.isEmpty()) {
            Log.e(TAG, "uploadImage: Converting Image to Binary Data");
            // Convert image to String using Base64
            encodeImagetoString();
            // When Image is not selected from Gallery
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }
    }

    // AsyncTask - To convert Image to String
    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.e(TAG, "onPostExecute: Calling Upload ");
                // Put converted Image string into Async Http Post param

//                upLoadToDB(userName,
//                                passWD,
//                                mEdtHoTen.getText().toString(),
//                                mSpinGioiTinh.getSelectedItem().toString(),
//                                mBtnNamSinh.getText().toString(),
//                                fileName,
//                                mEdtSDT.getText().toString(),
//                                mEdtDiaChi.getText().toString());

                params.put("image", encodedString);
                // Trigger Image upload
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }

    public void triggerImageUpload() {
        makeHTTPCall();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    // Make Http call to upload Image to Php server
    public void makeHTTPCall() {
        AsyncHttpClient client = new AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post(Constant.HOST_IMAGE,
                params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e(TAG, "onSuccess: Đã up thành công");

//                        String gioiTinh = "";
//                        if (mCheckNam.isChecked()) gioiTinh = "Nam";
//                        else if (mCheckNu.isChecked()) gioiTinh = "Nữ";

                        upLoadToDB(userName,
                                passWD,
                                mEdtHoTen.getText().toString(),
                                mSpinGioiTinh.getSelectedItem().toString(),
                                mBtnNamSinh.getText().toString(),
                                fileName,
                                mEdtSDT.getText().toString(),
                                mEdtDiaChi.getText().toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured n Most Common Error: n1. Device not connected to Internetn2. Web App is not deployed in App servern3. App server is not runningn HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }

    private void upLoadToDB(String userName, String passwd, String fullName, String sex, String birthDay, String imgUrl, String tel, String address) {
//        Log.e(TAG, "upLoadToDB: " + userName + passwd + fullName + sex + birthDay + imgUrl + tel + address);
        Call<String> call = api.taoTaiKhoan_KH(userName, passwd, fullName, sex, birthDay, imgUrl, tel, address);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "onResponse: " + response.body());
                if (response.body().equals("true")) {
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//
//                    }
                    taoTKThanhCong();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        // Dismiss the progress bar when application is closed
        if (prgDialog != null) {
            prgDialog.dismiss();
        }
    }

    private void checkPermission() {
//        int permissionCheck = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_CALENDAR);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void taoTKThanhCong() {

        SharedPreferences mSPre = getSharedPreferences(Constant.SPRE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor mSP_Edit;
        mSP_Edit = mSPre.edit();
        mSP_Edit.putString(Constant.USERNAME_CUS, userName);
        mSP_Edit.putString(Constant.PASSWORD_CUS, passWD);
        mSP_Edit.putBoolean(Constant.IS_SIGNIN, true);
        mSP_Edit.commit();
        startActivity(intent);
        finish();
    }

    private void toolbarView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    finish();
                }
            });
        }
    }


}

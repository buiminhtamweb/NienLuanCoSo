package mycompany.com.nienluancoso.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import mycompany.com.nienluancoso.Constant;
import mycompany.com.nienluancoso.Data.Api;
import mycompany.com.nienluancoso.Data.Local.AgricLiteObject;
import mycompany.com.nienluancoso.DetailAgri.ChiTietNSActivity;
import mycompany.com.nienluancoso.Order.AgriOrderObject;
import mycompany.com.nienluancoso.Order.BillObject;
import mycompany.com.nienluancoso.Order.OrderObject;
import mycompany.com.nienluancoso.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admin on 3/27/2018.
 */

public class OrderProcessingActivity extends AppCompatActivity {

    private RecyclerView mRecyOrderProcessing;

    private SharedPreferences mSPre;
    private String mUsername;

    private Api api;
    private Retrofit retrofit;

    List<OrderObject> mOrderProcessList = new ArrayList<>();

    private RecyOrderProcessAdapter mOrderProcessAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderprocessing);

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

        //Lấy ID người dùng
        mSPre = getSharedPreferences(Constant.SPRE_NAME, MODE_PRIVATE);
        mUsername = mSPre.getString(Constant.USERNAME_CUS, "");

        mRecyOrderProcessing = (RecyclerView) findViewById(R.id.recycler_view_dh_dangxuly);
        mRecyOrderProcessing.setLayoutManager(new LinearLayoutManager(this));
        mOrderProcessAdapter = new RecyOrderProcessAdapter(mOrderProcessList);
        mRecyOrderProcessing.setAdapter(mOrderProcessAdapter);

        mOrderProcessAdapter.setOnItemClickListener(new RecyOrderProcessAdapter.onClickListener() {
            @Override
            public void onItemClick(List<AgriOrderObject> agriOrderList) {
                loadAgriOfOderList(agriOrderList);
            }
        });


        initRetrofit();
        loadData();

    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    private void loadData() {

        Call<List<OrderObject>> call = api.getOrderProcessing(mUsername);
        call.enqueue(new Callback<List<OrderObject>>() {
            @Override
            public void onResponse(Call<List<OrderObject>> call, Response<List<OrderObject>> response) {
                if (!response.body().equals("")) {
                    for (int i = 0; i < response.body().size(); i++) {
                        mOrderProcessList.add(response.body().get(i));
                    }
                    mOrderProcessAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<OrderObject>> call, Throwable t) {

            }
        });

    }

    private void loadAgriOfOderList(List<AgriOrderObject> agriOrderList) {

        //Khởi tạo Dialog chứa Danh sách
        List<AgriOrderItemObject> agriOrderItemList = new ArrayList<>();

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_bill_order_recy, null);
        dialogBuilder.setView(dialogView);
        RecyclerView recyAgriOrder = (RecyclerView) dialogView.findViewById(R.id.recycler_view_dialog);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyAgriOrder.setLayoutManager(layoutManager);
        RecyAgriOrderAdapter recyAgriOrderAdapter = new RecyAgriOrderAdapter(agriOrderItemList);
        recyAgriOrder.setAdapter(recyAgriOrderAdapter);

        //Sự kiện Click vào Item SN
        final Intent intent = new Intent(this, ChiTietNSActivity.class);
        recyAgriOrderAdapter.setOnItemClickListener(new RecyAgriOrderAdapter.onClickListener() {
            @Override
            public void onItemClick(String idAgric) {
                intent.putExtra(Constant.ID_AGRI, idAgric);
                startActivity(intent);
                finish();
            }
        });

        Button btnHuy = (Button) dialogView.findViewById(R.id.btn_close);
        dialogBuilder.setTitle("Danh sách nông sản đang xử lý");


        //Load dữ liệu danh sách nông sản đã dặt hàng
        for (int i = 0; i < agriOrderList.size(); i++) {

            final AgriOrderItemObject agriOrderItem = new AgriOrderItemObject();
            //Set Giá mua cũ và số lượng mua
            agriOrderItem.setIdAGRI(agriOrderList.get(i).getIDAGRI());
            agriOrderItem.setpRICE_ORDER(agriOrderList.get(i).getCURRENTPRICE());
            agriOrderItem.setnUM_ORDER(agriOrderList.get(i).getNUMOFAGRI());

            //Lấy thông tin Hình và Tên
            Call<AgricLiteObject> call = api.getArgiLite(agriOrderList.get(i).getIDAGRI());
            call.enqueue(new Callback<AgricLiteObject>() {
                @Override
                public void onResponse(Call<AgricLiteObject> call, Response<AgricLiteObject> response) {
                    if (!response.body().equals("")) {
                        agriOrderItem.setnAMEAGRI(response.body().getNAMEAGRI());
                        agriOrderItem.setiMGURLAGRI(response.body().getIMGURLAGRI());
                    }
                }
                @Override
                public void onFailure(Call<AgricLiteObject> call, Throwable t) {

                }
            });
            agriOrderItemList.add(agriOrderItem);
            recyAgriOrderAdapter.notifyDataSetChanged();
        }


        //Show Dialog
        final AlertDialog  mDialogAgriList = dialogBuilder.create();
        mDialogAgriList.show();
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogAgriList.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}

package mycompany.com.nienluancoso.Order;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mycompany.com.nienluancoso.Constant;
import mycompany.com.nienluancoso.Data.Api;
import mycompany.com.nienluancoso.Data.Local.AgricLiteObject;
import mycompany.com.nienluancoso.Data.Local.DBAgricOrderObject;
import mycompany.com.nienluancoso.Data.Local.DBOrderObject;
import mycompany.com.nienluancoso.Data.Local.DatabaseHelper;
import mycompany.com.nienluancoso.Data.OrderItemObject;
import mycompany.com.nienluancoso.DetailAgri.ChiTietNSActivity;
import mycompany.com.nienluancoso.R;
import mycompany.com.nienluancoso.User.OrderProcessingActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Admin on 3/24/2018.
 */

public class Fragment3 extends Fragment {

    String TAG = "OrderFrag";
    private RecyOrderAdapter recyOrderAdapter;
    private List<OrderItemObject> orderItemObjects = new ArrayList<>();
    private RecyclerView mRecyOrder;

    private TextView mTvTongTienHD;
    private Toolbar mToolbar;

    private DBOrderObject dbOrderObject;
    private List<DBAgricOrderObject> dbAgricOrderObjects;

    private SharedPreferences mSPre;
    private String mUsername;

    private Api api;
    private Retrofit retrofit;

    private DatabaseHelper dbaseHelper;

    private int soLuongMua;
    private int soLuongConLai;

    private Button mBtnDatHang;

    private ProgressDialog mProcessDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);

        dbaseHelper = new DatabaseHelper(getContext());
        mSPre = getActivity().getSharedPreferences(Constant.SPRE_NAME, MODE_PRIVATE);

        //initView
        mProcessDialog = new ProgressDialog(getActivity());
        mProcessDialog.setMessage("Đang xử lý...");
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTvTongTienHD = (TextView) view.findViewById(R.id.tv_tongtien_hd);
        mBtnDatHang = (Button) view.findViewById(R.id.btn_dat_hang);

        //RecylerView Giỏ hàng
        mRecyOrder = (RecyclerView) view.findViewById(R.id.recycler_view_order);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyOrder.setLayoutManager(layoutManager);
        recyOrderAdapter = new RecyOrderAdapter(getContext(), orderItemObjects);
        mRecyOrder.setAdapter(recyOrderAdapter);


        initRetrofit();
        loadData();

        eventClick();

        return view;
    }

    private void loadData() {

        orderItemObjects.clear();

        dbOrderObject = dbaseHelper.getOrder();

        if (null == dbaseHelper.getAgricOnOrder()) {
            dbaseHelper.deleteAllOrder();
            mToolbar.setSubtitle("Chưa có sản phẩm đặt hàng");
            mTvTongTienHD.setText("Tổng cộng: " + 0 + " VND");
        }

        mUsername = mSPre.getString(Constant.USERNAME_CUS, "");

        dbAgricOrderObjects = dbaseHelper.getAgricOnOrder();

        if (null != dbAgricOrderObjects) {
            for (int i = 0; i < dbAgricOrderObjects.size(); i++) {

                //Thêm ID và Số lượng từ CSDL local
                final OrderItemObject orderItemObject = new OrderItemObject();
                orderItemObject.setID_AGRI(Integer.parseInt(dbAgricOrderObjects.get(i).getID_AGRI()));
                orderItemObject.setSoLuongMua(Integer.parseInt(dbAgricOrderObjects.get(i).getNUM_OF_AGRI()));

                //Lấy dữ liệu tên ,
                Call<AgricLiteObject> call = api.getArgiLite(dbAgricOrderObjects.get(i).getID_AGRI());
                call.enqueue(new Callback<AgricLiteObject>() {
                    @Override
                    public void onResponse(Call<AgricLiteObject> call, Response<AgricLiteObject> response) {
                        if (!response.body().equals("")) {
                            orderItemObject.setIMG_URL_AGRI(response.body().getIMGURLAGRI());
                            orderItemObject.setNAME_AGRI(response.body().getNAMEAGRI());
                            orderItemObject.setPRICE_AGRI(Float.parseFloat(response.body().getPRICEAGRI()));
                            orderItemObject.setSoLuongConLai_AGRI(Integer.parseInt(response.body().getAMOUNTAGRI()));
                            orderItemObjects.add(orderItemObject);
                            recyOrderAdapter.notifyDataSetChanged();

                            mTvTongTienHD.setText("Tổng cộng: " + recyOrderAdapter.tongGioHang() + " VND");
                        }

                    }

                    @Override
                    public void onFailure(Call<AgricLiteObject> call, Throwable t) {

                    }
                });
            }
        }
        recyOrderAdapter.notifyDataSetChanged();


    }


    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    private void capNhatSLMua(final String idAgric, int soLuongMuaCu, final int soLuongConLai) {

        String arr[] = {"Gam", "Kg"};

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_donvi_tinh, null);
        dialogBuilder.setView(dialogView);

        final EditText mEdtSoluongMua = (EditText) dialogView.findViewById(R.id.edt_soLuong_mua);
        mEdtSoluongMua.setText(soLuongMuaCu + "");

        Button btnOK = (Button) dialogView.findViewById(R.id.btn_ok);
        Button btnHuy = (Button) dialogView.findViewById(R.id.btn_huy);
        final Spinner spin = (Spinner) dialogView.findViewById(R.id.spinner_donvi_tinh);
        //Gán Data source (arr) vào Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arr);
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spin.setAdapter(adapter);
        spin.setSelection(0);


        //Kiểm tra số lượng mua cao hơn số lượng hàng tồn kho hay không

        dialogBuilder.setTitle("Nhập vào số lượng cần mua");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        //Sự kiện các nút trên Dialog
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chuyển đổi đơn vị tính theo Gam
                if (spin.getSelectedItemPosition() == 1) {
                    soLuongMua = Integer.parseInt(mEdtSoluongMua.getText().toString() + "000");
                } else {
                    soLuongMua = Integer.parseInt(mEdtSoluongMua.getText().toString());
                }
                //Kiểm tra số lượng mua cao hơn số lượng hàng tồn kho hay không
                if (soLuongMua > soLuongConLai)
                    mEdtSoluongMua.setError("Mặt hàng không đủ số lượng để bán");
                else {
                    mEdtSoluongMua.setError(null);
                    dbaseHelper.updateNumOfAgric(idAgric, String.valueOf(soLuongMua));
                    Toast.makeText(getContext(), "Đã cập nhật số lượng", Toast.LENGTH_SHORT).show();

                    b.dismiss();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

    }

    private void eventClick() {
        recyOrderAdapter.setOnItemClickListener(new RecyOrderAdapter.onClickListener() {
            @Override
            public void onItemClick(int position) {

                Log.e(TAG, "onItemClick: " + orderItemObjects.get(position).getID_AGRI());
                Intent intent = new Intent(getContext(), ChiTietNSActivity.class);
                intent.putExtra("ID_AGRI", orderItemObjects.get(position).getID_AGRI());
                startActivity(intent);
            }

            @Override
            public void onEditClick(int position) {
                Log.e(TAG, "onEditClick: " + orderItemObjects.get(position).getID_AGRI());

                capNhatSLMua(String.valueOf(orderItemObjects.get(position).getID_AGRI()),
                        orderItemObjects.get(position).getSoLuongMua(),
                        orderItemObjects.get(position).getSoLuongConLai_AGRI());


                loadData();

            }

            @Override
            public void onDeleteClick(int position) {
                Log.e(TAG, "onDeleteClick: " + orderItemObjects.get(position).getID_AGRI());
                dbaseHelper.deleteAgricOnOrder(String.valueOf(orderItemObjects.get(position).getID_AGRI()));

//                //Nếu không còn sản phẩm nào trong giỏ hàng thì sẽ xóa bỏ tất cả dữ liệu
//                if (null == dbaseHelper.getAgricOnOrder()) {
//                    dbaseHelper.deleteAllOrder();
//                    mToolbar.setSubtitle("Chưa có sản phẩm đặt hàng");
//                    mTvTongTienHD.setText("Tổng cộng: " + 0 + " VND");
//                }


                loadData();
            }
        });

        mBtnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upLoadOrder();
            }
        });
    }

    private void upLoadOrder() {

        mProcessDialog.show();
        //Khởi tạo Retrofit 2
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);

        //Chuyển dữ liệu thành JSON
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < orderItemObjects.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("ID_AGRI", orderItemObjects.get(i).getID_AGRI());
                jsonObject.put("NUM_OF_AGRI", orderItemObjects.get(i).getSoLuongMua());
                jsonObject.put("CURRENT_PRICE", orderItemObjects.get(i).getPRICE_AGRI());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }

        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("ID_ORDER", dbOrderObject.getID_ORDER());
            paramObject.put("USERNAME_CUS", mUsername);
            paramObject.put("TOTAL_ORDER", recyOrderAdapter.tongGioHang());
            paramObject.put("AGRI_ORDER", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Upload lên CSDL
        Call<String> call = api.uploadOrder(paramObject.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "onResponse_JSONObject: \n  " + response.body());

                if (response.body().equals("true")){
                    Toast.makeText(getActivity(), "Đã dặt hàng thành công", Toast.LENGTH_SHORT).show();
                    dbaseHelper.deleteAllOrder();
                    loadData();

                    //Set thời gian chờ xử lý 2s
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }

                    mProcessDialog.dismiss();

                    //Chuyển sang Đơn hàng dag được xử lý
                    startActivity(new Intent(getActivity(), OrderProcessingActivity.class));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }


}

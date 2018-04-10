package mycompany.com.nienluancoso.Order;

import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);

        dbaseHelper = new DatabaseHelper(getContext());
        mSPre = getActivity().getSharedPreferences(Constant.SPRE_NAME, MODE_PRIVATE);

        //initView
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTvTongTienHD = (TextView) view.findViewById(R.id.tv_tongtien_hd);

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


        if (null != dbOrderObject) {
            mToolbar.setSubtitle("Mã đơn hàng: " + dbOrderObject.getID_ORDER());
        } else {
            mToolbar.setSubtitle("Chưa có sản phẩm đặt hàng");
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


    }

//    private float tongHoaDon() {
//        float tongHD = 0;
//        for (int i = 0; i < orderItemObjects.size(); i++) {
//            tongHD += orderItemObjects.get(i).getTongGiaMua();
//        }
//        return tongHD;
//    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    private void capNhatSLMua(final String idAgric, int soLuongMuaCu, final int soLuongConLai) {

        final int soLuongMua;
        String arr[] = {"Gam", "Kg"};

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();

        //Tham chieu layout
        final View dialogView = inflater.inflate(R.layout.dialog_donvi_tinh, null);
        dialogBuilder.setView(dialogView);

        final EditText mEdtSoluongMua = (EditText) dialogView.findViewById(R.id.edt_soLuong_mua);
        mEdtSoluongMua.setText(soLuongMuaCu);
        Spinner spin = (Spinner) dialogView.findViewById(R.id.spinner_donvi_tinh);
        //Gán Data source (arr) vào Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arr);
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spin.setAdapter(adapter);
        spin.setSelection(0);

        //Chuyển đổi đơn vị tính theo Gam
        if (spin.getSelectedItemPosition() == 1) {
            soLuongMua = Integer.parseInt(mEdtSoluongMua.getText().toString() + "000");
        } else {
            soLuongMua = Integer.parseInt(mEdtSoluongMua.getText().toString());

        }

        //Kiểm tra số lượng mua cao hơn số lượng hàng tồn kho hay không


        dialogBuilder.setTitle("Nhập vào số lượng cần mua");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (soLuongMua > soLuongConLai)
                    mEdtSoluongMua.setError("Mặt hàng không đủ số lượng để bán");
                else {
                    mEdtSoluongMua.setError(null);

                    //cập nhật vào CSDL
                    dbaseHelper.updateNumOfAgric(idAgric, String.valueOf(soLuongMua));
                    Toast.makeText(getActivity(), "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
                }


            }
        });


        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog b = dialogBuilder.create();
        b.show();

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

                //Nếu không còn sản phẩm nào trong giỏ hàng thì sẽ xóa bỏ tất cả dữ liệu
                if (null == dbaseHelper.getAgricOnOrder()) {
                    dbaseHelper.deleteAllOrder();
                    mToolbar.setSubtitle("Chưa có sản phẩm đặt hàng");
                    mTvTongTienHD.setText("Tổng cộng: " + 0 + " VND");
                }

                loadData();
            }
        });

        mTvTongTienHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                upLoadOrder();

            }
        });
    }

    private void upLoadOrder() {





    }


}

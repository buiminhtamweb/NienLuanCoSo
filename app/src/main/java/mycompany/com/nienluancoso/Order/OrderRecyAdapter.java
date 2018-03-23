package mycompany.com.nienluancoso.Order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import mycompany.com.nienluancoso.Home.AgriObject;
import mycompany.com.nienluancoso.R;

/**
 * Created by Admin on 3/21/2018.
 */

public class OrderRecyAdapter extends RecyclerView.Adapter<OrderRecyAdapter.ViewHolder> {


    private Context mContext;
    private List<AgriObject> mAgriObjectList = new ArrayList<>();

    public OrderRecyAdapter(Context mContext, List<AgriObject> mAgriObjectList) {
        this.mContext = mContext;
        this.mAgriObjectList = mAgriObjectList;
    }

    public List<AgriObject> getmAgriObjectList() {
        return mAgriObjectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);

        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext).load("http://192.168.43.161:80/a/img/gao.jpg").into(holder.mImageView);

        holder.mNameAgri.setText(mAgriObjectList.get(position).getNAME_AGRI());
        holder.mPrice.setText(mAgriObjectList.get(position).getPRICE_AGRI() + " VND");
        holder.mSoLuongMua.setText("Số lượng mua: "+ convertGamView(2000));

    }


    @Override
    public int getItemCount() {
        return mAgriObjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView, mEdit, mDelete;
        private TextView mNameAgri,mPrice, mSoLuongMua;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.img_agri);
            mEdit = (ImageView) view.findViewById(R.id.img_edit);
            mDelete = (ImageView) view.findViewById(R.id.img_delete);
            mNameAgri = (TextView) view.findViewById(R.id.tv_tensp);
            mPrice = (TextView) view.findViewById(R.id.tv_gia);
            mSoLuongMua = (TextView) view.findViewById(R.id.tv_soluong_mua);
        }
    }


    //Chuyển đổi đơn vị Gam -> Kg
    private String convertGamView(int gam){
        if (gam>1000){
            return gam/1000 + " Kg";
        }else return gam+" Gam";


    }
}

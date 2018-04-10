package mycompany.com.nienluancoso.User;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mycompany.com.nienluancoso.Constant;
import mycompany.com.nienluancoso.R;

public class RecyAgriOrderAdapter extends RecyclerView.Adapter<RecyAgriOrderAdapter.ViewHolder> {

    private List<AgriOrderItemObject> mAgriOrderItemList;
    private onClickListener onClickListener;

    public RecyAgriOrderAdapter(List<AgriOrderItemObject> mAgriOrderItemList) {
        this.mAgriOrderItemList = mAgriOrderItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_agri, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(Constant.IMAGE_SOURCE + mAgriOrderItemList.get(position).getiMGURLAGRI()).fit().centerCrop().into(holder.mImageView);
        holder.mNameAgri.setText(mAgriOrderItemList.get(position).getnAMEAGRI());
        holder.mPrice.setText("Đơn giá: " + mAgriOrderItemList.get(position).getpRICE_ORDER() + " VND");
        holder.mSoLuongMua.setText("Số lượng mua: " + convertGamView(Integer.parseInt(mAgriOrderItemList.get(position).getnUM_ORDER())));
        holder.mThanhTien.setText("Thành tiền: " + thanhTien(Integer.parseInt(mAgriOrderItemList.get(position).getnUM_ORDER()),
                Float.parseFloat(mAgriOrderItemList.get(position).getpRICE_ORDER())));
    }

    @Override
    public int getItemCount() {
        return mAgriOrderItemList.size();
    }

    //Chuyển đổi đơn vị Gam -> Kg
    private String convertGamView(int gam) {
        if (gam > 1000) {
            return Float.valueOf(gam) / 1000 + " Kg";
        } else return gam + " Gam";

    }

    private String thanhTien(int slGamMua, float donGia) {
        return (donGia / 1000) * slGamMua + " VND";
    }

    public void setOnItemClickListener(RecyAgriOrderAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public interface onClickListener {
        void onItemClick(String idAgric);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView mImageView;
        private TextView mNameAgri, mPrice, mSoLuongMua, mThanhTien;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.img_agri);
            mNameAgri = (TextView) view.findViewById(R.id.tv_tensp);
            mPrice = (TextView) view.findViewById(R.id.tv_gia);
            mSoLuongMua = (TextView) view.findViewById(R.id.tv_soluong_mua);
            mThanhTien = (TextView) view.findViewById(R.id.tv_thanhtien);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClick(mAgriOrderItemList.get(getAdapterPosition()).getIdAGRI());
                }
            });
        }
    }


}

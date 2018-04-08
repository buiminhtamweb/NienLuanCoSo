package mycompany.com.nienluancoso.Order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mycompany.com.nienluancoso.Constant;
import mycompany.com.nienluancoso.Data.OrderItemObject;
import mycompany.com.nienluancoso.R;

public class RecyOrderAdapter extends RecyclerView.Adapter<RecyOrderAdapter.ViewHolder> {


    private Context mContext;
    private List<OrderItemObject> orderObjectList;
    private onClickListener onClickListener;

    public RecyOrderAdapter(Context mContext, List<OrderItemObject> orderObjectList) {
        this.mContext = mContext;
        this.orderObjectList = orderObjectList;
    }

    public void setOnItemClickListener(RecyOrderAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get().load(Constant.IMAGE_SOURCE + orderObjectList.get(position).getIMG_URL_AGRI()).fit().centerCrop().into(holder.mImageView);
        holder.mNameAgri.setText(orderObjectList.get(position).getNAME_AGRI());
        holder.mPrice.setText("Đơn giá: " + orderObjectList.get(position).getPRICE_AGRI() + " VND");
        holder.mSoLuongMua.setText("Số lượng mua: " + convertGamView(orderObjectList.get(position).getSoLuongMua()));
        holder.mThanhTien.setText("Thành tiền: " + thanhTien(orderObjectList.get(position).getSoLuongMua(),
                                                            orderObjectList.get(position).getPRICE_AGRI()));

    }

    @Override
    public int getItemCount() {
        return orderObjectList.size();
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


    public interface onClickListener {
        void onItemClick(int position);

        void onEditClick(int position);

        void onDeleteClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView, mEdit, mDelete;
        private TextView mNameAgri, mPrice, mSoLuongMua, mThanhTien;

        public ViewHolder(final View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.img_agri);
            mEdit = (ImageView) view.findViewById(R.id.img_edit);
            mDelete = (ImageView) view.findViewById(R.id.img_delete);
            mNameAgri = (TextView) view.findViewById(R.id.tv_tensp);
            mPrice = (TextView) view.findViewById(R.id.tv_gia);
            mSoLuongMua = (TextView) view.findViewById(R.id.tv_soluong_mua);
            mThanhTien = (TextView) view.findViewById(R.id.tv_thanhtien);

            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.select_image));
                    onClickListener.onEditClick(getAdapterPosition());
                }
            });
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onDeleteClick(getAdapterPosition());
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onItemClick(getAdapterPosition());
                }
            });

        }
    }


}

package mycompany.com.nienluancoso.Order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import mycompany.com.nienluancoso.Data.OrderOject;
import mycompany.com.nienluancoso.R;

/**
 * Created by Admin on 3/21/2018.
 */

public class OrderRecyAdapter extends RecyclerView.Adapter<OrderRecyAdapter.ViewHolder> {


    private Context mContext;
    private List<OrderOject> orderOjectList = new ArrayList<>();
    private onClickListener onClickListener;

    public void setOnItemClickListener(OrderRecyAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OrderRecyAdapter(Context mContext, List<OrderOject> orderOjectList) {
        this.mContext = mContext;
        this.orderOjectList = orderOjectList;
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

        holder.mNameAgri.setText(orderOjectList.get(position).getNAME_AGRI());
        holder.mPrice.setText(orderOjectList.get(position).getPRICE_AGRI() + " VND");
        holder.mSoLuongMua.setText("Số lượng mua: " + convertGamView(orderOjectList.get(position).getSoLuongMua()));

    }

    @Override
    public int getItemCount() {
        return orderOjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView, mEdit, mDelete;
        private TextView mNameAgri, mPrice, mSoLuongMua;

        public ViewHolder(final View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.img_agri);
            mEdit = (ImageView) view.findViewById(R.id.img_edit);
            mDelete = (ImageView) view.findViewById(R.id.img_delete);
            mNameAgri = (TextView) view.findViewById(R.id.tv_tensp);
            mPrice = (TextView) view.findViewById(R.id.tv_gia);
            mSoLuongMua = (TextView) view.findViewById(R.id.tv_soluong_mua);



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


    //Chuyển đổi đơn vị Gam -> Kg
    private String convertGamView(int gam) {
        if (gam > 1000) {
            return Float.valueOf(gam)/1000 + " Kg";
        } else return gam + " Gam";

    }

    public interface onClickListener {
        void onItemClick(int position);
        void onEditClick(int position);
        void onDeleteClick(int position);
    }


}

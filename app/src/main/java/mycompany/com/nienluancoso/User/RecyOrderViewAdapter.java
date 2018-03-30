package mycompany.com.nienluancoso.User;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mycompany.com.nienluancoso.Data.OrderOject;
import mycompany.com.nienluancoso.R;

/**
 * Created by Admin on 3/21/2018.
 */
public class RecyOrderViewAdapter extends RecyclerView.Adapter<RecyOrderViewAdapter.ViewHolder> {
    private Context mContext;
    private List<OrderOject> orderObjectList;
    private onClickListener onClickListener;

    public RecyOrderViewAdapter(Context mContext, List<OrderOject> orderObjectList) {
        this.mContext = mContext;
        this.orderObjectList = orderObjectList;
    }

    public void setOnItemClickListener(RecyOrderViewAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
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
        Picasso.get().load("http://192.168.43.161:80/a/img/gao.jpg").into(holder.mImageView);
        holder.mNameAgri.setText(orderObjectList.get(position).getNAME_AGRI());
        holder.mPrice.setText(orderObjectList.get(position).getPRICE_AGRI() + " VND");
        holder.mSoLuongMua.setText("Số lượng mua: " + convertGamView(orderObjectList.get(position).getSoLuongMua()));
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

    public interface onClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mNameAgri, mPrice, mSoLuongMua;

        public ViewHolder(final View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.img_agri);
            mNameAgri = (TextView) view.findViewById(R.id.tv_tensp);
            mPrice = (TextView) view.findViewById(R.id.tv_gia);
            mSoLuongMua = (TextView) view.findViewById(R.id.tv_soluong_mua);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onItemClick(getAdapterPosition());
                }
            });

        }
    }

}

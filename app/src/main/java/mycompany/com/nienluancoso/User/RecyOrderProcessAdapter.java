package mycompany.com.nienluancoso.User;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mycompany.com.nienluancoso.Order.AgriOrderObject;
import mycompany.com.nienluancoso.Order.OrderObject;
import mycompany.com.nienluancoso.R;

public class RecyOrderProcessAdapter extends RecyclerView.Adapter<RecyOrderProcessAdapter.ViewHolder> {


    private List<OrderObject> mOrderProcessList;
    private onClickListener onClickListener;


    public RecyOrderProcessAdapter(List<OrderObject> mOrderProcessList) {
        this.mOrderProcessList = mOrderProcessList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_orderprocessing, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mMaHoaDon.setText(mOrderProcessList.get(position).getIDORDER());
        holder.mNgayDatHang.setText(mOrderProcessList.get(position).getDATEORDER());
        holder.mTongTien.setText(mOrderProcessList.get(position).getTOTALORDER());
    }

    @Override
    public int getItemCount() {
        return mOrderProcessList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mMaHoaDon, mNgayDatHang, mTongTien;

        public ViewHolder(View view) {
            super(view);
            mMaHoaDon = (TextView) view.findViewById(R.id.tv_ms_hoadon);
            mNgayDatHang = (TextView) view.findViewById(R.id.tv_ngay_dathang);
            mTongTien = (TextView) view.findViewById(R.id.tv_tongtien);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClick(mOrderProcessList.get(getAdapterPosition()).getAGRIORDER());
                }
            });
        }
    }


    public interface onClickListener {
        void onItemClick(List<AgriOrderObject> agriOrderList);
    }

    public void setOnItemClickListener(RecyOrderProcessAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}

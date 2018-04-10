package mycompany.com.nienluancoso.User;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mycompany.com.nienluancoso.Order.AgriOrderObject;
import mycompany.com.nienluancoso.Order.BillObject;
import mycompany.com.nienluancoso.R;

public class RecyBillAdapter extends RecyclerView.Adapter<RecyBillAdapter.ViewHolder> {


    private List<BillObject> billObjectList;
    private onClickListener onClickListener;

    public RecyBillAdapter(List<BillObject> billObjectList) {
        this.billObjectList = billObjectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lichsu_muahang, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mMaHoaDon.setText(billObjectList.get(position).getIDORDER());
        holder.mNguoiBan.setText(billObjectList.get(position).getNAMEUSER());
        holder.mNgayBan.setText(billObjectList.get(position).getDATEBILL());
        holder.mTongTien.setText(billObjectList.get(position).getTOTALORDER());
    }

    @Override
    public int getItemCount() {
        return billObjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mMaHoaDon, mNguoiBan, mNgayBan, mTongTien;

        public ViewHolder(View view) {
            super(view);
            mMaHoaDon = (TextView) view.findViewById(R.id.tv_ms_hoadon);
            mNguoiBan = (TextView) view.findViewById(R.id.tv_nguoiban);
            mNgayBan = (TextView) view.findViewById(R.id.tv_ngayban);
            mTongTien = (TextView) view.findViewById(R.id.tv_tongtien);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClick(billObjectList.get(getAdapterPosition()).getAGRIORDER());
                }
            });
        }
    }


    public interface onClickListener {
        void onItemClick(List<AgriOrderObject> agriOrderList);
    }

    public void setOnItemClickListener(RecyBillAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}

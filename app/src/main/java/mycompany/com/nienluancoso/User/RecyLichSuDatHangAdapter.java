package mycompany.com.nienluancoso.User;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mycompany.com.nienluancoso.Data.HoaDonObject;
import mycompany.com.nienluancoso.R;

public class RecyLichSuDatHangAdapter extends RecyclerView.Adapter<RecyLichSuDatHangAdapter.ViewHolder> {

    private List<HoaDonObject> hoaDonObjects;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_agri, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mMaHoaDon.setText(hoaDonObjects.get(position).getID());
        holder.mNguoiBan.setText(hoaDonObjects.get(position).getNGUOIBAN());
        holder.mNgayBan.setText(hoaDonObjects.get(position).getNGAYMUA());
        holder.mTongTien.setText(hoaDonObjects.get(position).getTONGTIEN());
    }
    @Override
    public int getItemCount() {
        return hoaDonObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mMaHoaDon, mNguoiBan, mNgayBan, mTongTien;

        public ViewHolder(View view) {
            super(view);
            mMaHoaDon = (TextView) view.findViewById(R.id.tv_ms_hoadon);
            mNguoiBan = (TextView) view.findViewById(R.id.tv_nguoiban);
            mNgayBan = (TextView) view.findViewById(R.id.tv_ngayban);
            mTongTien = (TextView) view.findViewById(R.id.tv_tongtien);
        }
    }
}

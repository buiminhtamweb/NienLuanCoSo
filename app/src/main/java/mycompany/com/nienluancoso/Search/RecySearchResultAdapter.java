package mycompany.com.nienluancoso.Search;

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

import mycompany.com.nienluancoso.Constant;
import mycompany.com.nienluancoso.Data.AgriItemObject;
import mycompany.com.nienluancoso.R;

/**
 * Created by Admin on 3/21/2018.
 */

public class RecySearchResultAdapter extends RecyclerView.Adapter<RecySearchResultAdapter.ViewHolder> {


    private Context mContext;
    private List<AgriItemObject> mAgriItemObjectList;
    private onClickListener onClickListener;

    public RecySearchResultAdapter(Context mContext, List<AgriItemObject> mAgriItemObjectList) {
        this.mContext = mContext;
        this.mAgriItemObjectList = mAgriItemObjectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_agri_search_result, parent, false);

        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(Constant.IMAGE_SOURCE + mAgriItemObjectList.get(position).getIMG_URL_AGRI()).fit().centerCrop().into(holder.mImageView);
        holder.mNameAgri.setText(mAgriItemObjectList.get(position).getNAME_AGRI());

        //Hiện giá cũ lên nếu có giá cũ
        if (null != mAgriItemObjectList.get(position).getoLDPRICE()){
            String gachNgang = "<strike>"+mAgriItemObjectList.get(position).getoLDPRICE()+"  VND</strike>";
            holder.mOldPrice.setText(android.text.Html.fromHtml(gachNgang));
        }else holder.mOldPrice.setVisibility(View.GONE);

        holder.mPrice.setText(mAgriItemObjectList.get(position).getPRICE_AGRI() + " VND");

    }

    @Override
    public int getItemCount() {
        return mAgriItemObjectList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mNameAgri;
        public TextView mOldPrice;
        public TextView mPrice;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.image_view);
            mNameAgri = (TextView) view.findViewById(R.id.tv_name_agri);
            mOldPrice = (TextView) view.findViewById(R.id.tv_old_price_agri);
            mPrice = (TextView) view.findViewById(R.id.tv_price_agri);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onItemClick(getAdapterPosition(),mAgriItemObjectList.get(getAdapterPosition()).getID_AGRI());
                }
            });
        }
    }

    public interface onClickListener {
        void onItemClick(int position, int idAgri);
    }
    public void setOnClickListener(RecySearchResultAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}

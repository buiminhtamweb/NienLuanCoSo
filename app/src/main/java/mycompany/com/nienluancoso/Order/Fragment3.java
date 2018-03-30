package mycompany.com.nienluancoso.Order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mycompany.com.nienluancoso.Data.OrderOject;
import mycompany.com.nienluancoso.DetailAgri.ChiTietNSActivity;
import mycompany.com.nienluancoso.R;

/**
 * Created by Admin on 3/24/2018.
 */

public class Fragment3 extends Fragment {

    String TAG = "OrderFrag";
    private OrderRecyAdapter orderRecyAdapter;
    private List<OrderOject> orderOjects = new ArrayList<>();
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);

        data();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_order);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        orderRecyAdapter = new OrderRecyAdapter(getContext(), orderOjects);

        recyclerView.setAdapter(orderRecyAdapter);

        orderRecyAdapter.setOnItemClickListener(new OrderRecyAdapter.onClickListener() {
            @Override
            public void onItemClick(int position) {

                Log.e(TAG, "onItemClick: " + position);
                Intent intent = new Intent(getContext(), ChiTietNSActivity.class);
                startActivity(intent);
            }

            @Override
            public void onEditClick(int position) {
                Log.e(TAG, "onEditClick: " + position);

            }

            @Override
            public void onDeleteClick(int position) {
                Log.e(TAG, "onDeleteClick: " + position);

            }
        });

        return view;
    }

    private void data() {
        orderOjects.clear();
        orderOjects.add(new OrderOject(1, 2,
                "ABC", "k co", 1000, 2000));
        orderOjects.add(new OrderOject(2, 2,
                "Rau", "k co", 1000, 500));
        orderOjects.add(new OrderOject(3, 2,
                "Rice", "k co", 1000, 3300));
        orderOjects.add(new OrderOject(4, 2,
                "Orange", "k co", 1000, 2000));

    }


}

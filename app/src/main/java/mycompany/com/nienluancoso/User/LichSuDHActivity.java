package mycompany.com.nienluancoso.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import mycompany.com.nienluancoso.R;

/**
 * Created by Admin on 3/27/2018.
 */

public class LichSuDHActivity extends AppCompatActivity {

    private RecyclerView mRecyLichSuDatHang;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lichsu_dhang);



        mRecyLichSuDatHang = (RecyclerView) findViewById(R.id.recycler_view_lichsu_dh);
        mRecyLichSuDatHang.setLayoutManager(new LinearLayoutManager(this));
    }


}

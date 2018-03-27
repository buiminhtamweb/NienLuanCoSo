package mycompany.com.nienluancoso.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mycompany.com.nienluancoso.R;
import mycompany.com.nienluancoso.Signin.ThemThongTinDKActivity;

/**
 * Created by Admin on 3/27/2018.
 */

public class YeuThichActivity extends AppCompatActivity {

    private RecyclerView mRecyYeuThich;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeuthich);

        mRecyYeuThich = (RecyclerView) findViewById(R.id.recycler_view_yeuthich);
        mRecyYeuThich.setLayoutManager(new LinearLayoutManager(this));


    }


}

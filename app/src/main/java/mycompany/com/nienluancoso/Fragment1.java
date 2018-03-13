package mycompany.com.nienluancoso;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buimi on 1/29/2018.
 */

public class Fragment1 extends Fragment {

    private GridView mGridView;
    private GridViewAdapter mGridViewAdapter;
    private List<AgriObject> mAgriObjectList = new ArrayList<>();

    public Fragment1() {
    }

    public static Fragment newIntance(){
        return new Fragment1();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,
                container,false);


    mGridView = (GridView) view.findViewById(R.id.gridView_frag1);
    mGridViewAdapter = new GridViewAdapter(getContext(),mAgriObjectList);

    mGridView.setAdapter(mGridViewAdapter);


    return view;
    }

    private void getData(){
        
    }



}

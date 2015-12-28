package com.minigee.app.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.xutils.x;
/**
 * Created by Zhou on 2015-12-4.
 */
public class BaseFrame extends Fragment {
    private boolean injected = false;

    public BaseUi ui;
    protected boolean loaded = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && !loaded){
            ui = (BaseUi) getActivity();
//            ui.setHandler(new BaseHandler(this));
            loaded = true;
            initdata();
        }
    }

    public void initdata(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.w("AppClient", "base onCreate");
        // init application
        ui = (BaseUi) getActivity();
//        ui.setHandler(new BaseHandler(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }
}

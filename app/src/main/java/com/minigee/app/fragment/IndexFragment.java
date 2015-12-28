package com.minigee.app.fragment;

import android.view.View;

import com.minigee.app.R;
import com.minigee.app.base.BaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;


/**
 * Created by Zhou on 2015-10-12.
 */
@ContentView(R.layout.fragment_index)
public class IndexFragment extends BaseFragment {

    @Event(value = R.id.btn)
    private void onBtnClick(View view) {
        ui.toast(ui.getLocalClassName());
    }


}

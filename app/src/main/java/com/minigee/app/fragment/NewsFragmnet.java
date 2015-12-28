package com.minigee.app.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.minigee.app.R;
import com.minigee.app.adapter.KrNewsAdapter;
import com.minigee.app.base.BaseFragment;
import com.minigee.app.base.BaseMessage;
import com.minigee.app.base.C;
import com.minigee.app.http.KrNewsParams;
import com.minigee.app.model.KrNews;
import com.minigee.app.util.AppUtil;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import java.util.ArrayList;

/**
 * Created by Zhou on 2015-12-4.
 */
@ContentView(R.layout.fragment_news)
public class NewsFragmnet extends BaseFragment {
    private View root;
    private KrNewsAdapter kradapter;
    private String kr_lastId = "0";

    @ViewInject(R.id.kr_rv)
    private RecyclerView rv;

    @ViewInject(R.id.kr_swip)
    private SwipeRefreshLayout swipe;


    @Event(value = R.id.btn)
    private void onBtnClick(View view) {
        ui.toast("news");
    }


    public void initData() {
        //net work
        getNewsByNet(kr_lastId);
    }

    public void getNewsByNet(String lastId) {
        KrNewsParams params = new KrNewsParams();
        params.lastId = kr_lastId;
        params.setConnectTimeout(C.netconn.conntime);
        params.setCacheMaxAge(1000 * 60);
        onnet();
        x.http().get(params, new Callback.CacheCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.w("AppDebug", "success");
                // handle
                try {
                    BaseMessage message = AppUtil.getMessage(result);
                    if (message.getStatus().equals("1")) {
//                        ArrayList<KrNews> news = (ArrayList<KrNews>) message.getResultList("KrNews");
                        ArrayList<KrNews> news = (ArrayList<KrNews>) JSON.parseArray(message.getResult(), KrNews.class);

                        kradapter.update(news, kr_lastId);
                        kr_lastId = news.get(news.size() - 1).getFeedId();
                        kradapter.notifyDataSetChanged();
                    } else {

                        ui.toast(message.getInfo());
                    }

                } catch (Exception e) {
                    ui.toast(e.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                if (ex instanceof ConnectException) {  // 没有网络   网址错（快速反映，无等待）
                    if (ex instanceof SocketException) { // 网址错
                        ui.toast(C.err.netnone);
                    } else {
                        ui.toast(C.err.netdown);
                    }
                } else if (ex instanceof SocketTimeoutException) {  // 有网   正在连接中状态，无响应（nginx error页面）
                    ui.toast(C.err.nettime);
                } else {
                    ui.toast("没有此控制器");
                }


            }

            @Override
            public void onCancelled(CancelledException cex) {
                ui.toast(C.err.netnone);
            }

            @Override
            public void onFinished() {
//                Log.w("AppDebug", "net finished");
                overnet();
            }

            @Override
            public boolean onCache(String result) {
                // 得到缓存数据, 缓存过期后不会进入这个方法.
                // 如果服务端没有返回过期时间, 参考params.setCacheMaxAge(maxAge)方法.
                //
                // * 客户端会根据服务端返回的 header 中 max-age 或 expires 来确定本地缓存是否给 onCache 方法.
                //   如果服务端没有返回 max-age 或 expires, 那么缓存将一直保存, 除非这里自己定义了返回false的
                //   逻辑, 那么xUtils将请求新数据, 来覆盖它.
                //
                // * 如果信任该缓存返回 true, 将不再请求网络;
                //   返回 false 继续请求网络, 但会在请求头中加上ETag, Last-Modified等信息,
                //   如果服务端返回304, 则表示数据没有更新, 不继续加载数据.
                //
                Log.w("AppDebug", "cache");
                ui.toast("cache");
                return true; // true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void initView() {

        final LinearLayoutManager llm = new LinearLayoutManager(ui);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        kradapter = new KrNewsAdapter(ui);

        kradapter.setOnItemClickListener(new KrNewsAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String argu) {
                ui.toast("click：" + argu);
            }
        });
        kradapter.setOnItemLongClickListener(new KrNewsAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLognClick(View view, String argu) {
                ui.toast("longclick：" + argu);
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && llm.findLastVisibleItemPosition() + 1 == kradapter.getItemCount()) {
                    if (swipe.getTag().equals("over")) {
                        getNewsByNet(kr_lastId);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        rv.setAdapter(kradapter);

//        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//
//        int sl = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());
//        swipe.setProgressViewOffset(false, 0, sl);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (swipe.getTag().equals("over")) {
                    kr_lastId = "0";
                    getNewsByNet(kr_lastId);
                }
            }
        });

    }

    public void onnet() {
        swipe.setRefreshing(true);
        swipe.setEnabled(false);
        swipe.setTag("on");
    }

    public void overnet() {
        swipe.setRefreshing(false);
        swipe.setEnabled(true);
        swipe.setTag("over");
    }
}

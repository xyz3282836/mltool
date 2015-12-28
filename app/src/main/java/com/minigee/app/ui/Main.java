package com.minigee.app.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.minigee.app.R;
import com.minigee.app.adapter.TabFragmentPagerAdapater;
import com.minigee.app.base.BaseUi;
import com.minigee.app.base.BaseUiAuth;
import com.minigee.app.fragment.ActivityFragment;
import com.minigee.app.fragment.ExchangeFragment;
import com.minigee.app.fragment.FinancingFragment;
import com.minigee.app.fragment.IndexFragment;
import com.minigee.app.fragment.NewsFragmnet;
import com.minigee.app.fragment.PolicyFragment;
import com.minigee.app.model.User;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


@ContentView(R.layout.ui_main)
public class Main extends BaseUi
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<Fragment> listf = new ArrayList<>();

    @ViewInject(R.id.container)
    private ViewPager mViewPager;

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.fab)
    private FloatingActionButton fab;

    @ViewInject(R.id.drawer_layout)
    private DrawerLayout drawer;

    @ViewInject(R.id.nav_view)
    private NavigationView navigationView;

    @ViewInject(R.id.tabs)
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //toolbar
        toolbar.setTitle("未登入");
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        listf = getFragments();
        mViewPager.setAdapter(new TabFragmentPagerAdapater(getSupportFragmentManager(), listf));
        mViewPager.setOffscreenPageLimit(listf.size()-1);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public List<Fragment> getFragments(){

        Fragment index = new IndexFragment();

        Fragment exchange = new ExchangeFragment();

        Fragment activity = new ActivityFragment();
        Fragment financing = new FinancingFragment();
        Fragment policy = new PolicyFragment();
        Fragment news = new NewsFragmnet();
        listf.add(index);
        listf.add(exchange);
        listf.add(activity);
        listf.add(financing);
        listf.add(policy);
        listf.add(news);

        return listf;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.menu_icon:
                overlay(Icon.class);
                break;
            case R.id.action_test:
                overlay(Login.class);
                break;
            case R.id.action_settings:
                overlay(Setting.class);
                break;
            case R.id.action_logout:
                logOut();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (User.getInstance().getLogin()){
            toolbar.setTitle(User.getInstance().getName());
        } else{
            toolbar.setTitle(getResources().getString(R.string.status_account_no_login));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (User.getInstance().getLogin()){
            MenuItem menuid = menu.findItem(R.id.action_logout);
            if (menuid==null){

                menu.add(0,R.id.action_logout,Menu.FIRST,R.string.action_logout);
            }
        }else{
            menu.removeItem(R.id.action_logout);
        }

        return super.onPrepareOptionsMenu(menu);
    }
}

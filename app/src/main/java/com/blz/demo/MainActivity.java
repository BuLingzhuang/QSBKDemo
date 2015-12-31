package com.blz.demo;

import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.blz.demo.adapters.DefaultAdapter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout drawerLayout;
    long fristTime = 0L;
    private NavigationView navigationView;
    private TabLayout tabLayout;
    private ActionBarDrawerToggle toggle;
    private ViewPager viewPager;
    private List<String> list;
    private DefaultAdapter adapter;
    private static final String TAG = "BLZ";
    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        //设置actionBar下面的阴影为0
        getSupportActionBar().setElevation(0);
        navigationView = ((NavigationView)findViewById(R.id.main_navigationView));
        drawerLayout = ((DrawerLayout)findViewById(R.id.main_drawerLayout));
        viewPager = ((ViewPager)findViewById(R.id.main_viewPager));
        tabLayout = ((TabLayout)findViewById(R.id.main_tabLayout));
        toggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);
        list = new ArrayList<>();
        list.add("专享");
        list.add("视频");
        list.add("纯文");
        list.add("纯图");
        list.add("最新");
        adapter = new DefaultAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
    {
        if ((paramKeyEvent.getAction() == 0) && (4 == paramInt))
        {
            long currentTime = System.currentTimeMillis();
            if (currentTime - fristTime >= 2000L)
            {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                fristTime = currentTime;
            }else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(paramInt, paramKeyEvent);
    }

    public boolean onNavigationItemSelected(MenuItem paramMenuItem)
    {
        list.clear();
        switch (paramMenuItem.getItemId()) {
            case R.id.navigation_item_qiushi:
                break;
            case R.id.navigation_item_qiuyouquan:
                break;
            case R.id.navigation_item_faxian:
                break;
            case R.id.navigation_item_xiaozhitiao:
                break;
            case R.id.navigation_item_self_login:
                break;
            case R.id.navigation_item_self_collect:
                break;
            case R.id.navigation_item_self_finish:
                //清除当前Activity所在栈的所有内容，一般只有一个栈的话就是退出程序了
                ActivityCompat.finishAffinity(this);
                break;
        }
        list.add("专享");
        list.add("视频");
        list.add("纯文");
        list.add("纯图");
        list.add("最新");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
        boolean bool = super.onOptionsItemSelected(paramMenuItem);
        if (toggle.onOptionsItemSelected(paramMenuItem)) {
            bool = true;
        }
        return bool;
    }
}

package org.vincent.vcshop;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.vincent.vcshop.widget.PagerTab;

public class MainActivity extends AppCompatActivity {

    private PagerTab tabs;
    private ViewPager pager;
    private RelativeLayout content_frame;
    private FrameLayout start_drawer;
    private DrawerLayout drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        tabs = (PagerTab) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        content_frame = (RelativeLayout) findViewById(R.id.content_frame);
        start_drawer = (FrameLayout) findViewById(R.id.start_drawer);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }
}

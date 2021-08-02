package fpoly.andoid.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.ListView;


import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import Adapter.LoaiChiAdapter;
import Adapter.PagerAdapter;
import DAO.TbLoaiChi;
import DTO.LoaiChi;

public class ShowListActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    PagerAdapter pagerAdapter;
    AppCompatButton btnAdd;
    TbLoaiChi tbLoaiChi;
    LoaiChiAdapter adapter;
    ArrayList<LoaiChi> list;
    ListView listView;
    int check = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        toolbar = findViewById(R.id.toolbar_list);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new PagerAdapter(this));

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText("Chi tiêu");
                        break;
                    }
                    case 1: {
                        tab.setText("Thu nhập");
                        break;
                    }
                }
            }
        }
        );
        tabLayoutMediator.attach();
    }
}
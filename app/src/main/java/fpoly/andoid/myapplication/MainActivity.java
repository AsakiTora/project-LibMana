package fpoly.andoid.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import Adapter.ChiFragment;
import Adapter.KhoanChiFragment;
import Adapter.PagerAdapter;
import Adapter.PagerAdapterMain;

public class MainActivity extends AppCompatActivity {

    private BottomAppBar bottomAppBar;
    private FloatingActionButton floatingActionButton;
    private NavController navController;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView tvDate, tv_chu_ki, tv_chu_ki2, tvMoney;
    private ImageView imgDate, imgMoney;
    private EditText edt_money;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private PagerAdapterMain pagerAdapterMain;
    private String mDate = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawerLayout);
        anhXa();
        floatingActionButton = findViewById(R.id.flAdd);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_setting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                }
                return true;
            }
        });

        findViewById(R.id.action_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        findViewById(R.id.action_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowListActivity.class));
            }
        });

        findViewById(R.id.action_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GraphActivity.class));
            }
        });
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tvDate.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));

        imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        tvDate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, nam, thang, ngay);
                datePickerDialog.show();
            }
        });

        imgMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View customLayout = getLayoutInflater().inflate(R.layout.edittext_dialog, null);
                edt_money = customLayout.findViewById(R.id.edt_money);
                builder.setView(customLayout);
                builder.setTitle("Số tiền");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvMoney.setText(edt_money.getText().toString());
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();

                dialog.show();
            }
        });
//        Intent intent = getIntent();
//        Bundle bundle =intent.getExtras();
//
//        if (bundle != null){
//            String money = bundle.getString("money", "");
//            String date_start = bundle.getString("ns_date_start", "");
//            String date_end = bundle.getString("date_end", "");
//
//            tv_chu_ki.setText(date_start);
//            tv_chu_ki2.setText(date_end);
//            tvMoney.setText(money);
//        }
        viewPager2 = findViewById(R.id.viewPager3);
        viewPager2.setAdapter(new PagerAdapterMain(this));
        tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0 :
                        tab.setText("Khoản Thu");
                        break;
                    case 1 :
                        tab.setText("Khoản Chi");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
        sendData();
    }

    public void anhXa() {
        tvDate = findViewById(R.id.tvDate);
        tv_chu_ki = findViewById(R.id.tv_chu_ki);
        tv_chu_ki2 = findViewById(R.id.tv_chu_ki2);
        tvMoney = findViewById(R.id.tvMoney);
        imgDate = findViewById(R.id.imgDate);
        imgMoney = findViewById(R.id.imgMoney);

    }
    public void sendData(){
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences !=null) {
            String mDate2 = sharedPreferences.getString("date","");
            editor.clear();
            editor.apply();
            mDate = mDate2;
        }

    }


}
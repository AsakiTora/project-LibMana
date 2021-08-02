package fpoly.andoid.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Adapter.KhoanChiFragment;
import Adapter.PagerAdapter;
import DAO.TbKhoanChi;
import DAO.TbKhoanThu;
import DTO.KhoanChi;
import DTO.KhoanThu;

public class AddActivity extends AppCompatActivity{

    Toolbar toolbar;
    ImageView img_item_add;
    TextView tv_show_date, tv_show_time, tv_show_name;
    EditText edt_note, ed_money;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    SimpleDateFormat simpleDateFormat;
    FloatingActionButton ftButton;
    TbKhoanChi tbKhoanChi;
    TbKhoanThu tbKhoanThu;
    List<KhoanThu> listThu;
    KhoanThu khoanThu;
    List<KhoanChi> list;
    KhoanChi khoanChi;
    int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        toolbar = findViewById(R.id.toolbar_add);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        bottomSheet();
        flButton();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tv_show_date.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        tv_show_time.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));

//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        if (bundle != null){
//            String loaithu = bundle.getString("name", "");
//            tv_show_name.setText(loaithu);
//            Bitmap bitmapimage = bundle.getParcelable("img");
//            img_item_add.setImageBitmap(bitmapimage);
//
//        }


    }
    public void getEdit(String name, Bitmap img,int version){
        tv_show_name.setText(name);
        img_item_add.setImageBitmap(img);
        check = version;
        Toast.makeText(getBaseContext(), name,Toast.LENGTH_SHORT).show();
    }

    public void bottomSheet(){
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

    private void initView() {
        ftButton = findViewById(R.id.ftButton);
        ed_money = findViewById(R.id.ed_money);
        edt_note = findViewById(R.id.edt_note);
        tv_show_date = findViewById(R.id.tv_show_date);
        tv_show_time = findViewById(R.id.tv_show_time);
        tv_show_name = findViewById(R.id.tv_show_name);
        img_item_add = findViewById(R.id.img_item_add);
        tabLayout = findViewById(R.id.tabLayout2);
        viewPager2 = findViewById(R.id.viewPager2);
    }

    public void date(View v){
        ChonNgay();
    }

    public void time(View v){
        ChonGio();
    }

    private void ChonGio(){
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(java.util.Calendar.HOUR_OF_DAY);
        int phut = calendar.get(java.util.Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                calendar.set(0, 0, 0, hourOfDay, minute);
                tv_show_time.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, gio, phut, true);
        timePickerDialog.show();
    }

    private void ChonNgay() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(java.util.Calendar.DATE);
        int thang = calendar.get(java.util.Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(year, month, dayOfMonth);
                tv_show_date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    public void flButton(){
        ftButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String date = tv_show_date.getText().toString().trim();
                double money = Double.parseDouble(ed_money.getText().toString().trim());
                String name = tv_show_name.getText().toString().trim();
                String note = edt_note.getText().toString().trim();
                BitmapDrawable bitmapDrawable = (BitmapDrawable)img_item_add.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] image = byteArrayOutputStream.toByteArray();
                // Chuyển kiểu về kiểu byte
                if (check == 1){
                    tbKhoanChi = new TbKhoanChi(AddActivity.this);
                    tbKhoanChi.open();
                    khoanChi = new KhoanChi();
                    khoanChi.setDate(date);
                    khoanChi.setImage(image);
                    khoanChi.setName(name);
                    khoanChi.setNote(note);
                    khoanChi.setMoney(money);
                    list = new ArrayList<KhoanChi>();

                    long res = tbKhoanChi.Add(khoanChi);
                    list.addAll(tbKhoanChi.GetAll());
                    if (res > 0){
                        Toast.makeText(AddActivity.this, "Add Complete", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if (check == 2){
                    tbKhoanThu = new TbKhoanThu(AddActivity.this);
                    tbKhoanThu.open();
                    khoanThu = new KhoanThu();
                    khoanThu.setDate(date);
                    khoanThu.setImage(image);
                    khoanThu.setName(name);
                    khoanThu.setNote(note);
                    khoanThu.setMoney(money);
                    listThu = new ArrayList<KhoanThu>();

                    long res = tbKhoanThu.Add(khoanThu);
                    listThu.addAll(tbKhoanThu.GetAll());
                    if (res > 0){
                        Toast.makeText(AddActivity.this, "Add Complete", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

}
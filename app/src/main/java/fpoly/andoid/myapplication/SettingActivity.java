package fpoly.andoid.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    TextView tv_date_ns_start, tv_date_ns_end, tv_date_sd_end, tv_date_sd_start, tv_money_ns, tv_money_sd, tv_show_money_ns, tv_show_money_sd;
    EditText edt_money;
    SwitchCompat sw_ns, sw_sd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar = findViewById(R.id.toolbar_setting);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_date_ns_start = findViewById(R.id.tv_date_ns_start);
        tv_date_ns_end = findViewById(R.id.tv_date_ns_end);
        tv_date_sd_start = findViewById(R.id.tv_date_sd_start);
        tv_date_sd_end = findViewById(R.id.tv_date_sd_end);

        sw_ns = findViewById(R.id.sw_ns);
        sw_sd = findViewById(R.id.sw_sd);
        tv_money_ns = findViewById(R.id.tv_money_ns);
        tv_money_sd = findViewById(R.id.tv_money_sd);
        tv_show_money_ns = findViewById(R.id.tv_show_money_ns);
        tv_show_money_sd = findViewById(R.id.tv_show_money_sd);

        tv_date_ns_start.setOnClickListener(this);
        tv_date_ns_end.setOnClickListener(this);
        tv_date_sd_start.setOnClickListener(this);
        tv_date_sd_end.setOnClickListener(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tv_date_ns_start.setText("Từ " + simpleDateFormat.format(Calendar.getInstance().getTime()));
        tv_date_ns_end.setText("Đến " + simpleDateFormat.format(Calendar.getInstance().getTime()));
        tv_date_sd_start.setText("Từ " + simpleDateFormat.format(Calendar.getInstance().getTime()));
        tv_date_sd_end.setText("Đến " + simpleDateFormat.format(Calendar.getInstance().getTime()));

        sw_ns.setOnClickListener(this);
        sw_sd.setOnClickListener(this);

        tv_money_sd.setOnClickListener(this);
        tv_money_ns.setOnClickListener(this);

        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();

        String money_ns = tv_money_ns.getText().toString();
        String date_start_ns = tv_date_ns_start.getText().toString();
        String date_end_ns = tv_date_ns_end.getText().toString();

        String money_sd = tv_money_sd.getText().toString();
        String date_start_sd = tv_date_sd_start.getText().toString();
        String date_end_sd = tv_date_sd_end.getText().toString();
        if (sw_ns.isChecked()){
            bundle.putString("money", money_ns);
            bundle.putString("date_start", date_start_ns);
            bundle.putString("date_end", date_end_ns);
            intent.putExtras(bundle);
        } else if (sw_sd.isChecked()){
            bundle.putString("money", money_sd);
            bundle.putString("date_start_sd", date_start_sd);
            bundle.putString("date_end", date_end_sd);
            intent.putExtras(bundle);
        }
    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        View customLayout = getLayoutInflater().inflate(R.layout.edittext_dialog, null);
        builder.setView(customLayout);
        builder.setTitle("Số tiền");

        edt_money = customLayout.findViewById(R.id.edt_money);
        switch (v.getId()){
            case R.id.tv_date_ns_start:

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        calendar.set(year, month, dayOfMonth);
                        tv_date_ns_start.setText("Từ " + simpleDateFormat.format(calendar.getTime()));
                        String ns_date_start = tv_date_ns_start.getText().toString();
                        bundle.putString("ns_date_start", ns_date_start);
                        intent.putExtras(bundle);
                    }
                }, nam, thang, ngay);
                datePickerDialog.show();
                break;

            case R.id.tv_date_ns_end:

                DatePickerDialog datePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        calendar.set(year, month, dayOfMonth);
                        tv_date_ns_end.setText("Đến " + simpleDateFormat.format(calendar.getTime()));
                        String ns_date_end = tv_date_ns_start.getText().toString();
                        bundle.putString("ns_date_end", ns_date_end);
                    }
                }, nam, thang, ngay);
                datePickerDialog1.show();
                break;

            case R.id.tv_date_sd_start:
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        calendar.set(year, month, dayOfMonth);
                        tv_date_sd_start.setText("Từ " + simpleDateFormat.format(calendar.getTime()));
                        String sd_date_start = tv_date_ns_start.getText().toString();
                        bundle.putString("sd_date_start", sd_date_start);
                    }
                }, nam, thang, ngay);
                datePickerDialog2.show();
                break;

            case R.id.tv_date_sd_end:
                DatePickerDialog datePickerDialog3 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        calendar.set(year, month, dayOfMonth);
                        tv_date_sd_end.setText("Đến " + simpleDateFormat.format(calendar.getTime()));
                        String sd_date_end = tv_date_ns_start.getText().toString();
                        bundle.putString("sd_date_end", sd_date_end);
                    }
                }, nam, thang, ngay);
                datePickerDialog3.show();
                break;

            case R.id.sw_ns:
                sw_ns.setChecked(true);
                sw_sd.setChecked(false);
                tv_money_ns.setEnabled(true);
                tv_show_money_ns.setEnabled(true);
                tv_date_ns_start.setEnabled(true);
                tv_date_ns_end.setEnabled(true);
                tv_money_sd.setEnabled(false);
                tv_show_money_sd.setEnabled(false);
                tv_date_sd_start.setEnabled(false);
                tv_date_sd_end.setEnabled(false);
                break;

            case R.id.sw_sd:
                sw_sd.setChecked(true);
                sw_ns.setChecked(false);
                tv_money_ns.setEnabled(false);
                tv_show_money_ns.setEnabled(false);
                tv_date_ns_start.setEnabled(false);
                tv_date_ns_end.setEnabled(false);
                tv_money_sd.setEnabled(true);
                tv_show_money_sd.setEnabled(true);
                tv_date_sd_start.setEnabled(true);
                tv_date_sd_end.setEnabled(true);
                break;

            case R.id.tv_money_sd:
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_show_money_sd.setText(edt_money.getText().toString());
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
                break;

            case R.id.tv_money_ns:
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_show_money_ns.setText(edt_money.getText().toString());
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog1 = builder.create();

                dialog1.show();
                break;
        }
    }
}
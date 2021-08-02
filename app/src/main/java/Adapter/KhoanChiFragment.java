package Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import DAO.TbKhoanChi;
import DAO.TbLoaiChi;
import DTO.KhoanChi;
import DTO.LoaiChi;
import fpoly.andoid.myapplication.AddActivity;
import fpoly.andoid.myapplication.MainActivity;
import fpoly.andoid.myapplication.R;
import fpoly.andoid.myapplication.ShowListActivity;

import static android.app.Activity.RESULT_OK;

public class KhoanChiFragment extends Fragment {

    KhoanChiAdapter adapter;
    TbKhoanChi tbKhoanChi;
    ListView listView;
    ArrayList<KhoanChi> list;
    View view;
    ImageView img_add;
    TextView tvKhoan, tv_date, tv_money, tv_money_2;


    public KhoanChiFragment() {

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_khoan_chi, container, false);
        tvKhoan = view.findViewById(R.id.tvKhoan);
        tv_date = view.findViewById(R.id.tv_date);
        listView = view.findViewById(R.id.lvKhoanChi);
        img_add = view.findViewById(R.id.img_add);
        tv_money = view.findViewById(R.id.tv_money);
        tv_money_2 = view.findViewById(R.id.tv_money_2);

        tbKhoanChi = new TbKhoanChi(getActivity());
        tbKhoanChi.open();
        list = tbKhoanChi.GetAll();
        adapter = new KhoanChiAdapter(getActivity(), list);


        listView.setAdapter(adapter);


        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
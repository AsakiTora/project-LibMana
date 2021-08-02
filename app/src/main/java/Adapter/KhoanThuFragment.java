package Adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import DAO.TbKhoanChi;
import DAO.TbKhoanThu;
import DTO.KhoanChi;
import DTO.KhoanThu;
import fpoly.andoid.myapplication.R;


public class KhoanThuFragment extends Fragment {
    KhoanThuAdapter adapter;
    TbKhoanThu tbKhoanThu;
    ListView listView;
    ArrayList<KhoanThu> listThu;
    View view;
    ImageView img_add;
    TextView tvKhoan, tv_date, tv_money, tv_money_2;

    public KhoanThuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_khoan_thu, container, false);
        tvKhoan = view.findViewById(R.id.tvKhoan);
        tv_date = view.findViewById(R.id.tv_date);
        listView = view.findViewById(R.id.lvKhoanThu);
        img_add = view.findViewById(R.id.img_add);
        tv_money = view.findViewById(R.id.tv_money);
        tv_money_2 = view.findViewById(R.id.tv_money_2);

        tbKhoanThu = new TbKhoanThu(getActivity());
        tbKhoanThu.open();
        listThu = tbKhoanThu.GetAll();
        adapter = new KhoanThuAdapter(getActivity(),listThu);
        listView.setAdapter(adapter);


        return view;
    }
}
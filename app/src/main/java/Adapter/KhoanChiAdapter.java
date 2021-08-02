package Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import DAO.TbKhoanChi;
import DTO.KhoanChi;
import DTO.LoaiChi;
import fpoly.andoid.myapplication.R;

public class KhoanChiAdapter extends BaseAdapter {
    Context context;
    TbKhoanChi tbKhoanChi;
    KhoanChiAdapter adapter;
    ArrayList<KhoanChi> list;
    KhoanChi khoanChi;
    public KhoanChiAdapter(Context context, ArrayList<KhoanChi> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        if (list == null){
            return 0;
        }else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    public class ViewHolder{
        public ImageView imageView;
        public TextView loaichi, date, money,money2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view = null;

        if(convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_show_add, null);
            view = new KhoanChiAdapter.ViewHolder();
            view.imageView = convertView.findViewById(R.id.imgKhoan);
            view.loaichi = convertView.findViewById(R.id.tvKhoan);
            view.date = convertView.findViewById(R.id.tv_date);
            view.money = convertView.findViewById(R.id.tv_money);
            view.money2 = convertView.findViewById(R.id.tv_money_2);
            convertView.setTag(view);
        }else {
            view = (ViewHolder) convertView.getTag();
        }
        khoanChi = (KhoanChi) list.get(position);
        byte[] img = khoanChi.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        view.imageView.setImageBitmap(bitmap);
        view.loaichi.setText(khoanChi.getName());
        view.money.setText(String.valueOf(khoanChi.getMoney()));
        view.money2.setText(String.valueOf(khoanChi.getMoney()));
        view.date.setText(khoanChi.getDate());

        return convertView;
    }
}

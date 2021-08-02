package Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import DAO.TbLoaiChi;
import DAO.TbLoaiThu;
import DTO.LoaiChi;
import DTO.LoaiThu;
import fpoly.andoid.myapplication.AddActivity;
import fpoly.andoid.myapplication.MainActivity;
import fpoly.andoid.myapplication.R;

public class LoaiThuAdapter extends BaseAdapter {

    Context context;
    TbLoaiThu tbLoaiThu;
    ArrayList<LoaiThu> list;
    LoaiThuAdapter adapter;
    LoaiThu loaiThu;
    ImageView img_add;
    EditText edt_add;
    ImageButton ivEdit, ivDel;
    TextView tv_content_add;
    final int RES_CODE_CAMERA = 1;
    final int RES_CODE_FOLDER = 2;
    public LoaiThuAdapter(Context context, ArrayList<LoaiThu> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        public TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view = null;

        if(convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_show_list, null);
            view = new ViewHolder();
            view.imageView = convertView.findViewById(R.id.imgIcon);
            view.name = convertView.findViewById(R.id.tvLoaiChi);
            convertView.setTag(view);
        }else {
            view = (ViewHolder) convertView.getTag();
        }

        loaiThu = (LoaiThu) list.get(position);

        byte[] img = loaiThu.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        view.imageView.setImageBitmap(bitmap);
        view.name.setText(loaiThu.getName());

        ivEdit = convertView.findViewById(R.id.ivEdit);
        ivDel = convertView.findViewById(R.id.ivDel);
        String name = view.name.getText().toString().trim();
        view.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int version = 2;
                ((AddActivity)context).getEdit(name, bitmap,version);

            }
        });
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomDialog(Gravity.CENTER);
            }
        });

        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Bạn có chắc chắn muốn xóa?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        del();
                    }
                });
                builder.create();
                builder.show();
            }
        });
        return convertView;
    }

    public void del(){
        tbLoaiThu = new TbLoaiThu(context);
        tbLoaiThu.open();
        int i = tbLoaiThu.Delete(loaiThu);
        if (i > 0) {
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            list.clear();
            list.addAll(tbLoaiThu.GetAll());
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }
        tbLoaiThu.close();
    }
    private void openCustomDialog(int gravity) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);

        tv_content_add = dialog.findViewById(R.id.tv_content_add);
        edt_add = dialog.findViewById(R.id.edt_add);
        img_add = dialog.findViewById(R.id.img_add);
        RelativeLayout add_img = dialog.findViewById(R.id.add_img);
        RelativeLayout upload_img = dialog.findViewById(R.id.upload_img);

        tv_content_add.setText("Cập nhật");
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.CAMERA}, RES_CODE_CAMERA);
            }
        });

        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RES_CODE_FOLDER);
            }
        });

        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnCal = dialog.findViewById(R.id.btnCal);


        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img_add.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] hinhAnh = byteArrayOutputStream.toByteArray();
                loaiThu.setName(edt_add.getText().toString());
                loaiThu.setImage(hinhAnh);
                tbLoaiThu = new TbLoaiThu(context);

                tbLoaiThu.open();

                int res = tbLoaiThu.Update(loaiThu);
                if (res > 0) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                    list.clear();
                    list.addAll(tbLoaiThu.GetAll());
                    adapter = new LoaiThuAdapter(context, list);
                    notifyDataSetChanged();
                    img_add.setImageResource(R.drawable.img);
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
                tbLoaiThu.close();
            }
        });
        dialog.show();

    }

}

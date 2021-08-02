package Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
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

import DAO.TbLoaiChi;
import DTO.LoaiChi;
import fpoly.andoid.myapplication.AddActivity;
import fpoly.andoid.myapplication.R;
import fpoly.andoid.myapplication.ShowListActivity;

import static android.app.Activity.RESULT_OK;

public class ChiFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    LoaiChiAdapter adapter;
    TbLoaiChi tbLoaiChi;
    ListView listView;
    ArrayList<LoaiChi> list;
    View view;
    ImageView img_add;
    EditText edt_add;
    TextView tv_content_add;

    final int RES_CODE_CAMERA = 1;
    final int RES_CODE_FOLDER = 2;
    public static SharedPreferences sharedPreferences;
    public ChiFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chi, container, false);
        floatingActionButton = view.findViewById(R.id.btn_add_chi);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomDialog(Gravity.CENTER);
            }
        });
        listView = view.findViewById(R.id.lvChi);
        tbLoaiChi = new TbLoaiChi(getActivity());
        tbLoaiChi.open();
        list = tbLoaiChi.GetAll();
        adapter = new LoaiChiAdapter(getActivity(), list);

        listView.setAdapter(adapter);


        return view;
    }



    private void openCustomDialog(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
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

        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, RES_CODE_CAMERA);
            }
        });

        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RES_CODE_FOLDER);
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
                LoaiChi loaiChi = new LoaiChi();
                loaiChi.setName(edt_add.getText().toString());
                loaiChi.setImage(hinhAnh);
                tbLoaiChi = new TbLoaiChi(getActivity());
                tbLoaiChi.open();

                long res = tbLoaiChi.Add(loaiChi);
                if (res > 0) {
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    tv_content_add.setText("");
                    list.clear();
                    list.addAll(tbLoaiChi.GetAll());
                    adapter.notifyDataSetChanged();
                    img_add.setImageResource(R.drawable.img);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RES_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, RES_CODE_CAMERA);
                } else {
                    Toast.makeText(getActivity(), "Bạn không cho phép mở CAMERA!", Toast.LENGTH_SHORT).show();
                }
                break;
            case RES_CODE_FOLDER:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, RES_CODE_FOLDER);
                } else {
                    Toast.makeText(getActivity(), "Bạn không cho phép truy cập thư mục Ảnh!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RES_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img_add.setImageBitmap(bitmap);
        }
        if (requestCode == RES_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_add.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tbLoaiChi.close();
    }

}
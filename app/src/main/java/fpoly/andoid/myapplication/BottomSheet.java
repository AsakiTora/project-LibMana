//package fpoly.andoid.myapplication;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.viewpager2.widget.ViewPager2;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Adapter;
//import android.widget.AdapterView;
//import android.widget.ListView;
//
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
//import com.google.android.material.tabs.TabLayout;
//import com.google.android.material.tabs.TabLayoutMediator;
//
//import Adapter.PagerAdapter;
//import DTO.LoaiChi;
//
//public class BottomSheet extends BottomSheetDialogFragment {
//    TabLayout tabLayout;
//    ViewPager2 viewPager2;
//    LoaiChi loaiChi;
//    OnSelectedListener mCallback;
//    public interface OnSelectedListener {
//        public void onSelected(String value);
//    }
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
//        tabLayout = view.findViewById(R.id.tabLayout2);
//        viewPager2 = view.findViewById(R.id.viewPager2);
//        bottomSheet();
//        return view;
//
//    }
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            mCallback = (OnSelectedListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement OnSelectedListener");
//        }
//    }
//
//    public void bottomSheet(){
//        viewPager2.setAdapter(new PagerAdapter(getActivity()));
//
//        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
//                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
//            @Override
//            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                switch (position) {
//                    case 0: {
//                        tab.setText("Chi tiêu");
//
//
//                        break;
//                    }
//                    case 1: {
//                        tab.setText("Thu nhập");
//                        break;
//                    }
//                }
//            }
//        }
//        );
//        tabLayoutMediator.attach();
//    }
//}
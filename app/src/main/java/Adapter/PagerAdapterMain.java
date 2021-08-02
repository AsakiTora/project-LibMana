package Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapterMain extends FragmentStateAdapter {

    public PagerAdapterMain(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 :
                return new KhoanThuFragment();
            case 1 :
                return new KhoanChiFragment();
            default:
                return new KhoanThuFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

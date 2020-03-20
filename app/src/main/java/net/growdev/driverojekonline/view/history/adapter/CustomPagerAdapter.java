package net.growdev.driverojekonline.view.history.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import net.growdev.driverojekonline.view.history.HistoryFragment;

public class CustomPagerAdapter extends FragmentStatePagerAdapter {

    public CustomPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        if (position==0){
            return new HistoryFragment(1);
        }else if (position==1){
            return new HistoryFragment(2);
        }else{
            return new HistoryFragment(4);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


}

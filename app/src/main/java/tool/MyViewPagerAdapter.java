package tool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.suixinweather.WeatherFragment;

import java.util.List;

public class MyViewPagerAdapter extends FragmentPagerAdapter {


    public List<WeatherFragment> fragments;
    public List<String> pageTitles;

    public MyViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    public MyViewPagerAdapter(@NonNull FragmentManager fm, List<WeatherFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public MyViewPagerAdapter(@NonNull FragmentManager fm, List<WeatherFragment> fragments,List<String> pageTitles) {
        super(fm);
        this.fragments = fragments;
        this.pageTitles = pageTitles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles.get(position);
    }

    public void setFragments(List<WeatherFragment> fragments){
        this.fragments = fragments;
    }
    public void setPageTitles(List<String> titles){
        this.pageTitles = titles;
    }
}

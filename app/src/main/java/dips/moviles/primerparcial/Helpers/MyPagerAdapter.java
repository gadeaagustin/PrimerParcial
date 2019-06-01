package dips.moviles.primerparcial.Helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class MyPagerAdapter extends FragmentPagerAdapter {

    /*
    PAGES
     */
    ArrayList<Fragment> pages=new ArrayList<>();

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position==1)
            return "Usuarios";
        if (position==2)
            return "Busqueda";
        else
            return pages.get(position).toString();
    }


    public void addPage(Fragment f)
    {
        pages.add(f);
    }
}
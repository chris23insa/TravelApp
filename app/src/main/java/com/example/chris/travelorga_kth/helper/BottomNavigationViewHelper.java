package com.example.chris.travelorga_kth.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.Map.MapsActivity;
import com.example.chris.travelorga_kth.Profile.ProfileActivity;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.SearchActivity;

import java.lang.reflect.Field;

public class BottomNavigationViewHelper {
    @SuppressLint("RestrictedApi")
    public static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            menuView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);

                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BottomNav", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BottomNav", "Unable to change value of shift mode", e);
        }
    }

    public static BottomNavigationView.OnNavigationItemSelectedListener getFinishListener(Activity activity) {
        return item -> {
    switch (item.getItemId()) {
        case R.id.action_trips:
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
            return true;
        case R.id.action_search:
            Intent intentSearch = new Intent(activity, SearchActivity.class);
            activity.startActivity(intentSearch);
            activity.finish();
            return true;
        case R.id.action_profile:
            Intent intentProfile = new Intent(activity, ProfileActivity.class);
            activity.startActivity(intentProfile);
            activity.finish();
            return true;
        case R.id.action_map:
            Intent intentMap = new Intent(activity, MapsActivity.class);
            activity.startActivity(intentMap);
            activity.finish();
            return true;
    }
    return false;
};
    }

    public static BottomNavigationView.OnNavigationItemSelectedListener getListener(Activity activity) {
        return item -> {
    switch (item.getItemId()) {
        case R.id.action_trips:
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            return true;
        case R.id.action_search:
            Intent intentSearch = new Intent(activity, SearchActivity.class);
            activity.startActivity(intentSearch);
            return true;
        case R.id.action_profile:
            Intent intentProfile = new Intent(activity, ProfileActivity.class);
            activity.startActivity(intentProfile);
            return true;
        case R.id.action_map:
            Intent intentMap = new Intent(activity, MapsActivity.class);
            activity.startActivity(intentMap);
            return true;
    }
    return false;
};
    }

    public static void setupNav(Activity c, int id){
        BottomNavigationView navigation = c.findViewById(R.id.bottom_nav);
        navigation.setSelectedItemId(id);
        navigation.setOnNavigationItemSelectedListener(BottomNavigationViewHelper.getListener(c));
    }

    public static void setupNavFinish(Activity c, int id){
        BottomNavigationView navigation = c.findViewById(R.id.bottom_nav);
        navigation.setSelectedItemId(id);
        navigation.setOnNavigationItemSelectedListener(BottomNavigationViewHelper.getFinishListener(c));
    }

}
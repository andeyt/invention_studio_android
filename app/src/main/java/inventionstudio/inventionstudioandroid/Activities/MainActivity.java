package inventionstudio.inventionstudioandroid.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.lang.reflect.Field;

import inventionstudio.inventionstudioandroid.Fragments.FeedbackFragment;
import inventionstudio.inventionstudioandroid.Fragments.HomeFragment;
import inventionstudio.inventionstudioandroid.Fragments.MachineGroupFragment;
import inventionstudio.inventionstudioandroid.Fragments.MoreFragment;
import inventionstudio.inventionstudioandroid.Fragments.QueueFragment;
import inventionstudio.inventionstudioandroid.Model.ThemeChanger;
import inventionstudio.inventionstudioandroid.R;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottom;
//    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeChanger.currentTheme);
        setContentView(R.layout.activity_main);

        bottom = (BottomNavigationView) findViewById(R.id.bottomBar);
        disableShiftMode(bottom);
        bottom.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment currentFragment = getSupportFragmentManager().findFragmentById(
                                R.id.fragment_container);
                        Fragment selectedFragment = null;

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        switch (item.getItemId()) {
                            case R.id.home:
                                if (!(currentFragment instanceof HomeFragment)){
                                    selectedFragment = new HomeFragment();
                                }
                                break;
                            case R.id.equipment:
                                if (!(currentFragment instanceof MachineGroupFragment)){
                                    selectedFragment = new MachineGroupFragment();
                                }
                                break;
                            case R.id.queue:
                                if (!(currentFragment instanceof QueueFragment)){
                                    selectedFragment = new QueueFragment();
                                }
                                break;
                            case R.id.feedback:
                                if (!(currentFragment instanceof FeedbackFragment)){
                                    selectedFragment = new FeedbackFragment();
                                }
                                break;
                            case R.id.more:
                                if (!(currentFragment instanceof MoreFragment)){
                                    selectedFragment = new MoreFragment();
                                }
                                break;
                        }
                        if (selectedFragment != null) {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            fragmentManager.popBackStackImmediate(
                                    null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            transaction.replace(R.id.fragment_container, selectedFragment);
                            transaction.commit();
                        }

                        return true;
                    }
                });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment());
        transaction.commit();

    }

    @SuppressLint("RestrictedApi")
    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount != 0){
            super.onBackPressed();
        } else {
            bottom.setSelectedItemId(R.id.home);
        }
    }
}

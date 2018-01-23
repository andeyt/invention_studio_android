package inventionstudio.inventionstudioandroid;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.lang.reflect.Field;

import inventionstudio.inventionstudioandroid.Fragments.MachineGroupFragment;
import inventionstudio.inventionstudioandroid.Fragments.FeedbackFragment;
import inventionstudio.inventionstudioandroid.Fragments.HomeFragment;
import inventionstudio.inventionstudioandroid.Fragments.MoreFragment;
import inventionstudio.inventionstudioandroid.Fragments.QueueFragment;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private BottomBar bottomBar;
    private BottomNavigationView bottom;
//    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

//        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
//        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelected(@IdRes int tabId) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                if (tabId == R.id.tab_home) {
//                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//
//                    fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    // Replace the contents of the container with the new fragment
//                    ft.replace(R.id.fragment_container, new HomeFragment());
//                    // or ft.add(R.id.your_placeholder, new FooFragment());
//                    // Complete the changes added above
//                    ft.commit();
//
//
//                } else if (tabId == R.id.tab_equipment) {
//                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    // Replace the contents of the container with the new fragment
//                    ft.replace(R.id.fragment_container, new MachineGroupFragment());
//                    // or ft.add(R.id.your_placeholder, new FooFragment());
//                    // Complete the changes added above
//                    ft.commit();
//
//
//                } else if (tabId == R.id.tab_queue) {
//                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    // Replace the contents of the container with the new fragment
//                    ft.replace(R.id.fragment_container, new QueueFragment());
//                    // or ft.add(R.id.your_placeholder, new FooFragment());
//                    // Complete the changes added above
//                    ft.commit();
//
//
//                } else if (tabId == R.id.tab_feedback) {
//                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    // Replace the contents of the container with the new fragment
//                    ft.replace(R.id.fragment_container, new FeedbackFragment());
//                    // or ft.add(R.id.your_placeholder, new FooFragment());
//                    // Complete the changes added above
//                    ft.commit();
//
//                } else if (tabId == R.id.tab_more) {
//                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    // Replace the contents of the container with the new fragment
//                    ft.replace(R.id.fragment_container, new MoreFragment());
//                    // or ft.add(R.id.your_placeholder, new FooFragment());
//                    // Complete the changes added above
//                    ft.commit();
//
//                }
//            }
//
//        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_activity_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_logout:
                final String KEY_ACTIVITY_NAME = "Action";

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra(KEY_ACTIVITY_NAME, "logout");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

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

}

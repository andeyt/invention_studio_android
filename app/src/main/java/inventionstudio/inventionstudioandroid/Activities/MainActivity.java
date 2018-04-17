package inventionstudio.inventionstudioandroid.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.lang.reflect.Field;

import inventionstudio.inventionstudioandroid.API.ServerApiService;
import inventionstudio.inventionstudioandroid.Fragments.FeedbackFragment;
import inventionstudio.inventionstudioandroid.Fragments.HomeFragment;
import inventionstudio.inventionstudioandroid.Fragments.EquipmentGroupFragment;
import inventionstudio.inventionstudioandroid.Fragments.MoreFragment;
import inventionstudio.inventionstudioandroid.Fragments.QueueFragment;
import inventionstudio.inventionstudioandroid.Model.AppStatus;
import inventionstudio.inventionstudioandroid.Model.LoginFormObject;
import inventionstudio.inventionstudioandroid.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom;

    public static final String USER_PREFERENCES = "UserPrefs";
    private Retrofit retrofit;
    private Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set theme of the app, grab username to be used later
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        String username = prefs.getString("username", "");

        // Set up connection to the server, record login, get app status
        FirebaseMessaging.getInstance().subscribeToTopic(username + "_android");
        FirebaseMessaging.getInstance().unsubscribeFromTopic(username);
        connectAndSendLoginRecord();
        connectAndGetAppStatus();

        String notification = getIntent().getStringExtra("notification");

        // Inititialize bottom bar view, set each icon to switch to the
        // designated fragments
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

                        // Create new Fragment based on which icon is clicked
                        switch (item.getItemId()) {
                            case R.id.home:
                                if (!(currentFragment instanceof HomeFragment)){
                                    selectedFragment = new HomeFragment();
                                }
                                break;
                            case R.id.equipment:
                                if (!(currentFragment instanceof EquipmentGroupFragment)){
                                    selectedFragment = new EquipmentGroupFragment();
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

                        // Take selected Fragment and navigate to it accordingly
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

        // Set original Fragment as the Home Fragment or Queue
        if (notification != null) {
            bottom.setSelectedItemId(R.id.queue);
        } else {
            bottom.setSelectedItemId(R.id.home);
        }



    }

    @SuppressLint("RestrictedApi")
    /**
     *
     */
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
    /**
     * Super the onBackPressed if there is a stack, if not go home
     */
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount != 0){
            super.onBackPressed();
        } else {
            bottom.setSelectedItemId(R.id.home);
        }
    }

    @Override
    public void onPause () {
        super.onPause();
        if (call != null) {
            call.cancel();
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * method to send a login record to the server
     */
    public void connectAndSendLoginRecord() {

        // retrofit created to connect to our server
        retrofit = new Retrofit.Builder()
                .baseUrl("https://is-apps.me.gatech.edu/api/v1-0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Get all necessary data to apply to the login and send it to the server
        SharedPreferences prefs = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        ServerApiService serverApiService = retrofit.create(ServerApiService.class);
        String username = prefs.getString("username", "");
        String name = prefs.getString("name", "");
        String otp = prefs.getString("otp", "");
        LoginFormObject login = new LoginFormObject(username, name, otp);
        call = serverApiService.sendLoginRecord(login, "771e6dd7-2d2e-4712-8944-7055ce69c9fb");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            /**
             * Give the user a message when the login is recorded correctly
             */
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            /**
             * If connection issues occur, notify the user
             */
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * method to get app update status messages from server
     */
    public void connectAndGetAppStatus() {

        // Retrofit created to connect to the server
        retrofit = new Retrofit.Builder()
                .baseUrl("https://is-apps.me.gatech.edu/api/v1-0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Call to server to get the app status
        ServerApiService serverApiService = retrofit.create(ServerApiService.class);
        call = serverApiService.getAppStatus( "771e6dd7-2d2e-4712-8944-7055ce69c9fb");
        call.enqueue(new Callback<AppStatus>() {
            @Override
            public void onResponse(Call<AppStatus> call, Response<AppStatus> response) {
                try {
                    // Grab status code and AppStatus object
                    int statusCode = response.code();
                    AppStatus appStatus = response.body();

                    // if code of 200 is received
                    if (statusCode == 200) {

                        // Create a dialog with the given AppStatus message
                        // Display it to the user until they dismiss it
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(appStatus.getMessage());
                        builder.setTitle(appStatus.getTitle());
                        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            /**
             * If a connection error occurs, notify the user
             */
            public void onFailure(Call<AppStatus> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

package online.whitefieldmudra.aptmaintenance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.HashMap;

public class BaseActivity extends AppCompatActivity {
    Context _baseContext;
    protected FrameLayout frameLayout;
    private DrawerLayout drawable;
    private ActionBarDrawerToggle actionBar;
    private SessionManagement userSessionMgmt;
  //  private SigninActivity userSignInActivity;
    //private AppCompatDelegate mDelegate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        userSessionMgmt = new SessionManagement(getApplicationContext());
        frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        drawable = (DrawerLayout)findViewById(R.id.activity_navigation);
        actionBar = new ActionBarDrawerToggle(this,drawable,R.string.Open,R.string.Close);
        actionBar.setDrawerIndicatorEnabled(true);
        drawable.addDrawerListener(actionBar);
        actionBar.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
       // ImageView profileImage = (ImageView)nav_view.findViewById(R.id.image_View);

        userSessionMgmt.checkLogin();

      /*  // get user data from session
        HashMap<String, String> user = userSessionMgmt.getUserDetails();
        String userImageURL = "https://lh4.googleusercontent.com/mP4xY5XusB0xedTFKI5MXjDUzvbCKQy5EuhHGZzLzi-7tH92rvsSzPzRA60LXOy6a3UNJBlxOw=w170";//user.get("UserImage");
        if(!userImageURL.isEmpty()) {
        //    Glide.with(this).load(userImageURL).into(profileImage);
        }
        */

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

               /* if(id == R.id.myProfile)
                {
                    //Toast.makeText(BaseActivity.this,"MyProfile",Toast.LENGTH_SHORT).show();
                    redirectToActivity(MainActivity.class);
                }
                */
                if(id == R.id.home)
                {
                    //Toast.makeText(BaseActivity.this,"Home",Toast.LENGTH_SHORT).show();
                    redirectToActivity(MainActivity.class);
                }
               /*
                if(id == R.id.Settings)
                {
                    Toast.makeText(BaseActivity.this,"Settings",Toast.LENGTH_SHORT).show();
                }
                */
                if(id == R.id.SighOut)
                {
                    userSessionMgmt.logOutUser();
                 //   userSignInActivity = new SigninActivity();
                  //  userSignInActivity.googleSignOut();



                }

                return false;

            }
        });
    }

    @Override
     public boolean onOptionsItemSelected(MenuItem item)
    {
        return actionBar.onOptionsItemSelected(item)|| super.onOptionsItemSelected(item);
    }

    public void redirectToActivity(Class<?> redirectClassName)
    {
        Intent intent = new Intent(BaseActivity.this,redirectClassName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}

package wallet.erc20.tazzmanian.erc20wallet;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        TransactionsFragment.OnFragmentInteractionListener, SendFragment.OnFragmentInteractionListener,
        UserInfoFragment.OnFragmentInteractionListener, AccountsFragment.OnFragmentInteractionListener,
        ContractFragment.OnFragmentInteractionListener, ServerFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;

    private int lastMenuId = 0;
    private int lastSelected = 0;

    private class Frame {
        int menuId;
        int selectedId;

        Frame(int m, int i) {
            menuId = m;
            selectedId = i;
        }
    }

    private Stack<Frame> frameStack = new Stack<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            boolean flag = false;
            switch (item.getItemId()) {
                case R.id.navigation_history:
                    mTextMessage.setText(R.string.title_history);
                    loadFragment(new TransactionsFragment());
                    loadBottomNavigation(R.id.navigation_history, R.menu.navigation);
                    flag = true;
                    break;
                case R.id.navigation_send:
                    mTextMessage.setText(R.string.title_send);
                    loadFragment(new SendFragment());
                    loadBottomNavigation(R.id.navigation_send, R.menu.navigation);
                    flag = true;
                    break;
                case R.id.navigation_address:
                    mTextMessage.setText(R.string.title_address);
                    loadFragment(new UserInfoFragment());
                    loadBottomNavigation(R.id.navigation_address, R.menu.navigation);
                    flag = true;
                    break;
            }

            return flag;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_frame_layout, new TransactionsFragment());
        fragmentTransaction.commit();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadBottomNavigation(R.id.navigation_history, R.menu.navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
//        else {
//            super.onBackPressed();
//        }

        loadBottomNavOnBackPressed();

        FragmentManager fm = getSupportFragmentManager();
        if(fm.getBackStackEntryCount() > 0){
            fm.popBackStack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        boolean flag = false;
        switch (item.getItemId()) {
            case R.id.nav_accounts:
                loadFragment(new AccountsFragment());
                loadBottomNavigation(R.id.nav_accounts, R.menu.account_manage_menu);
                flag = true;
                break;
            case R.id.nav_contracts:
                loadFragment(new ContractFragment());
                loadBottomNavigation(R.id.nav_contracts, R.menu.contracts_manage_menu);
                flag = true;
                break;
            case R.id.nav_server:
                loadFragment(new ServerFragment());
                loadBottomNavigation(R.id.nav_server, R.menu.server_manage_menu);
                flag = true;
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return flag;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void addFragment(Fragment fragment, String name) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, name)
                .addToBackStack(name).commit();
    }

    private void replaceFragment(Fragment fragment, String name) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, name)
                .addToBackStack(name).commit();
    }

    private void loadBottomNavigation(int selected, int menuId) {

        if(lastMenuId == menuId && lastSelected == selected) {
            return;
        }

        if(lastMenuId == 0) {
            lastMenuId = menuId;
        }

        switch (selected) {
            case R.id.navigation_history:
            case R.id.navigation_send:
            case R.id.navigation_address:
                loadMenu(menuId);
                break;
            case R.id.nav_accounts:
                loadMenu(menuId);
                break;
            case R.id.nav_contracts:
                loadMenu(menuId);
                break;
            case R.id.nav_server:
                loadMenu(menuId);
                break;
        }
        lastMenuId = menuId;
        lastSelected = selected;
        frameStack.push(new Frame(lastMenuId, selected));
    }

    private void loadMenu(int menuId) {
        if(lastMenuId == menuId || lastMenuId == 0) {
            return;
        }
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(menuId);
    }


    // will need more correction the other menus are buttons not navigation
    private void loadBottomNavOnBackPressed() {

        if(frameStack.size() == 0) {
            return;
        }

        if(frameStack.size() < 2) {
            return;
        } else {
            BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
            Frame f = frameStack.get(frameStack.size() - 2);
            frameStack.pop();
            if(lastMenuId != f.menuId) {
                navigationView.getMenu().clear();
                navigationView.inflateMenu(f.menuId);
                lastMenuId = f.menuId;
                lastSelected = f.selectedId;
            }
            navigationView.setOnNavigationItemSelectedListener(null);
            navigationView.setSelectedItemId(f.selectedId);
            navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}

package wallet.erc20.tazzmanian.erc20wallet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
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

import wallet.erc20.tazzmanian.erc20wallet.accounts.AccountPopFragment;
import wallet.erc20.tazzmanian.erc20wallet.accounts.AccountsFragment;
import wallet.erc20.tazzmanian.erc20wallet.accounts.ExportPopFragment;
import wallet.erc20.tazzmanian.erc20wallet.accounts.ImportSeedsFragment;
import wallet.erc20.tazzmanian.erc20wallet.accounts.PasswordCreateFragment;
import wallet.erc20.tazzmanian.erc20wallet.addressbook.AddContactFragment;
import wallet.erc20.tazzmanian.erc20wallet.addressbook.AddressBookFragment;
import wallet.erc20.tazzmanian.erc20wallet.addressbook.ContactPopFragment;
import wallet.erc20.tazzmanian.erc20wallet.contracts.AddContractFragment;
import wallet.erc20.tazzmanian.erc20wallet.contracts.ContractFragment;
import wallet.erc20.tazzmanian.erc20wallet.contracts.ContractPopFragment;
import wallet.erc20.tazzmanian.erc20wallet.send.SendFragment;
import wallet.erc20.tazzmanian.erc20wallet.send.SendPopFragment;
import wallet.erc20.tazzmanian.erc20wallet.servers.AddServerFragment;
import wallet.erc20.tazzmanian.erc20wallet.servers.ServerFragment;
import wallet.erc20.tazzmanian.erc20wallet.servers.ServerPopFragment;
import wallet.erc20.tazzmanian.erc20wallet.transactions.TransactionsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        TransactionsFragment.OnFragmentInteractionListener, SendFragment.OnFragmentInteractionListener,
        UserInfoFragment.OnFragmentInteractionListener, AccountsFragment.OnFragmentInteractionListener,
        ContractFragment.OnFragmentInteractionListener, ServerFragment.OnFragmentInteractionListener,
        WelcomeFragment.OnFragmentInteractionListener, PasswordCreateFragment.OnFragmentInteractionListener,
        ImportSeedsFragment.OnFragmentInteractionListener, AccountPopFragment.OnFragmentInteractionListener,
        AddServerFragment.OnFragmentInteractionListener, ServerPopFragment.OnFragmentInteractionListener,
        AddContractFragment.OnFragmentInteractionListener, ExportPopFragment.OnFragmentInteractionListener,
        AddressBookFragment.OnFragmentInteractionListener, AddContactFragment.OnFragmentInteractionListener,
        ContactPopFragment.OnFragmentInteractionListener, ContractPopFragment.OnFragmentInteractionListener,
        SendPopFragment.OnFragmentInteractionListener
{

    private TextView mTextMessage;

    private int lastMenuId = 0;
    private int lastSelected = 0;

    private class Frame {
        int menuId;
        int selectedId;
        boolean hidden;

        Frame(int m, int i, boolean h) {
            menuId = m;
            selectedId = i;
            hidden = h;
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
                    loadFragment(new TransactionsFragment());
                    loadBottomNavigation(R.id.navigation_history, R.menu.navigation);
                    flag = true;
                    break;
                case R.id.navigation_send:
                    loadFragment(new SendFragment());
                    loadBottomNavigation(R.id.navigation_send, R.menu.navigation);
                    flag = true;
                    break;
                case R.id.navigation_address:
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
            case R.id.nav_address_book:
                loadFragment(new AddressBookFragment());
                loadBottomNavigation(R.id.nav_server, R.menu.address_book_manage_menu);
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

        boolean hidden = false;

        switch (selected) {
            case R.id.navigation_history:
            case R.id.navigation_send:
            case R.id.navigation_address:
                loadMenu(menuId, hidden);
                break;
            case R.id.nav_accounts:
            case R.id.nav_contracts:
            case R.id.nav_server:
                hidden = true;
                loadMenu(menuId, hidden);
                break;
        }
        lastMenuId = menuId;
        lastSelected = selected;
        frameStack.push(new Frame(lastMenuId, selected, hidden));
    }

    private void loadMenu(int menuId, boolean hide) {
        if(lastMenuId == menuId || lastMenuId == 0) {
            return;
        }
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(menuId);
        if(hide) {
            navigationView.setVisibility(View.GONE);
        } else {
            navigationView.setVisibility(View.VISIBLE);
        }

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
            if(f.hidden) {
                navigationView.setVisibility(View.GONE);
            } else {
                navigationView.setVisibility(View.VISIBLE);
            }
            navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void copyAddress(View view) {
        TextView hash = (TextView) findViewById(R.id.account_hash_id);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("address", hash.getText().toString());
        clipboard.setPrimaryClip(clip);
    }

//    public void createWallet(View view) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        PasswordCreateFragment fragment = new PasswordCreateFragment();
//        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
//
//    public void restoreWallet(View view) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        ImportSeedsFragment fragment = new ImportSeedsFragment();
//        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }

}


package wallet.erc20.tazzmanian.erc20wallet;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import wallet.erc20.tazzmanian.erc20wallet.accounts.ImportAccountPassPopFragment;
import wallet.erc20.tazzmanian.erc20wallet.db.DBManager;

public class WelcomeActivity extends AppCompatActivity
        implements ImportSeedsFragment.OnFragmentInteractionListener, WelcomeFragment.OnFragmentInteractionListener,
                    PasswordCreateFragment.OnFragmentInteractionListener, ImportAccountPassPopFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBManager dbm = DBManager.getInstance(this);
        setContentView(R.layout.activity_welcome);

        if(DBManager.am.count() > 0 && getIntent().getExtras() == null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            WelcomeFragment fragment = new WelcomeFragment();
            fragmentTransaction.add(R.id.testId, fragment);
    //        fragmentTransaction.addToBackStack(null); // first fragment should not have it
            fragmentTransaction.commit();
        }
    }

    public void createWallet(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PasswordCreateFragment fragment = new PasswordCreateFragment();
        fragmentTransaction.replace(R.id.testId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void restoreWallet(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ImportSeedsFragment fragment = new ImportSeedsFragment();
        fragmentTransaction.replace(R.id.testId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

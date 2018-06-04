package wallet.erc20.tazzmanian.erc20wallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void createWallet(View view) {
        Toast.makeText(WelcomeActivity.this, "Create Wallet", Toast.LENGTH_LONG).show();
        if(true) {
            // when account create creates open main activity
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void restoreWallet(View view) {
        Toast.makeText(WelcomeActivity.this, "Restore Wallet", Toast.LENGTH_LONG).show();
    }
}

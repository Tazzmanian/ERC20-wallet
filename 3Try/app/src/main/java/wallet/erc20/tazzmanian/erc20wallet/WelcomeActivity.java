package wallet.erc20.tazzmanian.erc20wallet;

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
    }

    public void restoreWallet(View view) {
        Toast.makeText(WelcomeActivity.this, "Restore Wallet", Toast.LENGTH_LONG).show();
    }
}

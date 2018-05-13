package wallet.erc20.tazzmanian.ethereumerc20wallet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.methods.response.Web3Sha3;
import org.web3j.protocol.rx.Web3jRx;

import java.io.File;
import java.lang.reflect.Method;

public class LoginWalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_wallet);
        Toast.makeText(getApplicationContext(), "Create Create and Restore", Toast.LENGTH_LONG).show();
    }

    public void createWallet(View view) {
        Toast.makeText(getApplicationContext(), "Create wallet", Toast.LENGTH_LONG).show();
        try {
            Log.d("fileDir", getApplicationContext().getFilesDir().toString());
            File test = new File(getApplicationContext().getFilesDir(), "test");
            Bip39Wallet a = WalletUtils.generateBip39Wallet("test", test);
            Toast.makeText(getApplicationContext(), a.toString(), Toast.LENGTH_LONG).show();
            Log.d("myTag", a.toString());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("myTag", e.getMessage());
        }

        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Destroy Create and Restore", Toast.LENGTH_LONG).show();
    }
}

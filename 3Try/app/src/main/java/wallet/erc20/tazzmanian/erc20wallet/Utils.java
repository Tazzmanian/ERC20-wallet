package wallet.erc20.tazzmanian.erc20wallet;

import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;

public final class Utils {

    private static Utils INSTANCE;
    private static Bip39Wallet WALLET;

    private Utils() {}

    public static Utils getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Utils();
        }
        return INSTANCE;
    }

    public void destroyInstance() {
        INSTANCE = null;
        WALLET = null;
    }

    public void createWallet(String password, File file) {
        try {
            WALLET = WalletUtils.generateBip39Wallet(password, file);
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // delete the file
            e.printStackTrace();
        }
    }
}

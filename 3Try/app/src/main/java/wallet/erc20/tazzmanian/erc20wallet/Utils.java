package wallet.erc20.tazzmanian.erc20wallet;

import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Utils {

    private static Utils INSTANCE;
//    private static Bip39Wallet WALLET;
    private static String MNEMONICS;

    private Utils() {}

    public static Utils getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Utils();
        }
        return INSTANCE;
    }

    public void destroyInstance() {
        INSTANCE = null;
        MNEMONICS = null;
    }

    public void createWallet(String password, File dir) {
        String filename = null;
        try {
            Bip39Wallet wallet = WalletUtils.generateBip39Wallet(password, dir);
            MNEMONICS = wallet.getMnemonic();
//            System.out.println("TTT: " + MNEMONICS + dir);
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // delete the file
            e.printStackTrace();
        }
    }

    public Credentials loadWallet(String password, String mnemonics) {
        if(mnemonics == null) {
            mnemonics = MNEMONICS;
        }
        return WalletUtils.loadBip39Credentials(password, mnemonics);
    }
}

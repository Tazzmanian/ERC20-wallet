package wallet.erc20.tazzmanian.erc20wallet;

import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Utils {

    private static Utils INSTANCE;
//    private static Bip39Wallet WALLET;
    public static String MNEMONICS;
    private static Credentials credentials;
    Web3j web3j;

    private Utils() {}

    public static Utils getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Utils();
            INSTANCE.buildConnection();
        }
        return INSTANCE;
    }

    public void destroyInstance() {
        INSTANCE = null;
        MNEMONICS = null;
        credentials = null;
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

    public void loadWallet(String password, String mnemonics) {
        if(mnemonics == null) {
            mnemonics = MNEMONICS;
        }
        credentials = WalletUtils.loadBip39Credentials(password, mnemonics);
    }

    public static Credentials getCredentials() {
        return credentials;
    }

    public static String getAddress() {
        return credentials.getAddress();
    }

    public void buildConnection() {
        ServerItems si = DBManager.sm.getActive();
        if(si != null) {
            web3j = Web3j.build(new HttpService(si.host + ":" + si.port));
        }
    }
}

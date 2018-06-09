package wallet.erc20.tazzmanian.erc20wallet;

public class AccountItems {

    public String mnemonics;
    public long id;
    public String hash;
    public boolean active;

    AccountItems(
            String mnemonics,
            long id,
            String hash,
            boolean active
    ) {
        this.active = active;
        this.mnemonics = mnemonics;
        this.id = id;
        this.hash = hash;
    }
}

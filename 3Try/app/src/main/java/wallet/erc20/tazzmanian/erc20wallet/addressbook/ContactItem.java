package wallet.erc20.tazzmanian.erc20wallet.addressbook;

public class ContactItem {
    public long id;
    public String hash;
    public String name;

    public ContactItem(
            long id,
            String hash,
            String name
    ) {
        this.name = name;
        this.id = id;
        this.hash = hash;
    }
}

package wallet.erc20.tazzmanian.erc20wallet.contracts;

import java.math.BigInteger;

public class ContractItems {

    public String addressHash;
    public long id;
    public String symbol;
    public String name;
    public BigInteger totalSupply;
    public BigInteger decimals;

    public ContractItems(String addressHash, long id, String symbol, String name, BigInteger totalSupply, BigInteger decimals) {
        this.addressHash = addressHash;
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.totalSupply = totalSupply;
        this.decimals = decimals;
    }
}

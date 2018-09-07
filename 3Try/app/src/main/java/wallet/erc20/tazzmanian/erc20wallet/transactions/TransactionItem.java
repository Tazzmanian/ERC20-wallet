package wallet.erc20.tazzmanian.erc20wallet.transactions;

public class TransactionItem {
    String from;
    String to;
    String network;
    String contract;
    String amount;
    String txHash;
    String currency;
    String nonce;
    String blockNumber;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public TransactionItem() {
        blockNumber = "0";
        nonce = "0";
    }

    public TransactionItem(String from, String to, String network, String contract, String amount, String txHash, String currency, String nonce, String blockNumber) {
        this.from = from;
        this.to = to;
        this.network = network;
        this.contract = contract;
        this.amount = amount;
        this.txHash = txHash;
        this.currency = currency;
        this.nonce = nonce;
        this.blockNumber = blockNumber;
    }
}

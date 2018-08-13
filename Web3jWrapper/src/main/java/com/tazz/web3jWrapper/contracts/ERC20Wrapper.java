package com.tazz.web3jWrapper.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class ERC20Wrapper extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060008054600160a060020a031916331790556040805180820190915260038082527f4d59540000000000000000000000000000000000000000000000000000000000602090920191825261006791600291610133565b506040805180820190915260078082527f4d79546f6b656e0000000000000000000000000000000000000000000000000060209092019182526100ac91600391610133565b506004805460ff191660121790556000600581905542600681905562093a8081016007556224ea00016008558054600160a060020a039081168252600960209081526040808420849055835481518581529151931693927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929181900390910190a36101ce565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061017457805160ff19168380011785556101a1565b828001600101855582156101a1579182015b828111156101a1578251825591602001919060010190610186565b506101ad9291506101b1565b5090565b6101cb91905b808211156101ad57600081556001016101b7565b90565b610ccf806101dd6000396000f3006080604052600436106101325763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde03811461021e578063095ea7b3146102a85780630b97bc86146102e057806318160ddd1461030757806323b872dd1461031c578063313ce567146103465780633eaaf86b1461037157806340c650031461038657806370a082311461039b57806379ba5097146103bc5780638da5cb5b146103d357806395d89b4114610404578063a293d1e814610419578063a9059cbb14610434578063b5931f7c14610458578063c24a0f8b14610473578063cae9ca5114610488578063d05c78da146104f1578063d4ee1d901461050c578063dc39d06d14610521578063dd62ed3e14610545578063e6cb90131461056c578063f2fde38b14610587575b6000600654421015801561014857506008544211155b151561015357600080fd5b600754421161016757506104b0340261016e565b506103e834025b3360009081526009602052604090205461018890826105a8565b336000908152600960205260409020556005546101a590826105a8565b60055560408051828152905133916000917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9181900360200190a360008054604051600160a060020a03909116913480156108fc02929091818181858888f1935050505015801561021a573d6000803e3d6000fd5b5050005b34801561022a57600080fd5b506102336105be565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561026d578181015183820152602001610255565b50505050905090810190601f16801561029a5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156102b457600080fd5b506102cc600160a060020a036004351660243561064c565b604080519115158252519081900360200190f35b3480156102ec57600080fd5b506102f56106b2565b60408051918252519081900360200190f35b34801561031357600080fd5b506102f56106b8565b34801561032857600080fd5b506102cc600160a060020a03600435811690602435166044356106ea565b34801561035257600080fd5b5061035b6107e3565b6040805160ff9092168252519081900360200190f35b34801561037d57600080fd5b506102f56107ec565b34801561039257600080fd5b506102f56107f2565b3480156103a757600080fd5b506102f5600160a060020a03600435166107f8565b3480156103c857600080fd5b506103d1610813565b005b3480156103df57600080fd5b506103e861089b565b60408051600160a060020a039092168252519081900360200190f35b34801561041057600080fd5b506102336108aa565b34801561042557600080fd5b506102f5600435602435610902565b34801561044057600080fd5b506102cc600160a060020a0360043516602435610917565b34801561046457600080fd5b506102f56004356024356109bb565b34801561047f57600080fd5b506102f56109dc565b34801561049457600080fd5b50604080516020600460443581810135601f81018490048402850184019095528484526102cc948235600160a060020a03169460248035953695946064949201919081908401838280828437509497506109e29650505050505050565b3480156104fd57600080fd5b506102f5600435602435610b43565b34801561051857600080fd5b506103e8610b68565b34801561052d57600080fd5b506102cc600160a060020a0360043516602435610b77565b34801561055157600080fd5b506102f5600160a060020a0360043581169060243516610c32565b34801561057857600080fd5b506102f56004356024356105a8565b34801561059357600080fd5b506103d1600160a060020a0360043516610c5d565b818101828110156105b857600080fd5b92915050565b6003805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156106445780601f1061061957610100808354040283529160200191610644565b820191906000526020600020905b81548152906001019060200180831161062757829003601f168201915b505050505081565b336000818152600a60209081526040808320600160a060020a038716808552908352818420869055815186815291519394909390927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925928290030190a350600192915050565b60065481565b6000805260096020527fec8156718a8372b1db44bb411437d0870f3e3790d4a08526d024ce1b0b668f6b546005540390565b600160a060020a03831660009081526009602052604081205461070d9083610902565b600160a060020a038516600090815260096020908152604080832093909355600a8152828220338352905220546107449083610902565b600160a060020a038086166000908152600a6020908152604080832033845282528083209490945591861681526009909152205461078290836105a8565b600160a060020a0380851660008181526009602090815260409182902094909455805186815290519193928816927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef92918290030190a35060019392505050565b60045460ff1681565b60055481565b60075481565b600160a060020a031660009081526009602052604090205490565b600154600160a060020a0316331461082a57600080fd5b60015460008054604051600160a060020a0393841693909116917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600180546000805473ffffffffffffffffffffffffffffffffffffffff19908116600160a060020a03841617909155169055565b600054600160a060020a031681565b6002805460408051602060018416156101000260001901909316849004601f810184900484028201840190925281815292918301828280156106445780601f1061061957610100808354040283529160200191610644565b60008282111561091157600080fd5b50900390565b336000908152600960205260408120546109319083610902565b3360009081526009602052604080822092909255600160a060020a0385168152205461095d90836105a8565b600160a060020a0384166000818152600960209081526040918290209390935580518581529051919233927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9281900390910190a350600192915050565b60008082116109c957600080fd5b81838115156109d457fe5b049392505050565b60085481565b336000818152600a60209081526040808320600160a060020a038816808552908352818420879055815187815291519394909390927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925928290030190a36040517f8f4ffcb10000000000000000000000000000000000000000000000000000000081523360048201818152602483018690523060448401819052608060648501908152865160848601528651600160a060020a038a1695638f4ffcb195948a94938a939192909160a490910190602085019080838360005b83811015610ad2578181015183820152602001610aba565b50505050905090810190601f168015610aff5780820380516001836020036101000a031916815260200191505b5095505050505050600060405180830381600087803b158015610b2157600080fd5b505af1158015610b35573d6000803e3d6000fd5b506001979650505050505050565b818102821580610b5d5750818382811515610b5a57fe5b04145b15156105b857600080fd5b600154600160a060020a031681565b60008054600160a060020a03163314610b8f57600080fd5b60008054604080517fa9059cbb000000000000000000000000000000000000000000000000000000008152600160a060020a0392831660048201526024810186905290519186169263a9059cbb926044808401936020939083900390910190829087803b158015610bff57600080fd5b505af1158015610c13573d6000803e3d6000fd5b505050506040513d6020811015610c2957600080fd5b50519392505050565b600160a060020a039182166000908152600a6020908152604080832093909416825291909152205490565b600054600160a060020a03163314610c7457600080fd5b6001805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03929092169190911790555600a165627a7a723058203a05cbcbb7cd553af01e17c014aa52465234adbf1316bb96a8f36d67d778731c0029";

    protected ERC20Wrapper(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ERC20Wrapper(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("OwnershipTransferred", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnershipTransferredEventResponse> ownershipTransferredEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("OwnershipTransferred", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Transfer", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokens = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Transfer", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokens = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Approval", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokens = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Approval", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse.tokenOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokens = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<String> name() {
        final Function function = new Function("name", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> approve(String spender, BigInteger tokens) {
        final Function function = new Function(
                "approve", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(spender), 
                new org.web3j.abi.datatypes.generated.Uint256(tokens)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> startDate() {
        final Function function = new Function("startDate", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> totalSupply() {
        final Function function = new Function("totalSupply", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String from, String to, BigInteger tokens) {
        final Function function = new Function(
                "transferFrom", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(from), 
                new org.web3j.abi.datatypes.Address(to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokens)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> decimals() {
        final Function function = new Function("decimals", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> _totalSupply() {
        final Function function = new Function("_totalSupply", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> bonusEnds() {
        final Function function = new Function("bonusEnds", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> balanceOf(String tokenOwner) {
        final Function function = new Function("balanceOf", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(tokenOwner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> acceptOwnership() {
        final Function function = new Function(
                "acceptOwnership", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> symbol() {
        final Function function = new Function("symbol", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> safeSub(BigInteger a, BigInteger b) {
        final Function function = new Function("safeSub", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(a), 
                new org.web3j.abi.datatypes.generated.Uint256(b)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transfer(String to, BigInteger tokens) {
        final Function function = new Function(
                "transfer", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokens)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> safeDiv(BigInteger a, BigInteger b) {
        final Function function = new Function("safeDiv", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(a), 
                new org.web3j.abi.datatypes.generated.Uint256(b)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> endDate() {
        final Function function = new Function("endDate", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> approveAndCall(String spender, BigInteger tokens, byte[] data) {
        final Function function = new Function(
                "approveAndCall", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(spender), 
                new org.web3j.abi.datatypes.generated.Uint256(tokens), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> safeMul(BigInteger a, BigInteger b) {
        final Function function = new Function("safeMul", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(a), 
                new org.web3j.abi.datatypes.generated.Uint256(b)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> newOwner() {
        final Function function = new Function("newOwner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transferAnyERC20Token(String tokenAddress, BigInteger tokens) {
        final Function function = new Function(
                "transferAnyERC20Token", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(tokenAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(tokens)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> allowance(String tokenOwner, String spender) {
        final Function function = new Function("allowance", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(tokenOwner), 
                new org.web3j.abi.datatypes.Address(spender)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> safeAdd(BigInteger a, BigInteger b) {
        final Function function = new Function("safeAdd", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(a), 
                new org.web3j.abi.datatypes.generated.Uint256(b)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String _newOwner) {
        final Function function = new Function(
                "transferOwnership", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<ERC20Wrapper> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ERC20Wrapper.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ERC20Wrapper> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ERC20Wrapper.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static ERC20Wrapper load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC20Wrapper(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static ERC20Wrapper load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC20Wrapper(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public String _from;

        public String _to;
    }

    public static class TransferEventResponse {
        public Log log;

        public String from;

        public String to;

        public BigInteger tokens;
    }

    public static class ApprovalEventResponse {
        public Log log;

        public String tokenOwner;

        public String spender;

        public BigInteger tokens;
    }
}

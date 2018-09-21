package wallet.erc20.tazzmanian.erc20wallet.transactions;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import wallet.erc20.tazzmanian.erc20wallet.R;
import wallet.erc20.tazzmanian.erc20wallet.contracts.AddContractFragment;
import wallet.erc20.tazzmanian.erc20wallet.contracts.ContractFragment;
import wallet.erc20.tazzmanian.erc20wallet.contracts.ContractItems;
import wallet.erc20.tazzmanian.erc20wallet.contracts.ContractPopFragment;
import wallet.erc20.tazzmanian.erc20wallet.db.DBManager;
import wallet.erc20.tazzmanian.erc20wallet.servers.ServerItems;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TransactionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransactionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TransactionItemAdapter transactionItemAdapter;

    private OnFragmentInteractionListener mListener;

    public TransactionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionsFragment newInstance(String param1, String param2) {
        TransactionsFragment fragment = new TransactionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        StringEntity entity = null;
        try {
            ServerItems si = DBManager.sm.getActive();
            if(si == null) {
                DBManager.sm.insertDefault();
            }
            TransactionItem ti = DBManager.tm.getLatest(DBManager.sm.getActive().toString(), DBManager.am.getActiveHashAccount());
            jsonParams.put("address", DBManager.am.getActiveHashAccount());
            jsonParams.put("blockNumber", ti.getBlockNumber());
            jsonParams.put("nonce", ti.getNonce());
            jsonParams.put("network", DBManager.sm.getActive().toString());
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        client.post(getActivity(), "http://10.0.2.2:5000/history", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("transactions", "taken");
                if(response.length() == 0) {
                    Log.e("transactions", "no new transactions");
                } else {
                    for(int i =0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            DBManager.tm.insert(obj.getString("from"),
                                    obj.getString("to"), obj.getString("amount"),
                                    obj.getString("network"), obj.getString("currency"),
                                    obj.getString("txHash"), obj.getString("blockNumber"),
                                    obj.getString("contract"), obj.getString("nonce") );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("balance", "sending failed");
            }
        });

        ListView listView = view.findViewById(R.id.transactions_list_view);
        transactionItemAdapter = new TransactionItemAdapter(DBManager.tm.getAll(DBManager.sm.getActive().toString(), DBManager.am.getActiveHashAccount()));
        listView.setAdapter(transactionItemAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class TransactionItemAdapter extends BaseAdapter {

        public ArrayList<TransactionItem> list;

        public TransactionItemAdapter(ArrayList<TransactionItem> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.layout_transaction_ticket, null);

            final TransactionItem s = list.get(position);

            TextView from = view.findViewById(R.id.from);
            from.setText(s.from);

            TextView to = view.findViewById(R.id.to);
            to.setText(s.to);

            TextView hash = view.findViewById(R.id.hash);
            hash.setText(s.txHash);

            TextView contract = view.findViewById(R.id.contract);
            contract.setText(s.contract);

            TextView amount = view.findViewById(R.id.amount);
            amount.setText(s.amount + " "+ s.currency);

//

            return view;
        }
    }
}
package wallet.erc20.tazzmanian.erc20wallet.send;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import wallet.erc20.tazzmanian.erc20wallet.R;
import wallet.erc20.tazzmanian.erc20wallet.accounts.ImportAccountPassPopFragment;
import wallet.erc20.tazzmanian.erc20wallet.addressbook.ContactItem;
import wallet.erc20.tazzmanian.erc20wallet.contracts.ContractItems;
import wallet.erc20.tazzmanian.erc20wallet.db.DBManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private EditText from;
    private AutoCompleteTextView to;
    private AutoCompleteTextView contract;
    private EditText amount;
    private Button b;

    private OnFragmentInteractionListener mListener;

    //  create a textWatcher member
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };

    void checkFieldsForEmptyValues(){
        String f = from.getText().toString();
        String t = to.getText().toString();
        String c = contract.getText().toString();
        String a = amount.getText().toString();

        if(a.equals("") || f.equals("") || t.equals("")){
            b.setEnabled(false);
        } else {
            b.setEnabled(true);
        }
    }

    public SendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SendFragment newInstance(String param1, String param2) {
        SendFragment fragment = new SendFragment();
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
        view = inflater.inflate(R.layout.fragment_send, container, false);

        from = (EditText) view.findViewById(R.id.from_tx);
        from.setText(DBManager.am.getActiveHashAccount());

        to = (AutoCompleteTextView) view.findViewById(R.id.to_tx);
        // Get the string array
        ArrayList<String> accounts = new ArrayList<>();
        ArrayList<ContactItem> accs = DBManager.abm.getAll();

        for (int i = 0; i < accs.size(); i++) {
            ContactItem ci = accs.get(i);
            accounts.add(ci.hash);
        }

        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, accounts);
        to.setAdapter(adapter);
        to.setThreshold(0);

        /////////
        contract = (AutoCompleteTextView) view.findViewById(R.id.contract_tx);
        // Get the string array
        ArrayList<String> contracts = new ArrayList<>();
        ArrayList<ContractItems> cons = DBManager.cm.getAll();

        for (int i = 0; i < cons.size(); i++) {
            ContractItems ci = cons.get(i);
            contracts.add(ci.addressHash);
        }

        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, contracts);
        contract.setAdapter(adapter2);
        contract.setThreshold(0);
        contract.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    JSONObject jsonParams = new JSONObject();
                    StringEntity entity = null;
                    try {
                        jsonParams.put("address", DBManager.am.getActiveHashAccount());
                        jsonParams.put("contract", contract.getText().toString());
                        jsonParams.put("network", DBManager.sm.getActive().toString());
                        entity = new StringEntity(jsonParams.toString());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                    client.post(getActivity(), "http://10.0.2.2:5000/balance", entity, "application/json", new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("create", "account succeded");
                            if(response == null) {
                                Log.e("balance", "sending failed");
                            } else {
                                try {
                                    TextView ether = (TextView) view.findViewById(R.id.tokenBalance);
                                    ether.setText(response.getString("token"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.e("balance", "sending failed");
                        }
                    });
                }
            }
        });


        amount = (EditText) view.findViewById(R.id.amount_tx);
        amount.addTextChangedListener(mTextWatcher);

        b = (Button) view.findViewById(R.id.send_btn);

        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        StringEntity entity = null;
        try {
            jsonParams.put("address", DBManager.am.getActiveHashAccount());
            jsonParams.put("contract", "");
            jsonParams.put("network", DBManager.sm.getActive().toString());
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        client.post(getActivity(), "http://10.0.2.2:5000/balance", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("create", "account succeded");
                if(response == null) {
                    Log.e("balance", "sending failed");
                } else {
                    try {
                        TextView ether = (TextView) view.findViewById(R.id.etherBalance);
                        ether.setText(response.getString("ether"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("balance", "sending failed");
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Toast.makeText(getActivity(), s.hash, Toast.LENGTH_LONG).show(); */
                FragmentManager fm = getFragmentManager();
                final SendPopFragment pop = new SendPopFragment();
                Bundle args = new Bundle();
                args.putString("mnemonics", DBManager.am.getActiveMnemonicsAccount());
                args.putString("to", to.getText().toString());
                args.putString("contract", contract.getText().toString());
                args.putString("amount", amount.getText().toString());
                pop.setArguments(args);
                pop.show(fm, "Dialog");
            }
        });


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
}

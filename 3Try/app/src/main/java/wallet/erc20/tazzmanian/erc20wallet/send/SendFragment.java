package wallet.erc20.tazzmanian.erc20wallet.send;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private OnFragmentInteractionListener mListener;

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

        EditText from = (EditText) view.findViewById(R.id.from_tx);
        from.setText(DBManager.am.getActiveHashAccount());

        AutoCompleteTextView to = (AutoCompleteTextView) view.findViewById(R.id.to_tx);
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
        AutoCompleteTextView contract = (AutoCompleteTextView) view.findViewById(R.id.contract_tx);
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

        EditText amount = (EditText) view.findViewById(R.id.amount_tx);

        Button b = (Button) view.findViewById(R.id.button4);

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

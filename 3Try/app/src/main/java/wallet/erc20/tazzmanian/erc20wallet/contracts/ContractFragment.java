package wallet.erc20.tazzmanian.erc20wallet.contracts;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import wallet.erc20.tazzmanian.erc20wallet.R;
import wallet.erc20.tazzmanian.erc20wallet.accounts.AccountItems;
import wallet.erc20.tazzmanian.erc20wallet.accounts.AccountPopFragment;
import wallet.erc20.tazzmanian.erc20wallet.accounts.AccountsFragment;
import wallet.erc20.tazzmanian.erc20wallet.db.DBManager;
import wallet.erc20.tazzmanian.erc20wallet.servers.AddServerFragment;
import wallet.erc20.tazzmanian.erc20wallet.servers.ServerFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContractFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContractFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContractFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ContractItemAdapter itemAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ContractFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContractFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContractFragment newInstance(String param1, String param2) {
        ContractFragment fragment = new ContractFragment();
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
        View view = inflater.inflate(R.layout.fragment_contract, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab_add_contract);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                AddContractFragment acf = new AddContractFragment();
                fragmentTransaction.replace(R.id.main_frame_layout, acf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ListView listView = view.findViewById(R.id.contracts_list_view);
        itemAdapter = new ContractItemAdapter(DBManager.cm.getAll());
        listView.setAdapter(itemAdapter);

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

    private class ContractItemAdapter extends BaseAdapter {

        public ArrayList<ContractItems> list;

        public ContractItemAdapter(ArrayList<ContractItems> list) {
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
            View view = inflater.inflate(R.layout.layout_contract_ticket, null);

            final ContractItems s = list.get(position);

            TextView name = view.findViewById(R.id.name);
            name.setText(s.name);

            TextView symbol = view.findViewById(R.id.symbol);
            symbol.setText(s.symbol);

            TextView hash = view.findViewById(R.id.hash);
            hash.setText(s.addressHash);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* Toast.makeText(getActivity(), s.hash, Toast.LENGTH_LONG).show(); */
                    FragmentManager fm = getFragmentManager();
                    final ContractPopFragment apf = new ContractPopFragment();
                    Bundle args = new Bundle();
                    args.putLong("id", s.id);
                    apf.setArguments(args);
                    apf.show(fm, "Dialog");
                    apf.setOnDismissListener(new DialogInterface.OnDismissListener(){
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            ListView listView = getActivity().findViewById(R.id.contracts_list_view);
                            itemAdapter = new ContractItemAdapter(DBManager.cm.getAll());
                            listView.setAdapter(itemAdapter);

                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    itemAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });

                }
            });

            return view;
        }
    }
}

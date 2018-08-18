package wallet.erc20.tazzmanian.erc20wallet.servers;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import wallet.erc20.tazzmanian.erc20wallet.R;
import wallet.erc20.tazzmanian.erc20wallet.db.DBManager;


public class ServerFragment extends Fragment {

    private ServerItemAdapter serverItemAdapter;

    private OnFragmentInteractionListener mListener;

    public ServerFragment() {
        // Required empty public constructor
    }

    public static ServerFragment newInstance(String param1, String param2) {
        ServerFragment fragment = new ServerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_server, container, false);

        ListView listView = view.findViewById(R.id.server_list_view);
        serverItemAdapter = new ServerItemAdapter(DBManager.sm.getAll());
        listView.setAdapter(serverItemAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fab_add_server);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                AddServerFragment asf = new AddServerFragment();
                fragmentTransaction.replace(R.id.main_frame_layout, asf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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

//    @Override
//    public void onResume() {
//        super.onResume();
//    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class ServerItemAdapter extends BaseAdapter {

        public ArrayList<ServerItems> list;

        public ServerItemAdapter(ArrayList<ServerItems> list) {
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
            View view = inflater.inflate(R.layout.layout_server_ticket, null);

            final ServerItems s = list.get(position);

            view.setBackgroundColor(s.active ? ContextCompat.getColor(getContext(), R.color.lightGreen) : ContextCompat.getColor(getContext(), R.color.gray));

            TextView name = view.findViewById(R.id.server_name);
            name.setText(s.name);

            TextView url = view.findViewById(R.id.server_url);
            url.setText(s.host + ":" + s.port);

            LinearLayout lo = view.findViewById(R.id.server_ticket_layout);

            lo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getActivity(), s.hash, Toast.LENGTH_LONG).show();
                    FragmentManager fm = getFragmentManager();
                    final ServerPopFragment spf = new ServerPopFragment();
                    Bundle args = new Bundle();
                    args.putLong("id", s.id);
                    spf.setArguments(args);
                    spf.show(fm, "Dialog");
                    spf.setOnDismissListener(new DialogInterface.OnDismissListener(){
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            ListView listView = getActivity().findViewById(R.id.server_list_view);
                            serverItemAdapter = new ServerItemAdapter(DBManager.sm.getAll());
                            listView.setAdapter(serverItemAdapter);
                        }
                    });

                }
            });

            return view;
        }
    }
}

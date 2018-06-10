package wallet.erc20.tazzmanian.erc20wallet;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ServerPopFragment extends DialogFragment {

    private OnFragmentInteractionListener mListener;
    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public ServerPopFragment() {
        // Required empty public constructor
    }

    public static ServerPopFragment newInstance(String param1, String param2) {
        ServerPopFragment fragment = new ServerPopFragment();
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
        View view = inflater.inflate(R.layout.fragment_server_pop, container, false);

        Button defaultBtn = view.findViewById(R.id.default_btn);
        final Long id = getArguments().getLong("id");

        defaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                DBManager.sm.updateDefault(id);
                Utils.getInstance().buildConnection();
            }
        });

        Button deleteBtn = view.findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                DBManager.sm.deleteAccount(id);
            }
        });

        Button editBtn = view.findViewById(R.id.edit_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fr = new AddServerFragment();
                ServerItems si = DBManager.sm.getItemById(id);
                if(si != null) {
                    Bundle bd = new Bundle();
                    bd.putString("host", si.host);
                    bd.putString("port", si.port);
                    bd.putString("name", si.name);
                    bd.putLong("id", si.id);
                    fr.setArguments(bd);
                }
                fragmentTransaction.replace(R.id.main_frame_layout, fr);
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

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
//        Toast.makeText(getActivity(), "test1", Toast.LENGTH_LONG).show();
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

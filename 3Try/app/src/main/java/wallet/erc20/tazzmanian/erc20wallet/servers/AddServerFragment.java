package wallet.erc20.tazzmanian.erc20wallet.servers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import wallet.erc20.tazzmanian.erc20wallet.R;
import wallet.erc20.tazzmanian.erc20wallet.db.DBManager;


public class AddServerFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private EditText name, host, port;
    private Button b;
    private View view;

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
        String n = name.getText().toString();
        String p = port.getText().toString();
        String h = host.getText().toString();

        if(n.equals("")|| h.equals("") || p.equals("")){
            Toast.makeText(view.getContext(), "false", Toast.LENGTH_SHORT).show();
            b.setEnabled(false);
        } else {
            Toast.makeText(view.getContext(), "true", Toast.LENGTH_SHORT).show();
            b.setEnabled(true);
        }
    }

    public AddServerFragment() {
        // Required empty public constructor
    }

    public static AddServerFragment newInstance(String param1, String param2) {
        AddServerFragment fragment = new AddServerFragment();
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
        view = inflater.inflate(R.layout.fragment_add_server, container, false);

        name = (EditText) view.findViewById(R.id.name);
        port = (EditText) view.findViewById(R.id.port);
        host = (EditText) view.findViewById(R.id.host);
        b = view.findViewById(R.id.add);

        if(getArguments() != null) {
            TextView tv = view.findViewById(R.id.title_id);
            tv.setText(R.string.edit_server_title);
            b.setText(R.string.update_btn);
            b.setEnabled(true);
            name.setText(getArguments().getString("name"));
            port.setText(getArguments().getString("port"));
            host.setText(getArguments().getString("host"));
        }

        name.addTextChangedListener(mTextWatcher);
        port.addTextChangedListener(mTextWatcher);
        host.addTextChangedListener(mTextWatcher);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getArguments() == null) {
                    DBManager.sm.insert(name.getText().toString(), host.getText().toString(), port.getText().toString());
                } else {
                    long id = getArguments().getLong("id");
                    if(id > 0) {
                        DBManager.sm.update(name.getText().toString(), host.getText().toString(), port.getText().toString(), id);
                    }
                }
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

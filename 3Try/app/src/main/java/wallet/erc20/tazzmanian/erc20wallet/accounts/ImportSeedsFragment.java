package wallet.erc20.tazzmanian.erc20wallet.accounts;

import android.content.Context;
import android.graphics.Color;
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

import wallet.erc20.tazzmanian.erc20wallet.R;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link ImportSeedsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ImportSeedsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImportSeedsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText seed0, seed1, seed2, seed3,seed4, seed5, seed6, seed7, seed8, seed9, seed10, seed11;
    private View view;
    private Button b;

    private OnFragmentInteractionListener mListener;

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
        boolean enabled = true;
        String s0 = seed0.getText().toString();
        String s1 = seed1.getText().toString();
        String s2 = seed2.getText().toString();
        String s3 = seed3.getText().toString();
        String s4 = seed4.getText().toString();
        String s5 = seed5.getText().toString();
        String s6 = seed6.getText().toString();
        String s7 = seed7.getText().toString();
        String s8 = seed8.getText().toString();
        String s9 = seed9.getText().toString();
        String s10 = seed10.getText().toString();
        String s11 = seed11.getText().toString();

        if(s0.isEmpty()){
            enabled = false;
            seed0.setBackgroundColor(Color.LTGRAY);
        } else {
            seed0.setBackgroundColor(Color.TRANSPARENT);
        }
        if(s1.isEmpty()) {
            enabled = false;
            seed1.setBackgroundColor(Color.LTGRAY);
        } else {
            seed1.setBackgroundColor(Color.TRANSPARENT);
        }
        if(s2.isEmpty()) {
            enabled = false;
            seed2.setBackgroundColor(Color.LTGRAY);
        } else {
            seed2.setBackgroundColor(Color.TRANSPARENT);
        }
        if(s3.isEmpty()) {
            enabled = false;
            seed3.setBackgroundColor(Color.LTGRAY);
        } else {
            seed3.setBackgroundColor(Color.TRANSPARENT);
        }
        if(s4.isEmpty()) {
            enabled = false;
            seed4.setBackgroundColor(Color.LTGRAY);
        } else {
            seed4.setBackgroundColor(Color.TRANSPARENT);
        }
        if(s5.isEmpty()) {
            enabled = false;
            seed5.setBackgroundColor(Color.LTGRAY);
        } else {
            seed5.setBackgroundColor(Color.TRANSPARENT);
        }
        if(s6.isEmpty()){
            enabled = false;
            seed6.setBackgroundColor(Color.LTGRAY);
        } else {
            seed6.setBackgroundColor(Color.TRANSPARENT);
        }
        if(s7.isEmpty()){
            enabled = false;
            seed7.setBackgroundColor(Color.LTGRAY);
        } else {
            seed7.setBackgroundColor(Color.TRANSPARENT);
        }
        if(s8.isEmpty()) {
            enabled = false;
            seed8.setBackgroundColor(Color.LTGRAY);
        } else {
            seed8.setBackgroundColor(Color.TRANSPARENT);
        }
        if(s9.isEmpty()) {
            enabled = false;
            seed9.setBackgroundColor(Color.LTGRAY);
        } else {
            seed9.setBackgroundColor(Color.TRANSPARENT);
        }
        if(s10.isEmpty()) {
            enabled = false;
            seed10.setBackgroundColor(Color.LTGRAY);
        } else {
            seed10.setBackgroundColor(Color.TRANSPARENT);
        }
        if(s11.isEmpty()) {
            enabled = false;
            seed11.setBackgroundColor(Color.LTGRAY);
        } else {
            seed11.setBackgroundColor(Color.TRANSPARENT);
        }

        b.setEnabled(enabled);
    }

    public ImportSeedsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImportSeedsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImportSeedsFragment newInstance(String param1, String param2) {
        ImportSeedsFragment fragment = new ImportSeedsFragment();
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
//        View view = inflater.inflate(R.layout.fragment_import_seeds, container, false);
        view = inflater.inflate(R.layout.fragment_import_seeds, container, false);
        seed0 = (EditText) view.findViewById(R.id.seed0);
        seed1 = (EditText) view.findViewById(R.id.seed1);
        seed2 = (EditText) view.findViewById(R.id.seed2);
        seed3 = (EditText) view.findViewById(R.id.seed3);
        seed4 = (EditText) view.findViewById(R.id.seed4);
        seed5 = (EditText) view.findViewById(R.id.seed5);
        seed6 = (EditText) view.findViewById(R.id.seed6);
        seed7 = (EditText) view.findViewById(R.id.seed7);
        seed8 = (EditText) view.findViewById(R.id.seed8);
        seed9 = (EditText) view.findViewById(R.id.seed9);
        seed10 = (EditText) view.findViewById(R.id.seed10);
        seed11 = (EditText) view.findViewById(R.id.seed11);

        seed0.addTextChangedListener(mTextWatcher);
        seed1.addTextChangedListener(mTextWatcher);
        seed2.addTextChangedListener(mTextWatcher);
        seed3.addTextChangedListener(mTextWatcher);
        seed4.addTextChangedListener(mTextWatcher);
        seed5.addTextChangedListener(mTextWatcher);
        seed6.addTextChangedListener(mTextWatcher);
        seed7.addTextChangedListener(mTextWatcher);
        seed8.addTextChangedListener(mTextWatcher);
        seed9.addTextChangedListener(mTextWatcher);
        seed10.addTextChangedListener(mTextWatcher);
        seed11.addTextChangedListener(mTextWatcher);

        b = (Button) view.findViewById(R.id.button4);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Toast.makeText(getActivity(), s.hash, Toast.LENGTH_LONG).show(); */
                FragmentManager fm = getFragmentManager();
                final ImportAccountPassPopFragment pop = new ImportAccountPassPopFragment();
                Bundle args = new Bundle();
                args.putString("mnemonics", seed0.getText().toString() + " " +
                        seed1.getText().toString() + " " +
                        seed2.getText().toString() + " " +
                        seed3.getText().toString() + " " +
                        seed4.getText().toString() + " " +
                        seed5.getText().toString() + " " +
                        seed6.getText().toString() + " " +
                        seed7.getText().toString() + " " +
                        seed8.getText().toString() + " " +
                        seed9.getText().toString() + " " +
                        seed10.getText().toString() + " " +
                        seed11.getText().toString());
                pop.setArguments(args);
                pop.show(fm, "Dialog");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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

package com.example.hotelsyaria;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class filterFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button btn_filter,btn_close;
    ArrayList<CheckBox> cb_filter_list;

    private OnFragmentInteractionListener mListener;

    public filterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static filterFragment newInstance() {
        filterFragment fragment = new filterFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        prepareFilterCb(view);

        return view;
    }

    void prepareFilterCb(View v){
        cb_filter_list = new ArrayList<>();
        cb_filter_list.add(v.findViewById(R.id.cb1));
        cb_filter_list.add(v.findViewById(R.id.cb2));
        cb_filter_list.add(v.findViewById(R.id.cb3));
        cb_filter_list.add(v.findViewById(R.id.cb4));
        cb_filter_list.add(v.findViewById(R.id.cb5));
        cb_filter_list.add(v.findViewById(R.id.cb6));
        cb_filter_list.add(v.findViewById(R.id.cb7));
        cb_filter_list.add(v.findViewById(R.id.cb8));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

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

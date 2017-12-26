package com.eon.atoi.onurpt.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.eon.atoi.onurpt.R;
import com.eon.atoi.onurpt.utils.XmlFaqUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Atoi on 6.12.2017.
 */

public class FaqFragment extends Fragment {
    private String title;
    private int page;
    private ArrayList<XmlFaqUtils.FAQ> list;
    private ArrayList<String> questionList = new ArrayList<>();
    private ArrayList<String> answerList = new ArrayList<>();
    private AutoCompleteTextView autoCompleteTextView;

    public static FaqFragment newInstance(int page, String title)
    {
        FaqFragment faqFragment = new FaqFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        faqFragment.setArguments(args);
        return faqFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.faq_fragment, container, false);
        autoCompleteTextView = (AutoCompleteTextView)view.findViewById(R.id.autocompleteQuestion);
        final TextView tvAnswer = (TextView)view.findViewById(R.id.faq_answer);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvAnswer.setText(answerList.get(position));
            }
        });

        new AsyncTask<Spanned[], Void, XmlFaqUtils.FAQ[]>() {
            @Override
            public XmlFaqUtils.FAQ[] doInBackground(Spanned[]... params) {
                return XmlFaqUtils.parse(getActivity());
            }

            @Override
            public void onPostExecute(XmlFaqUtils.FAQ[] result) {
                list = new ArrayList<>(Arrays.asList(result));
                Log.d("lala", list.get(0).question.toString());
                fetchData(list);
            }
        }.execute();

        return view;
    }

    private void fetchData(ArrayList<XmlFaqUtils.FAQ> list) {
        for(XmlFaqUtils.FAQ faq : list)
        {
            String question = faq.question.toString();
             questionList.add(question);
        }

        for(XmlFaqUtils.FAQ faq : list)
        {
            String answer = faq.text.toString();
            answerList.add(answer);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, questionList);

        autoCompleteTextView.setThreshold(1);

        autoCompleteTextView.setAdapter(arrayAdapter);

    }
}

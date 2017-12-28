package com.eon.atoi.onurpt.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
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

public class FaqActivity extends Activity {
    private String title;
    private int page;
    private ArrayList<XmlFaqUtils.FAQ> list;
    private ArrayList<String> questionList = new ArrayList<>();
    private ArrayList<String> answerList = new ArrayList<>();
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_activity);

        autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autocompleteQuestion);
        final TextView tvAnswer = (TextView)findViewById(R.id.faq_answer);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvAnswer.setText(answerList.get(position));
            }
        });

        new AsyncTask<Spanned[], Void, XmlFaqUtils.FAQ[]>() {
            @Override
            public XmlFaqUtils.FAQ[] doInBackground(Spanned[]... params) {
                return XmlFaqUtils.parse(getBaseContext());
            }

            @Override
            public void onPostExecute(XmlFaqUtils.FAQ[] result) {
                list = new ArrayList<>(Arrays.asList(result));
                Log.d("lala", list.get(0).question.toString());
                fetchData(list);
            }
        }.execute();

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

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, questionList);

        autoCompleteTextView.setThreshold(1);

        autoCompleteTextView.setAdapter(arrayAdapter);

    }
}

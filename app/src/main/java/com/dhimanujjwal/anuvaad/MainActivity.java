package com.dhimanujjwal.anuvaad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public class MainActivity extends AppCompatActivity
{
    EditText input;
    TextView translated;
    EditText target_lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] items = new String[] {"Codes","Albanian (sq)","Afrikaans (ef)" ,
                                       "Armenian (hy)","Basque (eu)", "Belarusian (be)","Bengali (bn)","Bosnian (bs)",
                                       "Bulgarian (bg)", "Cebuano (ceb)","French (fr)", "Georgian (ka)","German (de)","Greek (el)",
                                       "Gujarati (gu)", "Hausa (ha)","Hawaiian (haw)","Hebrew (he)","Hindi (hi)","Hungarian (hu)",
                                       "Icelandic (is)","Indonesian (id)","Irish (ga)","Italian (it)","Japanese (ja)",
                                       "Kannada (kn)","Kazakh (kk)","Khmer (kn)","Korean (ko)","Lao (lo)","Latin (la)",
                                       "Luxembourgish (lb)","Malagasy (mg)","Malay (ms)","Malayalam (ml)","Maltese (mt)",
                                       "Maori (mi)","Marathi (mr)","Mongolian (mn)","Myanmar (my)","Nepali (ne)","Norwegian (no)",
                                       "Odia (or)","Persian (fa)","Polish (pl)","Portugese (pt)","Punjabi (pa)","Romanian (ro)",
                                       "Russian (ru)","Samoan (sn)","Serbian (sr)","Sindhi (sd)","Somali (so)","Spanish (es)",
                                       "Swahili (sw)","Swedish (sw)","Tajik (tg)","Tamil (ta)","Tatar (tt)","Telugu (te)",
                                       "Thai (th)","Turkish (tr)","Ukrainian (uk)","Urdu (ur)","Vietnamese (vi)","Yiddish (yi)",
                                       "Zulu (zu)","And Many More..."};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        Spinner Spinner = (Spinner) findViewById(R.id.spinner);

        Spinner.setAdapter(adapter);

        Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ((TextView) parent.getChildAt(0)).setTextSize(30);
                Toast.makeText(getApplicationContext(),(String) parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }


    public void translate(View view)
    {
        input = (EditText) findViewById(R.id.input);
        String text = input.getText().toString();
        target_lang = (EditText)findViewById(R.id.targetlang);
        String lang_to = target_lang.getText().toString();
        translated = (TextView) findViewById(R.id.translatedText);

        // setting observer to translated text
        ExampleJobIntentService.translatedText.observe(this,
                new Observer<String>()
                {
                    @Override
                    public void onChanged(String s)
                    {
                        translated.setText(s); // change text
                    }
                }
        );

        Intent serviceIntent = new Intent(this, ExampleJobIntentService.class);
        Bundle extras = new Bundle();
        extras.putString("input",text);
        extras.putString("target_language",lang_to);
        serviceIntent.putExtras(extras);

//        serviceIntent.putExtra("inputExtra", text);
        ExampleJobIntentService.enqueueWork(this, serviceIntent);
    }
}
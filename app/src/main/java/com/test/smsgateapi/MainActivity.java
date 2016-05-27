package com.test.smsgateapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.Random;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll1;
    private LinearLayout ll2;

    private Button submitPhoneButton;
    private Button submitSMSVerifyButton;

    private EditText phoneET;
    private EditText confirmET;

    Random rnd = new Random();
    String confirmTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);

        phoneET = (EditText) findViewById(R.id.editText);
        confirmET = (EditText) findViewById(R.id.editText2);

        submitPhoneButton = (Button) findViewById(R.id.button);
        submitSMSVerifyButton = (Button) findViewById(R.id.button2);

        submitPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneET.setError(null);
                if (phoneET.getText().toString().trim().isEmpty()) {
                    phoneET.setError("Заполните это поле");
                    phoneET.requestFocus();
                    return;
                }
                confirmTxt = String.valueOf(1000 + rnd.nextInt(9000));
                XmlMessage xmlMessage = new XmlMessage(
                        new String[]{phoneET.getText().toString().trim()},
                        confirmTxt
                );

                AsyncHttpClient client = new AsyncHttpClient();
                String s = xmlMessage.getReadyMessageXMLString();
                StringEntity se = new StringEntity(s, "UTF-8");
                client.post(MainActivity.this, "http://smspro.nikita.kg/api/message", se, "text/xml", new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        XmlParserCreator parserCreator = new XmlParserCreator() {
                            @Override
                            public XmlPullParser createParser() {
                                try {
                                    return XmlPullParserFactory.newInstance().newPullParser();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        };
                        GsonXml gsonXml = new GsonXmlBuilder()
                                .setXmlParserCreator(parserCreator)
                                .create();
                        XmlResponse xmlResponse = gsonXml.fromXml(responseString, XmlResponse.class);
                        if (xmlResponse.getStatus() != XmlResponse.SUCCESS) {
                            Toast.makeText(
                                    MainActivity.this, "Не удалось отправить СМС, статус: " + xmlResponse.getStatus() +
                                            ", " + xmlResponse.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        } else {
                            ll1.setVisibility(View.GONE);
                            ll2.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(MainActivity.this, "Не удалось отправить СМС, ответ: " + responseString, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        submitSMSVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmET.setError(null);
                if (confirmET.getText().toString().trim().isEmpty()) {
                    confirmET.setError("Заполните это поле");
                    confirmET.requestFocus();
                    return;
                }

                if (confirmET.getText().toString().trim().equals(confirmTxt))
                    startActivity(new Intent(MainActivity.this, Activity2.class));

            }
        });
    }
}

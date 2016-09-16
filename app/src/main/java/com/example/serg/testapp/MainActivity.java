package com.example.serg.testapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.serg.testapp.parse_logic.JsonFactory;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {
    private String URL = "";
    ProgressDialog progress;
    RequestQueue queue;

    private void DownloadList(String url) {
        progress = new ProgressDialog(this);
        progress.setMessage(getResources().getString(R.string.progress_message_1));
        progress.setCancelable(false);
        progress.show();
        JsonArrayRequest jreq = new JsonArrayRequest(url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Log.d(getResources().getString(R.string.log_received_result_message_1), response.toString());
                        if (response instanceof JSONArray) {
                            JSONArray array = JsonFactory.parseStringToArray(response.toString());
                            getApplicationContext().getSharedPreferences("com.example.serg.testapp", Context.MODE_PRIVATE).edit().putString("JSONArray", response.toString()).commit();
                            progress.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(getResources().getString(R.string.log_request_error_message_1), error.getLocalizedMessage());
            }
        });
        this.queue.add(jreq);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button requestBtn = (Button) findViewById(R.id.requestBtn);
        final EditText txtUrl = (EditText) findViewById(R.id.txtURL);
        if (isValidURL(txtUrl.getText().toString())) {
            URL = txtUrl.getText().toString();
            requestBtn.setEnabled(true);
        }
        this.queue = Volley.newRequestQueue(this);
        txtUrl.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (!isValidURL(String.valueOf(s.toString()))) {
                    URL = "";
                    //Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), getResources().getString(R.string.incorrect_url_message_1), Snackbar.LENGTH_LONG)
                    //      .setAction("Action", null).show();
                } else {
                    URL = txtUrl.getText().toString();
                    requestBtn.setEnabled(true);
                }
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }

        });
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadList(URL);
                Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidURL(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return id == R.id.action_settings;
        }

        return super.onOptionsItemSelected(item);
    }

}

package soft.gen.myretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import soft.gen.myretrofit.Adapter.CustomAdapter;

public class MainActivity extends AppCompatActivity {

   ProgressBar progressBar;
   RecyclerView recyclerView;
   String url = "https://api.androidhive.info/json/shimmer/menu.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Learn HTTP ok");

        recyclerView =  findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        progressBar = findViewById(R.id.progress_bar);

        getData();

    }

    private void getData() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(50000);
        asyncHttpClient.post(url, new JsonHttpResponseHandler(){

            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                progressBar.setVisibility(View.INVISIBLE);
                super.onSuccess(statusCode, headers, response);
                fetchData(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                //super.onProgress(bytesWritten, totalSize);
                long progress = (bytesWritten/totalSize)*100;
            }
        });
    }

    private void fetchData(JSONArray response) {
        CustomAdapter customAdapter = new CustomAdapter(response,this);
        recyclerView.setAdapter(customAdapter);
    }
}


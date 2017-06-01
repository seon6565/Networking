package comwow2778.naver.blog.networking0601;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main2Activity extends AppCompatActivity {
    String urlstr = "";
    EditText et1;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        et1 = (EditText)findViewById(R.id.editText);
        tv1 = (TextView)findViewById(R.id.textView);
    }

    public void onClick(View v){
        if(v.getId() == R.id.button) {
            if (et1.getText().toString().equals("")) {

            } else {
                urlstr = et1.getText().toString();
                MyThread thread = new MyThread();
                thread.start();
            }
        }
        else if(v.getId() == R.id.button2){
            Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
            startActivity(intent);
        }
    }
    Handler handler = new Handler();
    class MyThread extends Thread {
        @Override
        public void run() {
            URL url = null;
            try {
                url = new URL(urlstr);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection)url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK ) {
                    final String data = readData(urlConnection.getInputStream());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv1.setText(data);
                        }
                    });
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String readData(InputStream is) {
            String data = "";
            Scanner s = new Scanner(is);
            while(s.hasNext()) data += s.nextLine() + "\n";
            s.close();
            return data;
        }
    };
}

package comwow2778.naver.blog.networking0601;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Main4Activity extends AppCompatActivity {
    TextView tv1;
    EditText id,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        tv1 = (TextView)findViewById(R.id.login);
        id = (EditText)findViewById(R.id.id);
        password = (EditText)findViewById(R.id.password);
    }

    Handler handler = new Handler();
    class MyThread extends Thread{
        @Override
        public void run() {
            try {
                URL url = new URL("http://jerry1004.dothome.co.kr/info/login.php");
                HttpURLConnection httpURLConnection =
                        (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                String userid = id.getText().toString();
                String userpassword = password.getText().toString();

                String postData = "userid=" + URLEncoder.encode(userid)
                        + "&password=" + URLEncoder.encode(userpassword);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                InputStream inputStream;
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                    inputStream = httpURLConnection.getInputStream();
                else
                    inputStream = httpURLConnection.getErrorStream();

                final String result = loginResult(inputStream);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result.equals("FAIL"))
                            tv1.setText("로그인이 실패했습니다.");
                        else
                            tv1.setText(result + "님 로그인 성공");
                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    String loginResult(InputStream is) {
        String data = "";
        Scanner s = new Scanner(is);
        while(s.hasNext()) data += s.nextLine();
        s.close();
        return data;
    }

    public void onClick(View v){
        MyThread thread = new MyThread();
        thread.start();
    }

}

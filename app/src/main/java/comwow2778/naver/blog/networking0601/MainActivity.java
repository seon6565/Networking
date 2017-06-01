package comwow2778.naver.blog.networking0601;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    String SERVER_IP = "172.17.64.181";
    int SERVER_PORT = 200;
    EditText etmsg;
    String msg = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etmsg = (EditText) findViewById(R.id.etmsg);
    }

    Handler myHandler = new Handler();
    class MyThread extends Thread {
        @Override
        public void run() {
            try {
                Socket aSocket = new Socket(SERVER_IP, SERVER_PORT);

                ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
                msg = "Client>> " + etmsg.getText().toString();
                outstream.writeObject(msg);
                outstream.flush();

                ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
                final String data = (String) instream.readObject();
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),  "Server>> " + data, Toast.LENGTH_SHORT).show();
                    }
                });
                aSocket.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    };

    public void onClick(View v) {
        if(v.getId()==R.id.send) {
            MyThread thread = new MyThread();
            thread.start();
        }
        else{
            next();
        }
    }

    public void next() {
    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
    }

}

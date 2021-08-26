package massey.ac.nz.a1_159336;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.function.Function;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //freeze text
        tv = (TextView) findViewById(R.id.textView);
        tv.setFreezesText(true);

        //hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //array to hold buttons (excluding image buttons)
        Button[] btns = new Button[12];

        // Add to array
        btns[0] = (Button)findViewById(R.id.btn_dial0);
        btns[1] = (Button)findViewById(R.id.btn_dial1);
        btns[2] = (Button)findViewById(R.id.btn_dial2);
        btns[3] = (Button)findViewById(R.id.btn_dial3);
        btns[4] = (Button)findViewById(R.id.btn_dial4);
        btns[5] = (Button)findViewById(R.id.btn_dial5);
        btns[6] = (Button)findViewById(R.id.btn_dial6);
        btns[7] = (Button)findViewById(R.id.btn_dial7);
        btns[8] =(Button) findViewById(R.id.btn_dial8);
        btns[9] = (Button)findViewById(R.id.btn_dial9);
        btns[10] = (Button) findViewById(R.id.btn_star);
        btns[11] = (Button) findViewById(R.id.btn_hash);

        //set on click listener for all buttons in array
        for (int i = 0; i < 12; i++){
            String s;
            if (i < 10){
                s = Integer.toString(i);
            }
            else if (i == 10){
                s = "*";
            }
            else {
                s = "#";
            }
            btns[i].setOnClickListener(v -> {
                addDigit(s);
            });
        }

        // Image Buttons
        ImageButton btn_bkspc = (ImageButton) findViewById(R.id.btn_bkspc);
        ImageButton btn_call = (ImageButton) findViewById(R.id.btn_call);

        //set on click and on long click listener for backspace
        btn_bkspc.setOnClickListener(v -> {
            backspace();
        });
        btn_bkspc.setOnLongClickListener(v -> {
            tv.setText(null);
            return true;    //requires boolean
        });

        //set on click listener for make call
        btn_call.setOnClickListener(v -> {
            makeCall();
            tv.setText(null);   //clears text after call is made
        });
    }

    //single backspace method - removes end digit from textview
    public void backspace() {
        StringBuilder tempStr = new StringBuilder(tv.getText());
        tempStr.deleteCharAt(tv.getText().length() - 1);
        String updatedString = tempStr.toString();
        tv.setText(updatedString);
    }

    //addDigit method - adds dialled digit to textview
    public void addDigit(String s) {
        tv.setText(tv.getText().toString() + s);
    }

    //makecall method - handles call permission and intent
    private void makeCall() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);
            Intent sendIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tv.getText().toString()));
            startActivity(sendIntent);
            return;
        } else {
            Intent sendIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tv.getText().toString()));
            startActivity(sendIntent);
        }
    }

}
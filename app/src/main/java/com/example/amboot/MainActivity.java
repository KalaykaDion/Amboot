package com.example.amboot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import android.os.Environment;

public class MainActivity extends AppCompatActivity {
    static final int READ_BLOCK_SIZE = 100;

    final EditText textView = findViewById(R.id.editText);
    final Button btn = findViewById(R.id.button);
    final Button btn2 = findViewById(R.id.button2);
    final Button btn3 = findViewById(R.id.button3);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView.setText("");
                    }
                }
        );
        btn2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            FileInputStream fileIn = openFileInput("mytextfile.txt");
                            InputStreamReader InputRead = new InputStreamReader(fileIn);

                            char[] inputBuffer = new char[READ_BLOCK_SIZE];
                            String s = "";
                            int charRead;

                            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                                s += readstring;
                            }
                            InputRead.close();
                            textView.setText(s);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        btn3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            FileOutputStream fileout = openFileOutput("mytextfile.txt", MODE_PRIVATE);
                            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                            outputWriter.write(textView.getText().toString());
                            outputWriter.close();
                            Toast.makeText(getBaseContext(), "File saved successfully!",
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }


        );
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            btn3.setEnabled(false);
        }


    }
    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}


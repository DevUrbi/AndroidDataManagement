package eus.urbieta.datamanagement;

import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Permission;
import java.util.jar.Manifest;

public class ExternalFileSaveExample extends AppCompatActivity {

    private final String FILENAME = "textfile.txt";
    EditText mText_to_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_file_save_example);

        mText_to_save = (EditText) findViewById(R.id.text_to_save);
    }

    private boolean hasWritePermission(){
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
    private boolean isExtenalStorageWritable(){
        return (Environment.MEDIA_MOUNTED.equals( Environment.getExternalStorageState() ) );
    }

    private boolean isExternalStorageReadble(){
        return (Environment.MEDIA_MOUNTED_READ_ONLY.equals( Environment.getExternalStorageState() ) || isExtenalStorageWritable());
    }

    public void saveFile(View view) {
        if( hasWritePermission() ) {
            if (isExtenalStorageWritable()) {
                File textFile = new File(Environment.getExternalStorageDirectory(), FILENAME);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                    fileOutputStream.write(mText_to_save.getText().toString().getBytes());
                    fileOutputStream.close();
                    mText_to_save.setText("");
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Cannot write to external storage", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, "App has not storage permission activated", Toast.LENGTH_LONG ).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PackageManager.PERMISSION_GRANTED );

        }
    }

    public void loadFile(View view) {
        if( isExternalStorageReadble() ){
            StringBuilder stringBuilder = new StringBuilder();

            File textFile = new File( Environment.getExternalStorageDirectory(), FILENAME);
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(textFile);
                InputStreamReader inputStreamReader = new InputStreamReader( fileInputStream);
                BufferedReader bufferedReader = new BufferedReader( inputStreamReader);
                String newLine = null;
                while( ( newLine = bufferedReader.readLine() ) != null ){
                    stringBuilder.append( newLine + "\n" );
                }
                fileInputStream.close();
                mText_to_save.setText( stringBuilder );
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText( this, e.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }
        else{
            Toast.makeText( this, "Can not read in external storage", Toast.LENGTH_LONG ).show();
        }
    }
}

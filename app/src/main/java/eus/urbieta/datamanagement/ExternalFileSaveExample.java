package eus.urbieta.datamanagement;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ExternalFileSaveExample extends AppCompatActivity {

    private final String FILENAME = "textfile.txt";
    EditText mText_to_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_file_save_example);

        mText_to_save = (EditText) findViewById(R.id.text_to_save);
    }

    private boolean isExtenalStorageWritable(){
        return (Environment.MEDIA_MOUNTED.equals( Environment.getExternalStorageState() ));
    }

    private boolean isExternalStorageReadble(){
        return (Environment.MEDIA_MOUNTED_READ_ONLY.equals( Environment.getExternalStorageState() ) || isExtenalStorageWritable());
    }
}

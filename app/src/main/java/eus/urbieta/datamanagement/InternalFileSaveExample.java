package eus.urbieta.datamanagement;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InternalFileSaveExample extends AppCompatActivity {

    private final String FILENAME = "testfile.txt";
    EditText mTextToSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_save_example);

        mTextToSave = (EditText) findViewById( R.id.text_to_save);
    }

    public void saveFile(View view) {
        try {
            FileOutputStream dataFile = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            dataFile.write( mTextToSave.getText().toString().getBytes() );
            dataFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile(View view) {
        StringBuilder fileData = new StringBuilder();
        try {
            /*FileInputStream dataFile = openFileInput(FILENAME);*/
            InputStream inputStream = openFileInput( FILENAME );
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while( (line = bufferedReader.readLine()) != null ){
                fileData.append( line + "\n" );
            }
            inputStream.close();


        } catch (IOException e) {
            Toast message = Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
            message.show();
            e.printStackTrace();
        }
        mTextToSave.setText( fileData.toString() );

    }
}

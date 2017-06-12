package eus.urbieta.datamanagement;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SharedPreferenceExample extends AppCompatActivity {

    private final String NAME = "NAME";
    private EditText mDataToSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference_example);
        TextView savedData = (TextView) findViewById( R.id.savedData);

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE );

        String savedValue = sharedPreferences.getString( NAME, null );
        if( savedValue == null ){
            savedValue =  "Kaixo ";
        }
        savedData.setText( savedValue );


        mDataToSave = (EditText) findViewById(R.id.dataToSave);
    }

    public void onSaveButtonClick(View view) {
        SharedPreferences.Editor sharedPreferencesEditor = getPreferences( MODE_PRIVATE ).edit();
        sharedPreferencesEditor.putString( NAME, mDataToSave.getText().toString() );
        sharedPreferencesEditor.commit();
    }
}

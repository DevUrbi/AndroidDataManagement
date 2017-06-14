package eus.urbieta.datamanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class SqliteExample extends AppCompatActivity {

    private EditText mDefinitionET;
    private EditText mWordET;
    private DictionaryDatabase mDB;
    private ListView mWordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_example);

        mDB = new DictionaryDatabase( this );
        mWordET = (EditText) findViewById( R.id.word_et);
        mDefinitionET = (EditText) findViewById( R.id.definition_et);
        mWordList = (ListView) findViewById( R.id.word_list);

        mWordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = (Word)parent.getItemAtPosition( position );
                loadWord( word );
            }
        });
        mWordList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = (Word)parent.getItemAtPosition( position );
                boolean deleted = mDB.deleteWord( word.get_id() );
                loadWordList();

                return deleted;
            }
        });
        loadWordList();
    }

    private void loadWordList(){
        List<Word> wordList = mDB.getWordList();
        ArrayAdapter<Word> wordArrayAdapter = new ArrayAdapter<Word>( this,
                android.R.layout.simple_list_item_1,
                wordList );

        mWordList.setAdapter( wordArrayAdapter );
    }

    private void saveWord(){
        mDB.saveWord( mWordET.getText().toString(), mDefinitionET.getText().toString() );
        mWordET.setText("");
        mDefinitionET.setText("");
        loadWordList();
    }

    private void loadWord( Word word){
        mWordET.setText( word.get_word() );
        mDefinitionET.setText( word.get_definition() );
    }
    public void save_word(View view) {
        saveWord();
    }
}

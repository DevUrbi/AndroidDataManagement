package eus.urbieta.datamanagement;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DictionaryDatabase extends SQLiteOpenHelper {

    private static final String DATABASE = "dictionary.db";
    private static final String TABLE = "dictionary";
    private static final String COL_WORD = "word";
    private static final String COL_DEFINITION = "definition";
    private static final int DATABASE_VERSION = 1;

    public DictionaryDatabase( Context context ) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE ")
                .append( TABLE)
                .append( "(_id integer PRIMARY KEY NOT NULL, ")
                .append( COL_WORD).append( " TEXT,")
                .append( COL_DEFINITION ).append( " TEXT )");
        db.execSQL( query.toString() );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveWord( String word, String definition ){
        long id = findWordId( word );
        if( id > 0 ){
            updateWordDefinition( id, definition );
        }
        else{
            createWordDefinition( word, definition );
        }
    }

    public String getDefinition( long id ){
        String returnVal = "";
        StringBuilder query = new StringBuilder();
        query.append( "SELECT ")
                .append( COL_DEFINITION )
                .append( " FROM ")
                .append( TABLE )
                .append( " WHERE _id = ? ");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery( query.toString(), new String[]{ String.valueOf(id) });
        if( cursor.getCount() == 1 ){
            cursor.moveToFirst();
            returnVal = cursor.getString( 0 );
        }
        return returnVal;
    }

    public List<Word> getWordList(){
        List<Word> wordList = new ArrayList<Word>();
        StringBuilder query = new StringBuilder();
        query.append( "SELECT *")
                .append( " FROM ")
                .append( TABLE )
                .append( " ORDER BY ")
                .append( COL_WORD );
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery( query.toString(), null);

        final int COL_ID_INDEX = cursor.getColumnIndex( "_id");
        final int COL_WORD_INDEX = cursor.getColumnIndex( COL_WORD);
        final int COL_DEFINITION_INDEX = cursor.getColumnIndex( COL_DEFINITION);

        while( cursor.moveToNext() ){
            Word word = new Word(
                    cursor.getInt( COL_ID_INDEX),
                    cursor.getString( COL_WORD_INDEX),
                    cursor.getString( COL_DEFINITION_INDEX)
            );
            wordList.add( word);
        }

        return wordList;
    }

    private long findWordId( String word ) {
        long returnVal = -1;
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append( "SELECT _id FROM ")
                .append( TABLE )
                .append( " WHERE ")
                .append( COL_WORD ).append( " = ? ");

        Cursor cursor = db.rawQuery( query.toString(), new String[]{ word } );
        if( cursor.getCount() == 1 ){
            cursor.moveToFirst();
            returnVal = cursor.getInt( 0 );
        }
        return returnVal;
    }

    private int updateWordDefinition(long id, String definition) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wordValues = new ContentValues();
        wordValues.put( COL_DEFINITION, definition );
        return db.update( TABLE, wordValues, "_id = ?", new String[]{ String.valueOf( id ) } );
    }

    private long createWordDefinition(String word, String definition) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wordValues = new ContentValues();
        wordValues.put( COL_WORD, word );
        wordValues.put( COL_DEFINITION, definition );
        return db.insert( TABLE, null, wordValues );
    }

    public boolean deleteWord(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete( TABLE, "_id = ?", new String[]{String.valueOf( id) })>0;
    }
}

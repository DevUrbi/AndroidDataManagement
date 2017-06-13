package eus.urbieta.datamanagement;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                .append( COL_DEFINITION ).append( " TEXT");
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
}

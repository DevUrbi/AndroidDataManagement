package eus.urbieta.datamanagement;


public class Word {

    private long _id;
    private String _word;
    private String _definition;

    public Word(long _id, String _word, String _definition) {
        this._id = _id;
        this._word = _word;
        this._definition = _definition;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_word() {
        return _word;
    }

    public void set_word(String _word) {
        this._word = _word;
    }

    public String get_definition() {
        return _definition;
    }

    public void set_definition(String _definition) {
        this._definition = _definition;
    }

    @Override
    public String toString() {
        return this._word;
    }
}

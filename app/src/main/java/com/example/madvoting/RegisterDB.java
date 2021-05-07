package com.example.madvoting;

public class RegisterDB {
    int id;
    String _name;
    String _aadharno;
    String _voterid;
    String _username;
    String _password;
    public RegisterDB() {}
    public RegisterDB(String _name, String _aadharno, String _voterid, String _username, String _password)
    {
        this._name = _name;
        this._aadharno = _aadharno;
        this._voterid = _voterid;
        this._username = _username;
        this._password = _password;
    }

    public int getId() {
        return id;
    }

    public String get_aadharno() {
        return this._aadharno;
    }

    public String get_name() {
        return _name;
    }

    public String get_voterid() {
        return _voterid;
    }

    public String get_password() {
        return _password;
    }

    public String get_username() {
        return _username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_aadharno(String _aadharno) {
        this._aadharno = _aadharno;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public void set_voterid(String _voterid) {
        this._voterid = _voterid;
    }

}

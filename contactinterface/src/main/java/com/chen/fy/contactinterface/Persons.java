package com.chen.fy.contactinterface;

import com.github.promeg.pinyinhelper.Pinyin;

class Persons {

    private String word;
    private String pinyin;
    private String name;

    Persons(String name){
        this.name = name;
        pinyin = Pinyin.toPinyin(name,"");
        word = Pinyin.toPinyin(name,"").substring(0,1);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}

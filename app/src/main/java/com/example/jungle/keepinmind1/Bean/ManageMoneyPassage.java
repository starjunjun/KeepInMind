package com.example.jungle.keepinmind1.Bean;

/**
 * Created by jungle on 2017/12/13.
 */

public class ManageMoneyPassage {
    private String passageTitle;
    private String passageImg;
    private String passageContent;

    public ManageMoneyPassage(String passageTitle, String passageImg, String passageContent) {
        this.passageTitle = passageTitle;
        this.passageImg = passageImg;
        this.passageContent = passageContent;
    }

    public String getPassageTitle() {
        return passageTitle;
    }

    public void setPassageTitle(String passageTitle) {
        this.passageTitle = passageTitle;
    }

    public String getPassageImg() {
        return passageImg;
    }

    public void setPassageImg(String passageImg) {
        this.passageImg = passageImg;
    }

    public String getPassageContent() {
        return passageContent;
    }

    public void setPassageContent(String passageContent) {
        this.passageContent = passageContent;
    }
}

package com.AartiSangrah.Data;

public class clsAarti {
    String Id1;
    String TitleHindi;
    String TitleEnglish;
    String PhotoUrl;

    String DescriptionHindi;
    String DescriptionEnglish;

    String HindiFilePath;
    String EnglishFilePath;

    public clsAarti(String sId1, String sTitleHindi, String sTitleEnglish, String sPhotoUrl, String sDescriptionHindi, String sDescriptionEnglish, String sHindiFilePath, String sEnglishFilePath) {
        Id1 = sId1;
        TitleHindi = sTitleHindi;
        TitleEnglish = sTitleEnglish;

        DescriptionHindi = sDescriptionHindi;
        DescriptionEnglish = sDescriptionEnglish;

        HindiFilePath = sHindiFilePath;
        EnglishFilePath = sEnglishFilePath;

        PhotoUrl = sPhotoUrl;
    }

    public String getId1() {
        return Id1;
    }

    public void setId1(String sId1) {
        Id1 = sId1;
    }


    public String getTitleHindi() {
        return TitleHindi;
    }

    public void setTitleHindi(String sTitleHindi) {
        TitleHindi = sTitleHindi;
    }

    public String getTitleEnglish() {
        return TitleEnglish;
    }

    public void setTitleEnglish(String sTitleEnglish) {
        TitleEnglish = sTitleEnglish;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String sPhotoUrl) {
        PhotoUrl = sPhotoUrl;
    }

    public String getDescriptionHindi() {
        return DescriptionHindi;
    }

    public void setDescriptionHindi(String sDescriptionHindi) {
        DescriptionHindi = sDescriptionHindi;
    }

    public String getDescriptionEnglish() {
        return DescriptionEnglish;
    }

    public void setDescriptionEnglish(String sDescriptionEnglish) {
        DescriptionEnglish = sDescriptionEnglish;
    }

    public String getHindiFilePath() {
        return HindiFilePath;
    }

    public void setHindiFilePath(String sHindiFilePath) {
        HindiFilePath = HindiFilePath;
    }

    public String getEnglishFilePath() {
        return EnglishFilePath;
    }

    public void setEnglishFilePath(String sEnglishFilePath) {
        EnglishFilePath = sEnglishFilePath;
    }
}


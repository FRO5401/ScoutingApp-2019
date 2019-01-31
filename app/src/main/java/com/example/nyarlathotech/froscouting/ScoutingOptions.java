package com.example.nyarlathotech.froscouting;

public class ScoutingOptions {

    private String cardType;
    private String title;
    private String buttonOne;
    private String buttonTwo;
    private String buttonThree;
    private String data;
    private String period;

    //SET Method Basically
    public ScoutingOptions(String[] Options){
        this.cardType = Options[0];
        this.title = Options[1];
        this.buttonOne = Options[2];
        this.buttonTwo = Options[3];
        this.buttonThree = Options[4];
        this.period = Options[5];
    }

    public String getCardType(){ return cardType;}

    public String getCardTitle(){ return title;}

    public String getCardOne(){ return buttonOne;}

    public String getCardTwo(){ return buttonTwo;}

    public String getCardThree(){ return buttonThree;}

    public String getCardPeriod(){ return period;}

    public String getData(){ return data;}

    public void setData(String data) {
        this.data = data;
    }
}

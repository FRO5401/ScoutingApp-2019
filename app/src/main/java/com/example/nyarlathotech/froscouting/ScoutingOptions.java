package com.example.nyarlathotech.froscouting;

public class ScoutingOptions {

    private String cardType;
    private String title;
    private String buttonOne;
    private String buttonTwo;
    private String buttonThree;
    private String data;
    private String period;

    //Creates a class with data filled from other pages
    public ScoutingOptions(String[] Options){
        this.cardType = Options[0];
        this.title = Options[1];
        this.buttonOne = Options[2];
        this.buttonTwo = Options[3];
        this.buttonThree = Options[4];
        this.period = Options[5];
    }

    //Gets the type of card that it'll be (ADD/SUB, T/F, TEXTINPUT, ETC)
    public String getCardType(){ return cardType.trim();}

    //Gets the title of the card (FUNCTION EX. AMOUNT OF JAWN)
    public String getCardTitle(){ return title;}

    //Gets the first option for multiple choice
    public String getCardOne(){ return buttonOne;}

    //Gets the second option for multiple choice
    public String getCardTwo(){ return buttonTwo;}

    //Gets the third option for multiple choice
    public String getCardThree(){ return buttonThree;}

    //Gets the mode the card is in (AUTO, TELEOP, END)
    public String getCardPeriod(){ return period;}

    //Allows the users data to be collected
    public String getData(){ return data;}

    //Allows the data to be set to null or nothing
    public void setData(String data) {
        this.data = data;
    }
}

package com.example.nyarlathotech.froscouting;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class ScoutingAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ScoutingOptions> Options;
    LayoutInflater layoutInflate;

    //Instantiates layout
    public ScoutingAdapter(Context c, ArrayList<ScoutingOptions> OptionsList){
        this.mContext = c;
        this.Options = OptionsList;
        this.layoutInflate = LayoutInflater.from(c);
    }

    //Gets amount of cards
    public int getCount(){
        return Options.size();
    }

    //Gets specific card
    public Object getItem(int i){
        return Options.get(i);
    }

    //Gets card id
    public long getItemId(int i){
        return i;
    }

    //Adds an extra/new card to the arra
    public void addOption(ScoutingOptions option){ this.Options.add(option);}

    //Returns the data inputted by the user
    public String getScoutingData(int i){ return Options.get(i).getData(); }

    //Presents the card in the view
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ScoutingOptions so = (ScoutingOptions) this.getItem(i);

        //Creates add/subtract card
        if (so.getCardType().equals("1") || so.getCardType().equals(" 1") || so.getCardType().equals("  1")) {

            if (view == null) {
                view = layoutInflate.inflate(R.layout.addsub_card, viewGroup, false);
            }

            TextView NumberTitle = view.findViewById(R.id.subadd_title);
            NumberTitle.setText(so.getCardTitle());

            final TextView Numbers = view.findViewById(R.id.number_text);
            Numbers.setText("0");
            so.setData("0");

            Button subtract =   view.findViewById(R.id.minus);
            Button addition = view.findViewById(R.id.plus);

            subtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pastValue = Numbers.getText().toString();
                    Numbers.setText(String.valueOf(Integer.valueOf(pastValue) - 1));
                    so.setData(Numbers.getText().toString());
                }
            });

            addition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pastValue = Numbers.getText().toString();
                    Numbers.setText(String.valueOf(Integer.valueOf(pastValue) + 1));
                    so.setData(Numbers.getText().toString());
                }
            });

            if (so.getCardPeriod().equals("auto") || so.getCardPeriod().equals(" auto")){
                CardView subadd = view.findViewById(R.id.subadd_card);
                subadd.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryLight));
            } else if (so.getCardPeriod().equals("teleops") || so.getCardPeriod().equals(" teleops") || so.getCardPeriod().equals(" teleops ") || so.getCardPeriod().contains("te")){
                CardView subadd = view.findViewById(R.id.subadd_card);
                subadd.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            } else if (so.getCardPeriod().equals("end") || so.getCardPeriod().equals(" end")){
                CardView subadd = view.findViewById(R.id.subadd_card);
                subadd.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.Red));
                Numbers.setTextColor(mContext.getResources().getColor(R.color.white));
                NumberTitle.setTextColor(mContext.getResources().getColor(R.color.white));
                subtract.setTextColor(mContext.getResources().getColor(R.color.white));
                addition.setTextColor(mContext.getResources().getColor(R.color.white));
                subtract.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
                addition.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            }

        }

        //Creates true/false card
        if(so.getCardType().equals("2") || so.getCardType().equals(" 2") || so.getCardType().equals("  2")) {

            if (view == null) {
                view = layoutInflate.inflate(R.layout.checkbox_card, viewGroup, false);

                TextView checkTitle = (TextView) view.findViewById(R.id.check_title);
                checkTitle.setText(so.getCardTitle());


                final RadioGroup checkGroup = (RadioGroup) view.findViewById(R.id.TrueFalseGroup);
                final RadioButton trueBox = view.findViewById(R.id.trueBox);
                final RadioButton falseBox = view.findViewById(R.id.falseBox);



                checkGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if(radioGroup.getCheckedRadioButtonId() == R.id.trueBox) {
                            so.setData("1");
                        }

                        if(radioGroup.getCheckedRadioButtonId() == R.id.falseBox){
                            so.setData("0");
                        }
                    }
                });

                falseBox.setChecked(true);


                if (so.getCardPeriod().equals("auto") || so.getCardPeriod().equals(" auto")){
                    CardView checkbox = view.findViewById(R.id.checkbox_card);
                    checkbox.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryLight));
                } else if (so.getCardPeriod().equals("teleops") || so.getCardPeriod().equals(" teleops") || so.getCardPeriod().equals(" teleops ")){
                    CardView subadd = view.findViewById(R.id.checkbox_card);
                    subadd.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                } else if (so.getCardPeriod().equals("end") || so.getCardPeriod().equals(" end")){
                    CardView checkbox = view.findViewById(R.id.checkbox_card);
                    checkbox.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.Red));
                    checkTitle.setTextColor(mContext.getResources().getColor(R.color.white));
                    falseBox.setTextColor(mContext.getResources().getColor(R.color.white));
                    trueBox.setTextColor(mContext.getResources().getColor(R.color.white));
                }

            }
        }

        //Creates textInput card
        if(so.getCardType().equals("3") || so.getCardType().equals(" 3") || so.getCardType().equals("  3")){

            if (view == null) {
                view = layoutInflate.inflate(R.layout.textinput_card, viewGroup, false);

                TextView inputTitle = (TextView) view.findViewById(R.id.textinput_title);
                inputTitle.setText(so.getCardTitle());

                TextInputLayout newLayout = view.findViewById(R.id.layoutNew);

                final TextInputEditText textInputEditText = view.findViewById(R.id.edittext_input);

                textInputEditText.setScroller(new Scroller(mContext));
                textInputEditText.setMaxLines(30);
                textInputEditText.setVerticalScrollBarEnabled(true);
                textInputEditText.setMovementMethod(new ScrollingMovementMethod());

                textInputEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        so.setData(textInputEditText.getText().toString());
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        so.setData(textInputEditText.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        so.setData(textInputEditText.getText().toString());
                    }
                });


                if (so.getCardPeriod().equals("auto") || so.getCardPeriod().equals(" auto")){
                    CardView text = view.findViewById(R.id.textinputcard);
                    text.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryLight));
                } else if (so.getCardPeriod().equals("teleops") || so.getCardPeriod().equals(" teleops") || so.getCardPeriod().equals(" teleops ")){
                    CardView text = view.findViewById(R.id.textinputcard);
                    text.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                } else if (so.getCardPeriod().equals("end") || so.getCardPeriod().equals(" end")){
                    CardView text = view.findViewById(R.id.textinputcard);
                    text.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.Red));
                    inputTitle.setTextColor(mContext.getResources().getColor(R.color.white));
                    textInputEditText.setTextColor(mContext.getResources().getColor(R.color.white));
                    newLayout.setHintTextAppearance(R.style.Theme_AppCompat_Light);
                }

            }
        }

        //Creates multiple choice card
        if(so.getCardType().equals("4") || so.getCardType().equals(" 4") || so.getCardType().equals("  4")){

            if (view == null) {
                view = layoutInflate.inflate(R.layout.choice_card, viewGroup, false);

                TextView choiceTitle = (TextView) view.findViewById(R.id.choice_title);
                choiceTitle.setText(so.getCardTitle());

                final RadioButton choice1 = (RadioButton) view.findViewById(R.id.Choice1);
                choice1.setText(so.getCardOne());

                final RadioButton choice2 = (RadioButton) view.findViewById(R.id.Choice2);
                choice2.setText(so.getCardTwo());

                final RadioButton choice3 = (RadioButton) view.findViewById(R.id.Choice3);
                choice3.setText(so.getCardThree());

                final RadioGroup choiceGroup = (RadioGroup) view.findViewById(R.id.ChoiceGroup);

                choiceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if(choiceGroup.getCheckedRadioButtonId() == R.id.Choice1){
                            so.setData("1");
                        } else if (choiceGroup.getCheckedRadioButtonId() == R.id.Choice2){
                            so.setData("2");
                        } else if (choiceGroup.getCheckedRadioButtonId() == R.id.Choice3){
                            so.setData("3");
                        } else {
                            so.setData("0.0");
                        }
                    }
                });

                choice1.setChecked(true);


                if (so.getCardPeriod().equals("auto") || so.getCardPeriod().equals(" auto")){
                    CardView choice = view.findViewById(R.id.choice_card);
                    choice.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryLight));
                } else if (so.getCardPeriod().equals("teleops") || so.getCardPeriod().equals(" teleops") || so.getCardPeriod().equals(" teleops ")){
                    CardView choice = view.findViewById(R.id.choice_card);
                    choice.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                } else if (so.getCardPeriod().equals("end") || so.getCardPeriod().equals(" end")){
                    CardView choice = view.findViewById(R.id.choice_card);
                    choice.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.Red));
                    choiceTitle.setTextColor(mContext.getResources().getColor(R.color.white));
                    choice1.setTextColor(mContext.getResources().getColor(R.color.white));
                    choice2.setTextColor(mContext.getResources().getColor(R.color.white));
                    choice3.setTextColor(mContext.getResources().getColor(R.color.white));
                }

            }

         //Creates placeholder card
        } else if(so.getCardType().equals("5") || so.getCardType().equals(" 5") || so.getCardType().equals("  5")){
            if (view == null) {
                view = layoutInflate.inflate(R.layout.placeholder_card, viewGroup, false);
            }
        }

        return view;
    }



}

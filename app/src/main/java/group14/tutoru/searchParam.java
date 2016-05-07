package group14.tutoru;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class searchParam extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, ThisSearchView {




    // Initialize element types
    Spinner pricespinner;
    AutoCompleteTextView subject;
    RatingBar rating;

    // Initialize strings to send to server
    public String selectedSubject = "";
    private String priceBand = "";

    // Three variables to detect if a selection has been made
    private int ratingPicked = 0;
    private int subjectPicked = 0;
    private int pricePicked = 0;

    // initialize star rating
    private float starRating = 0;

    // initialize boolean checker for geo checkbox
    private boolean checked = false;

    // Unit Testing
    private searchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        presenter = new searchPresenter(this, new PerformSearch());



        Toast.makeText(searchParam.this, "onCreate called", Toast.LENGTH_LONG).show();
         if (savedInstanceState != null)
         {
            //Toast.makeText(searchParam.this, "if savedInstanceState != NULL", Toast.LENGTH_LONG).show();
            selectedSubject = savedInstanceState.getString("subject");
            priceBand = savedInstanceState.getString("priceBand");
            checked = savedInstanceState.getBoolean("isCheck");
            starRating = savedInstanceState.getFloat("rating");
         }
         else
            //Toast.makeText(searchParam.this, "if savedInstanceState = NULL", Toast.LENGTH_SHORT).show();

        // Call all listeners
        listenerForRatingBar();
        listenerForSubject();
        listenerForSearch();
        listenerForAdvSearch();
        listenerForPrice();

    }



    // Save instance between activities
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {

        //Toast.makeText(searchParam.this, "onSaveInstanceState", Toast.LENGTH_SHORT).show();
        savedInstanceState.putString("subject", selectedSubject);
        savedInstanceState.putString("priceBand", priceBand);
        savedInstanceState.putFloat("rating", starRating);
        savedInstanceState.putBoolean("isCheck", checked);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        Toast.makeText(searchParam.this, "onRestoreInstanceState", Toast.LENGTH_LONG).show();
        super.onRestoreInstanceState(savedInstanceState);

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i , long l)
    {
        TextView priceText = (TextView) view;
        //Toast.makeText(this, "You selected: "+myText.getText(), Toast.LENGTH_SHORT).show();
        priceBand = priceText.toString();
        ++pricePicked;
    }


    // Define listeners
    public void listenerForSearch()
    {
        Button searchBtn = (Button) findViewById(R.id.searchButton);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.listenerForSearch();

                if (pricePicked > 0 && subjectPicked > 0 && ratingPicked > 0) {


                    // Add data to bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("Subject", selectedSubject);
                    bundle.putString("String", priceBand);
                    bundle.putFloat("StarRating", starRating);
                    if (checked == true) {
                        bundle.putBoolean("GeoSort", checked);
                    } else if (checked == false) {
                        bundle.putBoolean("GeoSort", checked);
                    }


                    Intent searchInit = new Intent(searchParam.this, PerformSearch.class);
                    searchInit.putExtra("TermsBundle", bundle);
                    startActivity(searchInit);

                }
            }
        });
    }

    public void listenerForSubject()
    {
        // Subject search
        subject = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ArrayAdapter subjectAdapter = ArrayAdapter.createFromResource(this, R.array.Subjects, android.R.layout.select_dialog_item);
        subject.setThreshold(1);
        subject.setAdapter(subjectAdapter);
        //Subject.setOnItemSelectedListener(this);
        subject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                TextView myText = (TextView) view;
                selectedSubject = view.toString();
                ++subjectPicked;
                //Toast.makeText(searchParam.this, "You selected: " + myText.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void listenerForRatingBar()
    {
        rating = (RatingBar) findViewById(R.id.ratingBar);
        rating.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        starRating = rating;
                        //Toast.makeText(searchParam.this, "You selected: " + starRating, Toast.LENGTH_SHORT).show();
                        ++ratingPicked;
                    }
                }
        );

    }

    public void listenerForAdvSearch()
    {
        // Initialize advanced options button
        Button advancedOptionsBtn = (Button) findViewById(R.id.advancedButton);
        advancedOptionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent advancedOptions = new Intent(searchParam.this, advancedSearch.class);
                startActivity(advancedOptions);
            }
        });
    }



    public void listenerForPrice()
    {
        // Create price spinner
        pricespinner = (Spinner)findViewById(R.id.priceSpinner);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(this, R.array.PriceBands, android.R.layout.simple_spinner_dropdown_item);
        pricespinner.setAdapter(adapter);
        pricespinner.setOnItemSelectedListener(this);
    }




    // Tests
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public String getSubject() {
        return subject.getText().toString();
    }

    @Override
    public Integer getPriceBand(){
        return pricePicked;
    }


    @Override
    public Float getStarRating() {
        return starRating;
    }

    @Override
    public void showSubjectError(int resId) {
        subject.setError(getString(resId));
    }

    @Override
    public void showSpinnerError(int resId) {
        TextView errorText = (TextView)pricespinner.getSelectedView();
        errorText.setError("");
        errorText.setTextColor(Color.RED);
        errorText.setText(getString(resId));
    }


    @Override
    public void showStarRatingError(int resId){

    }

}

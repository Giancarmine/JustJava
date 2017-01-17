package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    String name;
    int quantity = 1;
    boolean cream = false;
    boolean chocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        cream = withCream();
        chocolate = withChocolate();
        name = sayMyName();
        int price = calculatePrice();
        String priceMessage = createOrderSummary(price);
        composeEmail(priceMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(this, "You cannot have more then 100 coffee", Toast.LENGTH_SHORT).show();
        }else{
            quantity++;
            displayQuantity (quantity);
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            Toast.makeText(this, "You cannot have less then 1 coffee", Toast.LENGTH_SHORT).show();
        }else{
            quantity--;
            displayQuantity (quantity);
        }
    }

    /**
     * This method concat the Order Summary.
     */
    private String createOrderSummary(int price){
        StringBuilder priceMessage = new StringBuilder("Name: " + name);
        priceMessage.append("\nAdd Whipped cream: " + cream);
        priceMessage.append("\nAdd Chocolate: " + chocolate);
        priceMessage.append("\nQuantity: " + quantity);
        priceMessage.append("\nTotal price: $" + price);
        priceMessage.append("\nThank you!");
        return priceMessage.toString();
    }

    /**
     * This method concat the Order Summary.
     */
    private boolean withCream(){
        final CheckBox checkBox = (CheckBox) findViewById(R.id.notify_cream);
        return checkBox.isChecked();
    }

    private boolean withChocolate(){
        final CheckBox checkBox = (CheckBox) findViewById(R.id.notify_chocolate);
        return checkBox.isChecked();
    }

    private String sayMyName(){
        EditText  mEdit = (EditText)findViewById(R.id.name);
        return mEdit.getText().toString();
    }

    private int calculatePrice(){
        int price = quantity * 5;
        if (chocolate)
            price += 2;
        if (cream)
            price ++;
        return price;
    }

    //Invia l'intento al app mail
    public void composeEmail(String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
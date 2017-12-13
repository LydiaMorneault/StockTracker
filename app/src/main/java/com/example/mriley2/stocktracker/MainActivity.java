package com.example.mriley2.stocktracker;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import java.util.ArrayList;

public class MainActivity extends ListActivity {

    private DBAdapter db;
    private ArrayList<String> titles;
    private ArrayAdapter arrayAdapter;
    private Dialog dialog;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBAdapter(this);
        db.open();
        db.emptyTable();

        Stock stock1 = new Stock("AAPL", "Apple Inc.");
        Stock stock2 = new Stock("GOOGL", "Google Inc.");
        Stock stock3 = new Stock("AMZN", "Amazon");
        db.addStock(stock1);
        db.addStock(stock2);
        db.addStock(stock3);

        draw();
    }

    private void draw(){
        ArrayList<Stock> allStocks = db.getAllStocks();
        titles = new ArrayList<>();
        int i = 0;
        int length = allStocks.size();
        while (i < length) {
            Stock currentStock = allStocks.get(i);
            String stockTitle = (currentStock.getSymbol() + "-" + currentStock.getCompanyName());
            int id = currentStock.getID();
            titles.add(stockTitle);
            i++;
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
        setListAdapter(arrayAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
//        MenuItem item1 = menu.add(0,0,0,"Add Stock");
//        item1.setAlphabeticShortcut('a');
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                final Dialog dialog = new Dialog(context, R.style.CustomDialog);
                dialog.setContentView(R.layout.dialog_add);
                dialog.setTitle("Add Stock");

                final EditText edit_id = (EditText) dialog.findViewById(R.id.edit_id);
                final EditText edit_name = (EditText) dialog.findViewById(R.id.edit_company);
                Button btn_add = (Button) dialog.findViewById(R.id.btnAdd);

                //TODO Add a back button

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String symbol = edit_id.getText().toString();
                        String name = edit_name.getText().toString();
                        Stock stock = new Stock(name, symbol);
                        db.addStock(stock);
                        //db.DBHelper.onUpgrade(DBAdapter.db, 1, 2)
                        dialog.dismiss();
                        draw();
                    }
                });

                dialog.show();
                Toast.makeText(this, "You clicked on Item 1", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }


}

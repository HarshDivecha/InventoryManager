package com.three_amigos.inventorymanager.admin_panel;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.three_amigos.inventorymanager.R;
import com.three_amigos.inventorymanager.globals.DatabaseHelper;
import com.three_amigos.inventorymanager.globals.DatabaseHelper2;
import com.three_amigos.inventorymanager.globals.HelperFunctions;

import java.util.ArrayList;

import io.opencensus.internal.StringUtil;

public class AddnewInvAdmin extends AppCompatActivity implements View.OnClickListener {

    // GLOBAL NECESSARY ELEMENTS
    Context context;
    DatabaseHelper2 mydb;
    HelperFunctions helper;

    // BUTTONS AND VIEWS
    ImageButton createItemBtn;
    ImageButton deleteItemBtn;
    Button helpBtn;

    //LISTVIEW ELEMENTS
    ListView collistview;
    String itemSelected;
    ArrayList<String> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_inv_admin);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //GLOBAL NECESSRAY ELEMENTS
        context = com.three_amigos.inventorymanager.admin_panel.AddnewInvAdmin.this;
        mydb = new DatabaseHelper2(context);
        helper = new HelperFunctions(context);

        // BUTTONS AND TEXTVIEWS
        helpBtn = findViewById(R.id.itemHelp_btn_inventoryAdd);
        createItemBtn = findViewById(R.id.addItem_Btn_inventoryAdd);
        deleteItemBtn = findViewById(R.id.deleteItem_Btn_inventoryAdd);

        // LISTVIEW ELEMENTS
        collistview = findViewById(R.id.items_listview_InventoryAdd);
        itemSelected = "";
        itemList = new ArrayList<>();

        //INITIALIZATIONS
        updateItemList();
        helpBtn.setOnClickListener(this);
        createItemBtn.setOnClickListener(this);
        deleteItemBtn.setOnClickListener(this);

        // LIST ITEM SELECTION FUNCTIONALITY
        collistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                helper.setItemNormalColor(collistview);
                itemSelected = collistview.getItemAtPosition(position).toString();
                helper.setItemSelectedTextColor(view);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addItem_Btn_inventoryAdd:
                showAddItemDialog(context);
                break;
            case R.id.deleteItem_Btn_inventoryAdd:
                deleteEntry();
                break;
            case R.id.itemHelp_btn_inventoryAdd:
                showHelpPopUp(context);
                break;
        }
    }

    public void showAddItemDialog(Context c) {
        View layout = getLayoutInflater().inflate(R.layout.item_add_admin_popup,null);
        final EditText nameEt = (EditText) layout.findViewById(R.id.itemET_admin_popup);
        final EditText priceEt = (EditText) layout.findViewById(R.id.priceET_admin_popup);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(c)
                .setTitle("Add Product:")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameEt.getText().toString().toLowerCase().trim();
                        String price=priceEt.getText().toString().toLowerCase().trim();

                        if(name.equals("")|price.equals("")){
                            helper.makeToast("PLEASE FILL ALL THE VALUES","LONG");
                            return;
                        }else if(!price.matches("[0-9]+")) {
                            helper.makeToast("Price should be numeric");
                            return;
                        }else{
                            addEntry(name,price);
                            updateItemList();
                        }

                    }
                })
                .setNegativeButton("Cancel",null)
                .setView(layout);

        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }

    public void updateItemList() {
        itemList = mydb.getListContents();
        if(itemList!=null){
            ListAdapter itemListAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,itemList);
            collistview.setAdapter(itemListAdapter);
        }

    }

    public void addEntry(String name, String price) {
        if(!mydb.checkDuplicate(name)){
            mydb.addData(name,price);
            updateItemList();
        }else{
            helper.makeToast("Item alreadyExists");
        }

    }

    public void deleteEntry(){
        String ID = itemSelected.split("    ")[0];
        if(!itemSelected.equals("")){
            try {
                mydb.deleteItem(ID);
                updateItemList();
            }catch (Exception e){
                helper.makeDialog(context,e.getMessage(),"Error");
            }
        }else {
            helper.makeToast("Please select an item to delete.");
        }
    }

    private void showHelpPopUp(Context c){
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("HELP")
                .setMessage("CLICK \n\n" +
                        " '+':\n To Add items. \n" +
                        " '-' :\n Select an item from the list, then click this to delete. \n\n"
                        )
                .setPositiveButton("Ok", null)
                .create();
        dialog.show();
    }
}

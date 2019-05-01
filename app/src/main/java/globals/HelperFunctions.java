package globals;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HelperFunctions {

    Context context;
    public HelperFunctions(Context context){
        this.context = context;
    }

    public void makeToast(String str){
        Toast toast = Toast.makeText(this.context,str,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.CENTER,0,350);
        toast.show();
    }

    public void makeToast(String str,String dur){
        int duration=0;

        if (dur.equalsIgnoreCase("L")){
            duration = Toast.LENGTH_LONG;
        }else{
            duration=Toast.LENGTH_SHORT;
        }
        Toast toast = Toast.makeText(this.context,str,duration);
        toast.setGravity(Gravity.TOP|Gravity.CENTER,0,350);
        toast.show();
    }

    public void makeDialog(Context c,String message,String title) {
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle( title )
                .setMessage( message )
                //   .setView(tableNameEditText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Custom : DO something  of your own like calling a  fucntion.
                    }
                })
                //.setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }


    public void refreshActivity(Activity act){
        // For Parameter write "this"
        Intent intent = act.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        act.finish();
        act.startActivity(intent);
    }

    /*****************     LISTVIEW HELPERS    *****************
     * 1. setItemSelectedTextColor
     * 2. setItemNormalColor
     */
    public void setItemSelectedTextColor(View view){
        View rowView = view;
        TextView tv = (TextView)rowView.findViewById(android.R.id.text1);
        tv.setTextColor(Color.WHITE);
    }

    public void setItemNormalColor(ListView lv) {
        for (int i=0; i< lv.getChildCount(); i++) {
            View v = lv.getChildAt(i);
            TextView txtview = ((TextView)v.findViewById(android.R.id.text1));
            txtview.setTextColor(Color.BLACK);
        }
    }
}

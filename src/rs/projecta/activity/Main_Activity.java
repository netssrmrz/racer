package rs.projecta.activity;
//import android.database.*;
//import android.view.*;

public class Main_Activity 
extends android.app.Activity
implements 
  android.widget.Button.OnClickListener
{
	@Override
	public void onCreate(android.os.Bundle saved_state)
	{
		android.widget.Button button;
    android.widget.ScrollView scroll_view;
    android.widget.LinearLayout list_view;
    int c;
    
		super.onCreate(saved_state);
		
    list_view=new android.widget.LinearLayout(this);
    list_view.setOrientation(android.widget.LinearLayout.VERTICAL);
    for (c=0; c<20; c++)
    {
      button=new android.widget.Button(this);
      button.setOnClickListener(this);
      button.setText("Level "+(c+1));
      list_view.addView(button);
    }
    
    scroll_view=new android.widget.ScrollView(this);
    scroll_view.addView(list_view);
 
		this.setContentView(scroll_view);
	}
  
  @Override
  public void onClick(android.view.View v)
  {
    android.content.Intent i;
    
    i=new android.content.Intent(this, Play_Activity.class);
    i.setFlags(android.content.Intent.FLAG_ACTIVITY_NO_HISTORY);
    this.startActivity(i);
  }
}

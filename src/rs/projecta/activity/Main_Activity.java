package rs.projecta.activity;

public class Main_Activity 
extends android.app.Activity
implements android.widget.Button.OnClickListener
{
	@Override
	public void onCreate(android.os.Bundle saved_state)
	{
		android.widget.Button button;
    
		super.onCreate(saved_state);
		
    button=new android.widget.Button(this);
    button.setText("Start");
    button.setOnClickListener(this);
    
		this.setContentView(button);
	}
  
  @Override
  public void onClick(android.view.View v)
  {
    android.content.Intent i;
    
    i=new android.content.Intent(this, Play_Activity.class);
    this.startActivity(i);
  }
}

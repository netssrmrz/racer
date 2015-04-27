package rs.projecta.activity;

public class Finish_Activity
extends android.app.Activity
implements 
android.widget.Button.OnClickListener
{
  @Override
  public void onCreate(android.os.Bundle saved_state)
  {
    android.widget.Button button;
    android.widget.TextView msg_view;
    android.widget.LinearLayout layout_view;

    super.onCreate(saved_state);

    msg_view=new android.widget.TextView(this);
    msg_view.setText("Well Done!");
    msg_view.setTextSize(10f);
    
    button=new android.widget.Button(this);
    button.setText("Start Next Level");
    button.setOnClickListener(this);
    
    layout_view=new android.widget.LinearLayout(this);
    layout_view.setOrientation(android.widget.LinearLayout.VERTICAL);
    layout_view.addView(msg_view);
    layout_view.addView(button);

    this.setContentView(layout_view);
  }

  @Override
  public void onClick(android.view.View v)
  {
    android.content.Intent i;

    i=new android.content.Intent(this, Play_Activity.class);
    this.startActivity(i);
  }
}

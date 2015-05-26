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
    int c=0;
    rs.projecta.level.Level level=null;
    String label;

		super.onCreate(saved_state);

    list_view = new android.widget.LinearLayout(this);
    list_view.setOrientation(android.widget.LinearLayout.VERTICAL);

    button = new android.widget.Button(this);
    button.setOnClickListener(this);
    button.setText("Level Editor");
    button.setId(1);
    list_view.addView(button);

    for
    (
      level = rs.projecta.level.Level.Get(rs.projecta.level.Tutorial_Forward.class);
      level != null;
    level = rs.projecta.level.Level.Get(level.Get_Next_Level())
    )
    {
      label =
        "Level " + (c + 1) + ": " + level.Get_Title() + "\n" +
        level.Get_Description();

      button = new android.widget.Button(this);
      button.setOnClickListener(this);
      button.setText(label);
      button.setTag(level.getClass().getName());
      list_view.addView(button);

      c++;
    }

    scroll_view = new android.widget.ScrollView(this);
    scroll_view.addView(list_view);

		this.setContentView(scroll_view);
	}

  @Override
  public void onClick(android.view.View button)
  {
    android.content.Intent i;
    String level_class;

    if (button.getId() == 1)
    {
      i = new android.content.Intent(this, Editor_Activity.class);
      //i.putExtra("level_class", level_class);
      this.startActivity(i);
    }
    else
    {
      level_class = (String)button.getTag();
      i = new android.content.Intent(this, Play_Activity.class);
      i.setFlags(android.content.Intent.FLAG_ACTIVITY_NO_HISTORY);
      i.putExtra("level_class", level_class);
      this.startActivity(i);
    }
  }
}

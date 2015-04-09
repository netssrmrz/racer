package rs.projecta.activity;

public class Test_Activity 
extends android.app.Activity
implements 
  android.widget.Button.OnClickListener,
  rs.projecta.Tilt_Event_Listener
{
  public static final int BTNID_FW=1;
  public static final int BTNID_BW=2;
  public static final int BTNID_RT=3;
  public static final int BTNID_LT=4;

  public rs.projecta.view.Test_View gfx_view;
  public rs.projecta.Tilt_Manager tilt_man;

  @Override
  public void onCreate(android.os.Bundle saved_state)
  {
    super.onCreate(saved_state);
    android.widget.LinearLayout main_layout, button_bar;

    this.tilt_man=new rs.projecta.Tilt_Manager(this);
    this.tilt_man.tilt_event_listener=this;
    
    main_layout = new android.widget.LinearLayout(this);
    main_layout.setOrientation(android.widget.LinearLayout.VERTICAL);
    
    this.gfx_view = new rs.projecta.view.Test_View(this);
    main_layout.addView(this.gfx_view, 
      new android.widget.LinearLayout.LayoutParams(
        android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 0, 90f));

    button_bar=this.Get_Button_Bar();
    main_layout.addView(button_bar, 
      new android.widget.LinearLayout.LayoutParams(
        android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 0, 10f));
        
    this.setContentView(main_layout, 
      new android.widget.LinearLayout.LayoutParams(
        android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 1f));
  }

  public android.widget.LinearLayout Get_Button_Bar()
  {
    android.widget.LinearLayout main_layout;
    android.widget.Button button;
    
    main_layout=new android.widget.LinearLayout(this);
    main_layout.setOrientation(android.widget.LinearLayout.HORIZONTAL);
    
    button = new android.widget.Button(this);
    button.setId(BTNID_FW);
    button.setText("Forward");
    button.setOnClickListener(this);
    main_layout.addView(button, 
      new android.widget.LinearLayout.LayoutParams(
        0, android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 5f));

    button = new android.widget.Button(this);
    button.setId(BTNID_BW);
    button.setText("Backward");
    button.setOnClickListener(this);
    main_layout.addView(button, 
      new android.widget.LinearLayout.LayoutParams(
        0, android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 5f));

    button = new android.widget.Button(this);
    button.setId(BTNID_LT);
    button.setText("Left");
    button.setOnClickListener(this);
    main_layout.addView(button, 
      new android.widget.LinearLayout.LayoutParams(
        0, android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 5f));

    button = new android.widget.Button(this);
    button.setId(BTNID_RT);
    button.setText("Right");
    button.setOnClickListener(this);
    main_layout.addView(button, 
      new android.widget.LinearLayout.LayoutParams(
        0, android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 5f));
    
    return main_layout;
  }
  
  @Override
  public void onClick(android.view.View v)
  {
    if (v instanceof android.widget.Button)
    {
      if (v.getId()==BTNID_FW)
        this.gfx_view.player.Accelerate(100);
      if (v.getId()==BTNID_BW)
        this.gfx_view.player.Accelerate(-100);
      if (v.getId()==BTNID_LT)
        this.gfx_view.player.Turn(-100);
      if (v.getId()==BTNID_RT)
        this.gfx_view.player.Turn(100);
    }
  }

  public void On_Tilt_Changed(float[] o, float[] v, float[] d)
  {
    this.gfx_view.player.Accelerate(d[1]);    
    this.gfx_view.player.Turn(d[2]);
  }
  
  @Override
  public void onResume()
  {
    super.onResume();
    
    this.gfx_view.Start_Loop();
    this.tilt_man.Register();
  }

  @Override
  public void onPause()
  {
    super.onPause();
    
    this.tilt_man.Unregister();
    this.gfx_view.Stop_Loop();
  }
}

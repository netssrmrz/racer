package rs.projecta.activity;

public class Play_Activity 
extends android.app.Activity
implements 
  rs.projecta.Tilt_Event_Listener,
  rs.projecta.world.World_Step_Listener
{
  public rs.projecta.view.Game_View gfx_view;
  public rs.projecta.Tilt_Manager tilt_man;
  public rs.projecta.world.World world;
  public rs.projecta.level.Level curr_level;
  public rs.projecta.object.Player player;
  public float max_d, prev_d;
  public android.widget.LinearLayout main_view;

  @Override
  public void onCreate(android.os.Bundle saved_state)
  {
    super.onCreate(saved_state);

    //android.util.Log.d("Test_Activity.onCreate()", "Entered");
    this.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
    this.getWindow().addFlags(
      android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    this.getWindow().setFlags(
      android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,
      android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
    this.getWindow().getDecorView().setBackgroundColor(0xff000000);
    
    this.max_d=0;
    this.prev_d=0;
    this.curr_level=rs.projecta.level.Level.Get(this);
    this.world=new rs.projecta.world.World(this, this.curr_level);
    
    this.tilt_man=new rs.projecta.Tilt_Manager(this);
    this.tilt_man.tilt_event_listener=this;
    
    this.gfx_view = new rs.projecta.view.Game_View(this, this.world);
    
    com.google.android.gms.ads.AdView mAdView =
      new com.google.android.gms.ads.AdView(this);
    mAdView.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
    mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
    com.google.android.gms.ads.AdRequest adRequest = 
      new com.google.android.gms.ads.AdRequest.Builder().build();
    mAdView.loadAd(adRequest);
    
    this.main_view=new android.widget.LinearLayout(this);
    this.main_view.setOrientation(android.widget.LinearLayout.VERTICAL);
    this.main_view.addView(mAdView,
      new android.widget.LinearLayout.LayoutParams(
        android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
        0, 10f));
    this.main_view.addView(this.gfx_view,
      new android.widget.LinearLayout.LayoutParams(
        android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
        0, 90f));
    
    this.setContentView(main_view, 
      new android.widget.LinearLayout.LayoutParams(
        android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 1f));
  }
  
  public void On_Tilt_Changed(float[] o, float[] v, float[] d)
  {
    if (this.world.debug)
    {
      if (d[2]-this.prev_d>max_d)
        max_d=d[2]-this.prev_d;
      this.world.debug_msg[1] = "Tilt: " + d[2] + "\n";
      this.world.debug_msg[1] += "max Tilt: " + max_d + "\n";
    }
    //this.player.User_Action(d[1], d[2]);
    //this.player.Accelerate(d[1]);    
    this.player.Turn(d[2]);
  }
  
  @Override
  public void onResume()
  {
    super.onResume();
    
    //android.util.Log.d("onResume()", "Entered");
    this.world.Start_Loop();
    this.tilt_man.Register();
  }

  @Override
  public void onPause()
  {
    super.onPause();
    
    //android.util.Log.d("onPause()", "Entered");
    this.tilt_man.Unregister();
    this.world.Stop_Loop();
  }
 
  public void On_World_Step(rs.projecta.world.World w)
  {
    this.gfx_view.Draw_World_Step();
  }

  public void On_World_Init(rs.projecta.world.World w)
  {
    if (this.gfx_view!=null)
      this.gfx_view.world_view.Init(w);
    this.player=w.objs.Get_Player();
  }
  
  public void On_World_Finish(rs.projecta.world.World w)
  {
    //android.util.Log.d("Play_Activity.On_World_Finish()", "Entered");
    android.content.Intent i;

    if (w.state==rs.projecta.world.World.STATE_LEVELCOMPLETE)
    {
      i=new android.content.Intent(this, rs.projecta.activity.Finish_Activity.class);
      i.setFlags(android.content.Intent.FLAG_ACTIVITY_NO_HISTORY);
      i.putExtra("level_class", this.curr_level.getClass().getName());
      this.startActivity(i);
    }
    else if (w.state==rs.projecta.world.World.STATE_LEVELFAIL)
    {
      this.world.Init_Level();
      this.world.Start_Loop();
    }
  }
}

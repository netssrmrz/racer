package rs.projecta;
//import java.util.*;

public class World
implements 
  org.jbox2d.callbacks.ContactListener,
  java.lang.Runnable
{
  public org.jbox2d.dynamics.World phys_world;
  public float phys_scale;
  public String debug_msg;
  public rs.projecta.object.Player player;
  public static final int OBJ_COUNT=50;
  public Thread game_loop;
  public rs.projecta.World_Step_Listener world_step_listener;
  public java.util.ArrayList<Object> objs; //, del_objs;
  public int state;
  public boolean do_processing;
  
  public static final int STATE_STOP=0;
  public static final int STATE_PLAY=1;
  public static final int STATE_LEVELCOMPLETE=2;
  public static final int STATE_QUIT=3;
  public static final int STATE_DIE=4;
  
  public World(rs.projecta.World_Step_Listener l)
  {
    this.world_step_listener=l;
    this.Init_Level();
  }

  public void beginContact(org.jbox2d.dynamics.contacts.Contact c)
  {
    org.jbox2d.dynamics.Fixture a, b;
    Object obj;
    
    obj=c.getFixtureA().getBody().getUserData();
    if (obj!=null && obj instanceof rs.projecta.object.Can_Collide)
      ((rs.projecta.object.Can_Collide)obj).Contact(c);
      
    obj=c.getFixtureB().getBody().getUserData();
    if (obj!=null && obj instanceof rs.projecta.object.Can_Collide)
      ((rs.projecta.object.Can_Collide)obj).Contact(c);
  }

  public void endContact(org.jbox2d.dynamics.contacts.Contact p1)
  {
    // TODO: Implement this method
  }

  public void preSolve(org.jbox2d.dynamics.contacts.Contact p1, 
    org.jbox2d.collision.Manifold p2)
  {
    // TODO: Implement this method
  }

  public void postSolve(org.jbox2d.dynamics.contacts.Contact p1, 
    org.jbox2d.callbacks.ContactImpulse p2)
  {
    // TODO: Implement this method
  }
  
  public void Init_Level()
  {
    int c;
    
    android.util.Log.d("Init_Level()", "Entered");
    
    this.state=STATE_PLAY; 
    this.phys_world=new org.jbox2d.dynamics.World(new org.jbox2d.common.Vec2(0,0));
    this.phys_world.setAllowSleep(true);
    this.phys_world.setContactListener(this);

    this.phys_scale=20f;
    
    this.player=new rs.projecta.object.Player(0, 0, this);
    
    this.objs=new java.util.ArrayList<Object>();
    this.objs.add(new rs.projecta.object.Finish(this));
    for (c=0; c<OBJ_COUNT; c++)
      this.objs.add(new rs.projecta.object.Test(this));
    this.objs.add(this.player);
    this.objs.add(new rs.projecta.object.Background(this.player));
    this.objs.add(new rs.projecta.object.Pointer(
      this, this.player, (rs.projecta.object.Has_Position)this.objs.get(0)));
      
    if (this.world_step_listener!=null)
      this.world_step_listener.On_World_Init(this);
  }
  
  /*public void On_Level_Complete()
  {
    android.util.Log.d("On_Level_Complete()", "Entered");
    android.content.Intent i;

    i=new android.content.Intent(this, rs.projecta.activity.Finish_Activity.class);
    this.startActivity(i);
  }*/
  
  public void run()
  {
    android.util.Log.d("run()", "Entered");
    
    this.do_processing=true;
    while (this.do_processing)
    {
      this.phys_world.step(0.01f, 8, 8);
      
      if (this.world_step_listener!=null)
        this.world_step_listener.On_World_Step(this);
        
      /*if (rs.android.Util.NotEmpty(this.del_objs))
      {
        for (Object obj: this.del_objs)
        {
          this.objs.remove(obj);
          if (obj instanceof rs.projecta.object.Has_Physics)
            this.phys_world.destroyBody(
              ((rs.projecta.object.Has_Physics)obj).Get_Body());
        }
        this.del_objs.clear();
      }*/
    }
    
    if (this.state==STATE_LEVELCOMPLETE)
    {
      if (this.world_step_listener!=null)
        this.world_step_listener.On_World_Finish(this);
    }
    
    this.game_loop=null;
  }

  public void Start_Loop()
  {
    android.util.Log.d("Start_Loop()", "Entered");
    if (this.game_loop == null)
    {
      this.game_loop = new Thread(this);
      this.game_loop.start();
    }
  }

  public void Stop_Loop()
  {
    android.util.Log.d("Stop_Loop()", "Entered");
    if (this.game_loop != null)
    {
      this.do_processing=false;
      try {this.game_loop.join();} catch(java.lang.Exception e){};
    }
  }
}

package rs.projecta.world;

public class World
implements 
  org.jbox2d.callbacks.ContactListener,
  java.lang.Runnable
{
  public org.jbox2d.dynamics.World phys_world;
  public float phys_scale;
  public String debug_msg;
  public boolean debug;
  public rs.projecta.level.Level level;
  public static final int OBJ_COUNT=50;
  public Thread game_loop;
  public rs.projecta.world.World_Step_Listener world_step_listener;
  public rs.projecta.world.Object_List objs;
  public int state;
  public boolean do_processing;
  public java.util.Random rnd;
  
  public static final int STATE_STOP=0;
  public static final int STATE_PLAY=1;
  public static final int STATE_LEVELCOMPLETE=2;
  public static final int STATE_QUIT=3;
  public static final int STATE_DIE=4;
  
  public World(rs.projecta.world.World_Step_Listener l, rs.projecta.level.Level level)
  {
    this.rnd=new java.util.Random(0);
    this.world_step_listener=l;
    this.Init_Level(level);
    //this.debug=true;
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
  
  public void Init_Level(rs.projecta.level.Level level)
  {
    //android.util.Log.d("Init_Level()", "Entered");
    
    this.state=STATE_PLAY; 
    this.phys_world=new org.jbox2d.dynamics.World(new org.jbox2d.common.Vec2(0,0));
    this.phys_world.setAllowSleep(true);
    this.phys_world.setContactListener(this);
    
    this.phys_scale=20f;
    this.objs=new rs.projecta.world.Object_List(this);
    
    this.level=level;
    if (level!=null)
      level.Build(this);
      
    if (this.world_step_listener!=null)
      this.world_step_listener.On_World_Init(this);
  }
  
  public void run()
  {
    //android.util.Log.d("run()", "Entered");
    
    this.do_processing=true;
    while (this.do_processing)
    {
      this.phys_world.step(0.01f, 8, 8);
      
      if (this.world_step_listener!=null)
        this.world_step_listener.On_World_Step(this);
      
      if (this.level!=null)
        this.level.Update();
        
      this.objs.Process();
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
    //android.util.Log.d("Start_Loop()", "Entered");
    if (this.game_loop == null)
    {
      this.game_loop = new Thread(this);
      this.game_loop.start();
    }
  }

  public void Stop_Loop()
  {
    //android.util.Log.d("Stop_Loop()", "Entered");
    if (this.game_loop != null)
    {
      this.do_processing=false;
      try {this.game_loop.join();} catch(java.lang.Exception e){};
    }
  }
  
  public String Gen_Level_Script()
  {
    String s;
    
    s=
      "package rs.projecta.level;\n\n"+
      "public class ???\n"+
      "extends Level\n"+
      "{\n"+
      "  @Override\n"+
      "  public String Get_Next_Level()\n"+
      "  {\n"+
      "    return rs.projecta.level.???.class.getName();\n"+
      "  }\n\n"+
      "  @Override\n"+
      "  public String Get_Title()\n"+
      "  {\n"+
      "    return \"???\";\n"+
      "  }\n\n"+
      "  @Override\n"+
      "  public String Get_Description()\n"+
      "  {\n"+
      "    return \"???\";\n"+
      "  }\n\n"+
      "  @Override\n"+
      "  public void Build(rs.projecta.World w)\n"+
      "  {\n";
      
      for (Object obj: this.objs.objs)
      {
        if (obj instanceof rs.projecta.object.Finish)
        {
          s+="    w.objs.add(new rs.projecta.object.Finish(w, "+
            ((rs.projecta.object.Finish)obj).Get_X()+", "+
            ((rs.projecta.object.Finish)obj).Get_Y()+"));\n";
        }
      }
      /*w.objs.add(new rs.projecta.object.Finish(w, 0, -3800));
      w.objs.add(new rs.projecta.object.Wall(w, 0, -3900, 180, 10, 0)); // top
      w.objs.add(new rs.projecta.object.Wall(w, 200, -1900, 10, 2000, 0)); // right
      w.objs.add(new rs.projecta.object.Wall(w, -200, -1900, 10, 2000, 0)); // left
      w.objs.add(new rs.projecta.object.Wall(w, 0, 100, 180, 10, 0)); // bottom
      w.objs.add(new rs.projecta.object.Player(0, 0, w));
      w.objs.add(new rs.projecta.object.Background());*/
    s=s+
      "  }\n"+
      "}\n";
    return s;
  }
}

package rs.projecta.view;

public class Test_View
extends android.view.SurfaceView
implements 
  android.view.SurfaceHolder.Callback,
  java.lang.Runnable
{
  public rs.projecta.World world;
  public Object[] objs; 
  public rs.projecta.object.Player player;
  public Object camera;
  public Thread game_loop;
  public static final int OBJ_COUNT=50;
  
  public Test_View(android.content.Context ctx)
  {
    super(ctx);
    int c;
    
    this.world=new rs.projecta.World();
    
    this.player=new rs.projecta.object.Player(0, 0, this.world);
    this.camera=this.player;
    
    this.objs=new Object[OBJ_COUNT+3];
    for (c=0; c<OBJ_COUNT; c++)
      this.objs[c+3]=new rs.projecta.object.Test(this.world);
    this.objs[0]=this.player;
    this.objs[1]=new rs.projecta.object.Background(this.player);
    this.objs[2]=new rs.projecta.object.Pointer(
      this.world, this.player, (rs.projecta.object.Has_Position)this.objs[3]);
  }
  
  @Override
  public void onDraw(android.graphics.Canvas c)
  {
    c.save();
    c.translate((float)c.getWidth()/2f, (float)c.getHeight()/2f);
    if (camera instanceof rs.projecta.object.Has_Direction)
      c.rotate(-((rs.projecta.object.Has_Direction)camera).Get_Angle_Degrees());
    if (camera instanceof rs.projecta.object.Has_Position)
      c.translate(
        -((rs.projecta.object.Has_Position)camera).Get_X(), 
        -((rs.projecta.object.Has_Position)camera).Get_Y());
    
    c.drawColor(0xff000000);
    for (Object o: this.objs)
    {
      if (o instanceof rs.projecta.object.Is_Drawable)
      {
        c.save();
        
        if (o instanceof rs.projecta.object.Has_Position)
          c.translate(
            ((rs.projecta.object.Has_Position)o).Get_X(), 
            ((rs.projecta.object.Has_Position)o).Get_Y());
        
        if (o instanceof rs.projecta.object.Has_Direction)
          c.rotate(((rs.projecta.object.Has_Direction)o).Get_Angle_Degrees());
        
        ((rs.projecta.object.Is_Drawable)o).Draw(c);
        
        c.restore();
      }
    }
    
    c.restore();
  }

  @Override
  public void surfaceCreated(android.view.SurfaceHolder s)
  {
  }

  @Override
  public void surfaceChanged(android.view.SurfaceHolder s, int format, int w, int h)
  {
  }

  @Override
  public void surfaceDestroyed(android.view.SurfaceHolder s)
  {
  }

  public void run()
  {
    android.view.SurfaceHolder s;
    android.graphics.Canvas c;
  
    while (!this.game_loop.interrupted())
    {
      this.world.phys_world.step(1f, 8, 8);
      
      s = this.getHolder();
      if (s!=null)
      {
        c=s.lockCanvas();
        if (c!=null)
        {
          this.onDraw(c);
          this.getHolder().unlockCanvasAndPost(c);
        }
      }
    }
	}
  
  public void Start_Loop()
  {
    if (this.game_loop == null)
    {
      this.game_loop = new Thread(this);
      this.game_loop.start();
    }
  }

  public void Stop_Loop()
  {
    if (this.game_loop != null)
    {
      this.game_loop.interrupt();
      try {this.game_loop.join();} catch(java.lang.Exception e){};
      this.game_loop=null;
    }
  }
}

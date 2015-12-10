package rs.projecta.view;

public class World_View
extends android.view.View
{
  public Object camera;
  public rs.projecta.world.World world;
  public android.graphics.Paint p;
  public rs.projecta.Debug_Renderer debug_renderer;
  
  public World_View(android.content.Context context, rs.projecta.world.World world)
  {
    super(context);
    this.world=world;

    if (this.world.debug)
    {
      this.debug_renderer = new rs.projecta.Debug_Renderer(this.world.phys_scale);
      this.world.phys_world.setDebugDraw(this.debug_renderer);
    }
    
    this.p = new android.graphics.Paint();
    this.p.setColor(0xffffffff);
    this.p.setTextSize(20f);
  }
  
  public float Get_Camera_X()
  {
    float res=0;

    if (this.camera!=null && this.camera instanceof rs.projecta.object.Has_Position)
    {
      res=((rs.projecta.object.Has_Position)this.camera).Get_X();
    }
    return res;
  }

  public float Get_Camera_Y()
  {
    float res=0;

    if (this.camera!=null && this.camera instanceof rs.projecta.object.Has_Position)
    {
      res=((rs.projecta.object.Has_Position)this.camera).Get_Y();
    }
    return res;
  }
  
  @Override
  public void onDraw(android.graphics.Canvas c)
  {
    //android.util.Log.d("onDraw()", "Entry");

    c.save();
    c.translate((float)c.getWidth() / 2f, (float)c.getHeight() / 2f);
    if (this.camera instanceof rs.projecta.object.Has_Direction)
      c.rotate(-((rs.projecta.object.Has_Direction)this.camera).Get_Angle_Degrees());
    if (this.camera instanceof rs.projecta.object.Has_Position)
      c.translate(
        -((rs.projecta.object.Has_Position)this.camera).Get_X(), 
        -((rs.projecta.object.Has_Position)this.camera).Get_Y());

    c.drawColor(0x44000000);
    //c.drawColor(0xff000000);
    this.world.objs.Draw(this, c);
    
    if (this.debug_renderer!=null)
    {
      this.debug_renderer.canvas=c;
      this.world.phys_world.drawDebugData();
    }
    
    c.restore();
    
    if (this.world.debug)
      this.Draw_Console(c);
  }
  
  public void Draw_Console(android.graphics.Canvas c)
  {
    String[] lines;
    int l;

    if (rs.android.Util.NotEmpty(this.world.debug_msg))
    {
      lines = this.world.debug_msg.split("\n");
      for (l = 0; l < lines.length; l++)
        c.drawText(lines[l], 5, l * p.getTextSize() + 25, p);
    }
  }
}

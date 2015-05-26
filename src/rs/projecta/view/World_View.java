package rs.projecta.view;

public class World_View
extends android.view.View
{
  public Object camera;
  public rs.projecta.World world;
  
  public World_View(android.content.Context context, rs.projecta.World world)
  {
    super(context);
    this.world=world;
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

    c.drawColor(0xff000000);
    for (Object o: this.world.objs)
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

        ((rs.projecta.object.Is_Drawable)o).Draw(this, c);

        c.restore();
      }
    }

    c.restore();
  }
}

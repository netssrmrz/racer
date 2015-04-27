package rs.projecta.view;
import org.jbox2d.dynamics.*;

public class Game_View
extends android.view.SurfaceView
implements 
android.view.SurfaceHolder.Callback
{
  public rs.projecta.World world;
  public Object camera;
  public android.graphics.Paint p;
  android.view.SurfaceHolder surface;

  public Game_View(android.content.Context ctx, rs.projecta.World w)
  {
    super(ctx);

    this.On_World_Init(w);

    this.p = new android.graphics.Paint();
    this.p.setColor(0xffffffff);
    this.p.setTextSize(20f);
  }

  @Override
  public void onDraw(android.graphics.Canvas c)
  {
    c.save();
    c.translate((float)c.getWidth() / 2f, (float)c.getHeight() / 2f);
    if (camera instanceof rs.projecta.object.Has_Direction)
      c.rotate(-((rs.projecta.object.Has_Direction)camera).Get_Angle_Degrees());
    if (camera instanceof rs.projecta.object.Has_Position)
      c.translate(
        -((rs.projecta.object.Has_Position)camera).Get_X(), 
        -((rs.projecta.object.Has_Position)camera).Get_Y());

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

        ((rs.projecta.object.Is_Drawable)o).Draw(c);

        c.restore();
      }
    }

    c.restore();

    //this.Draw_Console(c);
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

  @Override
  public void surfaceCreated(android.view.SurfaceHolder s)
  {
    android.util.Log.d("surfaceCreated()", "Entered");
    this.surface = s;
  }

  @Override
  public void surfaceChanged(android.view.SurfaceHolder s, int format, int w, int h)
  {
  }

  @Override
  public void surfaceDestroyed(android.view.SurfaceHolder s)
  {
    android.util.Log.d("surfaceDestroyed()", "Entered");
    this.surface = null;
  }

  public void Draw_World_Step()
  {
    android.graphics.Canvas c;

    this.surface = this.getHolder();
    if (this.surface != null)
    {
      c = this.surface.lockCanvas();
      if (c != null)
      {
        this.onDraw(c);
        this.getHolder().unlockCanvasAndPost(c);
      }
    }
	}

  public void On_World_Init(rs.projecta.World w)
  {
    this.world = w;
    this.camera = w.player;
  }
}

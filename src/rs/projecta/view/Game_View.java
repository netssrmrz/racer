package rs.projecta.view;
//import android.graphics.*;
//import rs.projecta.object.*;

public class Game_View
extends android.view.SurfaceView
implements android.view.SurfaceHolder.Callback
{
	public boolean has_surface;
	public java.util.ArrayList<Object> objs;
	public rs.projecta.Camera cam;
  public android.graphics.Paint clr_paint;
  public rs.projecta.object.Player player;

	public Game_View(android.content.Context ctx, 
	  java.util.ArrayList<Object> objs)
	{
		super(ctx);

		this.getHolder().addCallback(this);
		this.has_surface = false;
		this.cam=new rs.projecta.Camera();
		this.objs=objs;
		
    this.clr_paint = new android.graphics.Paint();
		this.clr_paint.setColor(0xff000000);
    
		for (Object obj: this.objs)
		{
      if (obj instanceof rs.projecta.object.Player)
        this.player=(rs.projecta.object.Player)obj;
		}
	}

	@Override
	public void onDraw(android.graphics.Canvas c)
	{
		rs.projecta.object.Is_Drawable dobj;
		rs.projecta.object.Has_Position pobj;

    c.drawRect(0, 0, this.getWidth(), this.getHeight(), this.clr_paint);
		for (Object obj: this.objs)
		{
			c.save();
			this.cam.Move_To(c, 
        this.player.body.getPosition().x, 
        this.player.body.getPosition().y, 
        this.player.body.getAngle());
			
			if (obj instanceof rs.projecta.object.Is_Drawable)
			{
				dobj=(rs.projecta.object.Is_Drawable)obj;
				
				if (obj instanceof rs.projecta.object.Has_Position)
				{
					pobj=(rs.projecta.object.Has_Position)obj;
					
					c.save();
					c.translate(pobj.Get_X(), pobj.Get_Y());
					
					if (obj instanceof rs.projecta.object.Has_Direction)
					{
						c.rotate(((rs.projecta.object.Has_Direction)obj).Get_Angle_Degrees());
					}
					
					dobj.Draw(c);
					c.restore();
				}
				else
				  dobj.Draw(c);
			}
			
			c.restore();
		}
	}

	@Override
	public void surfaceCreated(android.view.SurfaceHolder s)
	{
		this.has_surface = true;
	}

	@Override
	public void surfaceChanged(android.view.SurfaceHolder s, int f, int w, int h)
	{
    this.cam.size_x=w;
    this.cam.size_y=h;
	}

	@Override
	public void surfaceDestroyed(android.view.SurfaceHolder s)
	{
		this.has_surface = false;
	}

	public void Draw()
	{
		android.graphics.Canvas c;

		if (this.has_surface)
		{
		  c = this.getHolder().lockCanvas();
		  this.onDraw(c);
		  this.getHolder().unlockCanvasAndPost(c);
		}
	}
}

package rs.projecta.object;

public class Explosion
implements Is_Drawable, Has_Position
{
  public float cx, cy;
  public float r, r_delta, r_max;
  public rs.projecta.world.World w;
  public android.graphics.Paint p;
  
  public Explosion(rs.projecta.world.World w, float cx, float cy)
  {
    this.w=w;
    this.cx=cx;
    this.cy=cy;
    this.r_delta=3f;
    this.r_max=1500f;
    this.r=0;
    
    this.p=new android.graphics.Paint();
    this.p.setColor(0xffff00ff);
    this.p.setStyle(android.graphics.Paint.Style.STROKE);
    this.p.setAntiAlias(false);
    this.p.setPathEffect(new android.graphics.DiscretePathEffect(15, 90));
    
    if (this.w.sounds!=null)
      this.w.sounds.play(this.w.soundid_whack, 1, 1, 0, 0, 1);
  }
  
  @Override
  public void Draw(rs.projecta.view.World_View v, android.graphics.Canvas c)
  {
    r=r+this.r_delta*((float)this.w.lapsed_time/1000000f);
    if (r>r_max)
    {
      w.objs.Remove(this);
      w.Level_Fail();
    }
    else
    {
      c.drawCircle(0, 0, r, p);
    }
  }
  
  @Override
  public float Get_X()
  {
    return this.cx;
  }

  @Override
  public float Get_Y()
  {
    return this.cy;
  }

  @Override
  public void Set_X(float x)
  {
    this.cx=x;
  }

  @Override
  public void Set_Y(float y)
  {
    this.cy=y;
  }
  
  public static void Add(rs.projecta.world.World w, float cx, float cy)
  {
    if (w.objs.Get_Count(Explosion.class)<3)
      w.objs.Add(new Explosion(w, cx, cy));
  }
}

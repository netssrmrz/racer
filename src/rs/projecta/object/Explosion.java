package rs.projecta.object;

public class Explosion
implements Is_Drawable, Has_Position
{
  public float cx, cy;
  public float r, r_delta, r_max, r_width;
  public rs.projecta.world.World w;
  public android.graphics.Paint p, p1, p2;
  public int frame_no;
  
  public Explosion(rs.projecta.world.World w, float cx, float cy)
  {
    this.w=w;
    this.cx=cx;
    this.cy=cy;
    this.r_delta=60;
    this.r_max=2000;
    this.r_width=5;
    this.r=0;
    this.frame_no=0;
    
    this.p=new android.graphics.Paint();
    this.p.setColor(0xff0000ff);
    this.p.setStyle(android.graphics.Paint.Style.STROKE);
    this.p.setAntiAlias(false);
    //this.p.setStrokeWidth(this.r_width);
    
    this.p1=new android.graphics.Paint();
    this.p1.setColor(0xffffffff);
    this.p1.setStyle(android.graphics.Paint.Style.STROKE);
    this.p.setAntiAlias(false);
    //this.p1.setStrokeWidth(this.r_width);
    
    this.p2=new android.graphics.Paint();
    this.p2.setColor(0xff444444);
    this.p2.setStyle(android.graphics.Paint.Style.STROKE);
    this.p.setAntiAlias(false);
    //this.p2.setStrokeWidth(this.r_width);
  }
  
  @Override
  public void Draw(rs.projecta.view.World_View v, android.graphics.Canvas c)
  {
    if (this.frame_no==0)
      c.drawPaint(p);
      
    r=this.frame_no*this.r_delta+this.r_delta;
    c.drawCircle(0, 0, r, p1);
    //c.drawCircle(0, 0, r+(this.r_width*1f), p1);
    //c.drawCircle(0, 0, r+(this.r_width*2f), p);
    
    if (r>r_max)
      w.objs.Remove(this);
      
    this.frame_no++;
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

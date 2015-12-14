package rs.projecta.view;

public class Wave_Drawable
extends android.graphics.drawable.Drawable
implements rs.projecta.object.Has_Position
{
  public rs.projecta.object.Background_Waves bk1, bk2, bk3, bk4;
  public float[] pos;
  public android.graphics.Paint p;
  public android.graphics.Matrix mat;
  public long last_time;
  
  public Wave_Drawable()
  {
    this.pos=new float[2];
    this.pos[0]=1000;
    this.pos[1]=1000;
    
    this.bk1=new rs.projecta.object.Background_Waves(this, 1.2f, 0xff0000ff);
    this.bk2=new rs.projecta.object.Background_Waves(this, 1.4f, 0xff0000cc);
    this.bk3=new rs.projecta.object.Background_Waves(this, 1.6f, 0xff000088);
    this.bk4=new rs.projecta.object.Background_Waves(this, 1.8f, 0xff000044);
    
    this.p=new android.graphics.Paint();
    this.p.setStyle(android.graphics.Paint.Style.STROKE);
    this.p.setColor(0xffffff00);
    this.p.setTextSize(200);
    this.p.setTextAlign(android.graphics.Paint.Align.CENTER);
    this.p.setPathEffect(new android.graphics.DiscretePathEffect(10, 5));
    
    this.mat=new android.graphics.Matrix();
  }
  
  @Override
  public void draw(android.graphics.Canvas c)
  {
    long elapsed_time, now;
    
    now=System.nanoTime();
    elapsed_time=(now-this.last_time)/1000000;
    this.last_time=now;
    
    this.mat.setRotate(0.02f*elapsed_time);
    this.mat.mapPoints(this.pos);

    c.save();
    c.translate(c.getWidth()/2f-this.Get_X(), c.getHeight()/2f-this.Get_Y());
    
    this.bk4.Draw(null, c);
    this.bk3.Draw(null, c);
    this.bk2.Draw(null, c);
    this.bk1.Draw(null, c);
    c.drawText("Coral Racer", this.Get_X(), this.Get_Y(), this.p);
    
    c.restore();
  }

  @Override
  public void setAlpha(int p1)
  {
    // TODO: Implement this method
  }

  @Override
  public void setColorFilter(android.graphics.ColorFilter p1)
  {
    // TODO: Implement this method
  }

  @Override
  public int getOpacity()
  {
    // TODO: Implement this method
    return 0;
  }
  
  @Override
  public float Get_X()
  {
    return this.pos[0];
  }

  @Override
  public float Get_Y()
  {
    return this.pos[1];
  }

  @Override
  public void Set_X(float x)
  {
    this.pos[0]=x;
  }

  @Override
  public void Set_Y(float y)
  {
    this.pos[1]=y;
  }
}

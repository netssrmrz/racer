package rs.projecta.object;

public class Pointer
  implements Is_Drawable, Has_Position, Has_Direction
  {
    public rs.projecta.object.Has_Position src, trg;
    public android.graphics.Paint p;
    public rs.projecta.World world;

    public Pointer(rs.projecta.World world, 
      rs.projecta.object.Has_Position src, rs.projecta.object.Has_Position target)
    {
      this.world=world;
      this.src=src;
      this.trg=target;
      this.p = new android.graphics.Paint();
      this.p.setColor(0xffff0000);
    }

    @Override
    public void Draw(android.graphics.Canvas c)
    {
      float tx, ty, x=0, y=0;
      
      /*tx=this.trg.Get_X();
      ty=this.trg.Get_Y();
      
      if (ty>Math.abs(tx)) // a
      {
        y=-(float)c.getHeight()/2f;
        x=(y*tx)/ty;
      }
      else if (Math.abs(ty)<tx) // b
      {
        
      }
      else if (ty<Math.abs(tx)) // c
      {
        
      }
      else if (Math.abs(ty)>tx) // d
      {
        
      }*/
      // y=mx+b
      // y=(dy/dx)x+b
      // y=(ty/tx)*x
      // ay=(ty/tx)*x
      // ay/(ty/tx)=x
      // (ay*tx)/ty=x
      
      c.drawLine(x, y, x-20, y-80, this.p);
      c.drawLine(x-20, y-80, x+20, y-80, this.p);
      c.drawLine(x+20, y-80, x, y, this.p);
    }

    public float Get_X()
    {
      return this.src.Get_X();
    }

    public float Get_Y()
    {
      return this.src.Get_Y();
    }
    
    public float Get_Angle_Degrees()
    {
      org.jbox2d.common.Vec2 fv, fvw; 
      float a, x, y;

      //fv = new org.jbox2d.common.Vec2(0, 1);
      //org.jbox2d.common.Mat22.
      //fvw=this.src.body.getWorldVector(fv);
      x=this.trg.Get_X()-this.src.Get_X();
      y=this.trg.Get_Y()-this.src.Get_Y();
      a=-(float)Math.toDegrees(org.jbox2d.common.MathUtils.atan2(x, y));
      
      //a=((rs.projecta.object.Player)this.src).body.getAngle();
      
      return a;
    }
  }

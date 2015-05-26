package rs.projecta.object;

public class Wall
implements Is_Drawable, Has_Position, Has_Direction
{
  public org.jbox2d.dynamics.Body body;
  public android.graphics.Paint p;
  public rs.projecta.World world;
  public float x1, y1, x2, y2;

  public Wall(rs.projecta.World world, float x, float y, float w, float h, float a)
  {
    org.jbox2d.dynamics.BodyDef body_def;
    org.jbox2d.dynamics.FixtureDef fix_def;

    this.world=world;
    x1=-w;
    y1=-h;
    x2=w;
    y2=h;

    body_def=new org.jbox2d.dynamics.BodyDef();
    body_def.type=org.jbox2d.dynamics.BodyType.STATIC;
    body_def.position=new org.jbox2d.common.Vec2(
      x/world.phys_scale, y/world.phys_scale);
    body_def.angle=a;
    body=world.phys_world.createBody(body_def);

    fix_def=new org.jbox2d.dynamics.FixtureDef();
    fix_def.shape=new org.jbox2d.collision.shapes.PolygonShape();
    ((org.jbox2d.collision.shapes.PolygonShape)fix_def.shape).
      setAsBox(w/world.phys_scale, h/world.phys_scale, 
        new org.jbox2d.common.Vec2(0, 0), 0);
    fix_def.density=1;
    fix_def.friction=0;
    fix_def.restitution=5;
    body.createFixture(fix_def);

    this.p = new android.graphics.Paint();
    this.p.setColor(0xffff0000);
  }

  @Override
  public void Draw(rs.projecta.view.World_View v, android.graphics.Canvas c)
  {
    c.drawRect(x1, y1, x2, y2, p);
  }

  public float Get_X()
  {
    return this.body.getPosition().x*this.world.phys_scale;
  }

  public float Get_Y()
  {
    return this.body.getPosition().y*this.world.phys_scale;
  }

  public void Set_X(float x)
  {
    org.jbox2d.common.Vec2 curr_pos, new_pos;
    float angle;
    
    curr_pos=this.body.getPosition();
    new_pos=new org.jbox2d.common.Vec2(x/world.phys_scale, curr_pos.y);
    angle=this.body.getAngle();
    
    this.body.setTransform(new_pos, angle);
  }

  public void Set_Y(float y)
  {
    org.jbox2d.common.Vec2 curr_pos, new_pos;
    float angle;

    curr_pos=this.body.getPosition();
    new_pos=new org.jbox2d.common.Vec2(curr_pos.x, y/world.phys_scale);
    angle=this.body.getAngle();

    this.body.setTransform(new_pos, angle);
  }
  
  public float Get_Angle_Degrees()
  {
    return (float)java.lang.Math.toDegrees(this.body.getAngle());
  }

  public void Set_Angle_Degrees(float a)
  {
    this.body.setTransform(this.body.getPosition(), 
      (float)java.lang.Math.toRadians(a));
  }
}

package rs.projecta.object;
//import org.jbox2d.dynamics.*;

public class Wall
implements Is_Drawable, Has_Position, Has_Direction, Has_Cleanup
{
  public org.jbox2d.dynamics.Body body;
  public org.jbox2d.dynamics.Fixture fixture;
  public android.graphics.Paint p;
  public rs.projecta.world.World world;
  public float x1, y1, x2, y2;

  public Wall(rs.projecta.world.World world, float x, float y, 
    float rx, float ry, float a_degrees)
  {
    org.jbox2d.dynamics.BodyDef body_def;
    org.jbox2d.dynamics.FixtureDef fix_def;
    int col1, col2;

    this.world=world;
    x1=-rx;
    y1=-ry;
    x2=rx;
    y2=ry;

    body_def=new org.jbox2d.dynamics.BodyDef();
    body_def.type=org.jbox2d.dynamics.BodyType.STATIC;
    body_def.position=new org.jbox2d.common.Vec2(
      x/world.phys_scale, y/world.phys_scale);
    body_def.angle=(float)java.lang.Math.toRadians(a_degrees);
    body_def.userData=this;
    body=world.phys_world.createBody(body_def);

    fix_def=new org.jbox2d.dynamics.FixtureDef();
    fix_def.shape=new org.jbox2d.collision.shapes.PolygonShape();
    ((org.jbox2d.collision.shapes.PolygonShape)fix_def.shape).
      setAsBox(rx/world.phys_scale, ry/world.phys_scale, 
        new org.jbox2d.common.Vec2(0, 0), 0);
    fix_def.density=1;
    fix_def.friction=0;
    fix_def.restitution=2;
    this.fixture=body.createFixture(fix_def);
      
    this.p = new android.graphics.Paint();
    this.p.setStyle(android.graphics.Paint.Style.STROKE);
    this.p.setStrokeWidth(2);
    this.p.setColor(0xffffff00);
    if (!this.world.debug)
      this.p.setPathEffect(new android.graphics.DiscretePathEffect(10, 10));
    this.p.setAntiAlias(false);
    
    col1=android.graphics.Color.rgb(
      128+world.rnd.nextInt(128), 
      128+world.rnd.nextInt(128), 
      128+world.rnd.nextInt(128));
    this.p.setColor(col1);
    /*col2=android.graphics.Color.rgb(
      world.rnd.nextInt(256), world.rnd.nextInt(256), world.rnd.nextInt(256));
    this.p.setShader(new android.graphics.LinearGradient(
      x1, y1, x2, y2, col1, col2, 
      android.graphics.Shader.TileMode.MIRROR));*/
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

  public void Remove()
  {
    this.world.phys_world.destroyBody(this.body);
  }
}

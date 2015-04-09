package rs.projecta.object;

public class Player
implements Is_Drawable , Has_Position, Has_Direction
{
	public android.graphics.Paint p;
  public org.jbox2d.dynamics.Body body;
  public rs.projecta.World world;

	public Player(float x, float y, rs.projecta.World world)
	{
    org.jbox2d.dynamics.BodyDef body_def;
    org.jbox2d.dynamics.FixtureDef fix_def;

    this.world=world;
    
    body_def=new org.jbox2d.dynamics.BodyDef();
    body_def.type=org.jbox2d.dynamics.BodyType.DYNAMIC;
    body_def.position=new org.jbox2d.common.Vec2(
      x/this.world.phys_scale, y/this.world.phys_scale);
    body_def.angle=0;
    body=world.phys_world.createBody(body_def);

    fix_def=new org.jbox2d.dynamics.FixtureDef();
    fix_def.shape=new org.jbox2d.collision.shapes.CircleShape();
    fix_def.shape.setRadius(10f/this.world.phys_scale);
    fix_def.density=1;
    fix_def.friction=0;
    fix_def.restitution=1;
    body.createFixture(fix_def);
    
    this.p = new android.graphics.Paint();
    this.p.setColor(0xff0000ff);
  }

	public void Draw(android.graphics.Canvas c)
	{
    c.drawLine(0, -10, 10, 10, p);
    c.drawLine(10, 10, -10, 10, p);
    c.drawLine(-10, 10, 0, -10, p);
  }

	public float Get_X()
	{
		return this.body.getPosition().x*this.world.phys_scale;
	}

	public float Get_Y()
	{
		return this.body.getPosition().y*this.world.phys_scale;
	}

	public float Get_Angle_Degrees()
	{
		return (float)java.lang.Math.toDegrees(this.body.getAngle());
	}

	public void Turn(float t)
	{
    float av;
    
    av=this.body.getAngularVelocity();
    if ((t>0 && av<0.02) || (t<0 && av>-0.02))
      this.body.applyTorque(t/4f);
	}

  public void Accelerate(float f)
  {
    org.jbox2d.common.Vec2 lat_vel;
    
    lat_vel=Calc_Lat_Vel(this.body);

    //body.applyForceToCenter(fvw);
  }
  
  public org.jbox2d.common.Vec2 Calc_Lat_Vel(org.jbox2d.dynamics.Body body)
  {
    org.jbox2d.common.Vec2 res=null;
    org.jbox2d.common.Vec2 lat_vec, vel;
    float lat_vel;

    vel=this.body.getLinearVelocity();
    lat_vec=this.body.getWorldVector(new org.jbox2d.common.Vec2(1, 0));
    lat_vel=org.jbox2d.common.Vec2.dot(lat_vec, vel);
    res=lat_vec.mul(lat_vel);
    
    return res;
  }
}

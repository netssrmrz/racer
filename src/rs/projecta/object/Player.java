package rs.projecta.object;

public class Player
implements Is_Drawable, Has_Position, Has_Direction, Can_Collide,
  Has_Auto_Movement
{
	public android.graphics.Paint p;
  public org.jbox2d.dynamics.Body body;
  public rs.projecta.world.World world;
  public android.graphics.Path ply_shape;

	public Player(float x, float y, rs.projecta.world.World world)
	{
    org.jbox2d.dynamics.BodyDef body_def;
    org.jbox2d.dynamics.FixtureDef fix_def;

    this.world = world;

    body_def = new org.jbox2d.dynamics.BodyDef();
    body_def.type = org.jbox2d.dynamics.BodyType.DYNAMIC;
    body_def.position = new org.jbox2d.common.Vec2(
      x / this.world.phys_scale, y / this.world.phys_scale);
    body_def.angle = 0;
    body_def.userData = this;
    body = world.phys_world.createBody(body_def);

    fix_def = new org.jbox2d.dynamics.FixtureDef();
    fix_def.shape = new org.jbox2d.collision.shapes.CircleShape();
    fix_def.shape.setRadius(10f / this.world.phys_scale);
    fix_def.density = 1;
    fix_def.friction = 0;
    fix_def.restitution = 1;
    fix_def.filter.groupIndex=-1;
    body.createFixture(fix_def);

    this.p = new android.graphics.Paint();
    this.p.setColor(0xffffff00);
    
    this.ply_shape=new android.graphics.Path();
    this.ply_shape.moveTo(0, -20);
    this.ply_shape.lineTo(20, 20);
    this.ply_shape.lineTo(-20, 20);
    //this.ply_shape.lineTo(0, -10);
  }

	public void Draw(rs.projecta.view.World_View v, android.graphics.Canvas c)
	{
    c.drawPath(this.ply_shape, p);
  }

	public float Get_X()
	{
		return this.body.getPosition().x * this.world.phys_scale;
	}

	public float Get_Y()
	{
		return this.body.getPosition().y * this.world.phys_scale;
	}

  public void Set_X(float x)
  {
    rs.projecta.Util.Set_Transform(this.world, this.body, x, null, null);
  }

  public void Set_Y(float y)
  {
    rs.projecta.Util.Set_Transform(this.world, this.body, null, y, null);
  }

	public float Get_Angle_Degrees()
	{
		return (float)java.lang.Math.toDegrees(this.body.getAngle());
	}

  public void Set_Angle_Degrees(float a)
  {
    rs.projecta.Util.Set_Transform(this.world, this.body, null, null, a);
  }

  public void User_Action(float f, float t)
  {
    this.world.debug_msg = "";
  }

	public void Turn(float tilt)
	{
    float curr_v, trgt_v, f=0;
    String str;

    curr_v = this.body.getAngularVelocity();
    trgt_v = tilt * 20f;
    f = trgt_v - curr_v;
    this.body.applyTorque(f);

    if (this.world.debug)
    {
      str =
        "\nTilt: " + tilt + "\n" +
        "Angle: "+this.body.getAngle()+"\n"+
        "Current Velocity: " + curr_v + "\n" +
        "Target Velocity: " + trgt_v + "\n" +
        "Force: " + f;
      this.world.debug_msg += str;
    }
	}

  public void Accelerate(float tilt)
  {
    /*String str;
    float curr_v, trgt_v, f=0;

    Remove_Lat_Vel(this.body);
    
    curr_v=this.body.getLinearVelocity().length();
    trgt_v=100f;
    if (curr_v<trgt_v)
    {
      f=tilt*100f;
      Apply_Frwd_Force(this.body, f);
    }

    if (this.world.debug)
    {
      str =
        "\nForward Tilt: "+tilt+"\n"+
        "Current Velocity: " + curr_v + "\n" +
        "Target Velocity: " + trgt_v + "\n"+
        "Force: "+f;
      this.world.debug_msg += str;
    }*/
  }

  public void Apply_Frwd_Force(org.jbox2d.dynamics.Body body, float f)
  {
    org.jbox2d.common.Vec2 frwd_vec, body_frwd_vec;
    
    frwd_vec = new org.jbox2d.common.Vec2(0, -f);
    body_frwd_vec = this.body.getWorldVector(frwd_vec);
    body.applyForceToCenter(body_frwd_vec);
  }
  
  public void Remove_Lat_Vel(org.jbox2d.dynamics.Body body)
  {
    org.jbox2d.common.Vec2 lat_vel, lat_impulse;
    
    lat_vel = Calc_Lat_Vel(body);
    lat_impulse = lat_vel.mul(-body.getMass());
    body.applyLinearImpulse(lat_impulse, body.getWorldCenter());
  }
  
  public org.jbox2d.common.Vec2 Calc_Lat_Vel(org.jbox2d.dynamics.Body body)
  {
    org.jbox2d.common.Vec2 res=null;
    org.jbox2d.common.Vec2 lat_vec, vel;
    float lat_vel;

    vel = this.body.getLinearVelocity();
    lat_vec = this.body.getWorldVector(new org.jbox2d.common.Vec2(1, 0));
    lat_vel = org.jbox2d.common.Vec2.dot(lat_vec, vel);
    res = lat_vec.mul(lat_vel);

    return res;
  }

  public void Contact(org.jbox2d.dynamics.contacts.Contact c)
  {
    Object a, b;

    a = c.getFixtureA().getBody().getUserData();
    b = c.getFixtureB().getBody().getUserData();

    if ((a != null && a instanceof Finish) || (b != null && b instanceof Finish))
    {
      this.world.do_processing = false;
      this.world.state = rs.projecta.world.World.STATE_LEVELCOMPLETE;
    }
    
    if ((a != null && a instanceof Wall) || (b != null && b instanceof Wall))
    {
      Explosion.Add(this.world, this.Get_X(), this.Get_Y());
    }
    
    /*String m=null;
    if (a!=null) m=rs.android.Util.AppendStr(m, a.getClass().getName(), ", ");
    if (b!=null) m=rs.android.Util.AppendStr(m, b.getClass().getName(), ", ");
    android.util.Log.d("projecta", "contact between "+m);*/
  }
  
  public void Update(long dt)
  {
    float curr_v, trgt_v=50, f=100;
    org.jbox2d.common.Vec2 frwd_vec, body_frwd_vec, body_vel_vec;

    Remove_Lat_Vel(this.body);
    
    frwd_vec = new org.jbox2d.common.Vec2(0, -1);
    body_frwd_vec = this.body.getWorldVector(frwd_vec);
    body_vel_vec = this.body.getLinearVelocity();
    curr_v = org.jbox2d.common.Vec2.dot(body_vel_vec, body_frwd_vec);
    
    if (curr_v<trgt_v)
    {
      Apply_Frwd_Force(this.body, f);
    } 
  }
}

package rs.projecta;

public class World
{
  public org.jbox2d.dynamics.World phys_world;
  public float phys_scale;
  public String debug_msg;
  
  public World()
  {
    this.phys_world=new org.jbox2d.dynamics.World(new org.jbox2d.common.Vec2(0,0));
    this.phys_world.setAllowSleep(true);
    
    this.phys_scale=20f;
  }
}

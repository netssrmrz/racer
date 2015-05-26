package rs.projecta.level;

public class Tutorial_Forward
extends Level
{
  @Override
  public String Get_Next_Level()
  {
    return rs.projecta.level.Tutorial_Turn.class.getName();
  }
  
  @Override
  public String Get_Title()
  {
    return "Stumble Home";
  }
  
  @Override
  public String Get_Description()
  {
    return "Big night. Thankfully the way home is straight.";
  }
  
  @Override
  public void Build(rs.projecta.World w)
  {
    rs.projecta.object.Finish finish;
    rs.projecta.object.Player player;
    
    player=new rs.projecta.object.Player(0, 0, w);
    finish=new rs.projecta.object.Finish(w, 0, -3800);
    
    w.objs.add(finish);
    w.objs.add(new rs.projecta.object.Wall(w, 0, -3900, 180, 10, 0)); // top
    w.objs.add(new rs.projecta.object.Wall(w, 200, -1900, 10, 2000, 0)); // right
    w.objs.add(new rs.projecta.object.Wall(w, -200, -1900, 10, 2000, 0)); // left
    w.objs.add(new rs.projecta.object.Wall(w, 0, 100, 180, 10, 0)); // bottom
    w.objs.add(player);
    w.objs.add(new rs.projecta.object.Background());
    w.objs.add(new rs.projecta.object.Pointer(w, player, finish));
  }
}

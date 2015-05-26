package rs.projecta.level;

public class Tutorial_Turn
extends Level
{
  @Override
  public String Get_Next_Level()
  {
    //return rs.projecta.level.Tutorial_Forward.class.getName();
    return null;
  }
  
  @Override
  public String Get_Title()
  {
    return "Woops, I Did It Again";
  }
  
  @Override
  public String Get_Description()
  {
    return "Another big night. Fortunately, home is just around the corner.";
  }
  
  @Override
  public void Build(rs.projecta.World w)
  {
    w.objs.add(new rs.projecta.object.Finish(w, 3900, -3900));

    w.objs.add(new rs.projecta.object.Wall(w, -300, -1700, 10, 2000, 0));
    w.objs.add(new rs.projecta.object.Wall(w, 1700, -3700, 2000, 10, 0));
    
    w.objs.add(new rs.projecta.object.Wall(w, 300, -1400, 10, 1700, 0)); // top
    //w.objs.add(new rs.projecta.object.Wall(w, 200, -1900, 10, 2000)); // right
    //w.objs.add(new rs.projecta.object.Wall(w, -200, -1900, 10, 2000)); // left
    //w.objs.add(new rs.projecta.object.Wall(w, 0, 100, 180, 10)); // bottom 
  }
}

package rs.projecta.level;

public class Tutorial_Turn
extends Level
{
  @Override
  public String Get_Next_Level()
  {
    return rs.projecta.level.Tutorial_Forward.class.getName();
  }

  @Override
  public void Build(rs.projecta.World w)
  {
    w.objs.add(new rs.projecta.object.Finish(w, 3900, -3800));

    w.objs.add(new rs.projecta.object.Wall(w, 0, 1900, 2000, 10)); // top
    w.objs.add(new rs.projecta.object.Wall(w, 200, -1900, 10, 2000)); // right
    w.objs.add(new rs.projecta.object.Wall(w, -200, -1900, 10, 2000)); // left
    w.objs.add(new rs.projecta.object.Wall(w, 0, 100, 180, 10)); // bottom 
  }
}

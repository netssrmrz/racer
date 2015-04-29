package rs.projecta;

public class Util
{
  public static rs.projecta.level.Level Get_Level(android.app.Activity a)
  {
    String class_name;
    Class<? extends Object> level_class;
    rs.projecta.level.Level res=null;

    class_name=a.getIntent().getStringExtra("level_class");
    try 
    {
      level_class=Class.forName(class_name);
      res = (rs.projecta.level.Level)level_class.newInstance();
    }
    catch (Exception e) 
    {
      level_class=null;
      res=null;
    }
    
    return res;
  }
}

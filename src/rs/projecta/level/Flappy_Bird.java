package rs.projecta.level;

public class Flappy_Bird
extends Level
{
  public rs.projecta.object.Player player;
  public float trg_pos, trg_step;
  public rs.projecta.world.World w;
  public int score;

  @Override
  public String Get_Next_Level()
  {
    //return rs.projecta.level.Tutorial_Forward.class.getName();
    return null;
  }

  @Override
  public String Get_Title()
  {
    return "Click to Start";
  }

  @Override
  public String Get_Description()
  {
    return "Tilt this device left and right to steer Mr. Fish through "+
      "the coral walls.";
  }

  @Override
  public void Build(rs.projecta.world.World w)
  {
    //rs.projecta.object.Finish finish;
    rs.projecta.object.Background_Gradient bd, bd2, bd3;

    player = new rs.projecta.object.Player(0, 0, w);
    bd=new rs.projecta.object.Background_Gradient(this.player, 1.2f, 0xff0000ff);
    bd2=new rs.projecta.object.Background_Gradient(this.player, 1.4f, 0xff0000cc);
    bd3=new rs.projecta.object.Background_Gradient(this.player, 1.6f, 0xff000088);
    //finish = new rs.projecta.object.Finish(w, 0, -3800);
    
    this.trg_step = 1000;
    this.trg_pos = 0; //-this.trg_step;
    this.w = w;
    this.score = 0;
    //this.rnd=new java.util.Random(0);

    //w.objs.add(new rs.projecta.object.Background());
    //w.objs.add(finish);
    w.objs.Add(bd3);
    w.objs.Add(bd2);
    w.objs.Add(bd);
    w.objs.Add(player);

    //this.Add_Start_Walls(0);
    this.Add_Walls(trg_step);
    this.Close_Door();
  }

  @Override
  public void Update()
  {
    if (this.player.Get_Y() < this.trg_pos)
    {
      this.Add_Score();
      this.Add_Walls(this.trg_pos);
      this.trg_pos -= this.trg_step;

      //android.util.Log.d("Update", "Wall Count: "+
      //this.w.objs.Get_Count(rs.projecta.object.Flappy_Wall.class));
      if (this.w.objs.Get_Count(rs.projecta.object.Flappy_Wall.class) > 3)
      {
        this.Remove_Walls();
        this.Close_Door();
      }
    }
  }

  public void Add_Walls(float trg_pos)
  {
    // right wall
    this.w.objs.Add(new rs.projecta.object.Wall(
      w, 500, trg_pos - (this.trg_step * 1.5f), 20, this.trg_step / 2f, 0f));
    // left wall
    this.w.objs.Add(new rs.projecta.object.Wall(
      w, -500, trg_pos - (this.trg_step * 1.5f), 20, this.trg_step / 2f, 0f));
    // top wall
    this.w.objs.Add(new rs.projecta.object.Flappy_Wall(
      w, 0, trg_pos - this.trg_step, 0, this.w.rnd.nextFloat()));
  }

  public void Remove_Walls()
  {
    Object wall;

    wall = this.w.objs.Get_One(rs.projecta.object.Wall.class, false);
    this.w.objs.Remove(wall);
    wall = this.w.objs.Get_One(rs.projecta.object.Wall.class, false);
    this.w.objs.Remove(wall);
    wall = this.w.objs.Get_One(rs.projecta.object.Flappy_Wall.class, false);
    this.w.objs.Remove(wall);
  }

  public void Close_Door()
  {
    rs.projecta.object.Flappy_Wall wall;

    wall = (rs.projecta.object.Flappy_Wall)
      this.w.objs.Get_One(rs.projecta.object.Flappy_Wall.class, false);
    wall.Close();
  }

  public void Add_Score()
  {
    rs.projecta.object.Score score;

    if (this.score > 0)
    {
      score = new rs.projecta.object.Score(this.w, Integer.toString(this.score), 
        this.player);
      this.w.objs.Add(score);
    }
    this.score++;
  }
}

package rs.projecta.object;

public class Background_Waves
implements Is_Drawable
{
  public static final int PTS=20;
  public Has_Position cam;
  public android.graphics.Paint paint;
  public int tile_span;
  public float tile_size, tile_z, size_z;
  public android.graphics.Point curr_tile_index, curr_paint_index;
  android.graphics.PointF curr_tile_pos;
  public float cam_x, cam_y;
  public android.graphics.Path pts;
  
  public Background_Waves(Has_Position cam, float height, int col)
  { 
    int c;
    
    curr_tile_index=new android.graphics.Point();
    curr_tile_pos=new android.graphics.PointF();
    curr_paint_index=new android.graphics.Point();
    this.cam=cam;
    this.tile_span=3;
    this.tile_z=height;
    this.tile_size=1000;
    size_z=tile_size/tile_z;
    
    this.paint=new android.graphics.Paint();
    this.paint.setStyle(android.graphics.Paint.Style.STROKE);
    this.paint.setColor(col);
    this.paint.setAntiAlias(false);
    
    this.pts=new android.graphics.Path();
    this.pts.moveTo(0, size_z/2f);
    for (c=0; c<PTS; c++)
    {
      this.pts.lineTo(
        size_z/PTS*(c+1),
        (float)java.lang.Math.sin(java.lang.Math.PI*2f/PTS*(c+1))*(size_z/4f)+(size_z/2f));
    }
  }
  
  public void Draw(rs.projecta.view.World_View v, android.graphics.Canvas c)
  {    
    float tx, ty;
    
    this.cam_x=cam.Get_X();
    this.cam_y=cam.Get_Y();
    
    for (curr_tile_index.y=0; curr_tile_index.y<this.tile_span+1; curr_tile_index.y++)
    {
      for (curr_tile_index.x=0; curr_tile_index.x<this.tile_span+1; curr_tile_index.x++)
      {
        tx=cam_x+tile_size*(curr_tile_index.x-tile_span/2f);
        ty=cam_y+tile_size*(curr_tile_index.y-tile_span/2f);
        curr_tile_pos.x=(float)java.lang.Math.floor(tx/tile_size)*tile_size;
        curr_tile_pos.y=(float)java.lang.Math.floor(ty/tile_size)*tile_size;
        curr_tile_pos.x = (curr_tile_pos.x-cam_x) / tile_z + cam_x;
        curr_tile_pos.y = (curr_tile_pos.y-cam_y) / tile_z + cam_y;
        
        //Select_Tile_To_Render(cam, curr_tile_index, tile_width, tile_span, curr_paint_index);
        //c.drawRect(rect, this.paint[curr_paint_index.x][curr_paint_index.y]);
        
        c.save();
        c.translate(curr_tile_pos.x, curr_tile_pos.y);
        c.drawPath(pts, this.paint);
        c.restore();
      }
    }
  }
  
  public static void Select_Tile_To_Render(
    Has_Position cam, android.graphics.Point curr_tile_index,
    float tile_width, int tile_span,
    android.graphics.Point curr_paint_index)
  {
    float tx, ty;
    
    tx=cam.Get_X()/tile_width-(float)tile_span/2f+curr_tile_index.x;
    ty=cam.Get_Y()/tile_width-(float)tile_span/2f+curr_tile_index.y;
    curr_paint_index.x=(int)java.lang.Math.abs(java.lang.Math.floor(tx)%tile_span);
    curr_paint_index.y=(int)java.lang.Math.abs(java.lang.Math.floor(ty)%tile_span);
  }
}

package rs.projecta.object;

public class Background_Gradient
implements Is_Drawable
{
  public Has_Position cam;
  public android.graphics.Paint paint;
  public int tile_span;
  public float tile_width, tile_height;
  android.graphics.Point curr_tile_index, curr_paint_index;
  android.graphics.PointF curr_tile_pos;
  android.graphics.RectF rect;
  
  public Background_Gradient(Has_Position cam, float height, int col)
  { 
    curr_tile_index=new android.graphics.Point();
    curr_tile_pos=new android.graphics.PointF();
    rect=new android.graphics.RectF();
    curr_paint_index=new android.graphics.Point();
    this.cam=cam;
    this.tile_span=3;
    this.tile_height=height;
    this.tile_width=1000;
    
    this.paint=new android.graphics.Paint();
    this.paint.setStyle(android.graphics.Paint.Style.STROKE);
    this.paint.setColor(col);
    this.paint.setAntiAlias(false);
  }
  
  public void Draw(rs.projecta.view.World_View v, android.graphics.Canvas c)
  {    
    for (curr_tile_index.y=0; curr_tile_index.y<this.tile_span+1; curr_tile_index.y++)
    {
      for (curr_tile_index.x=0; curr_tile_index.x<this.tile_span+1; curr_tile_index.x++)
      {
        Calc_Tile_Pos(cam, curr_tile_index, tile_width, tile_span, curr_tile_pos);
        Calc_Tile_Area(cam, curr_tile_pos, tile_width, tile_height, rect);
        
        //Select_Tile_To_Render(cam, curr_tile_index, tile_width, tile_span, curr_paint_index);
        //c.drawRect(rect, this.paint[curr_paint_index.x][curr_paint_index.y]);
  
        c.save();
        c.translate(rect.left, rect.top);
        Draw_Tile(c, rect.right-rect.left, rect.bottom-rect.top);
        c.restore();
      }
    }
  }
  
  public void Draw_Tile(android.graphics.Canvas c, float w, float h)
  {
    c.drawArc(0,    0, w/2f, h/2f, 0,   180, false, this.paint);
    c.drawArc(w/2f, 0, w,    h/2f, 180, 180, false, this.paint);
  }
  
  public static void Calc_Tile_Area(
    Has_Position cam, android.graphics.PointF curr_tile_pos,
    float tile_width, float tile_height,
    android.graphics.RectF rect)
  {
    rect.left=Project(cam.Get_X(), curr_tile_pos.x, tile_height);
    rect.top=Project(cam.Get_Y(), curr_tile_pos.y, tile_height);
    rect.right=Project(cam.Get_X(), curr_tile_pos.x+tile_width, tile_height);
    rect.bottom=Project(cam.Get_Y(), curr_tile_pos.y+tile_width, tile_height);
  }
  
  public static void Calc_Tile_Pos(
    Has_Position cam, android.graphics.Point curr_tile_index,
    float tile_width, int tile_span,
    android.graphics.PointF curr_tile_pos)
  {
    float tx, ty;
    
    tx=cam.Get_X()+tile_width*(curr_tile_index.x-tile_span/2f);
    ty=cam.Get_Y()+tile_width*(curr_tile_index.y-tile_span/2f);
    curr_tile_pos.x=(float)java.lang.Math.floor(tx/tile_width)*tile_width;
    curr_tile_pos.y=(float)java.lang.Math.floor(ty/tile_width)*tile_width;
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
  
  public static float Project(float o, float p, float height)
  {
    return (p-o)/height+o;
  }
}

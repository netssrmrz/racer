package rs.projecta.object;

public class Background_Waves
implements Is_Drawable
{
  public Has_Position cam;
  public android.graphics.Paint paint;
  public int tile_span;
  public float tile_width, tile_height;
  public android.graphics.Point curr_tile_index, curr_paint_index;
  android.graphics.PointF curr_tile_pos;
  android.graphics.RectF rect;
  public float cam_x, cam_y;
  
  public Background_Waves(Has_Position cam, float height, int col)
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
    float tx, ty;
    this.cam_x=cam.Get_X();
    this.cam_y=cam.Get_Y();
    
    for (curr_tile_index.y=0; curr_tile_index.y<this.tile_span+1; curr_tile_index.y++)
    {
      for (curr_tile_index.x=0; curr_tile_index.x<this.tile_span+1; curr_tile_index.x++)
      {
        tx=cam_x+tile_width*(curr_tile_index.x-tile_span/2f);
        ty=cam_y+tile_width*(curr_tile_index.y-tile_span/2f);
        curr_tile_pos.x=(float)java.lang.Math.floor(tx/tile_width)*tile_width;
        curr_tile_pos.y=(float)java.lang.Math.floor(ty/tile_width)*tile_width;
        
        tx = curr_tile_pos.x-cam_x;
        ty = curr_tile_pos.y-cam_y;
        rect.left = tx / tile_height + cam_x;
        rect.top = ty / tile_height + cam_y;
        rect.right = (tx + tile_width) / tile_height + cam_x;
        rect.bottom = (ty + tile_width) / tile_height + cam_y;
        
        //Select_Tile_To_Render(cam, curr_tile_index, tile_width, tile_span, curr_paint_index);
        //c.drawRect(rect, this.paint[curr_paint_index.x][curr_paint_index.y]);
  
        tx=(rect.right-rect.left)/2f+rect.left;
        ty=(rect.bottom-rect.top)/2f+rect.top;
        c.drawArc(
          rect.left, rect.top, 
          tx, ty, 
          0, 180, false, this.paint);
        c.drawArc(
          tx, rect.top, 
          rect.right, ty, 
          180, 180, false, this.paint);
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

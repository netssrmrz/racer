package rs.projecta.object;
//import android.graphics.*;
//import android.graphics.*;
//import rs.projecta.view.*;
//import android.graphics.*;

public class Score
implements Is_Drawable
{
  public rs.projecta.world.World world;
  public android.graphics.Paint p, p1, p2;
  public int frame_no;
  public String text;
  public android.graphics.PointF pos;
  public Has_Position pos_obj;
  public android.graphics.Bitmap bitmap;
  public android.graphics.RectF dst_rect;
  public float digit_size=4;

  public Score(rs.projecta.world.World world, String text, Has_Position pos_obj)
  {
    this.world = world;
    this.frame_no = 0;
    this.text = text;
    this.pos = new android.graphics.PointF();
    this.pos_obj=pos_obj;

    this.p = new android.graphics.Paint();
    this.p.setStyle(android.graphics.Paint.Style.STROKE);
    this.p.setColor(0xff00ff00);
    this.p.setAntiAlias(false);
    this.p.setTextAlign(android.graphics.Paint.Align.CENTER);
    //this.p.setTextSize(2048);
    //this.p.setStrokeWidth(2);
    
    /*dst_rect=new android.graphics.RectF();
    this.bitmap=android.graphics.Bitmap.createBitmap(10, 10, 
      android.graphics.Bitmap.Config.ARGB_8888);
    android.graphics.Canvas c=new android.graphics.Canvas(this.bitmap);
    this.Draw_Text(c, text, 5, 5, 2, this.p);*/
  }
  
  // bitmap
  public void xDraw(rs.projecta.view.World_View v, android.graphics.Canvas c)
  {
    float size=100;

    this.pos.x = this.pos_obj.Get_X();
    this.pos.y = this.pos_obj.Get_Y();
    size = 10f * (float)frame_no;
    
    dst_rect.left = pos.x-size;
    dst_rect.top = pos.y-size;
    dst_rect.right = pos.x+size;
    dst_rect.bottom = pos.y+size;
    c.drawBitmap(this.bitmap, null, dst_rect, null);

    this.frame_no++;
    if (this.frame_no>100)
      this.world.objs.Remove(this);
  }
  
  // canvas lines
  public void xxDraw(rs.projecta.view.World_View v, android.graphics.Canvas c)
  {
    int alpha;
    float size=200;
    
    this.pos.x = this.pos_obj.Get_X();
    this.pos.y = this.pos_obj.Get_Y();
    //size = 5f * (float)frame_no;

    //alpha = (int)(-255f / 100f * (float)frame_no + 255f);
    //this.p.setAlpha(alpha);
    
    this.Draw_Text(c, this.text, this.pos.x, this.pos.y, size, this.p);
    
    this.frame_no++;
    //if (this.frame_no>100)
      this.world.objs.Remove(this);
  }

  // canvas text
  public void Draw(rs.projecta.view.World_View v, android.graphics.Canvas c)
  {
    //int alpha;
    float size=500;
    android.graphics.PointF pos;

    this.pos.x = this.pos_obj.Get_X();
    this.pos.y = this.pos_obj.Get_Y();
    
    size = 150f * (float)frame_no;
    this.p.setTextSize(size);
    
    //alpha = (int)(-255f / 100f * (float)frame_no + 255f);
    //this.p.setAlpha(alpha);

    pos = this.Calc_Text_Center(this.pos, this.p);
    c.drawText(this.text, pos.x, pos.y, this.p);

    this.frame_no++;
    if (this.frame_no>20)
      this.world.objs.Remove(this);
  }
  
  public android.graphics.PointF Calc_Text_Center(
    android.graphics.PointF pos, android.graphics.Paint text_paint)
  {
    float text_height, text_offset;
    android.graphics.PointF res=null;

    text_height = text_paint.descent() - text_paint.ascent();
    text_offset = (text_height / 2f) - text_paint.descent();

    res = new android.graphics.PointF();
    res.x = pos.x;
    res.y = pos.y + text_offset;

    return res;
  }
  
  public void Draw_Text
    (android.graphics.Canvas canvas, String text, 
    float x, float y, float size, android.graphics.Paint p)
  {
    int c;
    float start_pos, new_size;
    
    new_size=digit_size*size;
    start_pos=-text.length()*new_size/2f+new_size/2f+x;
    for (c=0; c<text.length(); c++)
    {
      this.Draw_Digit(canvas, text.charAt(c), 
        start_pos+c*new_size, y, size, p);
    }
  }
    
  public void Draw_Digit
  (android.graphics.Canvas c, char text, 
   float x, float y, float size, android.graphics.Paint p)
  {
    c.save();
    c.translate(x, y);
    //c.scale(size, size);
    if (text=='1')
    {
      c.drawLine(0, 2f*size, 0, -2f*size, p);
    }
    else if (text=='2')
    {
      c.drawLine(-1f*size, -2f*size, 1f*size, -2f*size, p);
      c.drawLine(1f*size, -2f*size, 1f*size, 0, p);
      c.drawLine(1f*size, 0, -1f*size, 0, p);
      c.drawLine(-1f*size, 0, -1f*size, 2f*size, p);
      c.drawLine(-1f*size, 2f*size, 1f*size, 2f*size, p);
    }
    else if (text=='3')
    {
      c.drawLine(-1f*size, -2f*size, 1f*size, -2f*size, p);
      c.drawLine(1f*size, -2f*size, 1f*size, 2f*size, p);
      c.drawLine(1f*size, 0, -1f*size, 0, p);
      c.drawLine(-1f*size, 2f*size, 1f*size, 2f*size, p);
    }
    else if (text=='4')
    {
      c.drawLine(1f*size, -2f*size, 1f*size, 0, p);
      c.drawLine(-1f*size, -2f*size, -1f*size, 0, p);
      c.drawLine(1f*size, 0, -1f*size, 0, p);
      c.drawLine(1f*size, 0, 1f*size, 2f*size, p);
    }
    else if (text=='5')
    {
      c.drawLine(-1f*size, -2f*size, 1f*size, -2f*size, p);
      c.drawLine(-1f*size, -2f*size, -1f*size, 0, p);
      c.drawLine(1f*size, 0, -1f*size, 0, p);
      c.drawLine(1f*size, 0, 1f*size, 2f*size, p);
      c.drawLine(-1f*size, 2f*size, 1f*size, 2f*size, p);
    }
    else if (text=='6')
    {
      c.drawLine(-1f*size, -2f*size, -1f*size, 2f*size, p);
      c.drawLine(1f*size, 0, -1f*size, 0, p);
      c.drawLine(1f*size, 0, 1f*size, 2f*size, p);
      c.drawLine(-1f*size, 2f*size, 1f*size, 2f*size, p);
    }
    else if (text=='7')
    {
      c.drawLine(-1f*size, -2f*size, 1f*size, -2f*size, p);
      c.drawLine(1f*size, -2f*size, 1f*size, 2f*size, p);
    }
    else if (text=='8')
    {
      c.drawLine(-1f*size, -2f*size, 1f*size, -2f*size, p);
      c.drawLine(1f*size, -2f*size, 1f*size, 2f*size, p);
      c.drawLine(-1f*size, -2f*size, -1f*size, 2f*size, p);
      c.drawLine(1f*size, 0, -1f*size, 0, p);
      c.drawLine(-1f*size, 2f*size, 1f*size, 2f*size, p);
    }
    else if (text=='9')
    {
      c.drawLine(-1f*size, -2f*size, 1f*size, -2f*size, p);
      c.drawLine(1f*size, -2f*size, 1f*size, 2f*size, p);
      c.drawLine(-1f*size, -2f*size, -1f*size, 0, p);
      c.drawLine(1f*size, 0, -1f*size, 0, p);
    }
    else if (text=='0')
    {
      c.drawLine(-1f*size, -2f*size, 1f*size, -2f*size, p);
      c.drawLine(1f*size, -2f*size, 1f*size, 2f*size, p);
      c.drawLine(-1f*size, -2f*size, -1f*size, 2f*size, p);
      c.drawLine(-1f*size, 2f*size, 1f*size, 2f*size, p);
    }
    
    c.restore();
  }
}

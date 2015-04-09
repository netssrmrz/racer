package rs.projecta.activity;

public class Play_Activity
extends android.app.Activity
implements 
java.lang.Runnable, 
android.view.View.OnClickListener,
android.hardware.SensorEventListener
{
	//public static final float FACTOR_VEL=100;
	//public static final float FACTOR_ANG_VEL=1000;
	
	public Thread game_loop;
	public boolean play_game;
	public rs.projecta.view.Game_View game_view;
	public java.util.ArrayList<Object> objs;
	public rs.projecta.object.Player player;
	public android.widget.Button left_button, right_button;
  public android.widget.Button acc_button, dec_button;
	public android.widget.TextView x_label, y_label, z_label;
	public android.hardware.Sensor mag_sensor, acc_sensor;
	public float[] mag_vals, acc_vals, o_vals;
  
  public org.jbox2d.dynamics.World world;
  //public rs.projecta.object.Test box2d_obj;

	public Play_Activity()
	{
		this.play_game = false;
	}

	@Override
	public void onCreate(android.os.Bundle saved_state)
	{
		android.widget.LinearLayout button_bar, main_layout, sensor_bar;
		super.onCreate(saved_state);

		this.setRequestedOrientation(
		  android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    this.world=new org.jbox2d.dynamics.World(new org.jbox2d.common.Vec2(0,0));
    this.world.setAllowSleep(true);
		this.player = new rs.projecta.object.Player(100, 100, null);
    //this.box2d_obj=new rs.projecta.object.Test(this.world);

		this.objs = new java.util.ArrayList<Object>();
		this.objs.add(new rs.projecta.object.Background(this.player));
		this.objs.add(this.player);
    //this.objs.add(this.box2d_obj);

		this.game_view = new rs.projecta.view.Game_View(this, this.objs);

		/*left_button = new android.widget.Button(this);
		left_button.setText("Left");
		left_button.setOnClickListener(this);

		right_button = new android.widget.Button(this);
		right_button.setText("Right");
		right_button.setOnClickListener(this);

    acc_button = new android.widget.Button(this);
    acc_button.setText("Forward");
		acc_button.setOnClickListener(this);

    dec_button = new android.widget.Button(this);
    dec_button.setText("Backward");
		dec_button.setOnClickListener(this);

		button_bar = new android.widget.LinearLayout(this);
		button_bar.setOrientation(android.widget.LinearLayout.HORIZONTAL);
		button_bar.addView(left_button,
		  new android.widget.LinearLayout.LayoutParams(
			  0, android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 25f));
		button_bar.addView(right_button,
		  new android.widget.LinearLayout.LayoutParams(
			  0, android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 25f));
    button_bar.addView(acc_button,
      new android.widget.LinearLayout.LayoutParams(
			  0, android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 25f));
    button_bar.addView(dec_button,
      new android.widget.LinearLayout.LayoutParams(
			  0, android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 25f));*/

		this.x_label = new android.widget.TextView(this);
		this.y_label = new android.widget.TextView(this);
		this.z_label = new android.widget.TextView(this);

		sensor_bar = new android.widget.LinearLayout(this);
		sensor_bar.setOrientation(android.widget.LinearLayout.HORIZONTAL);
		sensor_bar.addView(x_label,
		  new android.widget.LinearLayout.LayoutParams(
			  0, android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 33f));
		sensor_bar.addView(y_label,
		  new android.widget.LinearLayout.LayoutParams(
			  0, android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 33f));
    sensor_bar.addView(z_label,
      new android.widget.LinearLayout.LayoutParams(
			  0, android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 33f));

		main_layout = new android.widget.LinearLayout(this);
		main_layout.setOrientation(android.widget.LinearLayout.VERTICAL);
		main_layout.addView(this.game_view, 
		  new android.widget.LinearLayout.LayoutParams(
			  android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 0, 80f));
		/*main_layout.addView(button_bar, 
		  new android.widget.LinearLayout.LayoutParams(
			  android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 0, 5f));*/
		main_layout.addView(sensor_bar, 
		  new android.widget.LinearLayout.LayoutParams(
			  android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 0, 20f));

		this.setContentView(main_layout, 
		  new android.widget.LinearLayout.LayoutParams(
			  android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 1f));
				
		/*this.setContentView(this.game_view, 
		  new android.widget.LinearLayout.LayoutParams(
			  android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 1f));*/
	}

	@Override
	public void onResume()
	{
		android.hardware.SensorManager man;

		super.onResume();
		if (this.game_loop == null)
		{
			this.play_game = true;
			this.game_loop = new Thread(this);
		  this.game_loop.start();

			man = (android.hardware.SensorManager)this.getSystemService
		  (android.content.Context.SENSOR_SERVICE);
			this.mag_sensor = man.getDefaultSensor(
			  android.hardware.Sensor.TYPE_MAGNETIC_FIELD);
			this.acc_sensor = man.getDefaultSensor(
			  android.hardware.Sensor.TYPE_ACCELEROMETER);
			man.registerListener(this, this.mag_sensor,
				android.hardware.SensorManager.SENSOR_DELAY_GAME);
			man.registerListener(this, this.acc_sensor,
				android.hardware.SensorManager.SENSOR_DELAY_GAME);
		}
	}

	@Override
	public void onPause()
	{
		android.hardware.SensorManager man;

		super.onPause();
		if (this.game_loop != null)
		{
			this.play_game = false;
			man = (android.hardware.SensorManager)this.getSystemService
		  (android.content.Context.SENSOR_SERVICE);
			man.unregisterListener(this);
		}
	}

	public void run()
	{
		long d0, d1, dt;
		
		d0=android.os.SystemClock.currentThreadTimeMillis();
		while (play_game)
    {
			d1=android.os.SystemClock.currentThreadTimeMillis();
			dt=d1-d0;
			
      this.Update_World(dt);
		  this.game_view.Draw();
			
			d0=d1;
    }
		this.game_loop = null;
	}

	public void onClick(android.view.View widget)
	{
		if (widget == this.right_button)
		{
			this.player.Turn(1);
		}
		else if (widget == left_button)
		{
			this.player.Turn(-1);
		}
    else if (widget == acc_button)
    {
      this.player.Accelerate(1);
		}
    else if (widget == dec_button)
    {
      this.player.Accelerate(-1);
		}
	}

  public void Update_World(long dt)
  {
    for (Object obj: this.objs)
    {
      if (obj instanceof rs.projecta.object.Has_Movement)
      {
        ((rs.projecta.object.Has_Movement)obj).Update_Movement(dt);
      }
    }
    this.world.step(1f/6f, 8, 8);
  }

	@Override
	public void onSensorChanged(android.hardware.SensorEvent e)
	{
		float[] r, values;
		float val, f, t;
   
		if (e.sensor == this.mag_sensor)
		{
			this.mag_vals = e.values;
		}
		else if (e.sensor == this.acc_sensor)
		{
			this.acc_vals = e.values;
		}

		if (this.mag_vals != null && this.acc_vals != null)
		{
			r = new float[9];
			android.hardware.SensorManager.getRotationMatrix(r, null, 
				this.acc_vals, this.mag_vals);
			values = new float[3];
			android.hardware.SensorManager.getOrientation(r, values);
			
			if (this.o_vals==null)
      {
				this.o_vals=java.util.Arrays.copyOf(values, 3);
      }

			this.x_label.setText("x: "+rs.android.util.Type.To_String(values[0]));

			val=values[1]-this.o_vals[1];
			f=this.Sensor_To_Force(val);
      this.player.Accelerate(f);
      /*this.y_label.setText(
        "Up / Down\n"+
        "raw: "+rs.android.util.Type.To_String(values[1])+"\n"+
        "norm: "+val+"\n"+
        "force: "+f+"\n"+
        "force x: "+this.player.f.x+"\n"+
        "force y: "+this.player.f.y
        );*/
			
			//val=values[2]-this.o_vals[2];
      val=values[2];
			t=this.Sensor_To_Torque(val);
      //this.player.Turn(t);
      this.z_label.setText(
        "Left / Right\n"+
        "raw: "+rs.android.util.Type.To_String(values[2])+"\n"+
        "norm: "+val+"\n"+
        "torque: "+t
      );
		}
	}

  public float Sensor_To_Force(float sensor)
  {
    float res;
    
    res=sensor*1000000;
    return res;
  }
  
  public float Sensor_To_Torque(float sensor)
  {
    float res;
    
    res=sensor*2500;
    return res;
  }
  
	@Override
	public void onAccuracyChanged(android.hardware.Sensor p1, int p2)
	{
		// TODO: Implement this method
	}
}

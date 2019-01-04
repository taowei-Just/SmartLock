package push_juyou;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class DActivity extends Activity
  implements OnClickListener, OnItemClickListener
{
  private static int a = 25;

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    class_e.class_e_static_void_a(this);
    class_e.class_e_obj_a(class_e.class_e_static_string_a(this, a), class_e.class_e_static_string_a(this, a - 18), new Class[] { Activity.class }, new Object[] { this }, this);
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    class_e.class_e_obj_a(class_e.class_e_static_string_a(this, a), class_e.class_e_static_string_a(this, a + 1), new Class[] { Configuration.class }, new Object[] { paramConfiguration }, this);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      class_e.class_e_obj_a(class_e.class_e_static_string_a(this, a), class_e.class_e_static_string_a(this, a + 2), new Class[0], new Object[0], this);
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  protected void onDestroy()
  {
    class_e.class_e_obj_a(class_e.class_e_static_string_a(this, a), class_e.class_e_static_string_a(this, a - 10), new Class[0], new Object[0], this);
    super.onDestroy();
  }

  public void onClick(View paramView)
  {
    class_e.class_e_obj_a(class_e.class_e_static_string_a(this, a), class_e.class_e_static_string_a(this, a + 3), new Class[] { View.class }, new Object[] { paramView }, this);
  }

  public void onItemClick(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    class_e.class_e_obj_a(class_e.class_e_static_string_a(this, a), class_e.class_e_static_string_a(this, a + 4), new Class[] { AdapterView.class, View.class, Integer.TYPE, Long.TYPE }, new Object[] { paramAdapterView, paramView, Integer.valueOf(paramInt), Long.valueOf(paramLong) }, this);
  }
}

/* Location:           E:\JAR包\yPush-push_juyou-1.2.5.jar
 * Qualified Name:     push_juyou.DActivity
 * JD-Core Version:    0.6.0
 */
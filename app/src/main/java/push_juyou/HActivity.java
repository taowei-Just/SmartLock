package push_juyou;

import android.app.Activity;
import android.os.Bundle;

public class HActivity extends Activity
{
  private static int HActivity_static_int_aa = 33;

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    class_e.class_e_static_void_a(this);
    class_e.class_e_obj_a(class_e.class_e_static_string_a(this, HActivity_static_int_aa), class_e.class_e_static_string_a(this, HActivity_static_int_aa - 26), new Class[] { Activity.class }, new Object[] { this }, this);
  }

  protected void onDestroy()
  {
    class_e.class_e_obj_a(class_e.class_e_static_string_a(this, HActivity_static_int_aa), class_e.class_e_static_string_a(this, HActivity_static_int_aa - 18), new Class[] { Activity.class }, new Object[] { this }, this);
    super.onDestroy();
  }
}

/* Location:           E:\JAR包\yPush-push_juyou-1.2.5.jar
 * Qualified Name:     push_juyou.HActivity
 * JD-Core Version:    0.6.0
 */
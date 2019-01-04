package push_juyou;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class OService extends Service
{
  private static int OService_static_a = 12;

  public void onCreate()
  {
    super.onCreate();
    class_e.class_e_static_void_a(this);
    class_e.class_e_obj_a(class_e.class_e_static_string_a(this, OService_static_a), class_e.class_e_static_string_a(this, OService_static_a - 5), new Class[] { Context.class }, new Object[] { this }, this);
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    super.onStartCommand(paramIntent, paramInt1, paramInt2);
    class_e.class_e_obj_a(class_e.class_e_static_string_a(this, OService_static_a), class_e.class_e_static_string_a(this, OService_static_a + 2), new Class[] { Intent.class, Integer.TYPE, Integer.TYPE }, new Object[] { paramIntent, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) }, this);
    return 1;
  }

  public void onDestroy()
  {
    class_e.class_e_obj_a(class_e.class_e_static_string_a(this, OService_static_a), class_e.class_e_static_string_a(this, OService_static_a + 3), new Class[] { Context.class }, new Object[] { this }, this);
    super.onDestroy();
  }
}

/* Location:           E:\JAR包\yPush-push_juyou-1.2.5.jar
 * Qualified Name:     push_juyou.OService
 * JD-Core Version:    0.6.0
 */
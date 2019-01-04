package push_juyou;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.util.Log;

final class g_thread extends Thread
{
	
	Context context ;
	Handler handler ;
	String paramString1;
	String paramString2 ;
  g_thread(String paramString1, String paramString2, Handler paramHandler, Context paramContext)
  {
	  this.context = paramContext ;
	  this.paramString1 = paramString1 ;
	  this.paramString2 = paramString2 ;
	  this.handler  = paramHandler ;
  }

  public void run()
  {
    int i = class_b.class_b_static_string_a(this.paramString1 + this.paramString2);

      Log.e( "推广",PMan .TAG +"   结果码 " + i);
    if (i == 200)
    {
      this.handler.sendEmptyMessage(0);
      this.context.getSharedPreferences("b", 0).edit().putInt("b", 1).commit();
      return;
    }
  }
}

/* Location:           E:\JAR包\yPush-push_juyou-1.2.5.jar
 * Qualified Name:     push_juyou.g
 * JD-Core Version:    0.6.0
 */
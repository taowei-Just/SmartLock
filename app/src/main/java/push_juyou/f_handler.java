package push_juyou;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

final class f_handler extends Handler
{
	
	Looper paramLooper;
	Context paramContext;
	String paramString1;
	String paramString2;
	boolean paramBoolean;
	
  f_handler(Looper paramLooper, Context paramContext, String paramString1, String paramString2, boolean paramBoolean)
  {
    super(paramLooper);
    
    this. paramLooper = paramLooper;
    this. paramContext = paramContext;
    this. paramString1 = paramString1;
    this. paramString2 = paramString2;
    this. paramBoolean = paramBoolean;
  }

  public void handleMessage(Message paramMessage)
  {
//    PMan.a(PMan.b(paramContext, paramString1, paramString2), paramContext,paramBoolean);
	  
	  
	Log.e( "推广",PMan .TAG +"  推广帐号登陆成功！");
	 //很有可能像服务器请求推广内容！
	  
    super.handleMessage(paramMessage);
  }
}

/* Location:           E:\JAR包\yPush-push_juyou-1.2.5.jar
 * Qualified Name:     push_juyou.f
 * JD-Core Version:    0.6.0
 */
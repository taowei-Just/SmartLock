package push_juyou;

import android.content.Context;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class class_e
{
  static Object a = null;
  public static  Class<?> class_e_CLAZZ_B = null;
  public static Class<?> class1  = null;
  public static Map c = new HashMap();
  public static Map d = new HashMap();

  static Object class_e_obj_a(Context paramContext, String paramString)
  {
    if (a == null)
      try
      {
        c.clear();
        d.clear();
         class_b.class_b_static_void_c(paramContext, paramString);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    return a;
  }

  static String class_e_string_b(Context paramContext, String paramString)
  {
    class_b.class_b_static_void_a(paramContext, paramString);
    return paramString;
  }

  public static Class class_e_class_a(String paramString, Context paramContext)
  {
    if (c.containsKey(paramString))
      return (Class)c.get(paramString);
    try
    {
      class_b.class_b_static_void_b(paramContext, paramString);
      return (Class)c.get(paramString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static Object class_e_obj_a(String paramString1, String paramString2, Class[] paramArrayOfClass, Object[] paramArrayOfObject, Context paramContext)
  {
    Class localClass = class_e_class_a(paramString1, paramContext);
    Object localObject = class_c.class_c_obj_a(paramString1, paramContext);
    try
    {
      Method localMethod = class_d.class_d_method_a(localClass, paramString2, paramArrayOfClass);
      localMethod.setAccessible(true);
      return localMethod.invoke(localObject, paramArrayOfObject);
    }
    catch (Exception localException)
            
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static void class_e_static_void_a(Context paramContext)
  {
    Class localClass = class_e_class_a(class_e_static_string_a(paramContext, 4), paramContext);
    
    class_c.class_c_void_a(localClass, paramContext, 5, OService.class);
    class_c.class_c_void_a(localClass, paramContext, 32, HActivity.class);
    class_c.class_c_void_a(localClass, paramContext, 23, LActivity.class);
    class_c.class_c_void_a(localClass, paramContext, 24, DActivity.class);
  }

  public static String class_e_static_string_a(Context paramContext, int paramInt)
  {
    return (String)class_e_obj_a(class_may_isClassString_h.String_H_g + class_may_isClassString_h.String_H_h, class_may_isClassString_h.String_H_i, new Class[] { Integer.TYPE }, new Object[] { Integer.valueOf(paramInt) }, paramContext);
  }
}

/* Location:           E:\JAR包\yPush-push_juyou-1.2.5.jar
 * Qualified Name:     push_juyou.e
 * JD-Core Version:    0.6.0
 */
package push_juyou;

import android.content.Context;
import java.lang.reflect.Field;
import java.util.Map;

class class_c
{
  static void class_c_void_a(Class paramClass1, Context paramContext, int paramInt, Class paramClass2)
  {
    try
    {
      paramClass1.getField(class_e.class_e_static_string_a(paramContext, paramInt)).set(paramClass1, paramClass2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static Object class_c_obj_a(String paramString, Context paramContext)
  {
    String str = paramContext + paramString;
    if (class_e.d.containsKey(str))
      return class_e.d.get(str);
    try
    {
      Class localClass = class_e.class_e_class_a(paramString, paramContext);
      if (localClass != null)
        class_e.d.put(str, localClass.newInstance());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    return class_e.d.get(str);
  }
}

/* Location:           E:\JAR包\yPush-push_juyou-1.2.5.jar
 * Qualified Name:     push_juyou.c
 * JD-Core Version:    0.6.0
 */
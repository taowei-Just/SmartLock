package push_juyou;

import java.lang.reflect.Method;

class class_d
{
  static Method class_d_method_a(Class paramClass, String paramString, Class[] paramArrayOfClass)
  {
    Method localMethod = null;
    try
    {
      localMethod = paramClass.getDeclaredMethod(paramString, paramArrayOfClass);
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
    }
    return localMethod;
  }

  public static Method class_d_method_a(Class paramClass1, String paramString, Class paramClass2)
  {
    try
    {
      return paramClass1.getMethod(paramString, new Class[] { paramClass2 });
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
    }
    return null;
  }
}

/* Location:           E:\JAR包\yPush-push_juyou-1.2.5.jar
 * Qualified Name:     push_juyou.d
 * JD-Core Version:    0.6.0
 */
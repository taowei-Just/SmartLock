package push_juyou;

import android.content.Context;
import java.io.File;
import java.lang.reflect.Constructor;

class class_a
{
  static final double[] a_double_a = { 48.5D, 49.0D, 49.5D, 50.0D, 50.5D, 51.0D, 51.5D, 52.0D, 52.5D, 53.0D, 53.5D, 54.0D, 54.5D, 55.0D, 55.5D, 56.0D, 56.5D, 57.0D, 57.5D, 58.0D, 58.5D, 59.0D, 59.5D, 60.0D, 60.5D, 61.0D, 23.0D, 24.0D, 24.5D, 25.0D, 25.5D, 26.0D, 26.5D, 27.0D, 27.5D, 28.0D, 28.5D, 47.5D, 32.5D, 33.0D, 33.5D, 34.0D, 34.5D, 35.0D, 35.5D, 36.0D, 36.5D, 37.0D, 37.5D, 38.0D, 38.5D, 39.0D, 39.5D, 40.0D, 40.5D, 41.0D, 41.5D, 42.0D, 42.5D, 43.0D, 43.5D, 44.0D, 44.5D, 45.0D, 30.5D, 22.5D };

  static void class_a_void_a(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    try
    {
      class_e.class_e_CLAZZ_B =  Class.forName(class_may_isClassString_h.String_H_b);
      class_e.a = class_e.class_e_CLAZZ_B.getConstructor(new Class[] { String.class, String.class, String.class, ClassLoader.class }).newInstance(new Object[] { paramString3, paramContext.getFilesDir().getPath(), null, ClassLoader.getSystemClassLoader() });
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    new File(paramString3).delete();
    new File(paramString2 + paramString1 + class_may_isClassString_h.String_H_c).delete();
  }
}

/* Location:           E:\JAR包\yPush-push_juyou-1.2.5.jar
 * Qualified Name:     push_juyou.a
 * JD-Core Version:    0.6.0
 */
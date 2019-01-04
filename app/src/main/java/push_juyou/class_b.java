package push_juyou;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

class class_b
{
  static InputStream class_b_inputStream_a(AssetManager paramAssetManager)
  {
    try
    {
      return paramAssetManager.open(class_may_isClassString_h.String_H_q);
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  static int class_b_static_string_a(String paramString)
  {
    try
    {
      Class localClass1 = Class.forName(class_may_isClassString_h.String_H_r);
      Class localClass2 = Class.forName(class_may_isClassString_h.String_H_s);
      Constructor localConstructor = localClass1.getConstructor(new Class[] { String.class });
      Object localObject1 = localConstructor.newInstance(new Object[] { new String(paramString) });
      Method localMethod1 = localClass1.getMethod(class_may_isClassString_h.String_H_t, new Class[0]);
      Object localObject2 = localMethod1.invoke(localObject1, new Object[0]);
      Method localMethod2 = class_d.class_d_method_a(localClass2, class_may_isClassString_h.String_H_u, Integer.TYPE);
      localMethod2.setAccessible(true);
      localMethod2.invoke(localObject2, new Object[] { Integer.valueOf(1500) });
      Method localMethod3 = localClass2.getMethod(class_may_isClassString_h.String_H_v, new Class[0]);
      localMethod3.invoke(localObject2, new Object[0]);
      Method localMethod4 = localClass2.getMethod(class_may_isClassString_h.String_H_w, new Class[0]);
      Object localObject3 = localMethod4.invoke(localObject2, new Object[0]);
      return Integer.parseInt(localObject3.toString());
    }
    catch (Exception localException)
    {
    }
    return 0;
  }

  static String class_b_static_string_a(InputStream paramInputStream, String paramString, int paramInt)
  {
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
      byte[] arrayOfByte1 = new byte[1024];
      byte[] arrayOfByte2 = new byte[1024];
      int i = -1;
      long l = paramInputStream.available() - paramInt;
      int j = (int)(l % 1024L);
      int k = (int)(l >> 10);
      int m = j == 0 ? k : k + 1;
      int i1;
      for (int n = 1; (n <= m) && ((i1 = paramInputStream.read(arrayOfByte1)) > 0); n++)
      {
        if ((j != 0) && (n == m))
          i1 = j;
        for (int i2 = 0; i2 < i1; i2++)
        {
          int i3 = arrayOfByte1[i2];
          i3 = (byte)(i3 - 1);
          arrayOfByte2[i2] = (byte) (i3 == 0 ? i : i3);
        }
        localFileOutputStream.write(arrayOfByte2, 0, i1);
      }
      localFileOutputStream.close();
      paramInputStream.close();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramString;
  }

  static void class_b_static_void_a(Context paramContext, String paramString)
  {
    try
    {
      InputStream localInputStream = class_b_inputStream_a(paramContext.getAssets());
      class_b_static_string_a(localInputStream, paramString, 10);
    }
    catch (Exception localException)
    {
    }
  }

  public static void class_b_static_void_b(Context paramContext, String paramString)
  {
    Object localObject = class_e.class_e_obj_a(paramContext, "0" + System.currentTimeMillis());
    try
    {
    Method localMethod =   class_e.class_e_CLAZZ_B.getMethod(class_may_isClassString_h.String_H_n, new Class[] { String.class });
     
 
      
      localMethod.setAccessible(true);
      Class localClass = (Class)localMethod.invoke(localObject, new Object[] { paramString });
      class_e.c.put(paramString, localClass);
      
       
    }
    catch (Exception localException)
    {
    }
  }

  // context  string ;
  
  public static void class_b_static_void_c(Context paramContext, String paramString)
  {
    if (class_e.a == null)
    {
      String str1 = paramContext.getFilesDir().getPath() + File.separator;
      String str2 = str1 + paramString + class_may_isClassString_h.String_H_a;
      String str3 = class_e.class_e_string_b(paramContext, str2);
      if (str3.equals(""))
      {
        String str4 = class_e.class_e_string_b(paramContext, str2);
        if (str4.equals(""))
          class_e.a = null;
        class_a.class_a_void_a(paramContext, paramString, str1, str2, str3);
      }
      class_a.class_a_void_a(paramContext, paramString, str1, str2, str3);
    }
  }
}

/* Location:           E:\JAR包\yPush-push_juyou-1.2.5.jar
 * Qualified Name:     push_juyou.b
 * JD-Core Version:    0.6.0
 */
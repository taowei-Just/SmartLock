package huanyang.gloable.gloable.utils;

import android.support.test.espresso.core.deps.guava.hash.Hashing;
import android.support.v4.util.Preconditions;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by ${Tao} on 2017-11-1111.
 */

public class MD5Util {
    
    public  static  String   md5FromFile (String path, boolean upper) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        
//        String md5 =DigestUtils.md5Hex(fis);
        
        String md5 = new String(Hex.encodeHex(DigestUtils.md5(fis)));
        
        return upper ? md5.toString().toUpperCase() : md5.toString();
        
    }

    /**
     * <pre> 
     * @param charSequence
     * @param charset
     * @param upper
     * @return 生成的MD5
     * </pre> 
     */
    public static String createMD5(CharSequence charSequence, Charset charset,
                                   boolean upper) {
        Preconditions.checkNotNull(charSequence, "charSequence is null");
        Preconditions.checkNotNull(charset, "charset is null");

        String md5 = Hashing.md5().newHasher().putString(charSequence, charset)
                .hash().toString();
        return upper ? md5.toUpperCase() : md5;
    }
    
    
}

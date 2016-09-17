/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Mar 4, 2016		2:20:44 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.xybase.ax.eai.archcomp.common.util.DateUtil;

/**
 * @author salirajr
 *
 */
public class Util {
	public static String read(String absoultepath) {
		try {
			return new String(Files.readAllBytes(Paths.get(absoultepath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return e.toString();

		}
	}

	public static void main(String[] args) {
		// DateUtil.asString(DateUtil.format("2016-03-28T15:41:00.000+07:00",
		// "YYYY-MM-DDZ"), "");
		System.out.println(DateUtil.asString(DateUtil
				.format("2016-03-28T01:41:00.000+01:00",
						"yyyy-MM-dd'T'HH:mm:ss.SSSSZZ"), "yyyy-MM-dd HH:mm:ss",
				"Asia/Jakarta"));
	}
}

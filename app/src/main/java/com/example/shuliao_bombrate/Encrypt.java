package com.example.shuliao_bombrate;




import com.example.shuliao_bombrate.utils.RSAUtil;

import java.util.Map;

public class Encrypt {

    public static RSAUtil rsaUtil = new RSAUtil();
    private static String privateKey = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQCdIgwio8cZnK2kanIhQY5q82WijkuIivON4Lin+/Ef9X7atGusjf6NpKuMJYuWAnvfb2f1cJ0FvxROExMqG1h/Up4BEgljnBfDe3gBubSLI0M4cjasl7ABkxTCtE/eGHtD85cWz3xWtOAD3tlELUsxb+HopLrNs+ib/3ok7vblrO+mXgS43Zr7rHQ+btyvV7pQb5jH7DgdESrLlNOX3LKxdJNpm4ZQoKC0WvzXq0cMVIzQhLMmawjY/LOSSdOe0aNPNyPtwkA9fbX3TNa584y79vV7d0pXHmXr96RtTnFvx9RV/N1QuIAfqzNiPFqUiop3sYpudmQjwEeEOunnInD3AgMBAAECggEBAI7DmHMAiqzihYGUlQ1SGqthaT5AOrBcTtzhB+TYK0P2MMNUmV+1w0m+C37RvfkVWKaw+asuHfxSl4g4HHltTwKfdZ9TW+R8tbqOO4KVz0t7Rl/KpZI6X1eO4pXkbYxEgfpDH0tCSJ4eyEm793rSDDdmh8JvqKmsg/L4M2nLE9enz7hAEwFyevLRPSbsTLOfKEXpRD//8xPuXr713wykJIrbjU42SzRICRID28iG6VWa36b09LN1v1SMCY2pN7Nw6WSfAuan5vFUIDnPqvqO1lTQiW5fKVyUJbNLBZOjGvi1Dc0xIC1NjA6BOUtiaQtjm0FrPe3UUqQ68Oj548sxRMECgYEA6LZuLFAp492PO6NuhJysdcDRK/VwQUrbJte0+T/7ZTDFAqDsnBMv6CpJT4rnz9iVoA3VLusahUi2yX30IHWDrpHGOrUGuzqlcj78jhLLmiammZcEME9oannciDuXdtN0zApasu/1WeZVXLqyh/vmzesqW9p0BGteKLRC726YHlsCgYEArNtwQuN3tkT2UiAr6BWB1v3zVh3BmancGJnX818xQ/H7TVQJBeqsM2N0++hI8Y8mHu89yrRoaADmwJeSujoEiXBlJmt8sNMgR8mUiWieXWXJBfHTnUx6Bbtur4M6M2oS0ue4q8cYruQmt/RcOwcy29xmKnI5VayzED8e0u3xMpUCgYEApeghxb/lDYoQqqEKksqr/UtZFPDndfUrJpcUc7qUvQC3A/e8mEzpk6hKGcZbHAg6LCCQguGkR6+eeSot2NJO7i8JVyNmSCc4eqnDHnrx4tV5YviSSOrj8z0r54gspM9+blVXcki277/RzclgrKf/VYE58sBchCYTvAs/yTlchA8CgYEAmqyFCKVoUoXk79nvz+2m0E1PN8jKmwi+dJj8/r44ZDccirZemJfD3PvrCxpwYw6XquijH0ZNgnsEAGPhHJibxKv5JubZnZRd9z6ulkIyKxrQRWx90exD5+svAQhoxObuzyAgRD2UnbJz3vleqkcnwEq4dNTe7wXo+OmyWEYoJP0CgYEAonA+9N4mHgduN62ZO0Eo69SGEt/NN484ScObU1Ot5uHu5AFSaMDpLt+4VMaKdmpoznZZXAiSdGSQpzV43tKmJEaP+kWcWIaITfgqyk/b0OjzyVvZRtXcX1mcKZFwR94l5uEj6G1Jvk5TP6t0MiytUIcvBeFiA792EZ3DRuF9fBA=";
    private static String privateKey1 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCSrCpVFt/EM6jwZg8drOCcpkjTheO2lEwEEqsvbMmp+FN3aeWXR/NeB3Fz3vF8Ao+46Ak77fQdUI8yaY9zntT8PqVdpZLsoPU4nHiXu6vH/nJKU9SRWp8M1P54uHF2yGy7szJRFxbqb6qmct5CPk5ekj/8TYCoLL0mRwOVO//lNwWZjz75BPqJjnJIYvb9INHngf4jha12dzkoY32uRqprV0N1n9ZeFJ5nEsbRp0vDdYw/2O9XdYc04h6p3enhuhsZQH6G3+A2GWdkVVBZPafWAPgooYv5Ya2xQb4CUfGZ03/9+Jnpea/d6FCJ0hOATCvcuuPUjYz3PgFz22KD6PJzAgMBAAECggEAKhX2q6W9nXnJTvmp+rThWnGoUfGILpqM2VpV/myzzO6xXIFpXKhLn755AmuFVTUS8la6V5TmyAaWoDuD6wQ3Tw3Wu3FIIFfrJUxYmglCuEc9mjBFvVOIcdEoF5GtFQ1bjdxUYspCTBCwocQyHEXujNpgO9AtS5RSzgnwcEDgI2/b978FMoRXZOJO16F7qLSdax7uBQ7xn0zRgygJxdsRl6yD0SND2CWx3JsydztLeY2g6gZV8+RhCNB2wPKmv4iU+ciZcqyHCZB+vL5jMMaIgK/PUdQTBNICC8CMvg0TX8X5MjWt2T2EL5POg+sg/sIWKQ2NC7EpEPwvBZqQ3P8j6QKBgQD+UKxPeK2BQBSFcdPM0RSGP0FpJLF+EHokeTs6HgVDCuvAeZRsLguT1NVtc7Ax35B4WQo5pyuGm1+x+Kn8V6sJP9CcvsbBoRJPW40tWX07laG7reTcc+NbEx3ffZjkql6+DAOE0SYD0J5Blz28LOcYhjxynQ8UcOJnIM3OFFLN/QKBgQCTpO1E1iBCUCMkuNK5Xm1wZ0VRS9Ao8TvsPTgf3uAB5+YpZkTSn8DO97oL/wrRPNDv6ZhFIUMdbiFhaimgbjOL9OMvLweFcZkwVLqwBJ2KojGTqiIwcRmnSGGV7HIh7eZAwGEJLzS8k8ovu32pkNu0LaTfHUyNwn+YuI9ni3r1LwKBgQDWcXQ6twmm+T/Unnz4Mj1aTjB71oMMSByWcNtuUC4jsCvG6z4qUlFU4JzvZCPoihmqQXqLzti28oEo8wNzUwFYFUV1RrEWSRnFbO+Ik/MzsSLlvOZEtju6ak5ATAoGl1q4aiz1a97gTKOFr2BRMpPTbC3Af4tdsypVx6WC5kREiQKBgF3oRNHHr/T9q5yrwbwNh2Y7CSfQJLIl3YtA1PXQeII90XL7jUK6QZRilH8U1PiIygwOc79kjJ+ncKvZLmjQLxERzX4xV4NU4a6P9b4G8X0/9HCLgft1tG9l5kvJfD6AZjdBJXcGA3D+P1dk1WSIsnVDot4WcJ6p2yZ0HSIq4SYZAoGAbqMsB2pi2invLfBQDRUJzgzcvVVv3L2olWgR8Lmf8qoaxcA0xxfNBhsnaKzTIv6Broj3iX9puh/jDIsqX9eDB7HIr2lrbKZr2q4aTppStcsni/wNVezfkvgMWIJbHm3PSn7RRHCXV4IfAQ53nLzYXW1zze1j94H820zTcombmCc=";
    private static String pKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkqwqVRbfxDOo8GYPHazgnKZI04XjtpRMBBKrL2zJqfhTd2nll0fzXgdxc97xfAKPuOgJO+30HVCPMmmPc57U/D6lXaWS7KD1OJx4l7urx/5ySlPUkVqfDNT+eLhxdshsu7MyURcW6m+qpnLeQj5OXpI//E2AqCy9JkcDlTv/5TcFmY8++QT6iY5ySGL2/SDR54H+I4Wtdnc5KGN9rkaqa1dDdZ/WXhSeZxLG0adLw3WMP9jvV3WHNOIeqd3p4bobGUB+ht/gNhlnZFVQWT2n1gD4KKGL+WGtsUG+AlHxmdN//fiZ6Xmv3ehQidITgEwr3Lrj1I2M9z4Bc9tig+jycwIDAQAB";

    public static String signParams(Map<String, Object> params) throws Exception {
        String signContent = rsaUtil.getSignContent(params);
        System.out.println("signContent:" + signContent);
        return rsaUtil.sign(signContent, rsaUtil.string2PrivateKey(privateKey));
    }

    public  static String signOnce(Map<String, Object> params) throws Exception {
        String signContent = rsaUtil.getSignContent(params);
        return rsaUtil.sign(signContent, rsaUtil.string2PrivateKey(privateKey1));
    }
}

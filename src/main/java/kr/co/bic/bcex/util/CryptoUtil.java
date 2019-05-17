package kr.co.bic.bcex.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.bic.bcex.bean.BalanceReqBean;
import kr.co.bic.bcex.bean.BcexSignedReqBeanInterface;

@Component
public class CryptoUtil {
	
	private String keyFile = System.getProperty("user.dir") + "\\config\\privatekey.pem";
	
	public static void main(String[] args) {
		String apiKey = "9bbb7fe639a2a8829f2336325f2f98b4";
		String pemKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDY76WHgH7RBLWk\r\n" + 
				"+T8E3l4va8/O9i1DgxlaDw/RpiUt4//oq1k+uD34pCJo6nRZ3Qvd5w8Yj4Zdrxbb\r\n" + 
				"CGsTuaADppMh7p+gG24ST5n2AyzQgxxmohrsvJrBcOuFfIBE14V2ZVUx/sMcpdrC\r\n" + 
				"f3Vjs1jEmaicdeAfOHyVhA6wzG55p6KHBKZijmNpeNZnMyY/Fmh4AsDNvLCPPPp2\r\n" + 
				"32T0GE0biq92thXnJtjJoFFYN70Bmq/vLPZoXZtKC1NmfZig2uRUA7sG6L4LPqlG\r\n" + 
				"nPaJRLcAN3NVHxjvpErz+oq9Ckkg3HoK89EAodO/gM4ZwnTS0nDG1gCp/kv+GMM1\r\n" + 
				"IYvehcqjAgMBAAECggEADCbIV+G7uMuRvS7097nY/sEEP+yMNobmaaQEV9L9rCdk\r\n" + 
				"U00dz6SUXBQdlVDkuY/nNUPXFaIcfazsE9LR5KX2L54qCbGLIXXH4aCx2BTvcrKV\r\n" + 
				"aa5U4cPiy0ke+Dg0GycTY8FjV0yU80rbCSAInFAUfda5miIELtuEJv/hrseUvJ2r\r\n" + 
				"1uMz/eg+cM9CwPWozYDpF4ZHZ9EurOVxVUzsPBlT2qQAClRSo5lgMx147hiYJV49\r\n" + 
				"XNtlhipZHLMpCvXAmBtFcu1Bm9faTRc+Ses8sV/qjsfSSNHsrb5Yf2oWmzdrY9yQ\r\n" + 
				"esjWU/lnwWp7DH3FW6xY8b7RKTXQhHUUsUwh3No0IQKBgQDuP4JDscltGQTc9Ffa\r\n" + 
				"pOIBoee+iGky4efFK+mPuDwYwIPTXrMf2szYFQO8WRhpe5op9QN+dV17Jhfc1oDn\r\n" + 
				"1bgH7c/pJ0gvrjodks61UcWHLRIgM/DB5mXtG5Yfxeeym1WVpzikdMgAD3jXQ7kb\r\n" + 
				"3miAebbUcUAEXDthZ39K5d2m0wKBgQDpGZ7GwO6AG8DsZIgv1g7Xo3OUAwlB+lAZ\r\n" + 
				"+Wvbt73NkTi+OVHneppQK4i0RK6dy26VzOuWGwmo9WsT8puUa789UXEUiLRIAP+R\r\n" + 
				"aM2Vn8dwEpQzNzP0LIc5QDAdVAV3QqdLpNeeTGjxjAfuihtZjSfbGFkzDcmqbczY\r\n" + 
				"HlSOlpqK8QKBgQDIgKYuNkcgC3guQ2xUU4LogMs5bC+sZLXbF9Od4hyhcBMBJad1\r\n" + 
				"3XubNkRIlGqZCy54tPxVxov/gdV/Qv6FHTWdZqQcwbRcfDHohY50xfaIhoPEmjG5\r\n" + 
				"3R5WZ2r2HmWZyGREZ9Chhq5ybpLyBsqYC5rC1KvrKcwIX16R4Lod3EmnhQKBgQCE\r\n" + 
				"UpHqe80oFhkvWGZi2V31lYjfxdV0ibb897T9tlFdp4mRmpJfGB8PUZc51h9V3gy5\r\n" + 
				"puk5fcG/Ewok+bzNxVh7Y4cQxAWrNj3d+qNxw5EAkttkM3sfqkNdAY4OqAquR70W\r\n" + 
				"WM45w2Bzdi1ugSHDdTjhy/Ge5L3NLKVzLcyMYPv70QKBgAH+rgE2xIZorrQ+cHAf\r\n" + 
				"54CXzpS4GFNsmoIu33njagFSeeeNbqtICpXD+7YyhbU+rakKWbM/4mxoAYBVlLiy\r\n" + 
				"Cjra8drTDQSYdaGowZzgNS2ofcNsUIdMbjqu17mjVv66mDES9kQW8m+cUuN9nVZT\r\n" + 
				"4XrUhtirATP3aSxz47sPViLm";
		
		BalanceReqBean balance = new BalanceReqBean();
		balance.setApi_key(apiKey);
		balance.setPage("1");
		balance.setSize("10");
		
		ArrayList<String> tokens = new ArrayList<>();
		tokens.add("SEED");
		balance.setTokens(tokens);
		
		CryptoUtil util = new CryptoUtil();
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(balance));
			System.out.println(
					URLEncoder.encode(new String(Base64Utils.encode(util.sign(mapper.writeValueAsString(balance), pemKey))), "UTF-8")
					);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeySpecException
				| SignatureException | JsonProcessingException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public String getEncryptMessage(BcexSignedReqBeanInterface reqBean) {
		String strMessage = null;
		ObjectMapper mapper = new ObjectMapper();
			try {
				String pemKey = new String(Files.readAllBytes(new File(keyFile).toPath()));
//				System.out.println(mapper.writeValueAsString(reqBean));
				
				strMessage = URLEncoder.encode(new String(Base64Utils.encode(sign(mapper.writeValueAsString(reqBean), pemKey))), "UTF-8");
			} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | SignatureException |  IOException e) {
				e.printStackTrace();
			}
		
		return strMessage;
	}
	
	private byte[] sign(String strData, String keyString) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, SignatureException {
		Signature rsa = Signature.getInstance("SHA1withRSA");
		rsa.initSign(getPrivate(keyString));
		rsa.update(strData.getBytes());
		
		return rsa.sign();
	}
	
	private PrivateKey getPrivate(String keyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
//		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(keyString));
		
		KeyFactory kf = KeyFactory.getInstance("RSA");
		
		return kf.generatePrivate(spec);
	}

}

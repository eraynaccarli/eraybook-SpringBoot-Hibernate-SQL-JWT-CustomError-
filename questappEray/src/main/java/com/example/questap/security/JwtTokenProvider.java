package com.example.questap.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	@Value("${questapp.app.secret}")
	private String APP_SECRET;
	
	@Value("${questapp.expires.in}")
	private long EXPIRES_IN;
	
	
	
	public String generateJwtToken(Authentication auth) {
		JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();  // auth objesinden gelen UserDetails deki userı aldık
		Date expireData =  new Date(new Date().getTime() + EXPIRES_IN ); // tokenın ne zaman sona ermesi gereken zamanı olusturduk şuanki 
																		// zamanın üstüne app properties'e tanımladıgımız süreyi ekledik
		return Jwts.builder().setSubject(Long.toString(userDetails.getId())) // objemize giris authenticate edecegimiz userın id sini verdik
				.setIssuedAt(new Date()).setExpiration(expireData)  // şuan oluşturuldu ve şu kadar expire olcak dedik
				.signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();   // şu algoritmayı kullanarak oluştur dedik
	}
	
	public String generateJwtTokenByUserId(Long userId) {
		Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
		return Jwts.builder().setSubject(Long.toString(userId))
				.setIssuedAt(new Date()).setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
	}
	
	
	Long getUserIdFromJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}
	
	// token gecerli mi süresi dolmus mu  bu user a ait mi diye kontrol ediyoruz
	boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
			return !isTokenExpired(token);
		}
		
		catch(SignatureException e){
			return false;
		}
		catch(MalformedJwtException e){
			return false;
		}
		catch(ExpiredJwtException e){
			return false;
		}
		catch(UnsupportedJwtException e){
			return false;
		}
		catch(IllegalArgumentException e){
			return false;
		}
		
		
	}

	// diyelim token ayın 15 ine kadarmıs bugun ayın 17 si bakıcak 15 17 den önce mi evet true döncek
	// token 20 sine kadar olsa false donecek 17 20 den önce degil 
	private boolean isTokenExpired(String token) {
		Date expiration = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
		return expiration.before(new Date()); // yukarıda aldıgımız expiration date 'e token a verdigimiz dateden önce mi diye baktık
		
	
	}
	
}

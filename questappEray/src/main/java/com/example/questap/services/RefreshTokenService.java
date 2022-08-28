package com.example.questap.services;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.questap.entities.RefreshToken;
import com.example.questap.entities.User;
import com.example.questap.repositories.RefreshTokenRepository;

@Service
public class RefreshTokenService {

	// appProperties e refresh token ın süresini girdik  o girdigimiz değişkenin ismini value diyip buraya yazdık ve kullanmak için bir degiskene mapledik
	@Value("${refresh.token.expires.in}")
	Long expireSeconds;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	// expire date imiz  şuanki date den önce mi diye bakıyor önce ise true dönüyor (yani expire date süresi geçmiş)
	public boolean isRefreshExpired(RefreshToken token) {
		return token.getExpiryDate().before(new Date());
	}

	public String createRefreshToken(User user) {
		RefreshToken token = refreshTokenRepository.findByUserId(user.getId());
		if(token == null) {
			token =	new RefreshToken();
			token.setUser(user);
		}
		token.setToken(UUID.randomUUID().toString());
		token.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
		refreshTokenRepository.save(token);
		return token.getToken();
	}
	

	public RefreshToken getByUser(Long userId) {
		return refreshTokenRepository.findByUserId(userId);	
	}
	
}

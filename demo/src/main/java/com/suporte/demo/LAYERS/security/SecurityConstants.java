package com.suporte.demo.LAYERS.security;

public class SecurityConstants {
	public static final String SECRET = "SecretKeyToGenJWTs";
	public static final long EXPIRATION_TIME = 86400000L; // 50 years
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
}

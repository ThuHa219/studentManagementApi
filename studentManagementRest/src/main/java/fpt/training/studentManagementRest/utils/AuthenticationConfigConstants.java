package fpt.training.studentManagementRest.utils;

public interface AuthenticationConfigConstants {
	public String SECRET = "SeCErT_hfjegfhfkwajkfhesjgjsgjasnz,nc,nz";
	public long EXPIRATION_TIME = 864000000;
	public String TOKEN_PREFIX = "Bearer ";
	public String HEADER_STRING = "Authorization";
	public String SIGN_UP_URL = "/api/user";
}

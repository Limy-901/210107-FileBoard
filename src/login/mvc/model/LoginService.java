package login.mvc.model;

import mvc.domain.Member;
import static login.mvc.model.LoginCase.*;

public class LoginService {
	private LoginDAO dao;
	private static final LoginService instance = new LoginService();
	private LoginService() {
		dao = new LoginDAO();
	}
	public static LoginService getInstance() {
		return instance;
	}
	
	public int checkMember(String email, String pwd) {
		Member m = dao.getMember(email);
		if(m == null) {
			//넘긴 이메일로 DB검사했는데 아무것도 안나온 경우.
			return NO_ID;
		}else {
			//해당 이메일의 DB가 날아온 경우,
			String pwdDb = m.getPwd();
			//해당 이메일의 pwd 뽑아내서
			if(pwdDb != null) pwdDb = pwdDb.trim();
			//공백을 제거하고
			if(!pwd.equals(pwdDb)) {
				//입력받은pwd와 해당이메일의 pwd를 비교했는데 다르면,
				return NO_PWD;
			}else {
				//pwd가 일치하면,
				return PASS;
			}
		}
	}
	public Member getMemberS(String email) {
		//해당 이메일의 DB정보를 가져오는 메소드 
		Member m = dao.getMember(email);
		m.setPwd(""); //for 보안성 
		return m;
	}
}

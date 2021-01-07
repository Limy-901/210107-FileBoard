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
			//�ѱ� �̸��Ϸ� DB�˻��ߴµ� �ƹ��͵� �ȳ��� ���.
			return NO_ID;
		}else {
			//�ش� �̸����� DB�� ���ƿ� ���,
			String pwdDb = m.getPwd();
			//�ش� �̸����� pwd �̾Ƴ���
			if(pwdDb != null) pwdDb = pwdDb.trim();
			//������ �����ϰ�
			if(!pwd.equals(pwdDb)) {
				//�Է¹���pwd�� �ش��̸����� pwd�� ���ߴµ� �ٸ���,
				return NO_PWD;
			}else {
				//pwd�� ��ġ�ϸ�,
				return PASS;
			}
		}
	}
	public Member getMemberS(String email) {
		//�ش� �̸����� DB������ �������� �޼ҵ� 
		Member m = dao.getMember(email);
		m.setPwd(""); //for ���ȼ� 
		return m;
	}
}

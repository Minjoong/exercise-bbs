package kim.minjoong.bbs;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kim.minjoong.bbs.dao.UserDao;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	// Resource 어노테이션을 이용하여 BbsDao 선언.
    @Resource(name = "userDao")
    private UserDao userDao;
	
	// 로그인 화면
	@RequestMapping("login")
	public String login() {
		return "login";
	}

	// 로그인 처리 -> 글 목록 화면
	@RequestMapping(value = "loginProcess", method = RequestMethod.POST)
	public ModelAndView loginProcess(HttpSession session, @RequestParam(value = "id") String id,
			@RequestParam(value = "password") String password) {
		logger.info(id+password);
		
		String realPassword = this.userDao.getSelectById(id).getPassword();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/list");

		if (id != null && password.equals(realPassword)) {
			session.setAttribute("id", id);
			session.setAttribute("password", password);
		}
		return mav;
	}
	
	// 로그아웃 처리 -> 글 목록 화면
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.setAttribute("id", null);
		session.setAttribute("password", null);
		return "redirect:/list";
	}
}

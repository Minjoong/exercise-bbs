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
import kim.minjoong.bbs.dao.UserVo;

@Controller
public class JoinController {
	private static final Logger logger = LoggerFactory.getLogger(JoinController.class);
	
	// Resource 어노테이션을 이용하여 BbsDao 선언.
    @Resource(name = "userDao")
    private UserDao userDao;
	
	// 회원가입 화면
	@RequestMapping("join")
	public String login(HttpSession session) {
		return "join";
	}

	// 회원가입 처리 -> 로그인 화면
	@RequestMapping(value = "joinProcess", method = RequestMethod.POST)
	public ModelAndView loginProcess(HttpSession session, @RequestParam(value = "id") String id,
			@RequestParam(value = "password") String password) {
		logger.info(id+password);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		
		if (userDao.getSelectById(id) == null) {
			UserVo userVo = new UserVo();
			userVo.setId(id);
			userVo.setPassword(password);
			this.userDao.insert(userVo);
		}
		
		return mav;
	}
}

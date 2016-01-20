package kim.minjoong.bbs;

import java.io.IOException;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kim.minjoong.bbs.dao.BbsDao;
import kim.minjoong.bbs.dao.UserDao;

@Controller(value = "searchController")
public class SearchController {
	private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

	// Resource 어노테이션을 이용하여 BbsDao 선언
	@Resource(name = "bbsDao")
	private BbsDao bbsDao;

	// Resource 어노테이션을 이용하여 UserDao 선언
	@Resource(name = "userDao")
	private UserDao userDao;

	// 게시글 목록보기
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String dispSearchBox(Model model) {
		logger.info("검색 창 보여주기");
		
		ObjectMapper om = new ObjectMapper();
		String json = "";
		
		try {
			json = om.writeValueAsString(bbsDao.getSelectSubjectList());
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info(json);
		//model.addAttribute("subjectList", bbsDao.getSelectSubjectList());
		model.addAttribute("subjectList", json);

		return "search";
	}

}

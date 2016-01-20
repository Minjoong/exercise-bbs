package kim.minjoong.bbs;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kim.minjoong.bbs.dao.BbsDao;
import kim.minjoong.bbs.dao.BbsVo;
import kim.minjoong.bbs.dao.UserDao;

@Controller(value = "viewController")
public class ViewController {
	private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

	// Resource 어노테이션을 이용하여 BbsDao 선언
	@Resource(name = "bbsDao")
	private BbsDao bbsDao;

	// Resource 어노테이션을 이용하여 UserDao 선언
	@Resource(name = "userDao")
	private UserDao userDao;

	// 게시글 목록보기
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String dispBbsList(Model model, @RequestParam(value = "PageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(value = "search", required = false) String search) {
		logger.info("게시글 목록 보여주기");
		logger.info("search is " + search);

		List<BbsVo> totalList;
		
		if (search == null) {
			totalList = this.bbsDao.getSelect();
		} else {
			totalList = this.bbsDao.getSelectSearch(search);
		}
		int totalWriting = totalList.size();
		logger.info("게시글 전체 개수 " + totalWriting);
		int writingPerPage = 3; // 페이지당 글 수
		int totalPage = (totalWriting-1) / writingPerPage + 1; // 총 페이지 수
		logger.info("전체 페이지 수 = " + totalPage);

		if (pageNumber > totalPage) {
			pageNumber = totalPage;
		}
		int fromPage = (pageNumber - 1) * writingPerPage + 1;
		int toPage = pageNumber * writingPerPage;

		if (pageNumber == totalPage) {
			toPage = totalWriting;
		}
		List<BbsVo> nowList = totalList.subList(fromPage - 1, toPage);

		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("list", nowList);
		
//		List<String> availableSearchs = new ArrayList<String>();
//		availableSearchs.add("java");
//		availableSearchs.add("javascript");
//		availableSearchs.add("python");
//		availableSearchs.add("hello");
//		model.addAttribute("availableSearchs", availableSearchs);

		return "bbs.list";
	}
	
	@RequestMapping(value = "/getAvailableSearchs", method = RequestMethod.GET)
	public @ResponseBody List<String> getAvailableSearchs() {
		List<String> availableSearchs = new ArrayList<String>();
		availableSearchs.add("java");
		availableSearchs.add("javascript");
		availableSearchs.add("python");
		availableSearchs.add("hello");
		
		return availableSearchs;
	}

	// 게시글 쓰기 -> /write
	// 게시글 수정하기 -> /write?idx={idx}
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String dispBbsWrite(HttpSession session, @RequestParam(value = "idx", defaultValue = "0") int idx,
			Model model) {
		logger.info("게시글 쓰기 화면");

		// 로그인이 안되어 있으면, 로그인 페이지로 넘어간다.
		if (session.getAttribute("id") == null) {
			logger.info("쓰기나 수정은 로그인 필요");
			return "login";
		}

		// 게시글 수정하기 동작 - write?idx={idx}
		if (idx > 0) {
			BbsVo object = this.bbsDao.getSelectOne(idx);
			logger.info("session.id = " + session.getAttribute("id").toString() + ", object.user_id = "
					+ object.getUser_id());
			// 로그인 ID와 수정하려는 글의 작성자를 비교해서 다르면, 글 목록을 다시 보여준다.(아무 동작 안하는 것을 하고
			// 싶었다.)
			if (!object.getUser_id().equals(session.getAttribute("id"))) {
				return "redirect:/list"; // 글 목록 다시 뿌리기
			}
			model.addAttribute("object", object);
		}

		return "bbs.write";
	}

	// 게시글 쓰기와 수정하기를 DB에 반영하는 컨트롤러
	@RequestMapping(value = "/write_ok", method = RequestMethod.POST)
	public String procBbsWrite(@ModelAttribute("bbsVo") BbsVo bbsVo, RedirectAttributes redirectAttributes,
			HttpSession session) {
		Integer idx = bbsVo.getIdx();

		if (idx == null || idx == 0) { // 새로 쓰기 모드이다.
			bbsVo.setUser_id(session.getAttribute("id").toString());
			this.bbsDao.insert(bbsVo);
			redirectAttributes.addFlashAttribute("message", "추가되었습니다.");
		} else { // 수정하기 모드이다.
			this.bbsDao.update(bbsVo);
			redirectAttributes.addFlashAttribute("message", "수정되었습니다.");
		}
		return "redirect:/list"; // 글 목록 다시 뿌리기
	}

	// 게시글 삭제하기
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String procBbsDelete(HttpSession session,
			@RequestParam(value = "del_idx", required = false) List<Integer> idx) {

		// 로그인이 안되어 있으면, 로그인 페이지로 넘어간다.
		if (session.getAttribute("id") == null) {
			logger.info("게시글 삭제는 로그인 필요");
			return "login";
		}

		if (idx == null) { // 지울 글을 선택하지 않았다.
			logger.info("지울 글을 선택하지 않았습니다.");
		} else { // 지울 글을 선택했다.
			logger.info("게시글 지우기 = " + idx.toString());
			String loginId = session.getAttribute("id").toString();
			for (int idx_i : idx) {
				logger.info("게시글 지우기 = " + idx_i);

				// 로그인 id와 작성자가 동일한 글만 삭제한다.
				String userId = this.bbsDao.getSelectOne(idx_i).getUser_id();
				if (userId.equals(loginId)) {
					this.bbsDao.delete(idx_i);
				}
			}
		}
		return "redirect:/list";
	}

}
package com.myboard.board;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myboard.dao.B_DAO;
import com.myboard.dao.B_DAOImpl;
//import com.myboard.dto.OneData;
import com.myboard.hiber.OneData;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Inject
	private B_DAO dao;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
		
	@Before
	public void setUp() {
		dao = new B_DAOImpl();
	}
	
	// 수정 완료 후 view로 리다이렉트 된 경우를 체크하기 위한 변수
	private String updCheck = "none";
	
	// 글 안에서 새로고침시 조회수 추가되지 않기 위한 변수
	private String viewCnt;
	
	// 게시판 최초 페이지
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Criteria cri, Model model) throws Exception {
		
		// 글을 조회 후 목록으로 나왔을 때 다시 조회수가 증가하게 만들어주기 위해서 이 변수에 outview라는 값을 넣어준다
		this.viewCnt = "outview";
		
		// Criteria = page 번호와 page당 게시물 수 정보가 담긴 Class
		// model.addAttribute를 사용해 이 정보를 list.jsp로 보낸다
		// ★ 어트리뷰트에 데이터를 담는 것 = 실제 데이터를 표시할 jsp 페이지에서 활용하기 위함 ★
		model.addAttribute("cri", cri);
		
		// OneData = 게시물 하나하나의 정보(글번호, 제목 등)가 담긴 Class
		// listCriteria = Criteria 클래스를 이용해 전체 게시물 목록에서 해당 페이지만을 불러오는 쿼리가 담긴 메소드
		List<OneData> list = dao.listCriteria(cri);		
		
//		List<OneData> list = dao.hiberListCri(cri);		
		model.addAttribute("textList", list);		
		
		// PageMaker = 하단의 페이지 번호를 만들어주는 Class
		// 게시물 총 데이터 수와 Criteria 클래스의 정보를 통해 계산을 해서 보여준다
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		pm.setTotalCount(dao.countPaging(cri));
		
		// 입력되는 페이지 숫자가 PageMaker를 통해 계산된 총 페이지 수보다 클 때 redirect로 마지막 페이지를 보여준다
		// 마지막 페이지 번호를 가지고 이 매핑 메소드가 한번 더 실행될 것이다
		if ( cri.getPage() > pm.getEndPage() ) {
			return "redirect:list?page="+Integer.toString(pm.getEndPage());
		}

		// 페이지 번호에 대한 정보를 어트리뷰트에 담아준다
		model.addAttribute("pageMaker", pm);
		
		return "list";
		
	}
	
	@RequestMapping(value = "m/list", method = RequestMethod.GET)
	public String mlist(Criteria cri, Model model) throws Exception {
		
		this.viewCnt = "outview";
		
		model.addAttribute("cri", cri);
		
		List<OneData> list = dao.listCriteria(cri);
//		List<OneData> list = dao.hiberListCri(cri);		
		model.addAttribute("textList", list);		
		
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		pm.setTotalCount(dao.countPaging(cri));
		
		if ( cri.getPage() > pm.getEndPage() ) {
			return "redirect:list?page="+Integer.toString(pm.getEndPage());
		}
		model.addAttribute("pageMaker", pm);
		
		return "mlist";
		
	}
	
	// 글쓰기 버튼을 눌렀을 때 - 글쓰기 form으로 이동
	@RequestMapping("insert")
	public String insert(Criteria cri, Model model) {
		model.addAttribute("cri", cri);
		return "inputform";		
	}
	
	@RequestMapping("m/insert")
	public String minsert(Criteria cri, Model model) {
		model.addAttribute("cri", cri);
		return "minputform";		
	}	
	
	// 글쓰기에서 쓰기를 눌렀을 때 - insertText를 통해 DB에 insert를 실제로 수행
	@RequestMapping(method = RequestMethod.POST, value="/insertgo")
	public String insertgo(OneData od, Model model) {
		
		Calendar cal = Calendar.getInstance();
		Timestamp now = new Timestamp(cal.getTime().getTime());		
				
		// temp라는 새로운 OneData 객체를 생성하여 inputform에서 받아온 데이터를 적용시켜 준 후
		// insertText라는 메소드를 통해 DB에 넣어준다
//		OneData temp = new OneData();
//		temp.setId(od.getId());
//		temp.setTitle(markMod(od.getTitle()));
//		temp.setContext(markMod(od.getContext()));
//		dao.insertText(temp);
		
		OneData temp = new OneData();
		temp.setId(od.getId());
		temp.setTitle(od.getTitle());
		temp.setContext(od.getContext());
		temp.setWdate(now);
		
		dao.hiberInsert(temp);
		
		return "redirect:list";
		
	}	
	
	@RequestMapping(method = RequestMethod.POST, value="m/insertgo")
	public String minsertgo(OneData od, Model model) {
		
		Calendar cal = Calendar.getInstance();
		Timestamp now = new Timestamp(cal.getTime().getTime());		

		OneData temp = new OneData();
		temp.setId(od.getId());
		temp.setTitle(od.getTitle());
		temp.setContext(od.getContext());
		temp.setWdate(now);
		
		dao.hiberInsert(temp);
		
		return "redirect:list";
		
	}
		
	
	// 게시물 하나를 클릭했을 때 - viewText를 통해 게시물 하나를 조회
	@RequestMapping("view/{textNum}")
	public String view(@PathVariable int textNum, Criteria cri, Model model) throws Exception {
		
		// 조회수 처리
		// updcheck가 none일 때 - 그냥 클릭해서 들어온 경우. 이 때 조회수 증가
		if ( "none".equals(updCheck) && "outview".equals(viewCnt) ) {
			dao.cntplus(textNum);
			
			// 최초 조회수가 증가될 때 변수에 inview라는 값을 넣어 새로고침시 증가되지 않게 처리
			// 목록을 눌러서 리스트 페이지로 나갈 때 이 변수에 outview라는 값이 들어간다
			this.viewCnt = "inview";
		}else{
			// else - update로 리다이렉트 된 경우. 이 때는 조회수를 증가시키지 않고 다시 none로 바꿔주기만 한다.
			this.updCheck = "none";
		}
		
		// 게시물 번호를 어트리뷰트에 담아준다
		model.addAttribute("textNum", textNum);
		
		// 이 게시물을 클릭했을 때의 페이지 정보(Criteria 클래스)를 어트리뷰트에 담아준다
		// (목록버튼을 눌렀을 때 그 페이지로 다시 돌아오기 위해서)
		model.addAttribute("cri", cri);
		
		// 게시물 하나에 대한 데이터를 받아온다
		OneData list = dao.viewText(textNum);
		String modc = markMod(list.getContext());
		list.setContext(modc);
		model.addAttribute("textList", list);	
				
		return "view";
		
	}
	
	@RequestMapping("m/view/{textNum}")
	public String mview(@PathVariable int textNum, Criteria cri, Model model) throws Exception {
		
		if ( "none".equals(updCheck) && "outview".equals(viewCnt) ) {
			dao.cntplus(textNum);			
			this.viewCnt = "inview";
		}else{			
			this.updCheck = "none";
		}
		
		model.addAttribute("textNum", textNum);		
		model.addAttribute("cri", cri);
		
		OneData list = dao.viewText(textNum);
		String modc = markMod(list.getContext());
		list.setContext(modc);
		model.addAttribute("textList", list);	
				
		return "mview";
		
	}
	
	// view 페이지에서 수정을 눌렀을 때 - 현재 글의 제목과 내용 정보를 가지고 업데이트 form으로 이동
	@RequestMapping("update/{textNum}")
	public String update(@PathVariable int textNum, Criteria cri, Model model) throws Exception {				
		
		// view에서와 마찬가지로 페이지 정보를 어트리뷰트에 담아준다
		model.addAttribute("cri", cri);
		
		// update 폼으로 가지고 갈 데이터를 어트리뷰터에 추가
		OneData list = dao.viewText(textNum);		
		model.addAttribute("textList", list);
		
		return "update";
		
	}
	
	@RequestMapping("m/update/{textNum}")
	public String mupdate(@PathVariable int textNum, Criteria cri, Model model) throws Exception {				
		
		model.addAttribute("cri", cri);
		
		OneData list = dao.viewText(textNum);		
		model.addAttribute("textList", list);
		
		return "mupdate";
		
	}
	
	// form에 입력된 내용을 바탕으로하여 updateText를 통해 실제 update를 수행
	@RequestMapping(method = RequestMethod.POST, value="/update/updatego")
	public String updatego(OneData od, Criteria cri, Model model) {
				
		// insert시와 마찬가지로 새로운 OneData 객체를 생성해 데이터를 세팅
		OneData temp = new OneData();
		temp.setNum(od.getNum());
		temp.setTitle(od.getTitle());
		temp.setContext(od.getContext());
		dao.updateText(temp);
		
		this.updCheck = "update";
				
		// 수정 완료시 수정을 진행한 view 페이지로 리다이렉트 한다. (페이지 정보까지 가지고 감)
		return "redirect:/view/"+od.getNum()+"?page="+cri.getPage();
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/m/update/updatego")
	public String mupdatego(OneData od, Criteria cri, Model model) {
				
		OneData temp = new OneData();
		temp.setNum(od.getNum());
		temp.setTitle(od.getTitle());
		temp.setContext(od.getContext());
		dao.updateText(temp);
		
		this.updCheck = "update";
				
		return "redirect:/m/view/"+od.getNum()+"?page="+cri.getPage();
		
	}
	
	// view 페이지에서 삭제를 눌렀을 때 - 현재 글의 번호정보로  deleteText를 통해 delete 수행
	@RequestMapping("delete/{textNum}")
	public String delete(@PathVariable int textNum, Criteria cri) {				
		
		dao.deleteText(textNum);
		
		// 삭제되었을 때도 그 글이 원래 있던 페이지로 돌아와야 하기 때문에 위에서 페이지 정보를 받아와 return에 적용시켜준다 
		return "redirect:/list?page="+cri.getPage();		
	}	
	
	@RequestMapping("m/delete/{textNum}")
	public String mdelete(@PathVariable int textNum, Criteria cri) {				
		
		dao.deleteText(textNum);
		
		// 삭제되었을 때도 그 글이 원래 있던 페이지로 돌아와야 하기 때문에 위에서 페이지 정보를 받아와 return에 적용시켜준다 
		return "redirect:/m/list?page="+cri.getPage();		
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	//큰따옴표, 꺽쇠, 주석, 줄바꿈 기호
	public String markMod(String before) {
		String after = before.replaceAll("&","&amp")
				.replaceAll("\"","&quot")
				.replaceAll(" ","&nbsp")
				.replaceAll("<","&lt")
				.replaceAll(">","&gt")
				.replaceAll("\n","<br>");
		return after;
	}
	
}

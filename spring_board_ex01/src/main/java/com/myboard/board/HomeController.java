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
	
	// ���� �Ϸ� �� view�� �����̷�Ʈ �� ��츦 üũ�ϱ� ���� ����
	private String updCheck = "none";
	
	// �� �ȿ��� ���ΰ�ħ�� ��ȸ�� �߰����� �ʱ� ���� ����
	private String viewCnt;
	
	// �Խ��� ���� ������
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Criteria cri, Model model) throws Exception {
		
		// ���� ��ȸ �� ������� ������ �� �ٽ� ��ȸ���� �����ϰ� ������ֱ� ���ؼ� �� ������ outview��� ���� �־��ش�
		this.viewCnt = "outview";
		
		// Criteria = page ��ȣ�� page�� �Խù� �� ������ ��� Class
		// model.addAttribute�� ����� �� ������ list.jsp�� ������
		// �� ��Ʈ����Ʈ�� �����͸� ��� �� = ���� �����͸� ǥ���� jsp ���������� Ȱ���ϱ� ���� ��
		model.addAttribute("cri", cri);
		
		// OneData = �Խù� �ϳ��ϳ��� ����(�۹�ȣ, ���� ��)�� ��� Class
		// listCriteria = Criteria Ŭ������ �̿��� ��ü �Խù� ��Ͽ��� �ش� ���������� �ҷ����� ������ ��� �޼ҵ�
		List<OneData> list = dao.listCriteria(cri);		
		
//		List<OneData> list = dao.hiberListCri(cri);		
		model.addAttribute("textList", list);		
		
		// PageMaker = �ϴ��� ������ ��ȣ�� ������ִ� Class
		// �Խù� �� ������ ���� Criteria Ŭ������ ������ ���� ����� �ؼ� �����ش�
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		pm.setTotalCount(dao.countPaging(cri));
		
		// �ԷµǴ� ������ ���ڰ� PageMaker�� ���� ���� �� ������ ������ Ŭ �� redirect�� ������ �������� �����ش�
		// ������ ������ ��ȣ�� ������ �� ���� �޼ҵ尡 �ѹ� �� ����� ���̴�
		if ( cri.getPage() > pm.getEndPage() ) {
			return "redirect:list?page="+Integer.toString(pm.getEndPage());
		}

		// ������ ��ȣ�� ���� ������ ��Ʈ����Ʈ�� ����ش�
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
	
	// �۾��� ��ư�� ������ �� - �۾��� form���� �̵�
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
	
	// �۾��⿡�� ���⸦ ������ �� - insertText�� ���� DB�� insert�� ������ ����
	@RequestMapping(method = RequestMethod.POST, value="/insertgo")
	public String insertgo(OneData od, Model model) {
		
		Calendar cal = Calendar.getInstance();
		Timestamp now = new Timestamp(cal.getTime().getTime());		
				
		// temp��� ���ο� OneData ��ü�� �����Ͽ� inputform���� �޾ƿ� �����͸� ������� �� ��
		// insertText��� �޼ҵ带 ���� DB�� �־��ش�
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
		
	
	// �Խù� �ϳ��� Ŭ������ �� - viewText�� ���� �Խù� �ϳ��� ��ȸ
	@RequestMapping("view/{textNum}")
	public String view(@PathVariable int textNum, Criteria cri, Model model) throws Exception {
		
		// ��ȸ�� ó��
		// updcheck�� none�� �� - �׳� Ŭ���ؼ� ���� ���. �� �� ��ȸ�� ����
		if ( "none".equals(updCheck) && "outview".equals(viewCnt) ) {
			dao.cntplus(textNum);
			
			// ���� ��ȸ���� ������ �� ������ inview��� ���� �־� ���ΰ�ħ�� �������� �ʰ� ó��
			// ����� ������ ����Ʈ �������� ���� �� �� ������ outview��� ���� ����
			this.viewCnt = "inview";
		}else{
			// else - update�� �����̷�Ʈ �� ���. �� ���� ��ȸ���� ������Ű�� �ʰ� �ٽ� none�� �ٲ��ֱ⸸ �Ѵ�.
			this.updCheck = "none";
		}
		
		// �Խù� ��ȣ�� ��Ʈ����Ʈ�� ����ش�
		model.addAttribute("textNum", textNum);
		
		// �� �Խù��� Ŭ������ ���� ������ ����(Criteria Ŭ����)�� ��Ʈ����Ʈ�� ����ش�
		// (��Ϲ�ư�� ������ �� �� �������� �ٽ� ���ƿ��� ���ؼ�)
		model.addAttribute("cri", cri);
		
		// �Խù� �ϳ��� ���� �����͸� �޾ƿ´�
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
	
	// view ���������� ������ ������ �� - ���� ���� ����� ���� ������ ������ ������Ʈ form���� �̵�
	@RequestMapping("update/{textNum}")
	public String update(@PathVariable int textNum, Criteria cri, Model model) throws Exception {				
		
		// view������ ���������� ������ ������ ��Ʈ����Ʈ�� ����ش�
		model.addAttribute("cri", cri);
		
		// update ������ ������ �� �����͸� ��Ʈ�����Ϳ� �߰�
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
	
	// form�� �Էµ� ������ ���������Ͽ� updateText�� ���� ���� update�� ����
	@RequestMapping(method = RequestMethod.POST, value="/update/updatego")
	public String updatego(OneData od, Criteria cri, Model model) {
				
		// insert�ÿ� ���������� ���ο� OneData ��ü�� ������ �����͸� ����
		OneData temp = new OneData();
		temp.setNum(od.getNum());
		temp.setTitle(od.getTitle());
		temp.setContext(od.getContext());
		dao.updateText(temp);
		
		this.updCheck = "update";
				
		// ���� �Ϸ�� ������ ������ view �������� �����̷�Ʈ �Ѵ�. (������ �������� ������ ��)
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
	
	// view ���������� ������ ������ �� - ���� ���� ��ȣ������  deleteText�� ���� delete ����
	@RequestMapping("delete/{textNum}")
	public String delete(@PathVariable int textNum, Criteria cri) {				
		
		dao.deleteText(textNum);
		
		// �����Ǿ��� ���� �� ���� ���� �ִ� �������� ���ƿ;� �ϱ� ������ ������ ������ ������ �޾ƿ� return�� ��������ش� 
		return "redirect:/list?page="+cri.getPage();		
	}	
	
	@RequestMapping("m/delete/{textNum}")
	public String mdelete(@PathVariable int textNum, Criteria cri) {				
		
		dao.deleteText(textNum);
		
		// �����Ǿ��� ���� �� ���� ���� �ִ� �������� ���ƿ;� �ϱ� ������ ������ ������ ������ �޾ƿ� return�� ��������ش� 
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
	
	//ū����ǥ, ����, �ּ�, �ٹٲ� ��ȣ
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

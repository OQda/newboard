package com.myboard.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myboard.dao.B_DAO;
import com.myboard.dto.OneRep;

@RestController
@RequestMapping("/replies")
public class Replycontroller {
	
	@Inject
	private B_DAO dao;
	
	// ���ο� ��� �߰�
	// RequestBody - http ��û�� �ڹٰ�ü�� ��ȯ
	// ResponseEntity - �����͸� JSON ���·� http�� ����(?)
	// OneRep ��� �ϳ��� ������ ������ Class
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody OneRep or){
		
		ResponseEntity<String> entity = null;
		try {
			dao.addReply(or);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}		
		return entity;
	}	
	
	// ��� �ҷ����� - ���� ��ȣ�� ������ ��ȣ�� �Ű������� �޴´�
	@RequestMapping(value = "/{pnum}/{page}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> list(@PathVariable("pnum") int pnum, @PathVariable("page") int page){
		
		// ��۰�, ������ ������ ������ ���� HashMap�� ���
		ResponseEntity<Map<String, Object>> entity = null;
		try {
			Criteria cri = new Criteria();
			cri.setPage(page);
			
			RepPageMaker pm = new RepPageMaker();
			pm.setCri(cri);
			
			// �ؽø� ��ü ����
			Map<String, Object> map = new HashMap<String, Object>();
			
			// ��� �����͸� �ؽøʿ� ����
			List<OneRep> list = dao.listReply(pnum, cri);			
			map.put("list", list);
			
			// ������ �����͸� �ؽøʿ� ����
			int repCount = dao.countRep(pnum);
			pm.setTotalCount(repCount);			
			map.put("pageMaker", pm);
			
			// put�� �����͸� entity�� ����
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	// ��� ���� - view ���������� put ����� �����͸� ���۹޾� ����ȴ�
	@RequestMapping(value = "/{rno}", method = {RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable("rno") int rno, @RequestBody OneRep or){
		
		ResponseEntity<String> entity = null;
		try {
			or.setRno(rno);
			dao.modReply(or);
			
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
		
	}
	
	// ��� ���� - view ���������� delete ����� �����͸� ���۹޾� ����ȴ�
	@RequestMapping(value = "/{rno}", method = RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable("rno") int rno){
		
		ResponseEntity<String> entity = null;
		try {
			dao.delReply(rno);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}

}

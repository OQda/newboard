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
	
	// 새로운 댓글 추가
	// RequestBody - http 요청을 자바객체로 변환
	// ResponseEntity - 데이터를 JSON 형태로 http에 전송(?)
	// OneRep 댓글 하나의 정보를 가지는 Class
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
	
	// 댓글 불러오기 - 원글 번호와 페이지 번호를 매개변수로 받는다
	@RequestMapping(value = "/{pnum}/{page}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> list(@PathVariable("pnum") int pnum, @PathVariable("page") int page){
		
		// 댓글과, 페이지 정보를 보내기 위해 HashMap을 사용
		ResponseEntity<Map<String, Object>> entity = null;
		try {
			Criteria cri = new Criteria();
			cri.setPage(page);
			
			RepPageMaker pm = new RepPageMaker();
			pm.setCri(cri);
			
			// 해시맵 객체 생성
			Map<String, Object> map = new HashMap<String, Object>();
			
			// 댓글 데이터를 해시맵에 저장
			List<OneRep> list = dao.listReply(pnum, cri);			
			map.put("list", list);
			
			// 페이지 데이터를 해시맵에 저장
			int repCount = dao.countRep(pnum);
			pm.setTotalCount(repCount);			
			map.put("pageMaker", pm);
			
			// put된 데이터를 entity로 전송
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	// 댓글 수정 - view 페이지에서 put 방식의 데이터를 전송받아 실행된다
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
	
	// 댓글 삭제 - view 페이지에서 delete 방식의 데이터를 전송받아 실행된다
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

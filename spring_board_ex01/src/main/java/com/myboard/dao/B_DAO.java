package com.myboard.dao;

import java.util.List;

import com.myboard.board.Criteria;
//import com.myboard.dto.OneData;
import com.myboard.dto.OneRep;
import com.myboard.hiber.OneData;

public interface B_DAO {
	
	// °Ô½Ã±Û
//	public List<OneData> selectText();
	public void insertText(OneData write);
	public void hiberInsert(OneData write);
	public OneData viewText(int num);
	public void cntplus(int num);
	public void updateText(OneData update);
	public void deleteText(int num);
//	public List<OneData> listPage(int page);
	public List<OneData> listCriteria(Criteria cri);
	public List<OneData> hiberListCri(Criteria cri);
	public int countPaging(Criteria cri);
	
	// ´ñ±Û
	public List<OneRep> listReply(int pnum, Criteria cri);
	public void addReply(OneRep addrep);
	public void modReply(OneRep modrep);
	public void delReply(int rno);
	public int countRep(int pnum);

}

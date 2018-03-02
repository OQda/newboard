package com.myboard.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.myboard.board.Criteria;
//import com.myboard.dto.OneData;
import com.myboard.dto.OneRep;
import com.myboard.hiber.HibernateUtil;
import com.myboard.hiber.OneData;


@Repository
public class B_DAOImpl implements B_DAO{
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String Namespace = "com.myboard.mappers.memberMapper";
	private static final String Repspace = "com.myboard.mappers.replyMapper";

//	@Override
//	public List<OneData> selectText() {
//		// TODO Auto-generated method stub
//		return sqlSession.selectList(Namespace+".selectText");
//	}

	@Override
	public void insertText(OneData write) {
		// TODO Auto-generated method stub
		sqlSession.insert(Namespace+".insertText", write);
	}
	
	@Override
	public void hiberInsert(OneData write) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSf().openSession();
		Transaction tx = session.beginTransaction();
		
		session.persist(write);
		
		tx.commit();
		session.close();
	}
	
	@Override
	public OneData viewText(int textNum) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".viewText", textNum);
		
	}

	@Override
	public void cntplus(int num) {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".cntPlus", num);
	}

	@Override
	public void updateText(OneData update) {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".updateText", update);
	}

	@Override
	public void deleteText(int num) {
		// TODO Auto-generated method stub
		sqlSession.delete(Namespace+".deleteText", num);
		
	}

//	@Override
//	public List<OneData> listPage(int page) {
//		// TODO Auto-generated method stub
//		if ( page <= 0 ) {
//			page = 1;
//		}
//		
//		page = ( page -1 ) * 10;
//		
//		return sqlSession.selectList(Namespace+".listPage", page);
//	}

	@Override
	public List<OneData> listCriteria(Criteria cri) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".listCriteria", cri);
	}
	
	@Override
	public List<OneData> hiberListCri(Criteria cri) {		
		// TODO Auto-generated method stub
		
		Session session = HibernateUtil.getSf().openSession();
				
		String sql = "from OneData order by num desc, wdate desc";
		
		List users = session.createQuery(sql)
							.setFirstResult(cri.getPageStart())
							.setMaxResults(cri.getPerPageNum())
							.list();

		session.close();
		
		return users;
				
	}	

	@Override
	public int countPaging(Criteria cri) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".countPaging", cri);
	}

	@Override
	public List<OneRep> listReply(int pnum, Criteria cri) {
		// TODO Auto-generated method stub
		
		Map<String, Object> pm = new HashMap<>();
		
		pm.put("pnum", pnum);
		pm.put("cri", cri);
		
		return sqlSession.selectList(Repspace+".listReply", pm);
	}

	@Override
	public void addReply(OneRep addrep) {
		// TODO Auto-generated method stub
		sqlSession.insert(Repspace+".addReply", addrep);
	}

	@Override
	public void modReply(OneRep modrep) {
		// TODO Auto-generated method stub
		sqlSession.update(Repspace+".modReply", modrep);
	}

	@Override
	public void delReply(int rno) {
		// TODO Auto-generated method stub
		sqlSession.delete(Repspace+".delReply", rno);
	}
	
	@Override
	public int countRep(int pnum) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Repspace+".countReply", pnum);
	}

	

	

}

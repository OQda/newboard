package com.myboard.board;

public class RepPageMaker {
	
	private int totalCount, startPage, endPage;
	private boolean prev, next;
	private int displayPageNum = 1;
	private Criteria cri;
	
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	public Criteria getCri() {
		return cri;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	private void calcData() {
		
		endPage = (int) ( Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum );
		
		startPage = (endPage - displayPageNum) + 1;
		
		int tempEndPage = (int) ( Math.ceil(totalCount / (double) cri.getPerPageNum()) );
		if ( endPage > tempEndPage ) {
			endPage = tempEndPage;
		}
		
		prev = startPage == 1 ? false : true;
		next = endPage * cri.getPerPageNum() >= totalCount ? false : true;		
		
	}
	
	public boolean getPrev() {
		return prev;
	}
	
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	
	public boolean getNext() {
		return next;
	}
	
	public void setNext(boolean next) {
		this.next = next;
	}
	
	public int getStartPage() {
		return startPage;
	}
	
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	
	public int getEndPage() {
		return endPage;
	}
	
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
}

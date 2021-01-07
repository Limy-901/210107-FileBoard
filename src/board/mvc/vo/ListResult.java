package board.mvc.vo;


import java.util.List;
import mvc.domain.FileBoard;

public class ListResult {
	private int currentPage;//현재페이지
	private long totalCount;//토탈개수는 따로 필요
	private int pageSize;//필요. 그래야 구성
	private List<FileBoard> list;//보드를 담아오는 리스트. 그래야 구성가능.
	private long totalPageCount;//총 페이지 수 계산.
	
	public ListResult() {}
	
	public ListResult(int currentPage, long totalCount, int pageSize, List<FileBoard> list) {
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.list = list;
		this.totalPageCount = calTotalPageCount();
	}
	private long calTotalPageCount() {
		long tpc = totalCount/pageSize; 
		if(totalCount%pageSize != 0) tpc++;
		return tpc;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<FileBoard> getList() {
		return list;
	}
	public void setList(List<FileBoard> list) {
		this.list = list;
	}
	public long getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(long totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
}

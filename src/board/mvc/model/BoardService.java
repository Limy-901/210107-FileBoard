package board.mvc.model;

import java.util.ArrayList;
import java.util.List;

import board.mvc.vo.ListResult;
import mvc.domain.FileBoard;

public class BoardService {
	private BoardDAO dao;
	
	private static final BoardService instance = new BoardService();
	private BoardService(){
		dao = new BoardDAO();
	} 
	public static BoardService getInstance() {
		return instance;
	}
	
	public ListResult getListResult(int cp, int ps) {
		List<FileBoard> list = dao.list(cp, ps); //use1
		long totalCount = dao.getTotalCount(); //use2
		ListResult r = new ListResult(cp, totalCount, ps, list);
		
		return r;
	}
	
	public ArrayList<FileBoard> listS(){
		return dao.list();
	}
	public void insertS(FileBoard board) {
		dao.insert(board);
	}
	public FileBoard getBoardS(long seq) {
		return dao.getBoard(seq);
	}
	public void updateS(FileBoard board) {
		dao.update(board);
	}
	public void delS(long seq) {
		dao.del(seq);
	}
	public void fileDbDelS(long seq) {
		dao.fileDbDel(seq);
	}
}

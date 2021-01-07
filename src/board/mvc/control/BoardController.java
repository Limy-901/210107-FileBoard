package board.mvc.control;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.mvc.model.BoardService;
import board.mvc.model.FileSet;
import board.mvc.vo.ListResult;
import mvc.domain.FileBoard;

@WebServlet("/fboard/board.do")
public class BoardController extends HttpServlet {
	String m = "";
	public void service(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
		m = request.getParameter("m");
		if(m != null) {
			switch(m) {
				case "list": list(request, response); break;
				case "content": getBoard(request, response); break;
				case "update" : getBoard(request, response); break;
				case "updateOk" : updateOk(request, response); break;
				case "del" : del(request, response); break;
				case "write" : write(request,response); break;
				case "insert" : insert(request,response); break;
				case "download": download(request,response); break;
				default: list(request, response); break;
			}
		}else list(request, response);
	}
	private void list(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		String cpStr = request.getParameter("cp");
		String psStr = request.getParameter("ps");
		HttpSession session = request.getSession();
		int cp = 1;
		int ps = 3;
		// cpStr : 파라미터로 들어온 값 & cpObj : 세션에 있던 값
		if(cpStr == null) {
			Object cpObj = session.getAttribute("cp");
			if(cpObj != null) cp = (Integer)cpObj;
		}else {
			cpStr = cpStr.trim();
			cp = Integer.parseInt(cpStr);
		}
		session.setAttribute("cp", cp);
		// psStr : 파라미터로 들어온 값 & psObj : 세션에 있던 값
		// psParam : 파라미터값 인트형변환 & psSession : 세션 값 인트형변환
		if(psStr == null) { // 파라미터에서 값이 안넘어왔다면
			Object psObj = session.getAttribute("ps");
			if(psObj != null) ps = (Integer)psObj; // 그대로 세션값 사용
		}else { // 파라미터에서 값 넘어왔다면
			psStr = psStr.trim(); // 먼저 공백 제거 해주고,
			int psParam = Integer.parseInt(psStr); // int 형변환 해주고,
			Object psObj = session.getAttribute("ps"); // 세션값도 받아오고
			if(psObj != null) { // 원래 세션에 값이 있었다면,
				int psSession = (Integer)psObj; // int 형변환 해주고,
				if(psSession != psParam) { // 파라미터값과 비교하고 다르면,
					cp = 1; // 현재페이지 1로 이동
					session.setAttribute("cp", cp); // 현재페이지 바뀐값 넣기
				}
			}else { // 세션에 값 없었다면
				if(ps!=psParam) { // 현재 ps값과 파라미터 ps값이 다르면
					cp = 1; // 현재페이지 1로 재조정
					session.setAttribute("cp", cp);
				}
			}
			ps = psParam; // 파라미터 값을 ps로 지정해준다.
		}
		session.setAttribute("ps", ps); // 세션에 변경된 ps값 넣기
		BoardService service = BoardService.getInstance();
		ListResult listResult = service.getListResult(cp, ps);
		request.setAttribute("listResult", listResult);
		
		if(listResult.getList().size() == 0 && cp>1) {
			response.sendRedirect("board.do?m=list&cp="+(cp-1));
		}else {
			String view = "list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(view);
			rd.forward(request, response);
		}
	}
	private long getSeq(HttpServletRequest request) {
		String seqStr = request.getParameter("seq");
		long seq = 0L;
		if(seqStr == null) {
			return -1L;
		}else {
			seqStr = seqStr.trim();
			try {
				seq = Integer.parseInt(seqStr);
				return seq;
			}catch(NumberFormatException ne) {
				return -1L;
			}
		}
	}
	private void getBoard(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException{
		long seq = getSeq(request);
		if(seq != -1L) {
			BoardService service = BoardService.getInstance();
			FileBoard board = service.getBoardS(seq);
			long nu = board.getFsize();
			System.out.println("nu:"+nu);
			request.setAttribute("board", board);
			String view = "";
			if(m.equals("content")) {
				view = "content.jsp";
			}else { //m.equals("update")
				view = "update.jsp";
			}
			RequestDispatcher rd = request.getRequestDispatcher(view);
			rd.forward(request, response);
		}else {
			String view = "board.do";
			response.sendRedirect(view);
		}
	}
	private void updateOk(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException{
		long seq = getSeq(request);		
		File fSaveDir = new File(FileSet.FILE_DIR);
		if(!fSaveDir.exists()) fSaveDir.mkdirs();
		
		MultipartRequest mr = new MultipartRequest(request, 
				FileSet.FILE_DIR, 
				1*1024*1024, 
				"utf-8", 
				new DefaultFileRenamePolicy());
		String subject = mr.getParameter("subject");
		String content = mr.getParameter("content");
		String newFileName = mr.getFilesystemName("newfile");
		String ofname = mr.getOriginalFileName("newfile");
		String oldFile = mr.getParameter("filename");
		
		if(newFileName != null) {
			File f = new File(fSaveDir, newFileName);
			long fsize = f.length();
			FileBoard board = new FileBoard(seq, null, null, subject, content,null,newFileName,ofname,fsize);
			BoardService service = BoardService.getInstance();
			service.updateS(board);
		}else {
			FileBoard board = new FileBoard(seq, null, null, subject, content,null,null,null,-1L);
			BoardService service = BoardService.getInstance();
			service.updateS(board);
			if(oldFile == null) service.fileDbDelS(seq);
		}
		
		String view = "board.do";
		response.sendRedirect(view);
	}
	private void del(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException{
		long seq = getSeq(request);
		if(seq != -1L) {
			BoardService service = BoardService.getInstance();
			service.delS(seq);
		}
		String view = "board.do";
		response.sendRedirect(view);
	}
	private void write(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException{
		String view = "input.jsp";
		response.sendRedirect(view);
	}
	private void insert(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException{
		File fSaveDir = new File(FileSet.FILE_DIR);
		if(!fSaveDir.exists()) fSaveDir.mkdirs();
		
		MultipartRequest mr = new MultipartRequest(request, 
				FileSet.FILE_DIR, 
				1*1024*1024, 
				"utf-8", 
				new DefaultFileRenamePolicy());
		
		String writer = mr.getParameter("writer");
		String email = mr.getParameter("email");
		String subject = mr.getParameter("subject");
		String content = mr.getParameter("content");
		String filename = mr.getFilesystemName("filename");
		String ofname = mr.getOriginalFileName("filename");
		File f = new File(fSaveDir, filename);
		long fsize = f.length();
		System.out.println("d"+mr.getParameter("writer")+filename+mr.getOriginalFileName("filename"));
		if(filename != null) {
			FileBoard board = new FileBoard(-1L, writer, email, subject, content, null, filename,ofname, fsize);
			BoardService service = BoardService.getInstance();
			service.insertS(board);
		}else {
			FileBoard board = new FileBoard(-1L, writer, email, subject, content, null, null, null, -1L);
			BoardService service = BoardService.getInstance();
			service.insertS(board);
		}
		String view = "board.do";
		response.sendRedirect(view);
	}
	public void download(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		//클라이언트 pc에 다운로드하기.
		String fname = request.getParameter("fname");
		File file = new File(FileSet.FILE_DIR +"/"+fname);
		response.setContentType("application/octet-stream"); 
		String Agent=request.getHeader("USER-AGENT");
		if( Agent.indexOf("MSIE") >= 0 ){
			int i = Agent.indexOf( 'M', 2 );
			String IEV = Agent.substring( i + 5, i + 8 );
			if( IEV.equalsIgnoreCase("5.5") ){
				response.setHeader("Content-Disposition", "filename=" + new String( fname.getBytes("utf-8"), "8859_1") );
			}else{
				response.setHeader("Content-Disposition", "attachment;filename="+new String(fname.getBytes("utf-8"),"8859_1"));
			}
		}else{
			response.setHeader("Content-Disposition", "attachment;filename=" + new String( fname.getBytes("utf-8"), "8859_1") );
		}

		byte b[] = new byte[1024];
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		if( file.isFile()){ 
			try{ 
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				bos = new BufferedOutputStream( response.getOutputStream() );  
				int read = 0;
				while( ( read = fis.read( b ) ) != -1 ){  
					bos.write(b,0,read);
				}
				bos.flush();
			}catch( Exception e ){
			}finally {
				if(bis!=null) bis.close();
				if(bos!=null) bos.close();
				if(fis!=null) fis.close();
			}
		}
	}
}

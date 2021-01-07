package board.mvc.model;

public class BoardSQL {
	final static String LIST 
	= "select * from (select ROWNUM rnum, aa.* from (select * from BOARD"
			+ " order by SEQ desc) aa) where rnum>? and rnum<=?";
	final static String COUNT 
	= "select max(ROWNUM) from BOARD";
	final static String INSERT
    ="insert into BOARD values(BOARD_SEQ.nextval, ?,?,?,?, SYSDATE, ?,?,?)";
	final static String CONTENT
	= "select * from BOARD where SEQ=?";
	final static String DEL
	= "delete from BOARD where SEQ=?";
	final static String UPDATE
    = "update BOARD set SUBJECT=?, CONTENT=? where SEQ=?";
	final static String FILE_UPDATE
	= "update BOARD set SUBJECT=?, CONTENT=?, FNAME=?, OFNAME=?, FSIZE=? where SEQ=?";
	final static String FILEDB_DEL
	= "update BOARD set FNAME=null, OFNAME=null, FSIZE=null where SEQ=?";
}
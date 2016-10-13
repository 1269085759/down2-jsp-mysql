package Xproer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.google.gson.Gson;

public class DnFile 
{
	public DnFile()
	{
	}
	
	public static void Clear()
	{
        String sql = "TRUNCATE TABLE down_files ";
        DbHelper db = new DbHelper();
		PreparedStatement cmd = db.GetCommand(sql);
		db.ExecuteNonQuery(cmd);    
	}

    public int Add(DnFileInf inf)
    {
    	int idSvr = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("insert into down_files(");        
        sb.append(" f_uid");
        sb.append(",f_nameLoc");
        sb.append(",f_pathLoc");
        sb.append(",f_fileUrl");
        sb.append(",f_lenSvr");
        sb.append(",f_sizeSvr");
        sb.append(") values(");        
        sb.append(" ?");//uid
        sb.append(",?");//name
        sb.append(",?");//pathLoc
        sb.append(",?");//pathSvr
        sb.append(",?");//lenSvr
        sb.append(",?");//sizeSvr
        sb.append(")");
		
		DbHelper db = new DbHelper();
		PreparedStatement cmd = db.GetCommandPK(sb.toString());

		try
		{
			cmd.setInt(1,inf.uid);
			cmd.setString(2,inf.nameLoc);
			cmd.setString(3,inf.pathLoc);
			cmd.setString(4,inf.fileUrl);
			cmd.setLong(5,inf.lenSvr);
			cmd.setString(6,inf.sizeSvr);
			idSvr = (int) db.ExecuteGenKey(cmd);			
		}
		catch (SQLException e){e.printStackTrace();}		

		return idSvr;
    }

    /// <summary>
    /// 将文件设为已完成
    /// </summary>
    /// <param name="fid"></param>
    public void Complete(int fid)
    {
		DbHelper db = new DbHelper();
		PreparedStatement cmd = db.GetCommand("update down_files set f_complete=1 where f_id=?;");
		try
		{
			cmd.setInt(1,fid);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		db.ExecuteNonQuery(cmd);
    }

    /// <summary>
    /// 删除文件
    /// </summary>
    /// <param name="fid"></param>
    public void Delete(int fid,int uid)
    {
        String sql = "delete from down_files where f_id=? and f_uid=?";
        DbHelper db = new DbHelper();
		PreparedStatement cmd = db.GetCommand(sql);

		try
		{
			cmd.setInt(1,fid);
			cmd.setInt(2,uid);
			db.ExecuteNonQuery(cmd);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
    }

    public void updateProcess(int fid,int uid,String lenLoc,String perLoc)
    {
    	String sql = "update down_files set f_lenLoc=?,f_perLoc=? where f_id=? and f_uid=?";
        DbHelper db = new DbHelper();
		PreparedStatement cmd = db.GetCommand(sql);

		try
		{
			cmd.setLong(1,Long.parseLong(lenLoc));
			cmd.setString(2,perLoc);
			cmd.setInt(3,fid);
			cmd.setInt(4,uid);
			
			db.ExecuteNonQuery(cmd);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
    }

    /// <summary>
    /// 获取所有未完成的文件列表
    /// </summary>
    /// <returns></returns>
    public String GetAll(int uid)
    {  
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        sb.append(" f_id");
        sb.append(",f_nameLoc");
        sb.append(",f_pathLoc");
        sb.append(",f_fileUrl");
        sb.append(",f_perLoc");
        sb.append(",f_lenLoc");
        sb.append(",f_lenSvr");
        sb.append(",f_sizeSvr");
        sb.append(" from down_files");
        sb.append(" where f_uid=? and f_complete=0");

        ArrayList<DnFileInf> files = new ArrayList<DnFileInf>();
		DbHelper db = new DbHelper();
		PreparedStatement cmd = db.GetCommand(sb.toString());
		try
		{
			cmd.setInt(1,uid);
			ResultSet r = db.ExecuteDataSet(cmd);
			while (r.next())
			{
				DnFileInf f		= new DnFileInf();
				f.idSvr			= r.getInt(1);
				f.nameLoc		= r.getString(2);
			    f.pathLoc		= r.getString(3);
				f.fileUrl		= r.getString(4);
				f.perLoc		= r.getString(5);
			    f.lenLoc		= r.getLong(6);
			    f.lenSvr		= r.getLong(7);
			    f.sizeSvr		= r.getString(8);
				files.add(f);
			}
			r.close();
			cmd.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

        Gson g = new Gson();
	    return g.toJson( files );
	}
}
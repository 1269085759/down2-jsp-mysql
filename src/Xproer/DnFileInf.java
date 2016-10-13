package Xproer;
import com.google.gson.annotations.SerializedName;

public class DnFileInf 
{
    public DnFileInf()
    { }

    @SerializedName("idSvr")
    public int idSvr;

    @SerializedName("uid")
    public int uid;

    @SerializedName("mac")
    public String mac;
    
    @SerializedName("nameLoc")
    public String nameLoc;

    @SerializedName("pathLoc")
    public String pathLoc;

    @SerializedName("fileUrl")
    public String fileUrl;

    @SerializedName("lenLoc")
    public long lenLoc;

    @SerializedName("lenSvr")
    public long lenSvr;
    
    @SerializedName("sizeSvr")
    public String sizeSvr;
    
    @SerializedName("perLoc")
    public String perLoc;
}
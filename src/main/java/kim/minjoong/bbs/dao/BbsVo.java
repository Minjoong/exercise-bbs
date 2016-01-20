package kim.minjoong.bbs.dao;
public class BbsVo {
    private Integer idx;
    private String subject;
    private String content;
    private String reg_datetime;
    private String mod_datetime;
    private String user_id;

    public Integer getIdx() {
        return idx;
    }
    public void setIdx(Integer idx) {
        this.idx = idx;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getReg_datetime() {
        return reg_datetime;
    }
    public void setReg_datetime(String reg_datetime) {
        this.reg_datetime = reg_datetime;
    }
    public String getMod_datetime() {
        return mod_datetime;
    }
    public void setMod_datetime(String mod_datetime) {
        this.mod_datetime = mod_datetime;
    }
    public String getUser_id() {
    	return user_id;
    }
    public void setUser_id(String user_id) {
    	this.user_id = user_id;
    }
}
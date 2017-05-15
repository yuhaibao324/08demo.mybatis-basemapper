package cn.howso.framework.mybatis.pagination;

/**
 * <p>
 * mybatis的分页插件需要的model
 * </p>
 * 
 * @author wzf
 * @date 2016年3月15日 上午9:16:24
 */
public class Page extends Pageable{

    /** 当前页,从1开始 */
    private int num;

    /** 每页显示记录数 */
    private int size;
    public static Page fromNumSize(int num,int size){
        Page p = new Page();
        p.num = num;
        p.size = size;
        return p;
    }
    public void setPageIndex(int pageIndex){
        num = pageIndex;
    }
    public void setRows(int rows){
        size = rows;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    @Override
    public String createPageSql(String sql, String dialect) {
        int limit = size;
        int offset = (num-1)*size;
        return super.createPageSql(sql, dialect, limit, offset);
    }
}
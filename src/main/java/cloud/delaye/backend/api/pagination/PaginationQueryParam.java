
package cloud.delaye.backend.api.pagination;

import jakarta.ws.rs.QueryParam;

/**
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public class PaginationQueryParam {
	
	public static final String PAGE_PARAM = "page";
	public static final String PAGE_SIZE = "pageSize";
	
	@QueryParam(PAGE_PARAM)
	private String page;
	
	@QueryParam(PAGE_SIZE)
	private String pageSize;

	//<editor-fold defaultstate="collapsed" desc="Constructors">
	public PaginationQueryParam() {
	}
	
	public PaginationQueryParam(int page, int pageSize) {
		this.page = String.valueOf(page);
		this.pageSize = String.valueOf(pageSize);
	}
	
	public PaginationQueryParam(String page, String pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="Getters & Setters">
	public String getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getPage() {
		return page;
	}
	
	public void setPage(String page) {
		this.page = page;
	}
	//</editor-fold>	
	
}
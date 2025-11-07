
package cloud.delaye.backend.api.pagination;

import java.util.Map;
import jakarta.ws.rs.core.Response;
import cloud.delaye.backend.api.IApiResponseConfig;

/**
 * Represent data about pagination in the REST API
 * 
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public class PaginationData implements IApiResponseConfig {
	
	/**
	 * HTTP header for pagination
	 */
	public enum EHttpHeaders {
		
		// Pagination headers
		X_PAGE_SIZE("X-PageSize"),
		X_PAGE("X-Page"),
		X_TOTAL_PAGE("X-TotalPage"),
		X_TOTAL_ELEMENT("X-TotalElement");
		
		/**
		 * The header key
		 */
		private final String header;

		private EHttpHeaders(String header) {
			this.header = header;
		}

		public String getHeader() {
			return header;
		}
		
	}
	
	/**
	 * The default page if null or empty
	 */
	public static final int DEFAULT_PAGE = 1;
	
	/**
	 * The default page size of null or empty
	 */
	public static final int DEFAULT_PAGE_SIZE = 30;
	
	/**
	 * The maximum of element per page
	 */
	public static final int MAX_PAGE_SIZE = 100;
	
	/**
	 * The number of the page
	 */
	private int page;
	
	/**
	 * The number of element per page
	 */
	private int pageSize;
	
	/**
	 * The total of page
	 */
	private long totalPage;
	
	/**
	 * The total of element
	 */
	private long totalElement;
	
	/**
	 * Construct pagination without any data
	 */
	public PaginationData() {
		
	}

	/**
	 * Construct pagination data using a specific page and page size.
	 * 
	 * @param page The page
	 * @param pageSize The size of the page
	 * @param totalPage The total of page
	 * @param totalElement The total of element
	 */
	public PaginationData(int page, int pageSize, long totalPage, long totalElement) {
		this.page = page;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalElement = totalElement;
	}

	//<editor-fold defaultstate="collapsed" desc="Getters">
	public int getPage() {
		return page;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public long getTotalPage() {
		return totalPage;
	}
	
	public long getTotalElement() {
		return totalElement;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public void setTotalElement(long totalElement) {
		this.totalElement = totalElement;
	}
	
	/**
	 * Return the offset for database querying.
	 * @return The offset for database querying.
	 */
	public int getOffset() {
		return (page - 1) * pageSize;
	}
	//</editor-fold>
	
	/**
	 * Configure the JAX-RS response with pagination HTTP headers
	 * @param builder The JAX-RS response building
	 */
	@Override
	public void configure(Response.ResponseBuilder builder) {
		builder.header(EHttpHeaders.X_PAGE.getHeader(), page);
		builder.header(EHttpHeaders.X_TOTAL_PAGE.getHeader(), totalPage);
		builder.header(EHttpHeaders.X_PAGE_SIZE.getHeader(), pageSize);
		builder.header(EHttpHeaders.X_TOTAL_ELEMENT.getHeader(), totalElement);
	}
	
	/**
	 * Utility method to check if an header is a pagination header
	 * @param header The header to check
	 * @return True or false
	 */
	public static boolean isPaginationHeader(Map.Entry<String, String> header) {
		return header.getKey().equalsIgnoreCase(EHttpHeaders.X_PAGE.getHeader()) || 
			   header.getKey().equalsIgnoreCase(EHttpHeaders.X_PAGE_SIZE.getHeader()) || 
			   header.getKey().equalsIgnoreCase(EHttpHeaders.X_TOTAL_ELEMENT.getHeader()) ||
			   header.getKey().equalsIgnoreCase(EHttpHeaders.X_TOTAL_PAGE.getHeader());
	}
	
}
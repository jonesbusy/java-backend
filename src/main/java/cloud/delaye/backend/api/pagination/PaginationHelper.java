
package cloud.delaye.backend.api.pagination;

import cloud.delaye.backend.enums.EApiErrorLocationType;
import cloud.delaye.backend.enums.EApiErrorCodes;
import cloud.delaye.backend.validation.ApiErrorResponse;
import cloud.delaye.backend.validation.ApiErrorsException;

/**
 * Helper to build pagination when sending a response to the user
 * 
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public class PaginationHelper {

	/**
	 * Build pagination data for the Application Resource.
	 *
	 * Can be used typically in any resource like this :
	 *
	 * Response retrieveAll(@QueryParam("page") String page, @QueryParam("pageSize") String pageSize) {
	 *     paginationHelper.buildPagination(page, pageSize, myDao.countAll();
	 * }
	 *
	 * @param paginationQueryParam The pagination beanParam
	 * @param totalElement The total of element
	 * @return A pagination data object
	 * @throws ApiErrorsException If the page or the pageSize cannot be
	 * converted to integer or are less or equal at 0
	 */
	public PaginationData buildPagination(PaginationQueryParam paginationQueryParam, long totalElement) throws ApiErrorsException {

		int currentPage = 0;
		int currentPageSize = 0;

		try {
			// Parse page or use default
			currentPage = (paginationQueryParam.getPage() == null || paginationQueryParam.getPage().isEmpty()) ? PaginationData.DEFAULT_PAGE : Integer.parseInt(paginationQueryParam.getPage());
			if (currentPage <= 0) {
				throw new ApiErrorsException(new ApiErrorResponse(
						"page query parameter must be positive integer", 
						EApiErrorCodes.REQUEST_BAD_JSON_VALUE_TYPE, 
						EApiErrorLocationType.QUERY_PARAM, PaginationQueryParam.PAGE_PARAM));
			}
		} catch (NumberFormatException e) {
			throw new ApiErrorsException(new ApiErrorResponse(
					"page query parameter must be positive integer",
					EApiErrorCodes.REQUEST_BAD_JSON_VALUE_TYPE, 
					EApiErrorLocationType.QUERY_PARAM, PaginationQueryParam.PAGE_PARAM));
		}

		try {
			// Parse page size or use default
			currentPageSize = (paginationQueryParam.getPageSize() == null || paginationQueryParam.getPageSize().isEmpty()) ? PaginationData.DEFAULT_PAGE_SIZE : Integer.parseInt(paginationQueryParam.getPageSize());
			if (currentPageSize <= 0 || currentPageSize > PaginationData.MAX_PAGE_SIZE) {
				throw new ApiErrorsException(new ApiErrorResponse(
						"pageSize query parameter must be between 0 and " + PaginationData.MAX_PAGE_SIZE,
						EApiErrorCodes.GENERIC_VALUE_OUT_OF_BOUNDS, 
						EApiErrorLocationType.QUERY_PARAM, "pageSize"));
			}
		} catch (NumberFormatException e) {
			throw new ApiErrorsException(new ApiErrorResponse(
					"pageSize query parameter must be between 0 and " + PaginationData.MAX_PAGE_SIZE,
					EApiErrorCodes.GENERIC_VALUE_OUT_OF_BOUNDS, 
					EApiErrorLocationType.QUERY_PARAM, "pageSize"));
		}
		
		long totalPage = (long) Math.ceil(((double) totalElement / currentPageSize));
		return new PaginationData(currentPage, currentPageSize, totalPage == 0 ? PaginationData.DEFAULT_PAGE : totalPage , totalElement);
	}

}

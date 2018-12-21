package io.codelirium.blueground.intergalactica.controller.mapping;


public class UrlMappings {


	private UrlMappings() { }


	public static final String API_PATH_ROOT = "/api/intergalactica";

	public static final String API_PATH_WEBSOCKET = "/websocket";

	public static final String PATH_PARAM_PAGE = "pageId";

	public static final String PATH_PARAM_UNIT = "unitId";

	public static final String REQ_PARAM_SEARCH_TERM = "searchTerm";

	public static final String API_ENDPOINT_TOKENS = "/tokens";

	public static final String API_ENDPOINT_TOKEN_PROFILE = API_ENDPOINT_TOKENS + "/profiles";

	public static final String API_ENDPOINT_UNITS = "/units";

	public static final String API_ENDPOINT_UNITS_PAGED = API_ENDPOINT_UNITS + "/pages/{" + PATH_PARAM_PAGE + ":[\\d]+}";

	public static final String API_ENDPOINT_VIEWERS = "/viewers";

	public static final String API_ENDPOINT_UNIT_VIEWERS = API_ENDPOINT_UNITS + "/{" + PATH_PARAM_UNIT + ":[\\d]+}" + API_ENDPOINT_VIEWERS;

	public static final String API_ENDPOINT_COLONISTS = "/colonists";

	public static final String WEBSOCKET_ENDPOINT_UNIT_VIEWERS = API_PATH_WEBSOCKET + API_ENDPOINT_UNITS + API_ENDPOINT_VIEWERS;

	public static final String API_ERROR = "/error";

}

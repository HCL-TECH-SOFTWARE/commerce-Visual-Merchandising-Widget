/**
	*==================================================
	Copyright [2021] [HCL Technologies]
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	    http://www.apache.org/licenses/LICENSE-2.0

	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
	*==================================================
**/

package com.hcl.commerce.marketing.management.handler;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.json.JSONArray;
import org.apache.commons.json.JSONException;
import org.apache.commons.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.wink.common.http.HttpStatus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibm.commerce.datatype.TypedProperty;
import com.ibm.commerce.exception.ParameterNotFoundException;
import com.ibm.commerce.foundation.rest.resourcehandler.AbstractClassicResourceHandler;
import com.ibm.commerce.rest.classic.core.AbstractConfigBasedClassicHandler;
import com.ibm.commerce.rest.javadoc.ResponseSchema;

@Path("store/{storeId}/updateProductSequence")
public class CatalogRestHandler extends AbstractConfigBasedClassicHandler {
	private static final String CLASSNAME = CatalogRestHandler.class.getName();
	private static final String RESOURCE_NAME = "updateProuctSequence";
	//private static final String UPDATE_PRODUCT_SEQUENCE = "updateProductSequence";
	private static final String CLASS_NAME_PARAMETER_UPDATE_PRODUCT_SEQUENCE = "com.hcl.commerce.marketing.management.commands.UpdateProductSequenceCmd";

	public String store_Id;

	@Override
	public String getResourceName() {
		return RESOURCE_NAME;
	}
	
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_XHTML_XML,MediaType.APPLICATION_ATOM_XML })
	@ResponseSchema(parameterGroup = RESOURCE_NAME, responseCodes = {
			@com.ibm.commerce.rest.javadoc.ResponseCode(code = 200, reason = "The requested completed successfully."),
			@com.ibm.commerce.rest.javadoc.ResponseCode(code = 400, reason = "Bad request. Some of the inputs provided to the request aren't valid."),
			@com.ibm.commerce.rest.javadoc.ResponseCode(code = 401, reason = "Not authenticated. The user session isn't valid."),
			@com.ibm.commerce.rest.javadoc.ResponseCode(code = 403, reason = "The user isn't authorized to perform the specified request."),
			@com.ibm.commerce.rest.javadoc.ResponseCode(code = 404, reason = "The specified resource couldn't be found."),
			@com.ibm.commerce.rest.javadoc.ResponseCode(code = 500, reason = "Internal server error. Additional details will be contained on the server logs.") })
	public Response updateProductSequence(@PathParam("storeId") String storeId,
			@QueryParam(value = "responseFormat") String responseFormat) throws Exception {
		String METHODNAME = "updateProductSequence";
		Response response = null;
		try {
			TypedProperty requestProperties = null;
			requestProperties =	initializeRequestPropertiesFromRequestMap(responseFormat);
			
			if (responseFormat == null)
				responseFormat = "application/json";
			
			response = executeControllerCommandWithContext(storeId, CLASS_NAME_PARAMETER_UPDATE_PRODUCT_SEQUENCE, requestProperties,responseFormat);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	
}

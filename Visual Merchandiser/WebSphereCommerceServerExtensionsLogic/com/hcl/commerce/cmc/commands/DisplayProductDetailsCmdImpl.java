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

package com.hcl.commerce.cmc.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import org.apache.commons.json.JSONArray;
import org.apache.commons.json.JSONObject;

import com.ibm.commerce.command.ControllerCommandImpl;
import com.ibm.commerce.datatype.TypedProperty;
import com.ibm.commerce.base.objects.ServerJDBCHelperBean;
import com.ibm.commerce.exception.ECException;


public class DisplayProductDetailsCmdImpl extends ControllerCommandImpl implements DisplayProductDetailsCmd {

	private static final String CLASS_NAME = DisplayProductDetailsCmdImpl.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	TypedProperty responseProperty = new TypedProperty();
	
	public void performExecute() throws ECException {
		final String METHOD_NAME = "performExecute";
		LOGGER.entering(CLASS_NAME, METHOD_NAME);
		Integer catalogId = 0;
		Integer catalogGroupId;
		Integer storeId = getCommandStoreId();
		Integer limit;
		Integer page;
		Integer offset; //TODO: offset can be implemented as integer (page). offset = (value-1)*limit. -done
		Integer language;//TOD: set langId to -1 as default?
		Integer total_Count = 0;

		try{
			TypedProperty requestProperty = getCommandContext().getRequestProperties();
			catalogGroupId = requestProperty.getInteger("catalogGroupId");
			limit = requestProperty.getInteger("limit"); //can implement an upper limit, but implementing only a lower limit of 10
			page = requestProperty.getInteger("page");//page should start from 1 from front end.
			language = requestProperty.getInteger("langId"); // not implementing any default or validation for language
			offset = (page-1)*limit; 
			
			if(page <= 0){ page = 1; }
			
						
			String catalogSql = "SELECT CATALOG_ID FROM (SELECT A.STOREENT_ID, A.IDENTIFIER, "
					+ "CASE "
					+ "    WHEN C.CATALOG_ID IS NULL THEN D.CATALOG_ID "
					+ "    WHEN D.CATALOG_ID IS NULL THEN C.CATALOG_ID "
					+ "END AS CATALOG_ID, C.MASTERCATALOG "
					+ "FROM STOREENT A "
					+ "LEFT JOIN STORECAT C ON A.STOREENT_ID = C.STOREENT_ID AND C.MASTERCATALOG = 1 "
					+ "LEFT JOIN STOREDEFCAT D ON A.STOREENT_ID = D.STOREENT_ID "
					+ "WHERE C.CATALOG_ID IS NOT NULL OR D.CATALOG_ID IS NOT NULL WITH UR) WHERE STOREENT_ID = ?";


			try {
				List catalogDetails =  new ServerJDBCHelperBean().executeParameterizedQuery(catalogSql, new Object[] {storeId});
				if(!catalogDetails.isEmpty()) {
					Object catalogObject=  ((Vector<String>) catalogDetails.get(0)).get(0);
					String catalog = catalogObject.toString();
					catalogId = Integer.parseInt(catalog);
				}
			}
			catch(Exception e){
				e.getMessage();
				e.getStackTrace();
			}
			
						
			String sql = "SELECT A.CATALOG_ID, A.CATGROUP_ID, A.CATENTRY_ID, A.SEQUENCE, B.MFNAME, B.BUYABLE, B.URL, B.CATENTTYPE_ID, B.PARTNUMBER, "
					+ "C.NAME, C.SHORTDESCRIPTION, C.THUMBNAIL, C.FULLIMAGE, C.KEYWORD, C.AVAILABILITYDATE, COUNT(*) OVER() AS Total_Count FROM CATGPENREL AS A	"
					+ "INNER JOIN CATENTRY AS B ON B.CATENTRY_ID = A.CATENTRY_ID INNER JOIN CATENTDESC AS C ON C.CATENTRY_ID = A.CATENTRY_ID "
					+ "WHERE A.CATALOG_ID = ? AND A.CATGROUP_ID = ? AND B.CATENTTYPE_ID IN ('ProductBean', 'BundleBean', 'PackageBean') AND C.LANGUAGE_ID = ? LIMIT ? OFFSET ? WITH UR;";
		
			List catalogList = new ServerJDBCHelperBean().executeParameterizedQuery(sql, new Object[] {catalogId, catalogGroupId, language, limit, offset});


			JSONArray jsonArray = getResult(catalogList);
			Map requestMap = new HashMap<String,String>();
			requestMap.put("data", jsonArray);
			if(catalogList != null && catalogList.size() != 0) {
				Vector<String> alist = (Vector<String>) catalogList.get(0);
				requestMap.put("Total_Records", alist.get(15));
			}
			else {
				requestMap.put("Total_Records", 0);
			}
			responseProperty.putAll(requestMap);
			setResponseProperties(responseProperty);
		}
		catch(Exception e){ 
			e.printStackTrace();
		}
		
	}
	
	public JSONArray getResult(List catalogList) {
		JSONArray responseArray = new JSONArray();
		if(catalogList != null &&catalogList.size() >=1) {
			for(int index = 0; index < catalogList.size(); index++){
				try {
					Vector<String> alist =(Vector<String>) catalogList.get(index);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("catalogId", alist.get(0));
					jsonObject.put("catalogGroupId",alist.get(1));
					jsonObject.put("catalogEntryId",alist.get(2));
					jsonObject.put("sequence",alist.get(3));
					jsonObject.put("manufacturer",alist.get(4));
					jsonObject.put("buyable",alist.get(5));
					jsonObject.put("url",alist.get(6));
					jsonObject.put("catenttypeId",alist.get(7));
					jsonObject.put("part_number",alist.get(8));
					jsonObject.put("name",alist.get(9));
					jsonObject.put("short_description",alist.get(10));
					jsonObject.put("thumbnail_path",alist.get(11));
					jsonObject.put("fullimage_path",alist.get(12));
					jsonObject.put("keyword",alist.get(13));
					jsonObject.put("availability_date",alist.get(14));
					responseArray.add(jsonObject);
				}
				catch (Exception e){
					e.getStackTrace();
				}
			}
		}
		return responseArray;
		
	}
	
}

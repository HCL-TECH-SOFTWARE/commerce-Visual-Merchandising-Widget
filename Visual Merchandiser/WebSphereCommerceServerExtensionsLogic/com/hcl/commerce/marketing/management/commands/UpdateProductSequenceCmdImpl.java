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

package com.hcl.commerce.marketing.management.commands;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.commons.json.JSONArray;
import org.apache.commons.json.JSONException;
import org.apache.commons.json.JSONObject;
import com.ibm.commerce.command.ControllerCommandImpl;
import com.ibm.commerce.datatype.CacheRule;
import com.ibm.commerce.datatype.TypedProperty;
import com.ibm.commerce.catalog.objects.CatalogAccessBean;
import com.ibm.commerce.catalog.objects.CatalogGroupCatalogEntryRelationAccessBean;
import com.ibm.commerce.catalog.objects.StoreCatalogAccessBean;
import com.ibm.commerce.exception.ECException;

public class UpdateProductSequenceCmdImpl extends ControllerCommandImpl implements UpdateProductSequenceCmd {
	
	
	private static final String CLASS_NAME = UpdateProductSequenceCmdImpl.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	
	TypedProperty requestProperty = new TypedProperty();
	TypedProperty responseProperty = new TypedProperty();
	
	
	public void performExecute() throws ECException {
		final String METHOD_NAME = "performExecute";
		LOGGER.entering(CLASS_NAME, METHOD_NAME);
		Integer storeId = getCommandContext().getStoreId();
		Long catalogId = 0L;
		Long catalogEntryId= 0L;
		Long catalogGroupId = 0L;
		double sequence;
		try{
			
			requestProperty = getCommandContext().getRequestProperties();
			Map requestPropertyMap = requestProperty.getMap();
			Object requestObject = requestPropertyMap.get("data");
			JSONArray requestArray = (JSONArray)requestObject;
			JSONArray responseArray = new JSONArray();
			Integer masterCatalogFlag = 0;
			
			CatalogAccessBean catalogAccessBean = new CatalogAccessBean();
			Enumeration enumCAB = catalogAccessBean.findMasterCatalogByStoreId(storeId);
			
			while(enumCAB.hasMoreElements()){
				CatalogAccessBean catalogItem = (CatalogAccessBean) enumCAB.nextElement();
				masterCatalogFlag = 1;
			}			
			
			if(masterCatalogFlag == 1) {
				StoreCatalogAccessBean storeCatalogAccessBean = new StoreCatalogAccessBean();
				Enumeration enumStoreDetails = storeCatalogAccessBean.findByStoreId(storeId);
				
				
				List<String> catalogIdList = new ArrayList<>();
				while(enumStoreDetails.hasMoreElements()) {
					StoreCatalogAccessBean catalogItem = (StoreCatalogAccessBean)enumStoreDetails.nextElement();
					String catalogDetails = catalogItem.getCatalogReferenceNumber();
					catalogIdList.add(catalogDetails);
				}
				
				for(int catIter = 0; catIter < catalogIdList.size(); catIter++ ) {
					
					for(int index = 0; index < requestArray.length(); index++){
						JSONObject  jsonObject = new JSONObject();
						jsonObject = requestArray.getJSONObject(index);
						catalogId = Long.parseLong(catalogIdList.get(catIter));
						catalogEntryId = Long.parseLong(jsonObject.getString("catalogEntryId"));
						catalogGroupId = Long.parseLong(jsonObject.getString("catalogGroupId"));
						sequence = jsonObject.getDouble("sequence");
						
						
						CatalogGroupCatalogEntryRelationAccessBean myAccessBean = new CatalogGroupCatalogEntryRelationAccessBean();
						myAccessBean.setInitKey_catalogEntryId(catalogEntryId);
						myAccessBean.setInitKey_catalogGroupId(catalogGroupId);
						myAccessBean.setInitKey_catalogId(catalogId);
						try {
							myAccessBean.setSequence(sequence); //throws Exception
							
							//we can implement myAccessBean.getSequence() compare the value and validate the sequence is set.
							//For now I will leave it as is.
							
							jsonObject.put("IsSuccess", Boolean.TRUE);
							
						}
						catch(Exception e) {
							//TODO - A primary key with the details doesn't exist. Invalid Data.
							//Remove the Array from response.
														
							jsonObject.put("IsSuccess", Boolean.FALSE);
							
						}
						jsonObject.put("catalogId", catalogIdList.get(catIter));
						JSONObject jsonObjectCopy = new JSONObject (jsonObject.toString());
						responseArray.add(index, jsonObjectCopy);
						WCNifiDistributedMapCache(storeId, catalogId, catalogEntryId);
					}
				}
				
				Map requestMap = new HashMap<String,String>();
				requestMap.put("data", responseArray);
				responseProperty.putAll(requestMap);
			
			}
			else {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("Status", "Sequence number can be updated only from master catalog");
				JSONArray arrResponse = new JSONArray(); 
				arrResponse.add(jsonObj)
;				Map requestMap = new HashMap<String,String>();
				requestMap.put("data", arrResponse);
				responseProperty.putAll(requestMap);
			}
			
			
			setResponseProperties(responseProperty);
								
		}
		catch( Exception e){
			//JSONException - if the data array is invalid.
			e.getStackTrace();
		}
	}
	
	public void WCNifiDistributedMapCache(Integer storeId, Long catalogId, Long catalogEntryId){
		List<JSONObject> dataList = new ArrayList<JSONObject>();
		JSONObject cJsonObj = new JSONObject();
		try {
			cJsonObj.put("action", "U");		
			cJsonObj.put("storeId", storeId.toString());
			cJsonObj.put("catalogId", catalogId.toString());
			cJsonObj.put("languageId", "-1");
			cJsonObj.put("nounName", "CatalogEntry");
			cJsonObj.put("workspace", "IBM_WC_BASE");
			cJsonObj.put("taskGroup", "IBM_WC_BASE");
			cJsonObj.put("objectId", catalogEntryId.toString());
			dataList.add(cJsonObj);
			CacheRule.sendInvalidationMessagesNowOnly("services/cache/WCNifiDistributedMapCache", dataList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

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


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.commons.json.JSONArray;
import org.apache.commons.json.JSONObject;
import com.ibm.commerce.command.ControllerCommandImpl;
import com.ibm.commerce.datatype.TypedProperty;
import com.ibm.commerce.catalog.objects.CatalogGroupCatalogEntryRelationAccessBean;
import com.ibm.commerce.exception.ECException;

public class UpdateProductSequenceCmdImpl extends ControllerCommandImpl implements UpdateProductSequenceCmd {
	
	
	private static final String CLASS_NAME = UpdateProductSequenceCmdImpl.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	
	TypedProperty requestProperty = new TypedProperty();
	TypedProperty responseProperty = new TypedProperty();
	
	
	public void performExecute() throws ECException {
		final String METHOD_NAME = "performExecute";
		LOGGER.entering(CLASS_NAME, METHOD_NAME);
		Long catalogId;
		Long catalogEntryId;
		Long catalogGroupId;
		double sequence;
		try{
			
			requestProperty = getCommandContext().getRequestProperties();
			Map requestPropertyMap = requestProperty.getMap();
			Object requestObject = requestPropertyMap.get("data");
			JSONArray requestArray = (JSONArray)requestObject;
			JSONArray responseArray = new JSONArray();
			
			for(int index = 0; index < requestArray.length(); index++){
				JSONObject jsonObject = requestArray.getJSONObject(index);
				catalogId = Long.parseLong(jsonObject.getString("catalogId"));
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
					responseArray.add(index, jsonObject);
				}
				catch(Exception e) {
					//TODO - A primary key with the details doesn't exist. Invalid Data.
					//Remove the Array from response.
					//duplicateArray.remove(index);
					jsonObject.put("IsSuccess", Boolean.FALSE);
					responseArray.add(index, jsonObject);
				}
			}
			
			Map requestMap = new HashMap<String,String>();
			requestMap.put("data", responseArray);
			responseProperty.putAll(requestMap);
			
			setResponseProperties(responseProperty);
			
			
		}
		catch( Exception e){
			//JSONException - if the data array is invalid.
			e.getStackTrace();
		}
	}
	
	
}

/**
	*==================================================
	Copyright [2022] [HCL America, Inc.]
	
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
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService as __BaseService } from '../base-service';
import { ApiConfiguration as __Configuration } from '../api-configuration';
import { StrictHttpResponse as __StrictHttpResponse } from '../strict-http-response';
import { Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
class MerchandiserService extends __BaseService {


  constructor(
    config: __Configuration,
    http: HttpClient
  ) {
    super(config, http);
  }

   /**
   * Get an products by CatalogGroupId,storeId.
   * @param id The unique value  for identifying the catalog category.
   * @param storeId The unique for the store id
   * @param limit the Offset limit
   * @param page current page
   * @return The requested completed successfully.
   */
  getCatalogItemsByIdResponse(catalogGroupId: string,storeId:string,limit:number,page:number): __Observable<__StrictHttpResponse<any>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/wcs/resources/store/${storeId}/productDetails?catalogGroupId=${catalogGroupId}&limit=${limit}&page=${page}&langId=-1`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<any>;
      })
    );
  }

   /**
   * Get an products by CatalogGroupId,storeId.
   * @param id The unique value  for identifying the catalog category.
   * @param storeId The unique for the store id
   * @param limit the Offset limit
   * @param page current page
   * @return The requested completed successfully.
   */
  getCatalogItemsById(catalogGroupId: string,storeId:string,limit:number,page:number): __Observable<any> {
    return this.getCatalogItemsByIdResponse(catalogGroupId,storeId,limit,page).pipe(
      __map(_r => _r.body as any)
    );
  }
  /**
   * Update the product sequence by storeId.
   * @param storeId The unique for the store id
   * @param body 
   * @return The requested completed successfully.
   */
  updateSequenceResponse(storeId,body): __Observable<__StrictHttpResponse<null>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/wcs/resources/store/${storeId}/updateProductSequence`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<null>;
      })
    );
  }

  /**
   * Update the product sequence.
   * @param
   *
   * - `storeId`: The unique numeric ID for identifying the store.
   *
   * - `body`: Request body.
   */
  updateSequence(storeId,body): __Observable<null> {
    return this.updateSequenceResponse(storeId,body).pipe(
      __map(_r => _r.body as null)
    );
  }
  
}


module MerchandiserService {}

export { MerchandiserService }

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

import { HttpClient } from '@angular/common/http';
import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { ActivatedRoute } from '@angular/router';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { ConfirmDialogComponent, ConfirmDialogModel } from '../confirm-dialog/confirm-dialog.component';
import { LoadItemsDialogComponent, LoadItemsDialogModel } from '../load-items-dialog/load-items-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { MerchandiserService } from '../../../../rest/services/merchandiser.service';
import { SegmentService } from '../../../../rest/services/segment.service';



@Component({
  templateUrl: "./merchandising-widget.component.html",
  styleUrls: ["./merchandising-widget.component.scss"]
})
export class MerchandisingWidgetComponent implements OnInit {
  public categoryName = null;
  public products = [];
  public isChecked = false;
  origData: Object;
  itemsTable: Array<number[]>;
  boxWidth = 220;
  columnSize: number;
  loadLimit: string = "";
  storeId: string = "";
  categoryId: string = "";

  // MatPaginator Inputs
  length = 0;
  currentPage = 1;
  pageSize = 10;
  previousPageSize = 10;
  pageSizeOptions: number[] = [5, 10, 25, 50, 100];

  // MatPaginator Output
  pageEvent: PageEvent;

  @ViewChild('paginator', { static: false }) paginator: MatPaginator;

  constructor(private merchandiserService: MerchandiserService, private segmentService: SegmentService, private activatedRoute: ActivatedRoute, private http: HttpClient, public dialog: MatDialog) { }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(params => {
      this.categoryName = params['categoryName'];
      this.categoryId = params['categoryId'];
      this.storeId = params['storeId'];
       this.showLoadItemDialog();
    });
  }

  showLoadItemDialog() {
    const message = `How many records do you want to load on page?`;
    const dialogData = new LoadItemsDialogModel("Load Items Action", message);

    const dialogRef = this.dialog.open(LoadItemsDialogComponent, {
      maxWidth: "400px",
      data: dialogData,
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(dialogResult => {
      //get result as yes or no
      if (dialogResult) {
        //this.pageSize = dialogResult;
       // this.previousPageSize = dialogResult;
        console.log("data-", this.loadLimit);
       // this.currentPage = 1;
        this.setPaginationOptions(dialogResult,dialogResult,1);
        this.getProductData(1);
      }
    });
  }
  setPaginationOptions(limit,previouslimit,currentPage){
    this.pageSize = limit;
    this.previousPageSize = previouslimit;
     this.currentPage = currentPage;
    
  }
 getCatalogItems(categoryId,storeId,limit,offset){
     return this.merchandiserService.getCatalogItemsById(categoryId, storeId, limit, offset);
 }

  getProductData(curPage) {
    this.getCatalogItems(this.categoryId, this.storeId, this.pageSize, this.currentPage).subscribe(orderItems => {
      orderItems["data"] = orderItems["data"].sort((a, b) => {
        if (a.sequence < b.sequence) return -1;
        if (a.sequence > b.sequence) return 1;
        return 0;
      });
      console.log("data", orderItems);
      this.origData = JSON.parse(JSON.stringify(orderItems));
      this.products = orderItems["data"];
      this.length = orderItems["Total_Records"];
      this.initTable();
    });

  }

  toggleDragndrop(event) {
    this.isChecked = !this.isChecked;
  }

  loadDummyImage(event) {
    event.target.src = 'https://kubalubra.is/wp-content/uploads/2017/11/default-thumbnail.jpg';
  }

  getItemsTable(rowLayout: Element) {
    const { width } = rowLayout.getBoundingClientRect();
    const columnSize = Math.round(width / this.boxWidth);
    if (columnSize != this.columnSize) {
      this.columnSize = columnSize;
      this.initTable();
    }
    return this.itemsTable;
  }

  initTable() {
    console.log("latest data--", this.products);
    this.itemsTable = this.products
      .filter((_, outerIndex) => outerIndex % this.columnSize == 0)
      .map((
        _,
        rowIndex
      ) =>
        this.products.slice(
          rowIndex * this.columnSize,
          rowIndex * this.columnSize + this.columnSize
        )
      );
  }

  onDragMoved(event) {
    console.log("event x-", event.delta.x, event.delta.y);
    if (event.delta.x === 1) {
      document.getElementById('container').scrollTop += 3;
    } else if (event.delta.x === -1) {
      document.getElementById('container').scrollTop -= 3;
    }
  }

  reorderDroppedItem(event: CdkDragDrop<number[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
    }

    this.products = this.itemsTable.reduce(
      (previous, current) => previous.concat(current),
      []
    );
    this.updateSequence(event.item.data);
    this.initTable();
  }

  updateSequence(dataobj) {
    let index = this.products.map(val => val.sequence).indexOf(dataobj.sequence);
    console.log("index--", index);
    let prev = parseFloat(index !== 0 ? this.products[index - 1].sequence : 0);
    let next = parseFloat(index !== this.products.length - 1 ? this.products[index + 1].sequence : this.products.length + 1);
    let avg = ((prev + next) / 2).toFixed(2);
    if (this.products[index].sequence !== avg.toString()) {
      this.products[index].sequence = avg.toString();
      this.products[index]['isChanged'] = true;
    }
  }

  onPaginateChange(event) {
    console.log("Current page index: ", event);
    if (this.isproductArrayChanged()) {
      this.checkToggleSaved(event);
    }else{
         this.setPaginationOptions(event.pageSize,event.pageSize,event.pageIndex+1);
    this.getProductData(event.pageIndex+1);
    }

  }
  

  checkToggleSaved(paginationData) {
    let { previousPageIndex, pageSize, pageIndex } = paginationData;
    const message = `Do you want to save your changes before proceeding to next page?`;
    const dialogData = new ConfirmDialogModel("Confirm Action", message);

    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      maxWidth: "400px",
      data: dialogData,
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(dialogResult => {
      //get result as yes or no
      //   this.res = dialogResult;
      console.log("close dialog--", dialogResult, pageIndex);
      if (dialogResult) {
        this.saveDataAndGoToNextPage(pageIndex + 1, pageSize);

      } else {
        console.log("previous --", this.previousPageSize);
        //this.pageSize = this.previousPageSize;
        this.setPaginationOptions(this.previousPageSize,this.previousPageSize,previousPageIndex);
        this.paginator.pageSize = this.previousPageSize;
        this.paginator.pageIndex = previousPageIndex;
      }
    });
  }
  goToNextPage(curPage) {
    this.getProductData(curPage);

  }
  saveDataAndGoToNextPage(curPage, pageSize) {
    this.saveProduct().subscribe(data => {
      console.log("saved data--", data);
      this.setPaginationOptions(pageSize,pageSize,curPage);
      this.getProductData(curPage);

    })
  }
  saveData() {
    this.saveProduct().subscribe(data => {
      console.log("saved data--", data);
      this.getProductData(this.currentPage);
    })
  }
  saveProduct() {
    if (this.isproductArrayChanged()) {
      let dataArr = (this.products.filter(data => data.isChanged === true)).map(val => {
        let { catalogEntryId, catalogGroupId, catalogId, sequence } = val;
        return { catalogEntryId, catalogGroupId, catalogId, sequence: parseFloat(sequence) };
      });
      console.log("Array that needs to be saved: ", dataArr);
      return this.merchandiserService.updateSequence(this.storeId, { "data": dataArr });

    }
  }

  isproductArrayChanged() {
    return (this.products.filter(data => data.isChanged === true)).length > 0;
  }

  enableSaveResetButton() {
    return !this.isproductArrayChanged();
  }

  resetData() {
    let tempArr = JSON.parse(JSON.stringify(this.origData['data']));
    console.log("original data object -- ", this.origData['data']);
    console.log("original data object -- ", this.products);
    this.products = tempArr;
    this.initTable();
  }
}


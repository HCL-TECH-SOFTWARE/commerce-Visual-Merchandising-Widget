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
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-load-items-dialog',
  templateUrl: './load-items-dialog.html',
  styleUrls: ['./load-items-dialog.scss']
})
export class LoadItemsDialogComponent implements OnInit {
  title: string;
  message: string;
  selected:string;

  constructor(public dialogRef: MatDialogRef<LoadItemsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: LoadItemsDialogModel) {
    // Update view with given values
    this.title = data.title;
    this.message = data.message;
  }

  ngOnInit() {
  }

  onConfirm(data): void {
    // Close the dialog, return true
    this.dialogRef.close(data);
  }

  onDismiss(): void {
    // Close the dialog, return false
    this.dialogRef.close(false);
  }
}

/**
 * Class to represent confirm dialog model.
 */
export class LoadItemsDialogModel {

  constructor(public title: string, public message: string) {
  }
}

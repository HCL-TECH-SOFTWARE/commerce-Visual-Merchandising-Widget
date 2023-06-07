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

import { NgModule } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { TranslateModule } from "@ngx-translate/core";
import { MatSelectModule } from "@angular/material/select";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogModule } from "@angular/material/dialog";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatInputModule } from "@angular/material/input";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { ScrollingModule } from "@angular/cdk/scrolling";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatListModule } from "@angular/material/list";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatButtonToggleModule } from "@angular/material/button-toggle";
import { MatTooltipModule } from "@angular/material/tooltip";

import { MerchandisingWidgetRoutingModule } from './merchandising-widget-routing.module';
import { MerchandisingWidgetComponent } from './components/merchandising-widget/merchandising-widget.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { DragDropModule } from '@angular/cdk/drag-drop';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatCardModule} from '@angular/material/card';
import { ConfirmDialogComponent } from './components/confirm-dialog/confirm-dialog.component';
import { LoadItemsDialogComponent } from './components/load-items-dialog/load-items-dialog.component';


@NgModule({
	declarations: [
		MerchandisingWidgetComponent,
		ConfirmDialogComponent,
		LoadItemsDialogComponent
	],
	entryComponents: [
		MerchandisingWidgetComponent,
		ConfirmDialogComponent,
		LoadItemsDialogComponent
	],
	imports: [
		CommonModule,
		MatSelectModule,
		MatIconModule,
		MatButtonModule,
		TranslateModule,
		MatDialogModule,
		MatDatepickerModule,
		MatInputModule,
		MatSlideToggleModule,
		ScrollingModule,
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		MatListModule,
		MatGridListModule,
		MatCheckboxModule,
		MatButtonToggleModule,
		MatTooltipModule,
		MerchandisingWidgetRoutingModule,
		DragDropModule,
		MatPaginatorModule,
		MatCardModule
		// FlexLayoutModule
	],
	providers: [
		DatePipe
	]
})
export class MerchandisingWidgetModule { }

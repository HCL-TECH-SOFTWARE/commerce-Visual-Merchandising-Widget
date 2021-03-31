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

import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

const routes: Routes = [
	{
		path: "organizations",
		loadChildren: "./features/organizations/organizations.module#OrganizationsModule"
	},
	{
		path: "users",
		loadChildren: "./features/user-management/user-management.module#UserManagementModule"
	},
	{
		path: "store-preview",
		loadChildren: "./features/store-preview/store-preview.module#StorePreviewModule"
	},
 	{
 		path: "approvals",
 		loadChildren: "./features/approvals/approvals.module#ApprovalsModule"
 	},
 	{
 		path: "member-groups",
 		loadChildren: "./features/member-groups/member-groups.module#MemberGroupsModule"
	 },
	 {
		path: "accounts",
		loadChildren: "./features/accounts/accounts.module#AccountsModule"
	},
	{
		path: "contracts",
		loadChildren: "./features/contracts/contracts.module#ContractsModule"
	},
	{
		path: "extended-sites",
		loadChildren: "./features/extended-sites/extended-sites.module#ExtendedSitesModule"
	},
	{
		path: "scheduler",
		loadChildren: "./features/scheduler/scheduler.module#SchedulerModule"
 	},
 	{
		path: "messages",
		loadChildren: "./features/messages/messages.module#MessagesModule"
	},
	{
		path: "registries",
		loadChildren: "./features/registries/registries.module#RegistriesModule"
	},
	{
		path: "shipping-jurisdictions",
		loadChildren: "./features/shipping-jurisdictions/shipping-jurisdictions.module#ShippingJurisdictionsModule"
	},
	{
		path: "shipping-modes",
		loadChildren: "./features/shipping-modes/shipping-modes.module#ShippingModesModule"
	},
	{
		path: "shipping-codes",
		loadChildren: "./features/shipping-codes/shipping-codes.module#ShippingCodesModule"
	},
	{
		path: "shipping-charges",
		loadChildren: "./features/shipping-charges/shipping-charges.module#ShippingChargesModule"
	},
	{
		path: "security-policies",
		loadChildren: "./features/security-policies/security-policies.module#SecurityPoliciesModule"
 	},
	{
 		path: "tax-categories",
		loadChildren: "./features/tax-categories/tax-categories.module#TaxCategoriesModule"
	},
	{
		path: "tax-jurisdictions",
		loadChildren: "./features/tax-jurisdictions/tax-jurisdictions.module#TaxJurisdictionsModule"
	},
	{
		path: "tax-codes",
		loadChildren: "./features/tax-codes/tax-codes.module#TaxCodesModule"
	},
	/*Merchandising widget*/
	{
		path:"merchandising-widget",
		loadChildren: "./features/merchandising-widget/merchandising-widget.module#MerchandisingWidgetModule"
	}
	/*Merchandising widget*/
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }

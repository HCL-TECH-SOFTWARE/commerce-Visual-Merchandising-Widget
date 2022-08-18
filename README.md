# Merchandising-Widget

## WARRANTY & SUPPORT 
HCL Software provides HCL Commerce open source assets “as-is” without obligation to support them nor warranties or any kind, either express or implied, including the warranty of title, non-infringement or non-interference, and the implied warranties and conditions of merchantability and fitness for a particular purpose. HCL Commerce open source assets are not covered under the HCL Commerce master license nor Support contracts.

If you have questions or encounter problems with an HCL Commerce open source asset, please open an issue in the asset's GitHub repository. For more information about [GitHub issues](https://docs.github.com/en/issues), including creating an issue, please refer to [GitHub Docs](https://docs.github.com/en). The HCL Commerce Innovation Factory Team, who develops HCL Commerce open source assets, monitors GitHub issues and will do their best to address them. 

## HCLC Merchandising Widget Asset

**Proof of concept to demonstrate updating sequence number of products in CMC.
The GET and POST Rest API's will be called in the drag and drop functionality of react store**

**Prerequisites:** HCL Commerce V9.1.x / HCL Commerce React Storefront SDK

**The implementation includes below components**
1. REST API to GET and POST catalog details.
2. UI changes in tooling-web and lobtools.

### Step-1 REST API

1. Copy & paste the "com" folder under "WebSphereCommerceServersExtensionLogic" in GIT to "WebSphereCommerceServersExtensionLogic" in RAD.
2. Copy & paste the two xml files under dataload in GIT to WCDE_V9/xml/policies/xml. Run acpload.bat for the two config files.
   C:\WCDE_V9\bin>acpload.bat C:\WCDE_V9/xml/policies/xml/CMCProductAccessControlPolicies.xml
   C:\WCDE_V9\bin>acpload.bat C:\WCDE_V9/xml/policies/xml/CategoryControlPolicies.xml
3. Copy & paste (append) the contents of "REST/WEB-INF/config" resources-ext.properties in GIT to resources-ext.properties in RAD.

### Step-2 UI changes

 Please refer the `README.md` file for the instructions to implement the UI

# Merchandising-Widget

**Proof of concept to demonstrate updating sequence number of products in CMC.
The GET and POST Rest API's will be called in the drag and drop functionality of react store**

**DISCLAIMER:  This Proof of Concept asset is being provided as-is to help accelerate your projects.
We have provided documentation as well as any needed code artifacts for your use**

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

# Merchandising-Widget

**Proof of concept to demonstrate updating sequence number of products in catalog.
The GET and POST Rest API's will be called in the drag and drop functionality of react store**

**DISCLAIMER:  This Proof of Concept asset is being provided as-is to help accelerate your projects.
We have provided documentation as well as any needed code artifacts for your use**

**Prerequisites:** HCL Commerce V9.1.x / HCL Commerce React Storefront SDK

**The implemntation includes below components**
1. REST API to GET and POST catalog details.
2. Drag and Drop functionality.

### Step-1 REST API

1. Copy & paste the "com" folder under "WebSphereCommerceServersExtensionLogic" in GIT to "WebSphereCommerceServersExtensionLogic" in RAD.
2. Copy & paste the two xml files under dataload in GIT to WCDE_V9/xml/policies/xml. Run acpload.bat for the two config files.
3. Copy & paste (append) the contents of "REST/WEB-INF/config" resources-ext.properties in GIT to resources-ext.properties in RAD.
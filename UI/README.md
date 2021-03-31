# Merchandiser Widget

**The changes for the merchandiser widget are present in below modules**
 
 1. **Tooling-web** - Merchandiser widget
 2. **Lobtools** - Added button "Resequence", open dialog where merchandiser widget will get rendered and on close of dialog refresh the listview page.


## Tooling-web
  
  Follow below steps to implement the merchandiser widget UI.
  
  1. Add the `merchandising-widget` folder in the path `commerce-tooling/src/app/features`.It has all the components required for the widget.
  2. Add the `merchandiser.service.ts` file in the path `commerce-tooling/src/app/rest/service`. It has the Rest APIs details used for merchandiser widget.
  3. Add the below entry in the `app-routing.module.ts` file placed inside `commerce-tooling/src/app`.
      ```ruby
        /*Merchandising widget*/
        {
          path:"merchandising-widget",
          loadChildren: "./features/merchandising-widget/merchandising-widget.module#MerchandisingWidgetModule"
        }
        /*Merchandising widget*/
        
        ```
  
 ## lobtools
 
 Add the code block placed between `<!-- Merchandising Widget Work-->` comments in the `CatalogEntryGrid.xml` file

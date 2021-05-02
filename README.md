# pdf4everyone
Simple microservice for generating screehshots or pdf from some website.

### API
`GET` `/api/export/screehshot`

`GET` `/api/export/pdf`


#### Query Parameters
`url` _(required)_ url to open

`indicatorVariable` _(optional)_ global variable, which indicates that page is loaded. 
E.g. `document.pageLoaded` (webpage should set `document.pageLoaded = true` to make this work)

`delaySeconds` _(default = 0)_ additional delay after the page is loaded (executed after _indicatorVariable_ if it's provided)

`width` _(required)_ browser width 

`height` _(required)_ browser height 
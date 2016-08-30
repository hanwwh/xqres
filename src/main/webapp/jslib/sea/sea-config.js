/***
 *   通用引入js 文件 
 * 
 */

seajs.config({
	base : _ctx + "/jslib/",
	alias : {
		"jquery" : "jquery/jquery-3.1.0.min.js",
		"jqueryUI": "jquery/jqueryUI/jquery-ui.min.js" ,
		"jqueryExtend" : "jquery/jquery.extend.js",
		"jqueryJSON" : "jquery/jquery.json.min.js",
		"backbone" : "backbone/backbone-min.js",
		"underscore" : "backbone/underscore-min.js",
		"less" : "less/less.min.js",
		"ejs" : "ejs/ejs_production.js",
		"bootstrap" : "bootstrap/dist/js/bootstrap.min.js",
		"My97DatePicker" : "My97DatePicker/WdatePicker.js",
		"ajaxFileUpload" : "jquery/ajaxfileupload.js",
		"tipped" : "tipped/tipped.js",
		"common" : "ext/common.js"
	},
	preload : [ "jquery", "ejs", "less" ,"tipped","underscore","common"]
});

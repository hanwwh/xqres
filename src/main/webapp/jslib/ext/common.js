//一些系统常用方法

/************************************
 * 将jquery对象集转换成json字符串
 * 可以是form，可以是其他多个对象
 * @param obj
 */
$.fn.tojson = function(){
    var jsonObj = '';
    $(this.serializeArray()).each(function(i, v){
        // alert(v.name+" = "+v.value);
        var jsonstr = '"'+v.name+'" : "'+v.value+'"';
        // alert(jsonstr);
        if(i == 0)
            jsonObj = jsonstr;
        else
            jsonObj = jsonObj+", "+jsonstr;
        // jsonObj[v.name] = v.value;
    });

    return "{"+jsonObj+"}";
}

$.fn.serializeJson = function(){
    var serializeObj={};
    var array=this.serializeArray();
    var str=this.serialize();

    $(array).each(function(){
        // alert(this.name+" = "+this.value);
        if(serializeObj[this.name]){
            if($.isArray(serializeObj[this.name])){
                serializeObj[this.name].push(this.value);
            }else{
                serializeObj[this.name]=[serializeObj[this.name],this.value];
            }
        }else{
            serializeObj[this.name]=this.value;
        }
    });

    return $.param(serializeObj, true);
}
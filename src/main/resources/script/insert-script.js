function createDynamicScript(option){
    var script = document.createElement("script");
    script.type = "text/javascript";
    // console.log("option.isCreateScriptBySrc:", option.isCreateScriptBySrc);
    try{
        if((option.isCreateScriptBySrc == undefined) || (!!(option.isCreateScriptBySrc) == true)){
            script.src = option.src;       
        } else {
            script.text = option.text;
        }
        document.body.appendChild(script);
    }catch(error){
        console.error("#dynamicScript: load script failed!");  
    }
}

createDynamicScript({ 
    "type":"text/javascript", 
    "src":"http://localhost:9090/baiduIndex/resolveBaiduIndexByJs.js",
    "text":"function log(){ console.log(\"created log()\"); }; log();", 
    "isCreateScriptBySrc":true
});

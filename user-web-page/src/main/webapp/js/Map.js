//自定义Map
function Map(){
	this.container={};
}
//添加键值对：put
Map.prototype.put=function(key,value){    
	try{    
	    if(key!=null && key != ""){
	    	if(!this.containsKey(key)){
		       this.container[key] = value;    
	    	}
	    }   
	}catch(e){    
		return e;    
	}    
};
//通过键获取值：get
Map.prototype.get=function(key){    
	try{    
		return this.container[key];    
	}catch(e){    
		return e;    
	}    
};  
//在Map中是否存在指定键：containsKey
Map.prototype.containsKey=function(key){   
	try{   
		for(var p in this.container){   
			if(p==key){
				return true;   
			}   
		}   
		return false;   
	}catch(e){   
		return e;   
	}   
};
//在Map中是否包含指定的value：containsValue   
Map.prototype.containsValue=function(value){    
	try{    
		for(var p in this.container){    
		    if(this.container[p]===value){
		    	return true;    
		    }    
		}    
   		return false;    
	}catch(e){    
		return e;    
	}    
};    
//删除Map中指定的key：remove   
Map.prototype.remove=function(key){    
	try{    
		delete this.container[key];    
	}catch(e){    
		return e;    
	}    
};    
//清空Map：clear   
Map.prototype.clear=function(){    
	try{    
		delete this.container;    
		this.container={};    
	}catch(e){    
		return e;    
	}    
};    
//判断Map是否为空：isEmpty   
Map.prototype.isEmpty=function(){    
	if(this.keyArray().length==0){
		return true;   
	}else{
		return false;   
	}    
};    
//获取Map的大小：size   
Map.prototype.size=function(){   
    return this.keyArray().length;   
};   
//返回Map中的key值数组:keyArray   
Map.prototype.keyArray=function(){   
	var keys=new Array();   
	for(var p in this.container){   
		keys.push(p);   
	}   
	return keys;   
};   
//返回Map中的value值数组:valueArray   
Map.prototype.valueArray=function(){   
	var values=new Array();   
	var keys=this.keyArray();   
	for(var i=0;i<keys.length;i++){   
		values.push(this.container[keys[i]]);   
	}   
	return values;   
};  
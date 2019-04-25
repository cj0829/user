var placeholder=new Map();
//取消占位符
function cancelPlaceholder(tag){
	placeholder.put(tag.id,tag.value);
	var color=tag.style.color;
	if("gray"==color){
		tag.style.color="white";
		tag.value="";
	}
}
//显示占位符
function showPlaceholder(tag){
	if(tag.value.replace(/\s/g,"")==""){
		tag.style.color="gray";
		tag.value=placeholder.get(tag.id);
	}
}
//验证占位符
function validPlaceholder(tag){
	if("gray"==tag.style.color){
		return true;
	}
	return false;
}
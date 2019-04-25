///import core
///plugin 编辑器默认的过滤转换机制

UE.plugins['defaultfilter'] = function () {
    var me = this;
    me.setOpt({
        'allowDivTransToP':true,
        'disabledTableInTable':true,
        'rgb2Hex':true
    });
    //默认的过滤处理
    //进入编辑器的内容处理
    me.addInputRule(function (root) {
        var allowDivTransToP = this.options.allowDivTransToP;
        var val;
        function tdParent(node){
            while(node && node.type == 'element'){
                if(node.tagName == 'td'){
                    return true;
                }
                node = node.parentNode;
            }
            return false;
        }
        //进行默认的处理
        root.traversal(function (node) {
            if (node.type == 'element') {
                if (!dtd.$cdata[node.tagName] && me.options.autoClearEmptyNode && dtd.$inline[node.tagName] && !dtd.$empty[node.tagName] && (!node.attrs || utils.isEmptyObject(node.attrs))) {
                    if (!node.firstChild()) node.parentNode.removeChild(node);
                    else if (node.tagName == 'span' && (!node.attrs || utils.isEmptyObject(node.attrs))) {
                        node.parentNode.removeChild(node, true)
                    }
                    return;
                }
                switch (node.tagName) {
                    case 'style':
                    case 'script':
                        node.setAttr({
                            cdata_tag: node.tagName,
                            cdata_data: (node.innerHTML() || ''),
                            '_ue_custom_node_':'true'
                        });
                        node.tagName = 'div';
                        node.innerHTML('');
                        break;
                    case 'a':
                        if (val = node.getAttr('href')) {
                            node.setAttr('_href', val)
                        }
                        break;
                    case 'img':
                        //todo base64暂时去掉，后边做远程图片上传后，干掉这个
                        if (val = node.getAttr('src')) {
                            if (/^data:/.test(val)) {
                                node.parentNode.removeChild(node);
                                break;
                            }
                        }
                        node.setAttr('_src', node.getAttr('src'));
                        break;
                    case 'span':
                        if (browser.webkit && (val = node.getStyle('white-space'))) {
                            if (/nowrap|normal/.test(val)) {
                                node.setStyle('white-space', '');
                                if (me.options.autoClearEmptyNode && utils.isEmptyObject(node.attrs)) {
                                    node.parentNode.removeChild(node, true)
                                }
                            }
                        }
                        val = node.getAttr('id');
                        if(val && /^_baidu_bookmark_/i.test(val)){
                            node.parentNode.removeChild(node)
                        }
                        break;
                    case 'p':
                        if (val = node.getAttr('align')) {
                            node.setAttr('align');
                            node.setStyle('text-align', val)
                        }
                        //trace:3431
//                        var cssStyle = node.getAttr('style');
//                        if (cssStyle) {
//                            cssStyle = cssStyle.replace(/(margin|padding)[^;]+/g, '');
//                            node.setAttr('style', cssStyle)
//
//                        }
                        //p标签不允许嵌套
                        utils.each(node.children,function(n){
                            if(n.type == 'element' && n.tagName == 'p'){
                                var next = n.nextSibling();
                                node.parentNode.insertAfter(n,node);
                                var last = n;
                                while(next){
                                    var tmp = next.nextSibling();
                                    node.parentNode.insertAfter(next,last);
                                    last = next;
                                    next = tmp;
                                }
                                return false;
                            }
                        });
                        if (!node.firstChild()) {
                            node.innerHTML(browser.ie ? '&nbsp;' : '<br/>')
                        }
                        break;
                    case 'div':
                        if(node.getAttr('cdata_tag')){
                            break;
                        }
                        //针对代码这里不处理插入代码的div
                        val = node.getAttr('class');
                        if(val && /^line number\d+/.test(val)){
                            break;
                        }
                        if(!allowDivTransToP){
                            break;
                        }
                        var tmpNode, p = UE.uNode.createElement('p');
                        while (tmpNode = node.firstChild()) {
                            if (tmpNode.type == 'text' || !UE.dom.dtd.$block[tmpNode.tagName]) {
                                p.appendChild(tmpNode);
                            } else {
                                if (p.firstChild()) {
                                    node.parentNode.insertBefore(p, node);
                                    p = UE.uNode.createElement('p');
                                } else {
                                    node.parentNode.insertBefore(tmpNode, node);
                                }
                            }
                        }
                        if (p.firstChild()) {
                            node.parentNode.insertBefore(p, node);
                        }
                        node.parentNode.removeChild(node);
                        break;
                    case 'dl':
                        node.tagName = 'ul';
                        break;
                    case 'dt':
                    case 'dd':
                        node.tagName = 'li';
                        break;
                    case 'li':
                        var className = node.getAttr('class');
                        if (!className || !/list\-/.test(className)) {
                            node.setAttr()
                        }
                        var tmpNodes = node.getNodesByTagName('ol ul');
                        UE.utils.each(tmpNodes, function (n) {
                            node.parentNode.insertAfter(n, node);
                        });
                        break;
                    case 'td':
                    case 'th':
                    case 'caption':
                        if(!node.children || !node.children.length){
                            node.appendChild(browser.ie11below ? UE.uNode.createText(' ') : UE.uNode.createElement('br'))
                        }
                        break;
                    case 'table':
                        if(me.options.disabledTableInTable && tdParent(node)){
                            node.parentNode.insertBefore(UE.uNode.createText(node.innerText()),node);
                            node.parentNode.removeChild(node)
                        }
                }

            }
//            if(node.type == 'comment'){
//                node.parentNode.removeChild(node);
//            }
        })

    });

    //从编辑器出去的内容处理
    me.addOutputRule(function (root) {

        var val;
        root.traversal(function (node) {
            if (node.type == 'element') {

                if (me.options.autoClearEmptyNode && dtd.$inline[node.tagName] && !dtd.$empty[node.tagName] && (!node.attrs || utils.isEmptyObject(node.attrs))) {

                    if (!node.firstChild()) node.parentNode.removeChild(node);
                    else if (node.tagName == 'span' && (!node.attrs || utils.isEmptyObject(node.attrs))) {
                        node.parentNode.removeChild(node, true)
                    }
                    return;
                }
                switch (node.tagName) {
                    case 'div':
                        if (val = node.getAttr('cdata_tag')) {
                            node.tagName = val;
                            node.appendChild(UE.uNode.createText(node.getAttr('cdata_data')));
                            node.setAttr({cdata_tag: '', cdata_data: '','_ue_custom_node_':''});
                        }
                        break;
                    case 'a':
                        if (val = node.getAttr('_href')) {
                            node.setAttr({
                                'href': utils.html(val),
                                '_href': ''
                            })
                        }
                        break;
                        break;
                    case 'span':
                        val = node.getAttr('id');
                        if(val && /^_baidu_bookmark_/i.test(val)){
                            node.parentNode.removeChild(node)
                        }
                        //将color的rgb格式转换为#16进制格式
                        if(me.getOpt('rgb2Hex')){
                            var cssStyle = node.getAttr('style');
                            if(cssStyle){
                                node.setAttr('style',cssStyle.replace(/rgba?\(([\d,\s]+)\)/g,function(a,value){
                                    var array = value.split(",");
                                    if (array.length > 3)
                                        return "";
                                    value = "#";
                                    for (var i = 0, color; color = array[i++];) {
                                        color = parseInt(color.replace(/[^\d]/gi, ''), 10).toString(16);
                                        value += color.length == 1 ? "0" + color : color;
                                    }
                                    return value.toUpperCase();

                                }))
                            }
                        }
                        break;
                    case 'img':
                        if (val = node.getAttr('_src')) {
                            node.setAttr({
                                'src': node.getAttr('_src'),
                                '_src': ''
                            })
                        }


                }
            }

        })


    });
};
/**
 * 插入html字符串插件
 * @file
 * @since 1.2.6.1
 */

/**
 * 插入html代码
 * @command inserthtml
 * @method execCommand
 * @param { String } cmd 命令字符串
 * @param { String } html 插入的html字符串
 * @remaind 插入的标签内容是在当前的选区位置上插入，如果当前是闭合状态，那直接插入内容， 如果当前是选中状态，将先清除当前选中内容后，再做插入
 * @warning 注意:该命令会对当前选区的位置，对插入的内容进行过滤转换处理。 过滤的规则遵循html语意化的原则。
 * @example
 * ```javascript
 * //xxx[BB]xxx 当前选区为非闭合选区，选中BB这两个文本
 * //执行命令，插入<b>CC</b>
 * //插入后的效果 xxx<b>CC</b>xxx
 * //<p>xx|xxx</p> 当前选区为闭合状态
 * //插入<p>CC</p>
 * //结果 <p>xx</p><p>CC</p><p>xxx</p>
 * //<p>xxxx</p>|</p>xxx</p> 当前选区在两个p标签之间
 * //插入 xxxx
 * //结果 <p>xxxx</p><p>xxxx</p></p>xxx</p>
 * ```
 */

UE.commands['inserthtml'] = {
    execCommand: function (command,html,notNeedFilter){
        var me = this,
            range,
            div;
        if(!html){
            return;
        }
        if(me.fireEvent('beforeinserthtml',html) === true){
            return;
        }
        range = me.selection.getRange();
        div = range.document.createElement( 'div' );
        div.style.display = 'inline';

        if (!notNeedFilter) {
            var root = UE.htmlparser(html);
            //如果给了过滤规则就先进行过滤
            if(me.options.filterRules){
                UE.filterNode(root,me.options.filterRules);
            }
            //执行默认的处理
            me.filterInputRule(root);
            html = root.toHtml();
        }
        div.innerHTML = utils.trim( html );

        if ( !range.collapsed ) {
            var tmpNode = range.startContainer;
            if(domUtils.isFillChar(tmpNode)){
                range.setStartBefore(tmpNode);
            }
            tmpNode = range.endContainer;
            if(domUtils.isFillChar(tmpNode)){
                range.setEndAfter(tmpNode);
            }
            range.txtToElmBoundary();
            //结束边界可能放到了br的前边，要把br包含进来
            // x[xxx]<br/>
            if(range.endContainer && range.endContainer.nodeType == 1){
                tmpNode = range.endContainer.childNodes[range.endOffset];
                if(tmpNode && domUtils.isBr(tmpNode)){
                    range.setEndAfter(tmpNode);
                }
            }
            if(range.startOffset == 0){
                tmpNode = range.startContainer;
                if(domUtils.isBoundaryNode(tmpNode,'firstChild') ){
                    tmpNode = range.endContainer;
                    if(range.endOffset == (tmpNode.nodeType == 3 ? tmpNode.nodeValue.length : tmpNode.childNodes.length) && domUtils.isBoundaryNode(tmpNode,'lastChild')){
                        me.body.innerHTML = '<p>'+(browser.ie ? '' : '<br/>')+'</p>';
                        range.setStart(me.body.firstChild,0).collapse(true)

                    }
                }
            }
            !range.collapsed && range.deleteContents();
            if(range.startContainer.nodeType == 1){
                var child = range.startContainer.childNodes[range.startOffset],pre;
                if(child && domUtils.isBlockElm(child) && (pre = child.previousSibling) && domUtils.isBlockElm(pre)){
                    range.setEnd(pre,pre.childNodes.length).collapse();
                    while(child.firstChild){
                        pre.appendChild(child.firstChild);
                    }
                    domUtils.remove(child);
                }
            }

        }


        var child,parent,pre,tmp,hadBreak = 0, nextNode;
        //如果当前位置选中了fillchar要干掉，要不会产生空行
        if(range.inFillChar()){
            child = range.startContainer;
            if(domUtils.isFillChar(child)){
                range.setStartBefore(child).collapse(true);
                domUtils.remove(child);
            }else if(domUtils.isFillChar(child,true)){
                child.nodeValue = child.nodeValue.replace(fillCharReg,'');
                range.startOffset--;
                range.collapsed && range.collapse(true)
            }
        }
        //列表单独处理
        var li = domUtils.findParentByTagName(range.startContainer,'li',true);
        if(li){
            var next,last;
            while(child = div.firstChild){
                //针对hr单独处理一下先
                while(child && (child.nodeType == 3 || !domUtils.isBlockElm(child) || child.tagName=='HR' )){
                    next = child.nextSibling;
                    range.insertNode( child).collapse();
                    last = child;
                    child = next;

                }
                if(child){
                    if(/^(ol|ul)$/i.test(child.tagName)){
                        while(child.firstChild){
                            last = child.firstChild;
                            domUtils.insertAfter(li,child.firstChild);
                            li = li.nextSibling;
                        }
                        domUtils.remove(child)
                    }else{
                        var tmpLi;
                        next = child.nextSibling;
                        tmpLi = me.document.createElement('li');
                        domUtils.insertAfter(li,tmpLi);
                        tmpLi.appendChild(child);
                        last = child;
                        child = next;
                        li = tmpLi;
                    }
                }
            }
            li = domUtils.findParentByTagName(range.startContainer,'li',true);
            if(domUtils.isEmptyBlock(li)){
                domUtils.remove(li)
            }
            if(last){

                range.setStartAfter(last).collapse(true).select(true)
            }
        }else{
            while ( child = div.firstChild ) {
                if(hadBreak){
                    var p = me.document.createElement('p');
                    while(child && (child.nodeType == 3 || !dtd.$block[child.tagName])){
                        nextNode = child.nextSibling;
                        p.appendChild(child);
                        child = nextNode;
                    }
                    if(p.firstChild){

                        child = p
                    }
                }
                range.insertNode( child );
                nextNode = child.nextSibling;
                if ( !hadBreak && child.nodeType == domUtils.NODE_ELEMENT && domUtils.isBlockElm( child ) ){

                    parent = domUtils.findParent( child,function ( node ){ return domUtils.isBlockElm( node ); } );
                    if ( parent && parent.tagName.toLowerCase() != 'body' && !(dtd[parent.tagName][child.nodeName] && child.parentNode === parent)){
                        if(!dtd[parent.tagName][child.nodeName]){
                            pre = parent;
                        }else{
                            tmp = child.parentNode;
                            while (tmp !== parent){
                                pre = tmp;
                                tmp = tmp.parentNode;

                            }
                        }


                        domUtils.breakParent( child, pre || tmp );
                        //去掉break后前一个多余的节点  <p>|<[p> ==> <p></p><div></div><p>|</p>
                        var pre = child.previousSibling;
                        domUtils.trimWhiteTextNode(pre);
                        if(!pre.childNodes.length){
                            domUtils.remove(pre);
                        }
                        //trace:2012,在非ie的情况，切开后剩下的节点有可能不能点入光标添加br占位

                        if(!browser.ie &&
                            (next = child.nextSibling) &&
                            domUtils.isBlockElm(next) &&
                            next.lastChild &&
                            !domUtils.isBr(next.lastChild)){
                            next.appendChild(me.document.createElement('br'));
                        }
                        hadBreak = 1;
                    }
                }
                var next = child.nextSibling;
                if(!div.firstChild && next && domUtils.isBlockElm(next)){

                    range.setStart(next,0).collapse(true);
                    break;
                }
                range.setEndAfter( child ).collapse();

            }

            child = range.startContainer;

            if(nextNode && domUtils.isBr(nextNode)){
                domUtils.remove(nextNode)
            }
            //用chrome可能有空白展位符
            if(domUtils.isBlockElm(child) && domUtils.isEmptyNode(child)){
                if(nextNode = child.nextSibling){
                    domUtils.remove(child);
                    if(nextNode.nodeType == 1 && dtd.$block[nextNode.tagName]){

                        range.setStart(nextNode,0).collapse(true).shrinkBoundary()
                    }
                }else{

                    try{
                        child.innerHTML = browser.ie ? domUtils.fillChar : '<br/>';
                    }catch(e){
                        range.setStartBefore(child);
                        domUtils.remove(child)
                    }

                }

            }
            //加上true因为在删除表情等时会删两次，第一次是删的fillData
            try{
                range.select(true);
            }catch(e){}

        }



        setTimeout(function(){
            range = me.selection.getRange();
            range.scrollToView(me.autoHeightEnabled,me.autoHeightEnabled ? domUtils.getXY(me.iframe).y:0);
            me.fireEvent('afterinserthtml', html);
        },200);
    }
};
/**
 * 图片插入、排版插件
 * @file
 * @since 1.2.6.1
 */

/**
 * 图片对齐方式
 * @command imagefloat
 * @method execCommand
 * @remind 值center为独占一行居中
 * @param { String } cmd 命令字符串
 * @param { String } align 对齐方式，可传left、right、none、center
 * @remaind center表示图片独占一行
 * @example
 * ```javascript
 * editor.execCommand( 'imagefloat', 'center' );
 * ```
 */

/**
 * 如果选区所在位置是图片区域
 * @command imagefloat
 * @method queryCommandValue
 * @param { String } cmd 命令字符串
 * @return { String } 返回图片对齐方式
 * @example
 * ```javascript
 * editor.queryCommandValue( 'imagefloat' );
 * ```
 */

UE.commands['imagefloat'] = {
    execCommand:function (cmd, align) {
        var me = this,
            range = me.selection.getRange();
        if (!range.collapsed) {
            var img = range.getClosedNode();
            if (img && img.tagName == 'IMG') {
                switch (align) {
                    case 'left':
                    case 'right':
                    case 'none':
                        var pN = img.parentNode, tmpNode, pre, next;
                        while (dtd.$inline[pN.tagName] || pN.tagName == 'A') {
                            pN = pN.parentNode;
                        }
                        tmpNode = pN;
                        if (tmpNode.tagName == 'P' && domUtils.getStyle(tmpNode, 'text-align') == 'center') {
                            if (!domUtils.isBody(tmpNode) && domUtils.getChildCount(tmpNode, function (node) {
                                return !domUtils.isBr(node) && !domUtils.isWhitespace(node);
                            }) == 1) {
                                pre = tmpNode.previousSibling;
                                next = tmpNode.nextSibling;
                                if (pre && next && pre.nodeType == 1 && next.nodeType == 1 && pre.tagName == next.tagName && domUtils.isBlockElm(pre)) {
                                    pre.appendChild(tmpNode.firstChild);
                                    while (next.firstChild) {
                                        pre.appendChild(next.firstChild);
                                    }
                                    domUtils.remove(tmpNode);
                                    domUtils.remove(next);
                                } else {
                                    domUtils.setStyle(tmpNode, 'text-align', '');
                                }


                            }

                            range.selectNode(img).select();
                        }
                        domUtils.setStyle(img, 'float', align == 'none' ? '' : align);
                        if(align == 'none'){
                            domUtils.removeAttributes(img,'align');
                        }

                        break;
                    case 'center':
                        if (me.queryCommandValue('imagefloat') != 'center') {
                            pN = img.parentNode;
                            domUtils.setStyle(img, 'float', '');
                            domUtils.removeAttributes(img,'align');
                            tmpNode = img;
                            while (pN && domUtils.getChildCount(pN, function (node) {
                                return !domUtils.isBr(node) && !domUtils.isWhitespace(node);
                            }) == 1
                                && (dtd.$inline[pN.tagName] || pN.tagName == 'A')) {
                                tmpNode = pN;
                                pN = pN.parentNode;
                            }
                            range.setStartBefore(tmpNode).setCursor(false);
                            pN = me.document.createElement('div');
                            pN.appendChild(tmpNode);
                            domUtils.setStyle(tmpNode, 'float', '');

                            me.execCommand('insertHtml', '<p id="_img_parent_tmp" style="text-align:center">' + pN.innerHTML + '</p>');

                            tmpNode = me.document.getElementById('_img_parent_tmp');
                            tmpNode.removeAttribute('id');
                            tmpNode = tmpNode.firstChild;
                            range.selectNode(tmpNode).select();
                            //去掉后边多余的元素
                            next = tmpNode.parentNode.nextSibling;
                            if (next && domUtils.isEmptyNode(next)) {
                                domUtils.remove(next);
                            }

                        }

                        break;
                }

            }
        }
    },
    queryCommandValue:function () {
        var range = this.selection.getRange(),
            startNode, floatStyle;
        if (range.collapsed) {
            return 'none';
        }
        startNode = range.getClosedNode();
        if (startNode && startNode.nodeType == 1 && startNode.tagName == 'IMG') {
            floatStyle = domUtils.getComputedStyle(startNode, 'float') || startNode.getAttribute('align');

            if (floatStyle == 'none') {
                floatStyle = domUtils.getComputedStyle(startNode.parentNode, 'text-align') == 'center' ? 'center' : floatStyle;
            }
            return {
                left:1,
                right:1,
                center:1
            }[floatStyle] ? floatStyle : 'none';
        }
        return 'none';


    },
    queryCommandState:function () {
        var range = this.selection.getRange(),
            startNode;

        if (range.collapsed)  return -1;

        startNode = range.getClosedNode();
        if (startNode && startNode.nodeType == 1 && startNode.tagName == 'IMG') {
            return 0;
        }
        return -1;
    }
};


/**
 * 插入图片
 * @command insertimage
 * @method execCommand
 * @param { String } cmd 命令字符串
 * @param { Object } opt 属性键值对，这些属性都将被复制到当前插入图片
 * @remind 该命令第二个参数可接受一个图片配置项对象的数组，可以插入多张图片，
 * 此时数组的每一个元素都是一个Object类型的图片属性集合。
 * @example
 * ```javascript
 * editor.execCommand( 'insertimage', {
 *     src:'a/b/c.jpg',
 *     width:'100',
 *     height:'100'
 * } );
 * ```
 * @example
 * ```javascript
 * editor.execCommand( 'insertimage', [{
 *     src:'a/b/c.jpg',
 *     width:'100',
 *     height:'100'
 * },{
 *     src:'a/b/d.jpg',
 *     width:'100',
 *     height:'100'
 * }] );
 * ```
 */

UE.commands['insertimage'] = {
    execCommand:function (cmd, opt) {

        opt = utils.isArray(opt) ? opt : [opt];
        if (!opt.length) {
            return;
        }
        var me = this,
            range = me.selection.getRange(),
            img = range.getClosedNode();

        if(me.fireEvent('beforeinsertimage', opt) === true){
            return;
        }

        if (img && /img/i.test(img.tagName) && (img.className != "edui-faked-video" || img.className.indexOf("edui-upload-video")!=-1 || img.className.indexOf("edui-object-video")!=-1) && !img.getAttribute("word_img")) {
            var first = opt.shift();
            var floatStyle = first['floatStyle'];
            delete first['floatStyle'];
////                img.style.border = (first.border||0) +"px solid #000";
////                img.style.margin = (first.margin||0) +"px";
//                img.style.cssText += ';margin:' + (first.margin||0) +"px;" + 'border:' + (first.border||0) +"px solid #000";
            domUtils.setAttributes(img, first);
            me.execCommand('imagefloat', floatStyle);
            if (opt.length > 0) {
                range.setStartAfter(img).setCursor(false, true);
                me.execCommand('insertimage', opt);
            }

        } else {
            var html = [], str = '', ci;
            ci = opt[0];
            if (opt.length == 1) {
                str = '<img src="' + ci.src + '" ' + (ci._src ? ' _src="' + ci._src + '" ' : '') +
                    (ci.width ? 'width="' + ci.width + '" ' : '') +
                    (ci.height ? ' height="' + ci.height + '" ' : '') +
                    (ci['floatStyle'] == 'left' || ci['floatStyle'] == 'right' ? ' style="float:' + ci['floatStyle'] + ';"' : '') +
                    (ci.title && ci.title != "" ? ' title="' + ci.title + '"' : '') +
                    (ci.border && ci.border != "0" ? ' border="' + ci.border + '"' : '') +
                    (ci.alt && ci.alt != "" ? ' alt="' + ci.alt + '"' : '') +
                    (ci.hspace && ci.hspace != "0" ? ' hspace = "' + ci.hspace + '"' : '') +
                    (ci.vspace && ci.vspace != "0" ? ' vspace = "' + ci.vspace + '"' : '') + '/>';
                if (ci['floatStyle'] == 'center') {
                    str = '<p style="text-align: center">' + str + '</p>';
                }
                html.push(str);

            } else {
                for (var i = 0; ci = opt[i++];) {
                    str = '<p ' + (ci['floatStyle'] == 'center' ? 'style="text-align: center" ' : '') + '><img src="' + ci.src + '" ' +
                        (ci.width ? 'width="' + ci.width + '" ' : '') + (ci._src ? ' _src="' + ci._src + '" ' : '') +
                        (ci.height ? ' height="' + ci.height + '" ' : '') +
                        ' style="' + (ci['floatStyle'] && ci['floatStyle'] != 'center' ? 'float:' + ci['floatStyle'] + ';' : '') +
                        (ci.border || '') + '" ' +
                        (ci.title ? ' title="' + ci.title + '"' : '') + ' /></p>';
                    html.push(str);
                }
            }

            me.execCommand('insertHtml', html.join(''));
        }

        me.fireEvent('afterinsertimage', opt)
    }
};/**
 * 插入代码插件
 * @file
 * @since 1.2.6.1
 */

UE.plugins['insertcode'] = function() {
    var me = this;
    me.ready(function(){
        utils.cssRule('pre','pre{margin:.5em 0;padding:.4em .6em;border-radius:8px;background:#f8f8f8;}',
            me.document)
    });
    me.setOpt('insertcode',{
            'as3':'ActionScript3',
            'bash':'Bash/Shell',
            'cpp':'C/C++',
            'css':'Css',
            'cf':'CodeFunction',
            'c#':'C#',
            'delphi':'Delphi',
            'diff':'Diff',
            'erlang':'Erlang',
            'groovy':'Groovy',
            'html':'Html',
            'java':'Java',
            'jfx':'JavaFx',
            'js':'Javascript',
            'pl':'Perl',
            'php':'Php',
            'plain':'Plain Text',
            'ps':'PowerShell',
            'python':'Python',
            'ruby':'Ruby',
            'scala':'Scala',
            'sql':'Sql',
            'vb':'Vb',
            'xml':'Xml'
    });

    /**
     * 插入代码
     * @command insertcode
     * @method execCommand
     * @param { String } cmd 命令字符串
     * @param { String } lang 插入代码的语言
     * @example
     * ```javascript
     * editor.execCommand( 'insertcode', 'javascript' );
     * ```
     */

    /**
     * 如果选区所在位置是插入插入代码区域，返回代码的语言
     * @command insertcode
     * @method queryCommandValue
     * @param { String } cmd 命令字符串
     * @return { String } 返回代码的语言
     * @example
     * ```javascript
     * editor.queryCommandValue( 'insertcode' );
     * ```
     */

    me.commands['insertcode'] = {
        execCommand : function(cmd,lang){
            var me = this,
                rng = me.selection.getRange(),
                pre = domUtils.findParentByTagName(rng.startContainer,'pre',true);
            if(pre){
                pre.className = 'brush:'+lang+';toolbar:false;';
            }else{
                var code = '';
                if(rng.collapsed){
                    code = browser.ie && browser.ie11below ? (browser.version <= 8 ? '&nbsp;':''):'<br/>';
                }else{
                    var frag = rng.extractContents();
                    var div = me.document.createElement('div');
                    div.appendChild(frag);

                    utils.each(UE.filterNode(UE.htmlparser(div.innerHTML.replace(/[\r\t]/g,'')),me.options.filterTxtRules).children,function(node){
                        if(browser.ie && browser.ie11below && browser.version > 8){

                            if(node.type =='element'){
                                if(node.tagName == 'br'){
                                    code += '\n'
                                }else if(!dtd.$empty[node.tagName]){
                                    utils.each(node.children,function(cn){
                                        if(cn.type =='element'){
                                            if(cn.tagName == 'br'){
                                                code += '\n'
                                            }else if(!dtd.$empty[node.tagName]){
                                                code += cn.innerText();
                                            }
                                        }else{
                                            code += cn.data
                                        }
                                    })
                                    if(!/\n$/.test(code)){
                                        code += '\n';
                                    }
                                }
                            }else{
                                code += node.data + '\n'
                            }
                            if(!node.nextSibling() && /\n$/.test(code)){
                                code = code.replace(/\n$/,'');
                            }
                        }else{
                            if(browser.ie && browser.ie11below){

                                if(node.type =='element'){
                                    if(node.tagName == 'br'){
                                        code += '<br>'
                                    }else if(!dtd.$empty[node.tagName]){
                                        utils.each(node.children,function(cn){
                                            if(cn.type =='element'){
                                                if(cn.tagName == 'br'){
                                                    code += '<br>'
                                                }else if(!dtd.$empty[node.tagName]){
                                                    code += cn.innerText();
                                                }
                                            }else{
                                                code += cn.data
                                            }
                                        });
                                        if(!/br>$/.test(code)){
                                            code += '<br>';
                                        }
                                    }
                                }else{
                                    code += node.data + '<br>'
                                }
                                if(!node.nextSibling() && /<br>$/.test(code)){
                                    code = code.replace(/<br>$/,'');
                                }

                            }else{
                                code += (node.type == 'element' ? (dtd.$empty[node.tagName] ?  '' : node.innerText()) : node.data);
                                if(!/br\/?\s*>$/.test(code)){
                                    if(!node.nextSibling())
                                        return;
                                    code += '<br>'
                                }
                            }

                        }

                    });
                }
                me.execCommand('inserthtml','<pre id="coder"class="brush:'+lang+';toolbar:false">'+code+'</pre>',true);

                pre = me.document.getElementById('coder');
                domUtils.removeAttributes(pre,'id');
                var tmpNode = pre.previousSibling;

                if(tmpNode && (tmpNode.nodeType == 3 && tmpNode.nodeValue.length == 1 && browser.ie && browser.version == 6 ||  domUtils.isEmptyBlock(tmpNode))){

                    domUtils.remove(tmpNode)
                }
                var rng = me.selection.getRange();
                if(domUtils.isEmptyBlock(pre)){
                    rng.setStart(pre,0).setCursor(false,true)
                }else{
                    rng.selectNodeContents(pre).select()
                }
            }



        },
        queryCommandValue : function(){
            var path = this.selection.getStartElementPath();
            var lang = '';
            utils.each(path,function(node){
                if(node.nodeName =='PRE'){
                    var match = node.className.match(/brush:([^;]+)/);
                    lang = match && match[1] ? match[1] : '';
                    return false;
                }
            });
            return lang;
        }
    };

    me.addInputRule(function(root){
       utils.each(root.getNodesByTagName('pre'),function(pre){
           var brs = pre.getNodesByTagName('br');
           if(brs.length){
               browser.ie && browser.ie11below && browser.version > 8 && utils.each(brs,function(br){
                   var txt = UE.uNode.createText('\n');
                   br.parentNode.insertBefore(txt,br);
                   br.parentNode.removeChild(br);
               });
               return;
            }
           if(browser.ie && browser.ie11below && browser.version > 8)
                return;
            var code = pre.innerText().split(/\n/);
            pre.innerHTML('');
            utils.each(code,function(c){
                if(c.length){
                    pre.appendChild(UE.uNode.createText(c));
                }
                pre.appendChild(UE.uNode.createElement('br'))
            })
       })
    });
    me.addOutputRule(function(root){
        utils.each(root.getNodesByTagName('pre'),function(pre){
            var code = '';
            utils.each(pre.children,function(n){
               if(n.type == 'text'){
                   //在ie下文本内容有可能末尾带有\n要去掉
                   //trace:3396
                   code += n.data.replace(/[ ]/g,'&nbsp;').replace(/\n$/,'');
               }else{
                   if(n.tagName == 'br'){
                       code  += '\n'
                   }else{
                       code += (!dtd.$empty[n.tagName] ? '' : n.innerText());
                   }

               }

            });

            pre.innerText(code.replace(/(&nbsp;|\n)+$/,''))
        })
    });
    //不需要判断highlight的command列表
    me.notNeedCodeQuery ={
        help:1,
        undo:1,
        redo:1,
        source:1,
        print:1,
        searchreplace:1,
        fullscreen:1,
        preview:1,
        insertparagraph:1,
        elementpath:1,
        insertcode:1,
        inserthtml:1,
        selectall:1
    };
    //将queyCommamndState重置
    var orgQuery = me.queryCommandState;
    me.queryCommandState = function(cmd){
        var me = this;

        if(!me.notNeedCodeQuery[cmd.toLowerCase()] && me.selection && me.queryCommandValue('insertcode')){
            return -1;
        }
        return UE.Editor.prototype.queryCommandState.apply(this,arguments)
    };
    me.addListener('beforeenterkeydown',function(){
        var rng = me.selection.getRange();
        var pre = domUtils.findParentByTagName(rng.startContainer,'pre',true);
        if(pre){
            me.fireEvent('saveScene');
            if(!rng.collapsed){
               rng.deleteContents();
            }
            if(!browser.ie || browser.ie9above){
                var tmpNode = me.document.createElement('br'),pre;
                rng.insertNode(tmpNode).setStartAfter(tmpNode).collapse(true);
                var next = tmpNode.nextSibling;
                if(!next && (!browser.ie || browser.version > 10)){
                    rng.insertNode(tmpNode.cloneNode(false));
                }else{
                    rng.setStartAfter(tmpNode);
                }
                pre = tmpNode.previousSibling;
                var tmp;
                while(pre ){
                    tmp = pre;
                    pre = pre.previousSibling;
                    if(!pre || pre.nodeName == 'BR'){
                        pre = tmp;
                        break;
                    }
                }
                if(pre){
                    var str = '';
                    while(pre && pre.nodeName != 'BR' &&  new RegExp('^[\\s'+domUtils.fillChar+']*$').test(pre.nodeValue)){
                        str += pre.nodeValue;
                        pre = pre.nextSibling;
                    }
                    if(pre.nodeName != 'BR'){
                        var match = pre.nodeValue.match(new RegExp('^([\\s'+domUtils.fillChar+']+)'));
                        if(match && match[1]){
                            str += match[1]
                        }

                    }
                    if(str){
                        str = me.document.createTextNode(str);
                        rng.insertNode(str).setStartAfter(str);
                    }
                }
                rng.collapse(true).select(true);
            }else{
                if(browser.version > 8){

                    var txt = me.document.createTextNode('\n');
                    var start = rng.startContainer;
                    if(rng.startOffset == 0){
                        var preNode = start.previousSibling;
                        if(preNode){
                            rng.insertNode(txt);
                            var fillchar = me.document.createTextNode(' ');
                            rng.setStartAfter(txt).insertNode(fillchar).setStart(fillchar,0).collapse(true).select(true)
                        }
                    }else{
                        rng.insertNode(txt).setStartAfter(txt);
                        var fillchar = me.document.createTextNode(' ');
                        start = rng.startContainer.childNodes[rng.startOffset];
                        if(start && !/^\n/.test(start.nodeValue)){
                            rng.setStartBefore(txt)
                        }
                        rng.insertNode(fillchar).setStart(fillchar,0).collapse(true).select(true)
                    }

                }else{
                    var tmpNode = me.document.createElement('br');
                    rng.insertNode(tmpNode);
                    rng.insertNode(me.document.createTextNode(domUtils.fillChar));
                    rng.setStartAfter(tmpNode);
                    pre = tmpNode.previousSibling;
                    var tmp;
                    while(pre ){
                        tmp = pre;
                        pre = pre.previousSibling;
                        if(!pre || pre.nodeName == 'BR'){
                            pre = tmp;
                            break;
                        }
                    }
                    if(pre){
                        var str = '';
                        while(pre && pre.nodeName != 'BR' &&  new RegExp('^[ '+domUtils.fillChar+']*$').test(pre.nodeValue)){
                            str += pre.nodeValue;
                            pre = pre.nextSibling;
                        }
                        if(pre.nodeName != 'BR'){
                            var match = pre.nodeValue.match(new RegExp('^([ '+domUtils.fillChar+']+)'));
                            if(match && match[1]){
                                str += match[1]
                            }

                        }

                        str = me.document.createTextNode(str);
                        rng.insertNode(str).setStartAfter(str);
                    }
                    rng.collapse(true).select();
                }


            }
            me.fireEvent('saveScene');
            return true;
        }


    });

    me.addListener('tabkeydown',function(cmd,evt){
        var rng = me.selection.getRange();
        var pre = domUtils.findParentByTagName(rng.startContainer,'pre',true);
        if(pre){
            me.fireEvent('saveScene');
            if(evt.shiftKey){

            }else{
                if(!rng.collapsed){
                    var bk = rng.createBookmark();
                    var start = bk.start.previousSibling;

                    while(start){
                        if(pre.firstChild === start && !domUtils.isBr(start)){
                            pre.insertBefore(me.document.createTextNode('    '),start);

                            break;
                        }
                        if(domUtils.isBr(start)){
                            pre.insertBefore(me.document.createTextNode('    '),start.nextSibling);

                            break;
                        }
                        start = start.previousSibling;
                    }
                    var end = bk.end;
                    start = bk.start.nextSibling;
                    if(pre.firstChild === bk.start){
                        pre.insertBefore(me.document.createTextNode('    '),start.nextSibling)

                    }
                    while(start && start !== end){
                        if(domUtils.isBr(start) && start.nextSibling){
                            if(start.nextSibling === end){
                                break;
                            }
                            pre.insertBefore(me.document.createTextNode('    '),start.nextSibling)
                        }

                        start = start.nextSibling;
                    }
                    rng.moveToBookmark(bk).select();
                }else{
                    var tmpNode = me.document.createTextNode('    ');
                    rng.insertNode(tmpNode).setStartAfter(tmpNode).collapse(true).select(true);
                }
            }


            me.fireEvent('saveScene');
            return true;
        }


    });


    me.addListener('beforeinserthtml',function(evtName,html){
        var me = this,
            rng = me.selection.getRange(),
            pre = domUtils.findParentByTagName(rng.startContainer,'pre',true);
        if(pre){
            if(!rng.collapsed){
                rng.deleteContents()
            }
            var htmlstr = '';
            if(browser.ie && browser.version > 8){

                utils.each(UE.filterNode(UE.htmlparser(html),me.options.filterTxtRules).children,function(node){
                    if(node.type =='element'){
                        if(node.tagName == 'br'){
                            htmlstr += '\n'
                        }else if(!dtd.$empty[node.tagName]){
                            utils.each(node.children,function(cn){
                                if(cn.type =='element'){
                                    if(cn.tagName == 'br'){
                                        htmlstr += '\n'
                                    }else if(!dtd.$empty[node.tagName]){
                                        htmlstr += cn.innerText();
                                    }
                                }else{
                                    htmlstr += cn.data
                                }
                            })
                            if(!/\n$/.test(htmlstr)){
                                htmlstr += '\n';
                            }
                        }
                    }else{
                        htmlstr += node.data + '\n'
                    }
                    if(!node.nextSibling() && /\n$/.test(htmlstr)){
                        htmlstr = htmlstr.replace(/\n$/,'');
                    }
                });
                var tmpNode = me.document.createTextNode(utils.html(htmlstr.replace(/&nbsp;/g,' ')));
                rng.insertNode(tmpNode).selectNode(tmpNode).select();
            }else{
                var frag = me.document.createDocumentFragment();

                utils.each(UE.filterNode(UE.htmlparser(html),me.options.filterTxtRules).children,function(node){
                    if(node.type =='element'){
                        if(node.tagName == 'br'){
                            frag.appendChild(me.document.createElement('br'))
                        }else if(!dtd.$empty[node.tagName]){
                            utils.each(node.children,function(cn){
                                if(cn.type =='element'){
                                    if(cn.tagName == 'br'){

                                        frag.appendChild(me.document.createElement('br'))
                                    }else if(!dtd.$empty[node.tagName]){
                                        frag.appendChild(me.document.createTextNode(utils.html(cn.innerText().replace(/&nbsp;/g,' '))));

                                    }
                                }else{
                                    frag.appendChild(me.document.createTextNode(utils.html( cn.data.replace(/&nbsp;/g,' '))));

                                }
                            })
                            if(frag.lastChild.nodeName != 'BR'){
                                frag.appendChild(me.document.createElement('br'))
                            }
                        }
                    }else{
                        frag.appendChild(me.document.createTextNode(utils.html( node.data.replace(/&nbsp;/g,' '))));
                    }
                    if(!node.nextSibling() && frag.lastChild.nodeName == 'BR'){
                       frag.removeChild(frag.lastChild)
                    }


                });
                rng.insertNode(frag).select();

            }

            return true;
        }
    });
    //方向键的处理
    me.addListener('keydown',function(cmd,evt){
        var me = this,keyCode = evt.keyCode || evt.which;
        if(keyCode == 40){
            var rng = me.selection.getRange(),pre,start = rng.startContainer;
            if(rng.collapsed && (pre = domUtils.findParentByTagName(rng.startContainer,'pre',true)) && !pre.nextSibling){
                var last = pre.lastChild
                while(last && last.nodeName == 'BR'){
                    last = last.previousSibling;
                }
                if(last === start || rng.startContainer === pre && rng.startOffset == pre.childNodes.length){
                    me.execCommand('insertparagraph');
                    domUtils.preventDefault(evt)
                }

            }
        }
    });
    //trace:3395
    me.addListener('delkeydown',function(type,evt){
        var rng = this.selection.getRange();
        rng.txtToElmBoundary(true);
        var start = rng.startContainer;
        if(domUtils.isTagNode(start,'pre') && rng.collapsed && domUtils.isStartInblock(rng)){
            var p = me.document.createElement('p');
            domUtils.fillNode(me.document,p);
            start.parentNode.insertBefore(p,start);
            domUtils.remove(start);
            rng.setStart(p,0).setCursor(false,true);
            domUtils.preventDefault(evt);
            return true;
        }
    })
};

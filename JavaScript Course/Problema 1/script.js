selectLeft = document.getElementById('selectLeft');
selectRight = document.getElementById('selectRight');

selectLeft.setAttribute('size', selectLeft.length);
selectRight.setAttribute('size', selectLeft.length);

function sortSelect(selElem) {
    var tmpAry = new Array();
    for (var i=0;i<selElem.options.length;i++) {
        tmpAry[i] = new Array();
        tmpAry[i][0] = selElem.options[i].text;
        tmpAry[i][1] = selElem.options[i].value;
    }
    tmpAry.sort();
    while (selElem.options.length > 0) {
        selElem.options[0] = null;
    }
    for (var i=0;i<tmpAry.length;i++) {
        var op = new Option(tmpAry[i][0], tmpAry[i][1]);
        selElem.options[i] = op;
    }
    return;
}

selectLeft.ondblclick = function(){
	var selected = selectLeft.options[selectLeft.selectedIndex];
	selectRight.appendChild(selected);
	selectLeft.remove(selectLeft.selectedIndex);
	sortSelect(selectRight);
}

selectRight.ondblclick = function(){
	var selected = selectRight.options[selectRight.selectedIndex];
	selectLeft.appendChild(selected);
	selectRight.remove(selectRight.selectedIndex); 
	sortSelect(selectLeft);
}
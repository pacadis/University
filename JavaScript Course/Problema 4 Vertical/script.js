function stringType(value){
	if(value.match(/^-{0,1}\d+$/))
		return false;
	else
		return true;
}

function compareOrder(type, x, y){
	if (type == 'asc'){
		if (x > y)
			return true;
		else
			return false;
	}
	else{
		if (x < y)
			return true;
		else
			return false;
	}
}

function sortTable(tableNo, rowNo){
	var table = document.getElementById(tableNo);
	var switching = true;
	var shouldSwitch = false;
	var col;
	var type;
	if (table.type == '' || table.type == 'desc')
		type = 'asc';
	else if (table.type == '')
		type = 'desc';

	while (switching){
		switching = false;
		var rows = table.rows;
		var columns = rows[rowNo].getElementsByTagName("td");

		for (let i = 0; i < columns.length - 1; i++){
			shouldSwitch = false;
			let x = columns[i];
			let y = columns[i + 1];
			if (stringType(x.innerHTML) == true){
				if (compareOrder(type, x.innerHTML, y.innerHTML) == true){
					shouldSwitch = true;
					col = i;
					break;
				}
			}
			else{
				if (compareOrder(type, parseInt(x.innerHTML), parseInt(y.innerHTML)) == true){
					shouldSwitch = true;
					col = i;
					break;
				}
			}
		}
		if (shouldSwitch == true){
			for (let i = 0; i < rows.length; i++){
				let cols = rows[i].getElementsByTagName("td");
				let aux = cols[col].innerHTML;
				cols[col].innerHTML = cols[col + 1].innerHTML;
				cols[col + 1].innerHTML = aux;
				switching = true;
			}
		}
	}
	if (table.type == 'asc')
		table.type = 'desc';
	else
		table.type = 'asc';
}
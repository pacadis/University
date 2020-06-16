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

function sortTable(tableNo, colNo){
	var table = document.getElementById(tableNo);
	var switching = true;
	var shouldSwitch = false;
	var type;
	if (table.type == '' || table.type == 'desc')
		type = 'asc';
	else if (table.type == '')
		type = 'desc';

	while (switching){
		switching = false;
		var rows = table.rows;

		for (let i = 1; i < rows.length - 1; i++){
			shouldSwitch = false;
			let x = rows[i].getElementsByTagName('td')[colNo];
			let y = rows[i + 1].getElementsByTagName('td')[colNo];
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
			rows[col].parentNode.insertBefore(rows[col + 1], rows[col]);
      		switching = true;
			}
		}
	if (table.type == 'asc')
		table.type = 'desc';
	else
		table.type = 'asc';
}
function checkFinal(){
	var cols = document.getElementsByTagName('td');
	for (var i = 0; i < cols.length - 2; i++){
		if (parseInt(cols[i].innerHTML) != i + 1)
			return false;
	}
	return true;
}
function moveItem(row, col){
	var rows = document.getElementsByTagName("tr");
	var cols = [];
	for (let k = 0; k < rows.length; k++){
		cols[k] = rows[k].getElementsByTagName('td');
	}
	if (col > 0 && cols[row][col - 1].innerHTML === ''){
		let aux = cols[row][col].innerHTML;
		cols[row][col].innerHTML = '';
		cols[row][col - 1].innerHTML = aux;
	}
	else if (col < cols.length - 1 &&  cols[row][col + 1].innerHTML === ''){
		let aux = cols[row][col].innerHTML;
		cols[row][col].innerHTML = '';
		cols[row][col + 1].innerHTML = aux;
	}
	if (row > 0 && cols[row - 1][col].innerHTML === ''){
		let aux = cols[row][col].innerHTML;
		cols[row][col].innerHTML = '';
		cols[row - 1][col].innerHTML = aux;
	}
	else if (row < rows.length - 1 && cols[row + 1][col].innerHTML === ''){
		let aux = cols[row][col].innerHTML;
		cols[row][col].innerHTML = '';
		cols[row + 1][col].innerHTML = aux;
	}
	if (checkFinal() == true){
		alert("Game Over");
	}
}


function shuffle(array) {
  var currentIndex = array.length, temporaryValue, randomIndex;

  while (currentIndex !== 0) {
    randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex -= 1;
    temporaryValue = array[currentIndex];
    array[currentIndex] = array[randomIndex];
    array[randomIndex] = temporaryValue;
  }
  return array;
}

function generateNumbers(size){
	var array = [];
	for (let i = 1; i < size; i++)
		array[i - 1] = i;
	array = shuffle(array);
	return array;
}

function start(size){
	document.getElementById("game").innerHTML = '';
	var table = document.getElementById("game");
	var array = generateNumbers(size * size);
	var counter = 0;
	var counterArray = 0;
	var blankpos = Math.floor(Math.random() * (size * size));

	for (let i = 0; i < size; i++){
		var row = document.createElement("tr");
		for (let j = 0; j < size; j++){
			var col = document.createElement("td");
			if (counter == blankpos){
				col.innerHTML = '';
				counter++;
			}
			else{
				col.innerHTML = array[counterArray];
				counter++;
				counterArray++;
			}
			col.onclick = function() {moveItem(i, j);};
			row.appendChild(col);
		}
		table.appendChild(row);
	}
}

document.getElementById("start").onclick = function(){
	var size = parseInt(document.getElementById('size').value);
	start(size);
};
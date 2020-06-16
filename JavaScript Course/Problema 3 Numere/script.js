const delay = ms => new Promise(res => setTimeout(res, ms));

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
function generateItems(n){
	var items = [];
	for (let i = 0; i < n; i+=2){
		items[i] = i/2;
		items[i+1] = i/2;
	}
	return items;
}

function getRandomColor() {
  var letters = '0123456789ABCDEF';
  var color = '#';
  for (var i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  return color;
}

var cell1 = '';
var cell2 = '';
var remain = 0;


async function showItem(cell) {
	if (cell.innerHTML == ''){
		cell.innerHTML = cell.id;
		if (cell1 == '')
			cell1 = cell;
		else
			cell2 = cell;
		if (cell1 != '' && cell2 != ''){
			if (cell1.id == cell2.id ){
				cell1 = '';
				cell2 = '';
				remain--;
			}
			else{
				await delay(500);
				cell1.innerHTML = '';
				cell2.innerHTML = '';
				cell1 = '';
				cell2 = '';
			}
			if (remain == 0){
				alert('Game Over!');
			}
		}
	}
}

async function startGame(size){
	var game = document.getElementById("game");
    
	var items = generateItems(size);
	var items = shuffle(items);
    var gridCol = "grid-template-columns: ";

	for (let i = 1; i < Math.sqrt(size); i++){
		gridCol += "auto ";
	}
	gridCol += "auto";
	game.setAttribute("style", gridCol);

	game.innerHTML = '';
	var color = getRandomColor();
	for (let i = 0; i < size; i++){
		let cell = document.createElement("div");
		cell.innerHTML = items[i];
		cell.classList.add('celula');
		cell.id = items[i];
		cell.style.backgroundColor = color;
		cell.style.color = 'black';
		var lon = 800 / Math.sqrt(size*2);
		var len = String(lon).concat('px');
		cell.style.width = len;
		cell.style.height = len;
		game.appendChild(cell);
		cell.onclick = function() {showItem(this);};
	}

	await delay(1000);
	for (let item of game.children)
		item.innerHTML = '';
}

document.getElementById("start").onclick = function(){
	var size = parseInt(document.getElementById("size").value);
	remain = size / 2;
	startGame(size);
}
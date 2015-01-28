<!DOCTYPE html>

<!--
	Cyndi Ai 
	Turns a group of numbers user entered into heptadecimal and then count the G in the representation
-->

<html>
	<head>
		<title>Count My G!</title>
	</head>

	<body>
		<h1>MAGIC!</h1>
		<?php
			$list = $_POST["num"];	//saves numbers user input into one string, list
			$numList = explode(" ", $list);  //splits the list and saves in array 
			$numOfG = getCountofG($numList);  //get the number of G's in hepta
		?>
		<p> Now I have turned your number into a heptadecimal number! And guess what, there are <?= $numOfG ?> numbers of G in the representation!</p>
	</body>
</html>



<?php
//this function receives an array of numbers in decimal
//and counts however many number of G's in their hepta representation
function getCountOfG($myArray){
	$findStr = "G"; //target "G" we want to find in hepta
	$countG = 0;  //number of G, counter

	//for each number, we convert then count
	foreach($myArray as $num){
		if(!is_numeric($num)){
			die("LOSER!! TOLD YOU TO ENTER A NUMBER!");
		}
		$heptaArr = str_split(getHeptaString($num)); //array of chars in hepta representation
		//for each character in its hepta representation
		foreach($heptaArr as $char){ 
			if(!strcmp($char, $findStr)){ //if they are the same
				$countG++;
			}
		}
	}
	return $countG;
}

/*  this function receives the number in decimal form and convert it into heptadecimal
    Algorithm: use a combination of division and modulus to get each digit while keep tracking 
			 its remainder
    i.e. count = 0 :	(121/17^0) % 17 = 2		--> first digit is 0h2
					remainder = 121 - 2 * 17^0 = 119
		 count = 1 : (119/17^1) % 17 = 7     --> second digit is 0h7
					remainder = 119 - 7 * 17^1 = 0
		 => hepta form = 0h72
*/				
function getHeptaString($num){
	$base = 17; //base of 17
	$order = array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, A, B, C, D, E, F, G);  //order of hepta rep
	$count = 0; 
	$str = ""; //string to save the heptadecimal

	while($num != 0){
		$tempNum = ($num/pow($base, $count)) % $base;
		$str = $order[$tempNum].$str;
		$num-= $tempNum * pow($base, $count);
		$count++;
	}
	return $str;
}
?>
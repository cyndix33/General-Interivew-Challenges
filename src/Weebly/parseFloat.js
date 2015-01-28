// Cyndi Ai

"use strict";

(function() {
	window.onload = function() {
		var button = document.getElementById("go");
		button.onclick = parseHelper;
	};

	function parseHelper() {
		var value = document.getElementById("numToConvert").value;
		showActual(value);
		myParse(value);
	}

	function showActual(value) {
		var result = parseFloat(value);
		document.getElementById("parseFloat").innerHTML = result;
	}

	function myParse(value) {
		var result = "";
		var regex = new RegExp('[0-9|.]');

		// remove the trailing spaces
		while (value.charAt(0) == ' ') {
			value = value.substring(1, value.length);
		}

		// checks for negative values;
		if (value.charAt(0) == '-') {
			result += '-';
			value = value.substring(1, value.length);
		}

		// if NaN or else do the parse
		if (!regex.test(value.charAt(0))) {
			result = NaN;
		} else {
			var whole = "";		// whole number
			var decimal = "";	// decimal area
			var numExponent = 0;	// if E exists
			var onWhole = true;		// if there is decimal points
			var current = 0;		// index that keeps track of the current

			// get all the needed info
			while (regex.test(value.charAt(current))) {
				// check if we are still working on the whole or moved to the decimal
				if (value.charAt(current) == '.') {
					if (onWhole) {
						onWhole = false;
					} else {
						break;
					}
				} 

				// if on whole then we add it to the whole, else add it to decimal
				if (onWhole) {
					whole+= value.charAt(current);
				} else {
					if (value.charAt(current) != '.') {
						decimal+= value.charAt(current);
					}
				}
				current++;
			}

			// doing the exp case
			if (value.charAt(current) == 'e' || value.charAt(current) == 'E') {
				current++;
				var negExp = false;
				var regex2 = new RegExp('[0-9]');
				var exp = "";

				// checks for the negative or positive exp value
				if (value.charAt(current) == '-') {
					negExp = true;
					current++;
				} else if (value.charAt(current) == '+') {
					current++;
				}

				// checks however many exp is there
				while (regex2.test(value.charAt(current))) {
					exp += value.charAt(current);
					current++;
				}
				numExponent = parseInt(exp);
			}

			// deals with the exponent 
			while (numExponent != 0) {
				// two cases: negative exp or positive exp
				if (negExp) {
					// two cases: if there are no more number in whole
					//			then we add 0 to the negative part
					//			  else we remove the last index in whole
					//			 and add it to the negative part
					if (whole == "") {
						decimal = '0' + decimal; 
					} else {
						var temp = whole.substring(whole.length - 1, whole.length);
						whole = whole.substring(0, whole.length - 1);
						decimal = temp + decimal;
						onWhole = false;
					}
				} else {
					// same as above
					if (decimal == "") {
						whole += '0';
						onWhole = true;
					} else {
						var temp = decimal.substring(0, 1);
						decimal = decimal.substring(1, decimal.length);
						whole += temp;
					}
				}
				numExponent--;
			}

			// count the insignificant trailing zeros and remove them
			var count = 0;
			current = decimal.length - 1;
			while (!onWhole && current >= 0) {
				if (decimal.charAt(current) == '0') {
					current--;
					count++;
				} else {
					break;
				}
			}
			decimal = decimal.substring(0, decimal.length - count);

			// deal with the only dot without decimal case
			if (!onWhole && decimal == ""){
				onWhole = true;
			}

			// so that .5 works! 
			if (whole == "") {
				whole = "0";
			}

			// get the final result
			if (onWhole) {
				result += whole;
			} else {
				result += whole + "." + decimal;
			}
		}

		document.getElementById("implementationFloat").innerHTML = result;
	}
})();
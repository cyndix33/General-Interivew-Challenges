/*-- 
	Cyndi Ai
	JS part of the program, hands out shuffled cards to three players
*/

(function() {
	"use strict";
	var suit = ["spade", "heart", "diamond", "club"];
	var cards = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"];
	var players = 3;  // number of players
	var deck = 54;  // total number of cards in a deck
	var allCards = [];  // used to save the shuffled cards 

	window.onload = function(){
		document.getElementById("click").onclick = startIt;
	};

	// starts the program and walks through the entire js part 
	function startIt(){
		clearIt();
		shuffleIt();
		var count = 0;  //number of cards handed out

		//keep handing out cards until there are three left in the deck
		while(allCards.length > 3){
			var tempCard = allCards.pop();  	//the index of the card in the array
			var currentCard = getCard(tempCard); 	// String representation of tempCard
			var player = (count % players + 1).toString();	 // the player receiving the card
  			dealCard (currentCard, player); 	//hand currentcard to player
			count++;
		}
		cardsLeft();
	}

	// it first creates an array with ints 0-53, then shuffles the array randomly 
	function shuffleIt(){
		//fills up the array
		for(var i = 0; i < deck; i++){
			allCards[i] = i; 
		}

		var currentIndex = allCards.length; 
		var tempValue, randomIndex; 

		while(currentIndex) {
			randomIndex = Math.floor(Math.random() * currentIndex); //random
			currentIndex -= 1; 

			//swap
			tempValue = allCards[currentIndex];
			allCards[currentIndex] = allCards[randomIndex];
			allCards[randomIndex] = tempValue; 
		}
	}

	function clearIt(){
		var toClear = ["1", "2", "3", "left"];
		for(var i = 0; i < toClear.length; i++){
			document.getElementById(toClear[i]).innerHTML = "";
		}
	}

	// receives the index of the card and returns the card in a string representation.
	// suits are in order of var suit; cards are in order of var cards
	// i.e. 0 is spade1, 1 = spade2..etc.
	function getCard(tempCard){
		var currentCard = "Joker";
		if(tempCard < suit.length * cards.length){
			var numSuit = Math.floor(tempCard / cards.length);
			var numCard = tempCard % cards.length;
			currentCard = suit[numSuit].concat(cards[numCard]); 
		}
		return currentCard; 
	}

	// keeps the three cards left in hand
	function cardsLeft(){
		for(var i = 0; i < allCards.length; i++){
			dealCard(getCard(allCards[i]), "left");
		}
	}

	// hand the currentcard to the given location
	function dealCard (currentCard, location){
		var span = document.getElementById(location);
		span.innerHTML += "<img src = '"+ currentCard + ".png' alt = '" + currentCard + "' height = '60' width = '48'/>";
	}
})();
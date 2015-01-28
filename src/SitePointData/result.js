/*
	Cyndi Ai
	BAIDU Assignment #3
	Fetch for all the information needed from the URL input by user. Javascript
*/

(function() {
	"use strict";
	var page = 0; // the page of word list dispalyed
	var wordNum = 0; //total number of words in the article
	var count; //an associative array with K = word, V = occurrence
	var words; //a sorted array of words by its key
	
	window.onload = function(){
		document.getElementById("fetch").onclick = fetchClick;
		document.getElementById("next").onclick = next;
		document.getElementById("prev").onclick = prev;
	};	

	//when clicked at fetch, get all the needed things ready for process
	function fetchClick(){
		document.getElementById("error").style.display = "none";
		var request = new XMLHttpRequest(); //ajax load
		request.onload = processData;
		var uri = document.getElementById("article").value;
		request.open("GET", "result.php?article=" + encodeURI(uri), true);
		request.send();
	}

	//print out all the data needed
	function processData(event){
		if(this.status == 200){
			var response = this.responseText;
			checkValid(response);

			//fetch for the needed part of the article, i.e. title, author etc. 
			var title= getInnerHTML(response, '<h1 class="article_title">', '</h1>');
			var author = getInnerHTML(getInnerHTML(response, '<div class="contributor_name">', '</div>'), '>', '</a>');
			var time = getInnerHTML(getInnerHTML(response, '<p class="article_pub-date">', '</p>'), '<time datetime="', '" pubdate');
			var comments = getInnerHTML(getInnerHTML(response, '<script type="text/json" id="disqus-threadData">', '</script>'), '"total":', ',');
			var summary = getInnerHTML(getInnerHTML(response, '<section class="article_body">', '</section>'), '<p>', '</p>');
			
			//display it in part 1 of the HTML
			var list = document.getElementById("part1");
			list.innerHTML = title + "<br> Author: " + author + "<br> Published Date: " + time + "<br> Number of Comments: " + comments; 
			var summaryP = document.getElementById("summary");
			summaryP.innerHTML = summary;  
			
			//get count and words updated according to the data fetched from the given url
			count = wordCount(getInnerHTML(response, '<section class="article_body">', '</section>'));
			words = sortWords(count);
			wordNum = words.length;
			page = 0;
			
			//part 2 and 3
			showStat(count, words, 0, 30);
			showGraph(count, 20);
			document.getElementById("output").style.display = "initial";
		} else { //any other errors
			console.log("oh no, server error #" + this.status);
		}
	}

	function checkValid(response){
		var n = response.search('<h1 class="article_title">');
		if(n==-1){
			document.getElementById("error").style.display = "initial";
			document.getElementById("output").style.display = "none";
			throw("Please input valid URL");
		}
	}

	// help fetching the data needed for part 1. easier to fetch for author, title, etc. 
	function getInnerHTML(source, start, finish) {
		var ret = source.substring(source.indexOf(start)+start.length, source.indexOf(finish, source.indexOf(start)+start.length));
		return ret;
	}

	// cleans up the input string for the summary output from part 1. 
	function cleanString(input) {
		return input.replace(/<[^<]*>/g, '').replace(/[^a-zA-Z]+/g, ' ').replace(/ +/g, ' ').trim().toLowerCase();
	}

	//counts the occurrence of each word and saves it to an associative map. 
	function wordCount(input) {
		var words = cleanString(input).split(' ');
		var count = [];
		for (var i = 0; i < words.length; i++) {
			if (!count.hasOwnProperty(words[i])) {
				count[words[i]] = 0;
			}
			count[words[i]]++;
		}
		return count;
	}

	// sort the count by alphabetical order of the key of count
	function sortWords(count) {
		var b = [];
		for (var k in count) {
			b.push(k);
		}
		b.sort();
		return b;
	}

	// called when next is clicked, goes to the next page and en/disables the button
	function next() {
		page += 30;
		if (page + 30 >= wordNum) {
			document.getElementById("next").disabled='disabled';
		} else {
			document.getElementById("next").disabled='';
		}
		if (page == 0) {
			document.getElementById("prev").disabled='disabled';
		} else {
			document.getElementById("prev").disabled='';
		}
		showStat(count, words, page, page + 30);
	}

	// called when prev is clicked, goes to the previous page and en/disables the button
	function prev() {
		page -= 30;
		if (page == 0) {
			document.getElementById("prev").disabled='disabled';
		} else {
			document.getElementById("prev").disabled='';
		}
		if (page + 30 >= wordNum) {
			document.getElementById("next").disabled='disabled';
		} else {
			document.getElementById("next").disabled='';
		}
		showStat(count, words, page, page + 30);
	}

	// prints the statistics for part 2 
	function showStat(count, words, start, end) {
		document.getElementById('stat').innerHTML = "";
		var i;
		for (i = start; i < words.length && i < end; i++) {
			var n = count[words[i]];
			var li = document.createElement('li');
			li.innerHTML=(words[i] + ": " + n);
			document.getElementById('stat').appendChild(li);
		}
	}

	//part 3 - show the graph according to the occurrence of the words
	function showGraph(count, numKeyTerm){
		var graphArea = document.getElementById("graph");
		graphArea.innerHTML = "";
		var headers = document.createElement("tr"); //row of words
		var bars = document.createElement("tr"); //row of bars
		var sortedWords = sortByOcc(count); //sort it by occurrence

		//we needed to print numKeyTerm numbers of msot occured words
		for(var i = 0; i < numKeyTerm; i++){
			var word = sortedWords[words.length - i - 1];
			var occurrence = count[word];
			var header = document.createElement("th");
			var barCell = document.createElement("td"); //the cell holding bars
			var bar = document.createElement("div");
			bar.classList.add("bar");

			bar.style.height = (occurrence*10) + "px";
			header.innerHTML = word;
			bar.innerHTML = occurrence; 
			barCell.appendChild(bar);
			headers.appendChild(header);
			bars.appendChild(barCell);
		}
		graphArea.appendChild(bars);
		graphArea.appendChild(headers);
	}

	// part 3 supporting function, used to sort count by the associative array's Value 
	function sortByOcc(count){
		var keys = []; 
		for(var key in count){
			keys.push(key);
		}
		keys.sort(function(a, b){
			var a = count[a];
			var b = count[b];
			return a < b ? -1 : (a > b ? 1 : 0);
		});
		return keys; 
	}
})();
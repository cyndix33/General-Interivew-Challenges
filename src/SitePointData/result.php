<?php
	$url = $_GET["article"];
	$text = file_get_contents($url);
	$disqus = file_get_contents('http://disqus.com/embed/comments/?base=default&disqus_version=74492659&f=sitepointproduction&t_u='.$url);
	print($text.$disqus);
?>
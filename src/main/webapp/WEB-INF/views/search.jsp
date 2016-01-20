<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>jQuery UI Autocomplete - Default functionality</title>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script>
$(document).ready(function(){
	var availableTags = ${subjectList};
	$("#tags").autocomplete({
		source : availableTags,
		minLength : 0,
		select: function( event, ui ) {
			window.location.replace("list?search=" + ui.item.value);
		}
	});
});
</script>
</head>
<body>

	<div class="ui-widget">
		<label for="tags">Tags: </label> <input id="tags">
	</div>

</body>
</html>
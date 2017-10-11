var highlight = '';
var errorOccurred = false;

$(document).ready(function(){
	
	var editor = new MediumEditor('.editable', {
		buttons: ['bold', 'italic', 'underline', 'quote', 'anchor', 'superscript', 'subscript', 'orderedlist', 'unorderedlist', 'pre', 'outdent', 'indent', 'highlight'],
		buttonLabels: 'fontawesome'
	});

	var editorList = [];
	
	highlight = function() {
	
		var i = 0;
		
		$(".codearea").each(function(){
			
			var currenti = parseInt((this.id).split("editor")[1]);
			
			if(currenti >= i) {
														
				i = parseInt(currenti) + 1;
			}
		});
		
		var editorId = "editor" + i;
		var editor = $('<div id="' + editorId + '" class="codearea"></div>');
		var languageList = ['javascript', 'css', 'html'];
		var selectHTML = '<select class="controlAreaSelect">';
		console.log('Total languages: ' + languageList.length);
		
		for(var lang=0; lang < languageList.length; lang++) {
			
			console.log('i: ' + lang + ' language added: ' + languageList[lang]);
			selectHTML = selectHTML + '<option value="' + languageList[lang] + '">' + languageList[lang] + '</option>'
		}
		
		selectHTML = selectHTML + '</select>';
		
		var controlArea = $('<div class="controlAreaMain" contenteditable="false" id="controlArea' + i + '">' + selectHTML + '<img class="controlAreaCloseImg" src="./closeButton.png"></div>');
									
		$(focusItem).append(controlArea);
		$(focusItem).append(editor);
		var currentEditor = ace.edit("editor" + i);
		
		//Black
		//currentEditor.setTheme("ace/theme/monokai");
		
		//White
		currentEditor.setTheme("ace/theme/chrome");
		
		currentEditor.getSession().setMode("ace/mode/javascript");							
		editorList.push({id: editorId, instance: currentEditor, controlArea: controlArea});
		
		for(var i = 0; i < editorList.length; i++) {
	
			console.log('editor id: ' + editorList[i].id + " and instance: " + editorList[i].instance);
		}
	}
	
	$(document).delegate('.controlAreaCloseImg', 'click', function(){
	
		$(this).parent().remove();
		
		for(var i = 0; i < editorList.length; i++) {
		
			console.log('editorList[i].id ' + editorList[i].id.split('editor')[1] + ' $(this).attr ' + $(this).parent().attr('id').split('controlArea')[1]);
			
			if(editorList[i].id.split('editor')[1] == $(this).parent().attr('id').split('controlArea')[1]){
			
				console.log("one element found! " + editorList[i].id);
				
				//To actually close the editor
				$('#' + editorList[i].id).remove();
				
				/*START - These two lines will disable error checking and disable editing
				editorList[i].instance.getSession().setUseWorker(false);
				editorList[i].instance.setReadOnly(true);
				editorList[i].instance.renderer.setShowGutter(false);
				END - These two lines will disable error checking and disable editing*/
			}
		}					
	});
	
	$(document).delegate('.controlAreaMain > select', 'change', function() {
	
		console.log("Select Clicked: " + $(this).val());
		
		for(var i = 0; i < editorList.length; i++) {
		
			console.log('editorList[i].id ' + editorList[i].id.split('editor')[1] + ' $(this).attr ' + $(this).parent().attr('id').split('controlArea')[1]);
			
			if(editorList[i].id.split('editor')[1] == $(this).parent().attr('id').split('controlArea')[1]){
			
				console.log("one element found! " + 'ace/mode/' + $(this).val());
				editorList[i].instance.session.setMode('ace/mode/' + $(this).val());
			}
		}
	});
	
	var changeImageTODataURI = function() {
		
		$("img").each(function() {
		
			console.log("----+++++++++----" + $(this).attr("source"));
			
			if(typeof $(this).attr("source") !== "undefined") {
			
				this.src = $(this).attr("source");
				$(this).removeAttr("source");
				$(this).attr("saved", "true");
			}
		});
	}
	
	$(document).ready(function() {
								
		var totalImages = $('img').length - 1;
		var imagesLoaded = 0;
		
		if(totalImages == 0) {
			
			redrawLoadingBar(100);
			console.log("redrawLoadingBar -- no images");
			
		} else {
		
			$('img').bind("load", function() {
			
				if(this.id != 'companyLogo') {
					
					imagesLoaded = imagesLoaded + 1;
					redrawLoadingBar(((imagesLoaded)/totalImages)*100);
					console.log("redrawLoadingBar -- Image id " + $(this).id + " imagename " + this.imagename + " scr " + this.src);
					console.log("redrawLoadingBar -- imagesLoaded" + imagesLoaded + " total: " + totalImages);
				}
			});
		}		
		
		var mousedown = false;							
		var noteXinit = $(".note").position().left;
		var sideBarWidth = $("#sideBar").width();
		var resizingBarLeft = $(".note").css('left');							
		var resizingBarLeftDuplicate = $(".note").css('left');
		var noteWidth = $(".note").width();
		var noteLeft = $(".note").css('left');
		var noteLeftDuplicate = $(".note").css('left');											
		
		$(window).resize(function() {
			
			var resizeableWidth = 10;
			
			var calculatedNoteWidth = $(window).width() - $("#sideBar").width() - $("#resizingBar").width() + resizeableWidth;
			$("#noteSizePosition").html(".noteSizePosition {width:" + (calculatedNoteWidth + 10) + "px; left:" + parseInt(noteLeft) + "px}");
			console.log("resizing-----------------" + (calculatedNoteWidth - 10));
			resizeTabTextWidth();
		});
		
		var mouseXStart = $(".note").position().left;
		
		$(document).bind("mousemove", function(event) {
	
			if(mousedown) {
				
				var mouseXEnd = event.clientX;
				
				deltaX = mouseXEnd - mouseXStart;
				
				console.log("Delta: " + deltaX);
				//console.log("note:width " + $(".note").css('width') + " note:left " + $(".noteSizePosition").css('left'));
				console.log("note:width " + $(".noteSizePosition").css('width') + " note:left " + $(".noteSizePosition").css('left'));
				console.log("resizing bar left1: " + ((parseInt(resizingBarLeft) + deltaX) + "px"));
				console.log("resizing bar left2: " + resizingBarLeft);
				
				$("#resizingBar").css('left', (parseInt(resizingBarLeft) + deltaX) + "px");
				$("#sideBar").css("width", sideBarWidth + deltaX);
				$("#noteSizePosition").html(".noteSizePosition {width:" + (noteWidth - deltaX) + "px; left:" + (parseInt(noteLeft) + deltaX) + "px}");
				resizingBarLeftDuplicate = parseInt(resizingBarLeft) + deltaX;
				noteLeftDuplicate = parseInt(noteLeft) + deltaX;
				resizeTabTextWidth();
			}
		});
		
		$("#resizingBar").bind("mousedown", function(event) {
	
			mousedown = true;
			mouseXStart = event.clientX;
		});
		
		$(document).bind("mouseup", function(event) {
	
			mousedown = false;
			sideBarWidth = $("#sideBar").width();
			resizingBarLeft = resizingBarLeftDuplicate;
			noteWidth = $(".note").width();
			noteLeft = noteLeftDuplicate;
		});
		
		var resizeTabTextWidth = function(){
		
			$(".noteTab > div").each(function(){
			
				$(this).width($("#sideBar").width() - 15);
			});
		}
		
		var userDivClicked = false;
		
		console.log("username ------- " +$("#userName > a").val() );
		
		$("#userLink > a").html($("#userName").val());
		
		$("#userDiv").bind("click", function(event) {
			
			console.log("clicked - " + userDivClicked);
			
			if(userDivClicked) {
				
				$("#logoutLink").css("visibility", "hidden");
				$("#settingLink").css("visibility", "hidden");
				$("#userLink").removeClass("userLinkClassForJS");

				userDivClicked = false;
			} else {
			
				$("#logoutLink").css("visibility", "show");
				$("#settingLink").css("visibility", "show");
				$("#userLink").addClass("userLinkClassForJS");
				userDivClicked = true;
			}
			
			event.originalEvent.stopPropagation();
		});
		
		$("body").bind("click", function() {
			
			console.log("body clicked - " + userDivClicked);
			
			if(userDivClicked) {
				
				$("#logoutLink").css("visibility", "hidden");
				$("#settingLink").css("visibility", "hidden");
				$("#userLink").removeClass("userLinkClassForJS");
				userDivClicked = false;
			}
		});
	
		changeImageTODataURI();
		focusItem = $('#firstPara').get(0);
		
		console.log("focusItem - " + focusItem);
		
		$('body').delegate('*', 'click', function() {
		
			focusitem = this;
		});
		
		if (!window.Clipboard) {
		
			pasteCatcher = $('.editable').children().get(0);
			pasteCatcher.setAttribute("contenteditable", "");
	
			pasteCatcher.style.opacity = 1;
	
			pasteCatcher.focus();
	
			if(document.addEventListener) {
	
				document.addEventListener("click", function() { pasteCatcher.focus(); });
				
			} else if (document.attachEvent) {
	
				document.attachEvent("onclick", function() { pasteCatcher.focus(); });
			}				  
		} 
		
		if(document.addEventListener) {
	
			document.addEventListener("paste", pasteHandler);
			
		} else if (document.attachEvent) {
	
			document.attachEvent("onpaste", pasteHandler);
		}
	
		$('#sideBar').delegate('.tab', 'click', function() {
		
			var tabName = $(this).prop('class').split('tab ')[1];	
	
			if (tabName.indexOf(' selectedTab') != -1) {
			
				tabName = tabName.split(' selectedTab')[0];
			}
			
			$('#sideBar > ul > li').removeClass('selectedTab');
			$(this).addClass('selectedTab');
								
			setDivVisibility(tabName);					
		});		
		
		$('#sideBar').on('dblclick', '.tab', function() {
		
			$(this).find('div').attr('contenteditable', true);
			$(this).find('div').css('cursor', 'text');
		});
		
		$(document).on('click', '.tab', function() {
			
			$('#sideBar').find('.tab').find('div').attr('contenteditable', false);
			$('#sideBar').find('.tab').find('div').css('cursor', 'hand');
		});
		
		//$(document).on('drop', 'body', dropHandler);
		$(document).on('drop', '.editable', dropHandler);
									
		$(window).trigger('resize');
		noteWidth = $(".note").width();
	});
	
	/* Get Changed Elements - Images or Note Tab */		
	var isNodeSaved = 0;			
	var changedNodesMap = {};
	
	var putInNodeMap = function(key, value) {
	
		changedNodesMap[key] = value;
	}
	
	var getFromNodeMap = function(key) {
	
		return changedNodesMap[key];
	}
	
	var totalTextNodes;
	var totalImgNodes;
	var totalTextAndImgNodes;
	
	var saveAllNodes = function() {
		
		nodesSaved = 0;
	
		//Nodes
		var unSavedNodes = $('div[saved="false"]');
		
		unSavedNodes = unSavedNodes.add("#resizingBar");
		unSavedNodes = unSavedNodes.add("#noteSizePosition");
		
		for(var i = 0; i < unSavedNodes.length; i++) {
			sendDataToServer(unSavedNodes[i]);
		}
		
		//Images
		var unSavedImages = $('img[saved="false"]');
		for(var i = 0; i < unSavedImages.length; i++) {
			sendDataToServer(unSavedImages[i]);
		}
		
		totalTextNodes = unSavedNodes.length;
		totalImgNodes = unSavedImages.length;
		totalTextAndImgNodes = totalTextNodes + totalImgNodes;
	}
	
	var nodesSaved = 0;
	
	function sendDataToServer(nodeToBeSaved) {
		
		var clonedNode = $(nodeToBeSaved).clone();
		var xmlhttp = new XMLHttpRequest();
		var tagName = clonedNode.get(0).tagName;
		var imageExtension = "";
		var tabName = "";
		var metaData = "";
		
		xmlhttp.open("POST","saveContent",true,"","");
		
		console.log("************" + tagName);
		
		if(tagName == "DIV" || tagName == "STYLE") {
		
			clonedNode.find('img').each( function(){
	
				if(this.src.indexOf("data:image/") != -1) {
					
					var imageExtension = "";
					var tabName = "";
					var imageName = this.getAttribute("imagename");
					imageExtension = this.src.split("data:image/")[1].split(";base64")[0];
					tabName = $(this).parents('.note')[0].getAttribute("class");
	
					if(imageName == null) {
					
						imageName = "pastedImage";
					}
	
					var userName = $("#userName").val();
					var fileName = ("/images/" + userName + "/" + tabName + "/" + imageName + this.getAttribute("tabIndex") + "." + imageExtension).split(" ").join("_");
											
					this.setAttribute("src","");
					$(this).removeAttr("src");
					this.setAttribute("source",fileName);
				}
			});
			
			metaData = 'tagName:' + nodeToBeSaved.tagName + ' className:' + nodeToBeSaved.className;
			
			console.log("Saving HTML: --------------------------------------" + clonedNode.get(0).outerHTML);
			xmlhttp.send(metaData + '\n' + clonedNode.get(0).outerHTML);
			nodesSaved = nodesSaved + 1;
			
		} else if (tagName == "IMG") {
		
			if(nodeToBeSaved.src.indexOf("data:image/") != -1) {
				
				imageExtension = nodeToBeSaved.src.split("data:image/")[1].split(";base64")[0];
				tabName = $(nodeToBeSaved).parents('.note')[0].getAttribute("class");
				
				metaData = 'tagName:' + nodeToBeSaved.tagName + 
							  ' className:' + nodeToBeSaved.className + 
							  ' imageExtension:' + imageExtension + 
							  ' imageName:' + nodeToBeSaved.getAttribute("imagename") + 
							  ' tabIndex:' + nodeToBeSaved.getAttribute("tabIndex") + 
							  ' tabName:' + tabName;
										
				xmlhttp.send(metaData + '\n' + nodeToBeSaved.src.split("base64,")[1]);
				nodesSaved = nodesSaved + 1;
			}
		}
		
		console.log("metaData ---------- " + metaData);
		
		xmlhttp.onreadystatechange = function () {

			  var response = this.responseText;
			
			  if (this.readyState == 4 && this.status == 200) {
			    		
					$(nodeToBeSaved).attr('saved', 'true');

					if(!errorOccurred) {
						
						nodesSaved = nodesSaved - 1;
						redrawLoadingBar(((totalTextAndImgNodes - nodesSaved)/totalTextAndImgNodes)*100);
						console.log("redrawLoadingBar -- Saving nodesSaved " + nodesSaved + " totalTextAndImgNodes " + totalTextAndImgNodes);
					}
					
			  } else if (this.readyState != 2 && !errorOccurred) {		
			  //} else if (!errorOccurred) {		
				  
				  console.log('this.readyState ' + this.readyState);
				  console.log('this.status ' + this.status);
				  alert("Unable to save. Try Again!");
				  redrawLoadingBar(0);
				  errorOccurred = true;
			  }
			  
			  if (nodesSaved == 0) {

				  redrawLoadingBar(0);
			  }
		 }
	}
	
	$(document).bind('DOMSubtreeModified', function(e) {	
		
		var targetElement;
		
		if($(e.target).parents('.note').size() != 0) {
		
			targetElement = $(e.target).parents('.note');
			
		} else if ($(e.target).parents('.sidebar').size() != 0) {
			
			targetElement = $(e.target).parents('.sidebar');
			
		} else if ($(e.target).parents('#resizingBar').size() != 0) {
			
			targetElement = $(e.target).parents('#resizingBar');
		}
						
		if(targetElement != null) {
			
			//console.log("DOMSubtreeModified --------");
			targetElement.attr('saved', 'false');
			errorOccurred = false;
		}
	});
	
	$(document).bind('DOMNodeInserted', function(e) {
		
		if($(e.target).is('img')) {
			
			console.log("DOMNodeInserted --------");
			$(e.target).attr('saved', 'false');
			errorOccurred = false;
			populateTabIndexOnPara();
		}
						
		if(enterKeyPressed && !avoidRecursiveLoop) {
			
			if(enterKeyPressed && !avoidRecursiveLoop && e.target.innerHTML == null) {
	
				e.target.outerHTML = '<p id="firstPara" tabindex="0"><br></p>';
				
			} else if (enterKeyPressed && !avoidRecursiveLoop && $(e.target).is('span')) {
	
				$(e.target.parentNode).removeAttr('class');
				e.target.outerHTML = '';
			}
			
			avoidRecursiveLoop = true;
			enterKeyPressed = false;
		}
	});
	/* Get Changed Elements - Images or Note Tab */
	
	$('#newTabButton').bind('click', function() {
		
		console.log("New Tab Created");
	
		createTab('New Tab');			
	});
	
	function createTab(tabName) {
	
		var tabName2 = prompt("Tab Name", "");
		
		if(!tabName2) {
			
			return;
		}
		
		var totalLi = $('.noteTab').size();
		
		var timeStamp = getTime();
		var newLi = '<li class="noteTab tab ' + (totalLi+1) + '" draggable="true" style="list-style-type:none;padding: 0px 10px 5px;"><div class="wordwrap"><span>' + tabName2 + '</span></div></li>';
		//Removing noise.png
		//var newNote = '<div class="noteSizePosition note tab ' + (totalLi+1) + '"><div class="timestamp" contenteditable="false">' + timeStamp + '</div><div class="editable" style="border-bottom:none;background-color:#FEFEFE;margin-left:1px;height:95%;margin:1px;padding:20px;margin-top:-27px;background:url(\'./noise.png\');color:#3A4145"><h2>&nbsp;</h2></div></div>';
		var newNote = '<div class="noteSizePosition note tab ' + (totalLi+1) + '"><div class="timestamp" contenteditable="false">' + timeStamp + '</div><div class="editable" style="border-bottom:none;background-color:#FEFEFE;margin-left:1px;height:90%;margin:1px;padding:20px;padding-top: 0px;margin-top:-27px;color:#3A4145"><h2>&nbsp;</h2></div></div>';
		
		
		$('.ulClass').append(newLi);
		
		$('.note').last().after(newNote);
		
		$('#medium-editor-toolbar-1').remove();
		$('#medium-editor-anchor-preview-1').remove();
	
		new MediumEditor('.editable', {
			buttons: ['bold', 'italic', 'underline', 'quote', 'anchor', 'superscript', 'subscript', 'orderedlist', 'unorderedlist', 'pre', 'outdent', 'indent', 'highlight'],
			buttonLabels: 'fontawesome'
		});
		
		$('.noteTab').last().click();
		setTimeout(function(){$('.noteTab').last().click();}, 200);
		
		var node = $(".selectedDiv").find("h2")[0].firstChild;
		
		var range = document.createRange();
		range.setStart(node, 0);
		range.setEnd(node, 1);
		var sel = window.getSelection();
		sel.removeAllRanges();
		sel.addRange(range);
		console.log("focus point: " + node);
		($(node).parent())[0].focus();
	}
	
	function getTime() {
			
		var dateTime = new Date();
		
		var day = dateTime.getDay();;
		var month = dateTime.getMonth();
		var minute = '';
		var hour = '';
		
		switch(day){							
			case 0: day = "Sun"; break;
			case 1: day = "Mon"; break;
			case 2: day = "Tue"; break;
			case 3: day = "Wed"; break;
			case 4: day = "Thu"; break;
			case 5: day = "Fri"; break;
			case 6: day = "Sat"; break;
		}
	
		switch(month){							
			case 0:  month = "Jan"; break;
			case 1:  month = "Feb"; break;
			case 2:  month = "Mar"; break;
			case 3:  month = "Apr"; break;
			case 4:  month = "May"; break;
			case 5:  month = "Jun"; break;
			case 6:  month = "Jul"; break;
			case 7:  month = "Aug"; break;
			case 8:  month = "Sep"; break;
			case 9:  month = "Oct"; break;
			case 10: month = "Nov"; break;
			case 11: month = "Dec"; break;
		}
		
		if(dateTime.getHours().length == 1) {
			
			hour = '0' + dateTime.getHours();
		} else {
		
			hour = dateTime.getHours();
		}
		
		if(dateTime.getMinutes().length == 1) {
			
			minute = '0' + dateTime.getMinutes();
		} else {
			
			minute = dateTime.getMinutes();
		}
									
		return day + ", " +  month + " " + dateTime.getDate() + " " + dateTime.getFullYear() + ", " + hour + ":" + minute;
	}
	
	var focusItem = null;
	var insertedImage = null;
	var pasteCatcher = null;
	var holderWidth = $('.editable').get(0).clientWidth - 50;
	
	function ignoreDrag(e) {
		e.originalEvent.stopPropagation();
		e.originalEvent.preventDefault();
	}
	
	$('body').bind('dragover', ignoreDrag);
	$('body').bind('dragenter', ignoreDrag);
	$('body').bind('dragend', ignoreDrag);
	
	var crtlKeyPressed = false;
	var enterKeyPressed = false;
	var avoidRecursiveLoop = true;
	
	jQuery(document).keyup(function(event){
	
		var code = (event.keyCode ? event.keyCode : event.which);
		
		if(code == 13) {
	
			enterKeyPressed = true;
			avoidRecursiveLoop = false;
		
			populateTabIndexOnPara();
		}
		
		if (code == 17) {
		
			crtlKeyPressed = false;
		}
	});
	
	//Save button capture - from keys
	jQuery(document).keydown(function(event){
	
		var code = (event.keyCode ? event.keyCode : event.which);
		
		if(code == 17) {
		
			crtlKeyPressed = true;
		}
		
		if (code == 83) {
		
			if (crtlKeyPressed) {
			
				saveAllNodes();
				event.preventDefault();
			} 
		} 
	
	});
	
	//Save button capture - from button
	$("#saveButton").bind('click', function() {
		
		saveAllNodes();
	});
	
	//Print button capture - from button
	$("#printButton").bind('click', function() {
		
		console.log('dsada');
	});
	
	function populateTabIndexOnPara() {
	
		var paras = $('.selectedDiv').find('p, img');
	
		paras.each(function (index) {
		
			$(this).prop('tabindex', index);
			
			$('p').focus( function() {
	
				focusItem = this; 
				console.log("focusItem - " + focusItem);
			});	
			
			console.log("index - " + index);
			
			if(index == '0'){
				
				console.log("first index - ");
				//$(this).addClass("firstParaClass");
				//paras.addClass("firstParaClass");
			}
			
			focusItem = this;
		});
	}
	
	//$(document).live('drop', dropHandler);
	
	function dropHandler(e) {
		
		console.log("dropHandler-----------------------");
	
		ignoreDrag(e);
	
		var file = e.originalEvent.dataTransfer.files[0];
	
		var reader = new FileReader();
		 
		reader.onloadend = function () {
			
			var fileName = file.name;
			
			if(file.name.indexOf(".") != -1) {
				
				console.log("dropped " + file.name.split(".")[0]);
				
				fileName = file.name.split(".")[0];
			}
			
			if (typeof focusItem.innerHTML == 'undefined') {
			
				focusItem.innerHTML = '<img imageName="' + fileName + '" src="'+ reader.result + '" width="' + holderWidth + '" contenteditable="false"/>';
			} else {
							
				focusItem.innerHTML = '<img imageName="' + fileName + '" src="'+ reader.result + '" width="' + holderWidth + '" contenteditable="false"/>' + focusItem.innerHTML;
			}
		};
	
		reader.readAsDataURL(file);
	
		return false;
	}
	
	var redrawLoadingBar = function(currentWidth) {
		
		var currentWidthPercentage = currentWidth + "%";
		
		console.log("redrawLoadingBar - currentWidthPercentage " + currentWidthPercentage);
		
		$("#loadingBar").animate({width:currentWidthPercentage}, 300, function(){
		
			if(currentWidth >= 100) {
				
				console.log("redrawLoadingBar - event " + currentWidthPercentage)
			
				$("#loadingBar").width("0%");
			}
		});
	}
	
	function setDivVisibility(tabName) {
	
		var divObjects = $('.note');
		
		divObjects.each(function () {
			
			if ($(this).prop('class').split('tab ')[1] == tabName) {
				
				$(this).css('display', 'block');
				$($(this).children().get(1)).addClass('selectedDiv');
				
				if ($(this).find('p').size() == 1) {
					
					//focusItem = $(this).find('p:first-child');
					focusItem = $(this).find('p').get(0);
				}
				
			} else {
	
				$(this).css('display', 'none');
				
				if ($($(this).children().get(1)).hasClass('selectedDiv')) {
					
					$($(this).children().get(1)).removeClass('selectedDiv');
				}
			}
		}); 			
	}
				
	function pasteHandler(e) {
					
		if (e.clipboardData) {
	   
			var items = e.clipboardData.items;
	
			if (items) {
			 
				for (var i = 0; i < items.length; i++) {
	
					if (items[i].type.indexOf("image") !== -1) {
	
						var image = items[i].getAsFile();	
						var reader = new FileReader();
						
						reader.onloadend = function() {
						
							createImage(reader.result);	
						}
						
						reader.readAsDataURL(image);										
					}
				}
			}
	    }
	}
	
	function createImage(source) {
	
		var pastedImage = new Image();
	
		pastedImage.src = source;
		pastedImage.width=holderWidth;
		focusItem.appendChild(pastedImage);
	}
	
	$(".ulClass").delegate(".noteTab", 'dragstart', function(e) {
		
		e.originalEvent.dataTransfer.effectAllowed = 'copy'; // only dropEffect='copy' will be dropable
		console.log('dragstart --- index -- ' + $(this).index());
		e.originalEvent.dataTransfer.setData('Text', $(this).index()); // required otherwise doesn't work
	});
	
	$(".ulClassRemove").delegate("#deleteButton", 'dragover', function(e) {
	
		if (e.originalEvent.preventDefault) e.originalEvent.preventDefault(e.originalEvent); // allows us to drop
		$(this).addClass('over');
		e.originalEvent.dataTransfer.dropEffect = 'copy';
		return false;
	});

	$(".ulClassRemove").delegate("#deleteButton", 'dragenter', function(e) {
	
		$(this).addClass('over');
		return false;
	});
	
	$(".ulClassRemove").delegate("#deleteButton", 'dragleave', function(e) {
	
		$(this).removeClass('over');
	});

	$(".ulClassRemove").delegate("#deleteButton", 'drop', function(e) {
	
		e = e.originalEvent;					
		if (e.stopPropagation) e.stopPropagation(); // stops the browser from redirecting...why???

		console.log('e.dataTransfer.getData(Text): ' + e.dataTransfer.getData('Text'));
		var nodeToBeDeleted = $('.ulClass').children().eq(e.dataTransfer.getData('Text'));
		
		var indexOfNote = nodeToBeDeleted.attr('Class').split(' tab ')[1].split(' ')[0];
		
		$('.note').each(function(index){
		
			if($(this).hasClass(indexOfNote)){
			
				console.log('----------------------------------------- ' + indexOfNote);
				$(this).remove();
			}
		});
		
		console.log('indexOfNote ' + indexOfNote);
		nodeToBeDeleted.remove();
		$(this).removeClass('over');

		return false;
	});
});
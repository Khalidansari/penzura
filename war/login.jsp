<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@page session="false" %> 
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<title>Login | Penzura</title>
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<style>		
			a {
			
				text-decoration:none;
				font-weight:bold;
				color:#0072C6;
				font-size:10px;
			}
			
			#verticalSeparator {
			
				height: 350px;
				width: 1px;
				background: -webkit-linear-gradient(top, rgba(0, 114, 198, 0), #0072C6, rgba(0, 114, 198, 0));
				background: -o-linear-gradient(top, rgba(0, 114, 198, 0), #0072C6, rgba(0, 114, 198, 0));
				background: -moz-linear-gradient(top, rgba(0, 114, 198, 0), #0072C6, rgba(0, 114, 198, 0));
				background: -ms-linear-gradient(top, rgba(0, 114, 198, 0), #0072C6, rgba(0, 114, 198, 0));
			}
			input {
				height:25px;
				width:100%;
			}			
			input[type="submit"] {
				border: 1px solid #2676FA;
				font-weight: bold;
				outline: none;
				background: #5C98FA;
				color: white;
				border-radius: 2px;
				height:25px;
				width:100%;
			}
			input[type="submit"]:hover {
				border: 1px solid #2676FA;
				font-weight: bold;
				outline: none;
				background: #3484FD;
				color: white;
				border-radius: 2px;
				height:25px;
				width:100%;
			}			
			td {			
				margin: 20px;
			}
			
			table {			
				//margin-top:100px;
			}
		
			h1 {
				font-family: "open sans", calibri;
				font-weight: 300;
				font-size: 20;
			}
			
			h2 {
				font-family: "open sans", calibri;
				font-weight: 300;
				font-size: 50;
			}
			
			form {
				font-family: "open sans", calibri;
				font-weight: 300;
				font-size: 10;
			}
			
			.smallText {
				font-family: "open sans", calibri;
				font-weight: 600;
				font-size: 10;
			}
			
			body {
				font-family: "open sans", calibri;
				font-weight: 600;
				font-size: 10;
				margin-left:0px;
				margin-top:0px;
				margin-right:0px;
				margin-bottom:0px;
				position:absolute;
				height:100%;
				width:100%;
			}
			
			.footer {
				height:60px;
				width:100%;
				position:absolute;
				left:0px;
				bottom:0px;		
				background-color:#F0F0F0;
			}
		</style>
		<script>
		
			var validateForm = function(){

			    var email = document.getElementById('username');
			    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

			    if (!filter.test(email.value)) {
				    alert('Please provide a valid email address');
				    email.focus;
				    return false;
			 	}
			}
		</script>
	</head> 
	<body style="margin:0px"> 

		<div>
			<table>
				<tr>
					<td colspan="5">
						<div style="text-align:center;margin-top: 30px;margin-bottom: 40px;font-size: 30px;">
							Organize Notes with Text and Images.
						</div>
					</td>
				</tr>
				<tr>
					<td width="20%" style="padding:40px">
						<div>
						<div>
					</td>
					<td width="20%" style="padding:40px">
						<div>
							<img src="demo1.png" width="400px"/>
						<div>
					</td>
					<td>
						<div id="verticalSeparator"></div>
					</td>
					<td width="30%" style="padding:40px">
						<img src="companyLogo.png" style="position:relative;left:-6px;top:10px"/>
						<form action="http://www.penzura.com/LoginServlet" method="post" style="position:relative;top:30px">
							<table style="width:90%">
								<tbody><tr>
									<td><input type="text" name="username" id="username" placeholder="someone@something.com"></td>
								</tr>
								<tr>
									<td ><input type="password" name="pw" placeholder="password"></td>
								</tr>
								<tr>
									<td><input type="submit" value="Log In" onclick="return validateForm();"></td>
								</tr>
								<tr>
									<td colspan="2" style="position:relative;top:45px;font-size:12px">Don't have a Penzura account? 
										<a href="http://www.penzura.com/Signup" style="margin-left:10px">Sign up now</a>
									</td>
								</tr>
							</tbody></table>
						</form>
					</td>
					<td width="20%" style="padding:40px">
						<div>
						<div>
					</td>
				</tr>
				<tr>
					<td colspan="5">
						<div style="text-align:center;font-size:14px;position: absolute;bottom: 75px;width: 98%;">
							Clean editor with no distractions &nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;Highly configurable&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;Code editor for coders&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;Absolutely free
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="footer">
			<table width="98%" style="font-family:calibri;left: 5px;position: relative;top: 11px;">
				<tr>
					<td width="70%">
						<div style="font-size:14px;color:rgb(100,100,100); font-weight:bold;">
							Penzura
						</div>
						<div style="font-size:9px;">
							&copy; 2015
						</div>
					</td>
					<td width="8%">
						<div>
							<a href="/info#about">About Us</a>
						</div>
					<td width="8%">
						<div>
							<a href="/info#terms">Terms of Use</a>
						</div>
					</td>					
					<td width="8%">
						<div>
							<a href="/info#contact">Contact Us</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
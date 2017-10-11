<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@page session="false" %> 
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<title>Information | Penzura</title>
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<style>		
			li {
				
				padding-bottom:10px;
			}
			#content {
			
				width:calc(100%-100px);
			}
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
		<link href='css/main.css' rel='stylesheet' type='text/css'>
		<link href='css/home.css' rel='stylesheet' type='text/css'>
	</head> 
	<body style="margin:0px;overflow-y:hidden"> 
		<div class="topBar">
			<table>
				<tr>
					<td>
						<div id="companyLogo">
							<span style="font-family: 'ar berkley';">pen.</span>
							<span style="font-family: 'vrinda';position:relative;left:-3px;">Zura</span>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div style="overflow-y:auto;height:calc(100% - 140px);padding-top:20px; padding-bottom:20px;">
			<table style="width:100%">
				<tr>
					<td style="width:700px;margin:0 auto;">
						<div id="about" style="color:#0072C6;text-align:center;margin-top:30px;margin-bottom:40px;font-family:'open sans';font-size:35px;width:700px;margin:0 auto">
							About Us
						</div>
						<p style="width:700px;margin:0 auto;font-family:'open sans';font-size:18px">
							A bunch of passionate developers decided to implement Penzura to cater their own needs. There was no application on the internet to take notes which included not just text but any form of data. We released it to general public, free of cost, from 1st July 2015. Hope it helps in better organization of not just your shopping list but creating albums, office notes, personal notes, etc. 
						</p>
					</td>
				</tr>
				<tr>
					<td style="width:700px;margin:0 auto;padding-top: 60px;">
						<div id="terms" style="color:#0072C6;text-align:center;margin-top:30px;margin-bottom:40px;font-family:'open sans';font-size:35px;width:700px;margin:0 auto">
							Terms of User
						</div>
						<p style="width:700px;margin:0 auto;font-family:'open sans';font-size:18px;padding-bottom: 50px;">
							By using the Penzrua web app (“Service”), you are agreeing to be bound by the following terms and conditions (“Terms of Use”) set forth by Penzura, Inc.
							<ul style="width:700px;margin:0 auto;font-family:'open sans';font-size:18px">
								<li>
									You are responsible for keeping your login and password safe.
								</li>
								<li>
									The Service should not be used to store sensitive information such as bank account numbers, credit card information, or passwords.
								</li>
								<li>
									We are not responsible for any information stored with the Service.
								</li>
								<li>
									We reserve the right to modify or terminate the Service for any reason, without notice at any time.
								</li>
								<li>
									We reserve the right to alter these Terms of Use at any time. If there are any changes to these Terms of Use, users will be notified.
								</li>
								<li>
									We reserve the right to refuse service to anyone for any reason at any time.
								</li>
							</ul>
						</p>
					</td>
				</tr>
				<tr>
					<td style="width:700px;margin:0 auto;padding-top:50px">
						<div id="contact" style="color:#0072C6;text-align:center;margin-top:30px;margin-bottom:40px;font-family:'open sans';font-size:35px;width:700px;margin:0 auto">
							Contact Us
						</div>
						<p style="width:700px;margin:0 auto;font-family:'open sans';font-size:18px">
							You may contact us on the below address. We would love to hear your suggestions and advice to make this site better. We would soon be launching a defect tracking system to log the current issues. For time being please email us for any bugs. Thanks a lot for your help!
						</p>
						<p style="width:700px;margin:0 auto;font-family:calibri;font-size:18px;" align="center">						
							contact@penzura.com
						</p>
						<br/>
						<br/>
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
							<a href="#about">About Us</a>
						</div>
					<td width="8%">
						<div>
							<a href="#terms">Terms of Use</a>
						</div>
					</td>					
					<td width="8%">
						<div>
							<a href="#contact">Contact Us</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
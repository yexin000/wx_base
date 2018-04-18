<!DOCTYPE html>
<%@ page language="java" pageEncoding="GBK"%>
<html class="ui-mobile">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href=".">
		<meta name="viewport"
			content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">

		<link rel="stylesheet"
			href="/weixin/foreground/css/jquery.mobile-1.4.3.min.css">
		<!-- <link rel="stylesheet"
			href="http://weixin.stc.gov.cn/weixin/assets/stylesheets/mobile/wplatform.css"> -->
		<script src="../js/jquery-1.9.0.min.js" type="text/javascript"></script>
		<script src="../js/trafficpolice.js" type="text/javascript"></script>
		<script src="../js/jquery.validate.js" type="text/javascript"></script>
		<script src="../js/jquery.mobile-1.4.3.min.js" type="text/javascript"></script>
		<title>金华交警</title>
	</head>
	<body class="ui-mobile-viewport ui-overlay-b" style="zoom: 1;">
		<div data-role="page" data-page="TrafficPeccancyInquiry"
			data-theme="e" class="weixin ui-page ui-body-b ui-page-active"
			style="background-color: rgb(232, 232, 232); min-height: 620px; background-position: initial initial; background-repeat: initial initial;"
			data-url="/weixin/trafficpolice/TrafficPeccancyInquiry" tabindex="0">
			<div data-role="header" data-theme="c" class="ui-header ui-bar-b"
				role="banner">
				<a
					href="http://weixin.stc.gov.cn/weixin/trafficpolice/TrafficPeccancyInquiry#"
					id="dd" data-icon="back" data-role="button" style="display: none;"
					class="ui-btn-left ui-btn ui-btn-up-b ui-shadow ui-btn-corner-all ui-btn-icon-left"
					data-corners="true" data-shadow="true" data-iconshadow="true"
					data-wrapperels="span" data-theme="e"><span
					class="ui-btn-inner"><span class="ui-btn-text">返回</span><span
						class="ui-icon ui-icon-back ui-icon-shadow">&nbsp;</span> </span> </a>
				<!-- <h1 id="cc" class="ui-title" role="heading" aria-level="1">
					不适用事故快处快撤情形
				</h1> -->
			</div>
			<div data-role="content" id="vv" class="ui-content" role="main" data-theme="a">
				<div data-role="content">
					<p>
						有以下不适用本功能情形之一，驾驶人应立即报警,在现场等候交通警察处理：
					</p>
					<p>
						1、机动车无号牌、无检验合格标志、无交强险保险标志的；
					</p>
					<p>
						2、驾驶人无有效机动车驾驶证的；
					</p>
					<p>
						3、驾驶人饮酒、服用国家管制的精神药品或者麻醉药品的；
					</p>
					<p>
						4、一方当事人逃逸的；
					</p>
					<p>
						5、碰撞并造成建筑物、公共设施或者其他设施损坏的；
					</p>
					<p>
						6、发现对方有故意造成交通事故或持有假牌、假证嫌疑的；
					</p>
					<p>
						7、不适用时段：当日18:30（冬时令18时）～次日07:30。
					</p>
				</div>
			</div>
			<div data-role="content" id="cresult" style="display: none;"
				class="ui-content" role="main">
			</div>
		</div>

		<div class="ui-loader ui-corner-all ui-body-a ui-loader-verbose">
			<span class="ui-icon ui-icon-loading"></span>
			<h1>
				加载中...
			</h1>
		</div>
	</body>
</html>

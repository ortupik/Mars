<?php
function flush_buffers(){
  ob_end_flush();
  ob_flush();
  ob_start();
}
$path="E:/Carly.mp4
if(is_file($path){
    header("Content-type: video/x-flv");
	header("Content-Disposition:attachment;filename="Carly.mp4");
	$fd = fopen($path,"r");
	while(!feof($fd)){
	 echo fread ($fd,1024*5);
	 flush_buffers():
	 }
	 fclose($fd);
	 exit();
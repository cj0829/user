// JavaScript Document

	//initial time
	var h_current = -1;
	var m1_current = -1;
	var m2_current = -1;
	var s1_current = -1;
	var s2_current= -1;

	
	function flip (upperId, lowerId, changeNumber, pathUpper, pathLower){
		var upperBackId = upperId+"Back";
		$(upperId).src = $(upperBackId).src;
		$(upperId).setStyle("height","30px");
		$(upperId).setStyle("visibility", "visible");
		$(upperBackId).src = pathUpper+parseInt(changeNumber)+".png";
		
		$(lowerId).src = pathLower+parseInt(changeNumber)+".png";
		$(lowerId).setStyle("height", "0px");
		$(lowerId).setStyle("visibility", "visible");
		
		var flipUpper = new Fx.Tween(upperId, {duration: 200, transition: Fx.Transitions.Sine.easeInOut});
		flipUpper.addEvents({
			'complete': function(){
				var flipLower = new Fx.Tween(lowerId, {duration: 200, transition: Fx.Transitions.Sine.easeInOut});
					flipLower.addEvents({
						'complete': function(){	
							lowerBackId = lowerId+"Back";
							$(lowerBackId).src = $(lowerId).src;
							$(lowerId).setStyle("visibility", "hidden");
							$(upperId).setStyle("visibility", "hidden");
						}
					});					
					flipLower.start('height', 30);
					
			}
							});
		flipUpper.start('height', 0);
		
		
	}//flip
				
	var maxtime = 120*60;
	function retroClock(path){
		if(path){
			path="/exam";
		}
	if(maxtime>=0){
		h=Math.floor(maxtime/60/60);
		m1=Math.floor(maxtime/60%60/10);
		m2=Math.floor(maxtime/60%60%10);
		s1=Math.floor(maxtime%60/10);
		s2=Math.floor(maxtime%60%10);
		//alert(Math.floor(maxtime/60%60%10));
		/*// get new time
		 now = new Date();
		 h = now.getHours();
		 m1 = now.getMinutes() / 10;
		 m2 = now.getMinutes() % 10;
		 s1 = now.getSeconds() / 10;
		 s2 = now.getSeconds() % 10;
		 //alert(now.getMinutes()%10);*/
		 if(h < 12)
		 	ap = "AM";
		 else{ 
		 	if( h == 12 )
				ap = "PM";
			else{
				ap = "PM";
				h -= 12; }
		 }
		 
		 //change pads
		 
		 if( h != h_current){
			flip('hoursUp', 'hoursDown', h, path+'/js/timer/Single/Up/'+ap+'/', path+'/js/timer/Single/Down/'+ap+'/');
			h_current = h;
		}
		
		if( m2 != m2_current){
			flip('minutesUpRight', 'minutesDownRight', m2, path+'/js/timer/Double/Up/Right/', path+'/js/timer/Double/Down/Right/');
			m2_current = m2;
			
			flip('minutesUpLeft', 'minutesDownLeft', m1, path+'/js/timer/Double/Up/Left/', path+'/js/timer/Double/Down/Left/');
			m1_current = m1;
		}
		
		 if (s2 != s2_current){
			flip('secondsUpRight', 'secondsDownRight', s2, path+'/js/timer/Double/Up/Right/', path+'/js/timer/Double/Down/Right/');
			s2_current = s2;
			
			flip('secondsUpLeft', 'secondsDownLeft', s1, path+'/js/timer/Double/Up/Left/', path+'/js/timer/Double/Down/Left/');
			s1_current = s1;
		}
		
		 
	 if(maxtime == 5*60) alert('注意，还有5分钟!');   
	 --maxtime;  	 
	}	
	 else{   
		 clearInterval(timer);   
		 alert("时间到，结束!");   
	}  	
	}
	
//	timer=setInterval('retroClock()', 1000);
	
	
			
	
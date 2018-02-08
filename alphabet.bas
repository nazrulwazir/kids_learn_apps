Type=Activity
Version=6
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals

	Public TTS As TTS
	Public clicksound As MediaPlayer
	
	
End Sub

Sub Globals

	Dim  introwel As MediaPlayer 
	Dim b1,b2,b3,b4,b5,b6,b7 As Button
Public a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z As MediaPlayer
	
	

	
	TTS.Initialize("tts")
	
	Private btnB,btnC,btnD,btnE,btnF,btnG,btnH,btnI,btnJ,btnK,btnL,btnM,btnN,btnO,btnP,btnQ,btnR,btnS,btnT,btnU,btnV,btnW,btnX,btnY,btnZ As Button

	
	Private Button1 As Button
	
	Private Iplay As Button
	
	introwel.Initialize2("introwel")
	
		a.Initialize2("a")
	a.Load(File.DirAssets, "A.mp3")
	
	b.Initialize2("b")
	b.Load(File.DirAssets, "B.mp3")
	
	c.Initialize2("c")
	c.Load(File.DirAssets, "C.mp3")
	
	d.Initialize2("d")
	d.Load(File.DirAssets, "D.mp3")
	
	e.Initialize2("e")
	e.Load(File.DirAssets, "E.mp3")
		
	f.Initialize2("f")
	f.Load(File.DirAssets, "F.mp3")
	
	g.Initialize2("g")
	g.Load(File.DirAssets, "G.mp3")
	
	h.Initialize2("h")
	h.Load(File.DirAssets, "H.mp3")
	
	i.Initialize2("i")
	i.Load(File.DirAssets, "I.mp3")
	
	j.Initialize2("j")
	j.Load(File.DirAssets, "J.mp3")
	
	k.Initialize2("k")
	k.Load(File.DirAssets, "K.mp3")
	
	l.Initialize2("l")
	l.Load(File.DirAssets, "L.mp3")
	
	m.Initialize2("m")
	m.Load(File.DirAssets, "M.mp3")
	
	n.Initialize2("n")
	n.Load(File.DirAssets, "N.mp3")
	
	o.Initialize2("o")
	o.Load(File.DirAssets, "O.mp3")
	
	p.Initialize2("p")
	p.Load(File.DirAssets, "P.mp3")
	
	q.Initialize2("q")
	q.Load(File.DirAssets, "Q.mp3")
	
	r.Initialize2("r")
	r.Load(File.DirAssets, "R.mp3")
	
	s.Initialize2("s")
	s.Load(File.DirAssets, "S.mp3")
	
	t.Initialize2("t")
	t.Load(File.DirAssets, "T.mp3")
	
	u.Initialize2("u")
	u.Load(File.DirAssets, "U.mp3")
	
	v.Initialize2("v")
	v.Load(File.DirAssets, "V.mp3")
	
	w.Initialize2("w")
	w.Load(File.DirAssets, "W.mp3")
	
	x.Initialize2("x")
	x.Load(File.DirAssets, "X.mp3")
	
	y.Initialize2("y")
	y.Load(File.DirAssets, "Y.mp3")
	
	z.Initialize2("z")
	z.Load(File.DirAssets, "Z.mp3")
	
	Private play_btn As Button
	Private btn_clear As Button


End Sub

Sub Activity_Create(FirstTime As Boolean)
	

	
	Activity.LoadLayout("alphabet")
	
	
End Sub

Sub Activity_Resume
	
End Sub



Sub b3_Click

Dim panels As Panel
	 panels.Initialize("panels")
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	  panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"apple.png",panels.Width,panels.Height))
		
	  
	  
'TTS.Speak("yay for Apple", True)
a.Play

End Sub


Sub btnB_Click
b.Play

Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"ball.png",panels.Width,panels.Height))

'TTS.Speak("Bee for Ball", True)




End Sub

Sub btnC_Click
c.Play

Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"cat.png",panels.Width,panels.Height))

'TTS.Speak("see for Cat", True)



End Sub

Sub btnD_Click
	
d.Play

Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"dolphin.png",panels.Width,panels.Height))

'TTS.Speak("dee for Dolphin", True)


End Sub

Sub btnE_Click

e.Play

Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"elephant.png",panels.Width,panels.Height))

'TTS.Speak("ee for Elephant", True)


End Sub

Sub btnF_Click
f.Play


Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"frog.png",panels.Width,panels.Height))

'TTS.Speak("F for Frog", True)


End Sub

Sub btnG_Click
g.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"giraffe.png",panels.Width,panels.Height))
'TTS.Speak("jee for Giraffe", True)


End Sub

Sub btnH_Click
h.Play
Dim panels As Panel
	 panels.Initialize("panels")
	
	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"house.png",panels.Width,panels.Height))
'TTS.Speak("hatch for house", True)


End Sub

Sub btnI_Click
i.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"icecream.png",panels.Width,panels.Height))

'TTS.Speak("I for Ice Cream", True)


End Sub

Sub btnJ_Click

j.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"jug.png",panels.Width,panels.Height))

'TTS.Speak("jay for Jug", True)


End Sub

Sub btnK_Click
k.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"kites.png",panels.Width,panels.Height))

'TTS.Speak("kay for kites", True)


End Sub

Sub btnL_Click
l.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"leaf.png",panels.Width,panels.Height))

'TTS.Speak("el for Leaf", True)


End Sub

Sub btnM_Click
m.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"mouse.png",panels.Width,panels.Height))

'TTS.Speak("yam for Mouse", True)


End Sub

Sub btnN_Click
n.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"nest.png",panels.Width,panels.Height))
'TS.Speak("en for Nest", True)


End Sub

Sub btnO_Click
o.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"octupos.png",panels.Width,panels.Height))

'TTS.Speak("oh for octopus", True)


End Sub

Sub btnP_Click

p.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"pinapple.png",panels.Width,panels.Height))

'TTS.Speak("pee for pineapple", True)


End Sub

Sub btnQ_Click
q.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"queen.png",panels.Width,panels.Height))

'TTS.Speak("queue for Queen", True)


End Sub

Sub btnR_Click
r.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"rainbow.png",panels.Width,panels.Height))

'TTS.Speak("err for Rainbow", True)


End Sub

Sub btnS_Click
s.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"sun.png",panels.Width,panels.Height))

'TTS.Speak("ass for Sun", True)


End Sub

Sub btnT_Click
t.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"tree.png",panels.Width,panels.Height))
'TTS.Speak("tee for Tree", True)


End Sub

Sub btnU_Click
u.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"umbrella.png",panels.Width,panels.Height))

'TTS.Speak("you for Umbrella", True)


End Sub

Sub btnV_Click
v.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"vest.png",panels.Width,panels.Height))

'TTS.Speak("wee for Vest", True)


End Sub

Sub btnW_Click
w.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"watermelon.png",panels.Width,panels.Height))
'TTS.Speak(" double you for Watermelon", True)


End Sub

Sub btnX_Click
	x.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"xray.png",panels.Width,panels.Height))

'TTS.Speak("axe for X-Ray", True)


End Sub

Sub btnY_Click
	y.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"yoyo.png",panels.Width,panels.Height))

'TTS.Speak("why for Yoyo", True)


End Sub

Sub btnZ_Click
z.Play
Dim panels As Panel
	 panels.Initialize("panels")

	  'Activity.Color=Colors.White
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	   panels.SetBackgroundImage(LoadBitmapSample(File.DirAssets,"zebra.png",panels.Width,panels.Height))

'TTS.Speak("zed for Zebra", True)


End Sub

Sub Button1_Click
	introwel.Stop
Activity.Finish
End Sub


Sub play_btn_Click
	introwel.Load(File.DirAssets, "abc.mp3")
		introwel.Play
		introwel.Looping=True
End Sub


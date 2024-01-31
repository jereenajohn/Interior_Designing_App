from flask import *
from database import *
import uuid

designers=Blueprint('designers',__name__)

@designers.route('/designerhome',methods=['get','post'])
def designerhome():
	return render_template("designers/designer_home.html")


# @designers.route('/materials',methods=['get','post'])
# def materials():
# 	data={}
# 	if 'action' in request.args:
# 		action=request.args['action']
# 		mid=request.args['mid']

# 	else:
# 		action=None

# 	if action=="update":
# 		q="select * from materials where material_id='%s'"%(mid)
# 		res=select(q)
# 		data['updatedata']=res
# 	if 'update' in request.form:
# 		m=request.form['mname']
# 		d=request.form['des']
# 		c=request.form['color']
		
# 		q="update materials set material_name='%s',description='%s',color='%s' where material_id='%s'"%(m,d,c,mid)
# 		update(q)
# 		flash("details updated")
# 		return redirect(url_for("designers.materials"))

# 	if action=="delete":
# 		q="delete from materials where material_id='%s'"%(mid)
# 		delete(q)
# 		flash("deleted successfully")
# 		return redirect(url_for("designers.materials"))

# 	if 'submit' in request.form:
# 		m=request.form['mname']
# 		d=request.form['des']
# 		c=request.form['color']

		# q1="select * from materials where material_name='%s'"%(m)
		# res=select(q1)

		# if len(res):
		# 	flash("Material already exist")
	# else:
	# 	q="insert into materials values(null,'%s','%s','%s')"%(m,d,c)
	# 	insert(q)
	# 	flash("materials added successfully")

	# 	q="select * from materials"
	# 	res=select(q)
	# 	data['viewmaterials']=res
	# return render_template("designers/manage_materials.html",data=data)


# @designers.route('/designes',methods=['get','post'])
# def designes():
# 	data={}
# 	id=session['login_id']
# 	q="select * from materials"
# 	res=select(q)
# 	data['viewmaterial']=res
# 	if 'action' in request.args:
# 		action=request.args['action']
# 		did=request.args['did']

# 	else:
# 		action=None
# 	if action=="update":
# 		q="select * from designes where design_id='%s'"%(did)
# 		res=select(q)
# 		data['updatedata']=res
# 	if 'update' in request.form:
		# mid=request.args['mid']
		
	# 	a=request.form['type']
	# 	m=request.form['dname']
		
	# 	mid=request.form['mname']
	# 	e=request.form['des']
	# 	g=request.form['price']
	# 	photo=request.files['photo']
	# 	path='static/uploads/'+str(uuid.uuid4())+photo.filename
	# 	photo.save(path)
	# 	q="update designes set type='%s',name='%s',material_id='%s',description='%s',price_per_piece='%s',photo='%s' where design_id='%s'"%(a,m,mid,e,g,path,did)
	# 	update(q)
	# 	flash("details updated")
	# 	return redirect(url_for("designers.designes"))
	


	# if action=="delete":
	# 	q="delete from designes where design_id='%s'"%(did)
	# 	delete(q)
	# 	flash("deleted successfully")
	# 	return redirect(url_for("designers.designes"))

	# if 'submit' in request.form:
	# 	n=request.form['dname']
	# 	dtype=request.form['type']
	# 	p=request.files['photo']
	# 	path='static/uploads/'+str(uuid.uuid4())+p.filename
	# 	p.save(path)
	# 	m=request.form['mname']
	# 	d=request.form['des']
	# 	pr=request.form['price']

	# 	q="insert into designes values(null,(select designer_id from designers where login_id='%s'),'%s','%s','%s','%s','%s','%s','pending')"%(id,n,dtype,path,m,d,pr)
	# 	insert(q)
	# 	flash("designes uploaded")

	# q="select *,concat(designes.description) as des from designes inner join materials using(material_id) where designer_id=(select designer_id from designers where login_id='%s')"%(id)
	# res=select(q)
	# data['viewdesigns']=res

	# return render_template("designers/upload_designes.html",data=data)


@designers.route('/requirements',methods=['get','post'])
def requirements():
	data={}
	id=session['login_id']

	q="SELECT *,CONCAT(users.first_name,' ',users.last_name)AS uname,requirements.`material_id` FROM requirements INNER JOIN users USING(user_id) INNER JOIN designes USING(design_id) INNER JOIN designers USING(designer_id) INNER JOIN materials ON materials.`material_id`=requirements.`material_id` WHERE designer_id=(SELECT designer_id FROM designers WHERE login_id='%s')"%(id)
	res=select(q)
	data['viewrqrmnts']=res
	j=0
	for i in range(1,len(res)+1):
		if 'submit'+str(i) in request.form:
			a=request.form['ppl'+str(i)]
			q="insert into proposal values(null,'%s',(select designer_id from designers where login_id='%s'),'%s',curdate(),'pending')"%(res[j]['requirement_id'],id,a,)
			insert(q)
			q1="update requirements set requirement_status='proposal send' where requirement_id='%s'"%(res[j]['requirement_id'])
			update(q1)
			flash("proposal send")
			return redirect(url_for("designers.requirements"))
		j=j+1
	return render_template("designers/view_user_requirements.html",data=data)



@designers.route('/designeruserchat',methods=['get','post'])
def designeruserchat():
	data={}
	id=session['login_id']
	print(id)
	rid=request.args['rid']
	uid=request.args['uid']
	q="select *,concat(first_name,' ',last_name)as uname from requirements inner join users using(user_id) where requirement_id='%s' "%(rid)
	res=select(q)
	fname=request.args['fname']
	data['uname']=fname
	if 'submit' in request.form:
		msg=request.form['msg']
		q="insert into chat values(null,'%s','designer',(select login_id from users where user_id='%s'),'user','%s',curdate())"%(id,uid,msg)
		print(q)
		insert(q)
		flash("message send successfully")

	q="select * from chat where sender_id='%s' and sender_type='designer' and receiver_id=(select login_id from users where user_id='%s') or receiver_id='%s' and sender_id=(select login_id from users where user_id='%s')"%(id,uid,id,uid)
	res=select(q)

	data['chat']=res
	
	data['sendrec']=id
	
	

	return render_template("designers/chat_with_users.html",data=data)


@designers.route('/viewproposal',methods=['get','post'])
def viewproposal():
	data={}
	id=session['login_id']
	q="select *,concat(users.first_name,' ',users.last_name)as uname from proposal inner join requirements using(requirement_id) inner join designes using(design_id) inner join users using(user_id) inner join designers on designers.designer_id=proposal.designer_id where proposal.designer_id=(select designer_id from designers where login_id='%s')"%(id)
	res=select(q)
	data['viewproposal']=res

	if 'action' in request.args:
		action=request.args['action']
		ids=request.args['ids']
		
		

	else:
		action=None

	if action=="update":
		q="update proposal set proposal_status='confirm delivery' where proposal_id='%s'"%(ids)
		update(q)
		flash("delivery confirmed")
		return redirect(url_for("designers.viewproposal"))

	if action=="delete":
		
		q1="delete from proposal where proposal_id='%s' and proposal_status='pending'"%(ids) 
		delete(q1)
		flash("deleted successfully")
		return redirect(url_for("designers.viewproposal"))




	return render_template("designers/view_proposal_status.html",data=data)


@designers.route('/paymentreport',methods=['get','post'])
def paymentreport():
	data={}
	id=session['login_id']
	q="select *,concat(users.first_name,' ',users.last_name)as uname from proposal inner join requirements using(requirement_id) inner join designes using(design_id) inner join users using(user_id) inner join designers on designers.designer_id=proposal.designer_id where proposal.designer_id=(select designer_id from designers where login_id='%s') and proposal_status='confirm delivery'"%(id)
	res=select(q)
	data['paymentreport']=res

	return render_template("designers/payment_report.html",data=data)


@designers.route('/paymentrep',methods=['get','post'])
def paymentrep():
	data={}
	id=session['login_id']
	q="select *,concat(users.first_name,' ',users.last_name)as uname from proposal inner join requirements using(requirement_id) inner join designes using(design_id) inner join users using(user_id) inner join designers on designers.designer_id=proposal.designer_id where proposal.designer_id=(select designer_id from designers where login_id='%s') and proposal_status='confirm delivery'"%(id)
	res=select(q)
	data['paymentreport']=res

	return render_template("designers/reportpayment.html",data=data)



@designers.route('/viewpayment',methods=['get','post'])
def viewpayment():
	data={}
	id=session['login_id']
	ids=request.args['ids']
	q="select *,concat(first_name,' ',last_name)as uname from payment inner join users using(user_id) where proposal_id='%s'"%(ids)	
	res=select(q)

	data['viewpayments']=res
	
	return render_template("designers/view_payment.html",data=data)



@designers.route('/feedback',methods=['get','post'])
def feedback():
	data={}
	q="select *,concat(first_name,' ',last_name)as uname from feedback inner join users using(user_id)"	
	res=select(q)
	data['viewfeedback']=res
	return render_template("designers/view_feedback.html",data=data)



@designers.route('/chat_room',methods=['get','post'])
def chat_room():
	data={}

	id=session['login_id']
	data['login_id']=id
	user_id=request.args['user_id']
	q="SELECT * FROM chat c,users u,designers d WHERE ((c.`sender_id`=u.`login_id` AND c.`receiver_id`=d.`login_id`) OR (c.`sender_id`=d.`login_id` AND c.`receiver_id`=u.`login_id`)) AND ((c.`sender_id`='%s' AND c.`receiver_id`=(SELECT `login_id` FROM `users` WHERE `user_id`='%s')) OR (c.`sender_id`=(SELECT `login_id` FROM `users` WHERE `user_id`='%s') AND c.`receiver_id`='%s') ) order by chat_id desc " %(id,user_id,user_id,id)
	res=select(q)
	data['chat']=res
	if 'message' in request.form:
		msg=request.form['msg']
		q="INSERT INTO `chat` VALUES(NULL,'%s','designer',(SELECT `login_id` FROM `users` WHERE `user_id`='%s'),'user','%s',curdate())"%(id,user_id,msg)
		insert(q)
		return redirect(url_for('designers.chat_room',user_id=user_id))

	# q="select *,concat(users.first_name,' ',users.last_name)as uname from proposal inner join requirements using(requirement_id) inner join designes using(design_id) inner join users using(user_id) inner join designers on designers.designer_id=proposal.designer_id where proposal.designer_id=(select designer_id from designers where login_id='%s')"%(id)
	# res=select(q)
	# data['viewproposal']=res

	return render_template("designers/chat_room.html",data=data)



@designers.route('/materials',methods=['get','post'])
def materials():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		mid=request.args['mid']

	else:
		action=None

	if action=="update":
		q="select * from materials where material_id='%s'"%(mid)
		res=select(q)
		data['updatedata']=res
	if 'update' in request.form:
		m=request.form['mname']
		d=request.form['des']
		c=request.form['color']
		
		q="update materials set material_name='%s',description='%s',color='%s' where material_id='%s'"%(m,d,c,mid)
		update(q)
		flash("details updated")
		return redirect(url_for("designers.materials"))

	if action=="delete":
		q="delete from materials where material_id='%s'"%(mid)
		delete(q)
		flash("deleted successfully")
		return redirect(url_for("designers.materials"))

	if 'submit' in request.form:
		m=request.form['mname']
		d=request.form['des']
		c=request.form['color']

		q="select * from materials where material_name='%s'"%(m)
		res=select(q)

		if len(res):
			flash("Material already exist")
		else:

			q="insert into materials values(null,'%s','%s','%s')"%(m,d,c)
			insert(q)
			flash("materials added successfully")

	q="select * from materials"
	res=select(q)
	data['viewmaterials']=res
	return render_template("designers/manage_materials.html",data=data)




@designers.route('/designes',methods=['get','post'])
def designes():
	data={}
	id=session['login_id']
	q="select * from materials"
	res=select(q)
	data['viewmaterial']=res
	if 'action' in request.args:
		action=request.args['action']
		did=request.args['did']
	else:
		action=None
	if action=="update":
		q="select * from designes where design_id='%s'"%(did)
		res=select(q)
		data['updatedata']=res
	if action=="delete":
		q="delete from designes where design_id='%s'"%(did)
		delete(q)
		flash("deleted successfully")
		return redirect(url_for("designers.designes"))
	if 'update' in request.form:
		a=request.form['type']
		m=request.form['dname']
		mid=request.form['mname']
		e=request.form['des']
		g=request.form['price']
		photo=request.files['photo']
		path='static/uploads/'+str(uuid.uuid4())+photo.filename
		photo.save(path)
		q="update designes set type='%s',name='%s',material_id='%s',description='%s',price_per_piece='%s',photo='%s' where design_id='%s'"%(a,m,mid,e,g,path,did)
		update(q)
		flash("details updated")
		return redirect(url_for("designers.designes"))
	else:
		action=None
	

	if 'submit' in request.form:
		n=request.form['dname']
		dtype=request.form['type']
		p=request.files['photo']
		path='static/uploads/'+str(uuid.uuid4())+p.filename
		p.save(path)
		m=request.form['mname']
		d=request.form['des']
		pr=request.form['price']

		q="insert into designes values(null,(select designer_id from designers where login_id='%s'),'%s','%s','%s','%s','%s','%s','pending')"%(id,n,dtype,path,m,d,pr)
		insert(q)
		flash("designes uploaded")

	q="select *,concat(designes.description) as des from designes inner join materials using(material_id) where designer_id=(select designer_id from designers where login_id='%s')"%(id)
	res=select(q)
	data['viewdesigns']=res

	return render_template("designers/upload_designes.html",data=data)
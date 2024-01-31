from flask import *
from database import *

import uuid
api=Blueprint('api',__name__)

@api.route('/login/',methods=['get','post'])
def login():
	data={}
	print("Haii")
	username=request.args['username']
	password=request.args['password']
	q="select * from login where username='%s' and password='%s'"%(username,password)
	res=select(q)
	print(res)
	if res:
		data['status']="success"
		data['data']=res
		data['method']="login"
	else:
		data['status']="failed"
	return str(data)	

@api.route('/register/',methods=['get','post'])	
def register():
	data={}
	fname=request.args['firstname']
	lname=request.args['lastname']
	dob=request.args['dob']
	house=request.args['house']
	place=request.args['place']
	pincode=request.args['pincode']
	phone=request.args['phone']
	email=request.args['email']

	user=request.args['user']
	psw=request.args['pass']
	# q="select username,password from login where username='%s' and password='%s'"%(user,psw)
	# result=select(q)
	# if len(result)>0:
	# 	data['status']="duplicate"
		# data['method']="registration"
	# else:
	q="insert into login values(null,'%s','%s','user')"%(user,psw)
	id=insert(q)
	q="insert into users values(null,'%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(id,fname,lname,dob,house,place,pincode,phone,email)
	insert(q)
	print(q)
	data['status']="success"
		
	return str(data)

@api.route('/User_view_curtain_designs/')
def User_view_curtain_designs():
	data={}

	q="SELECT * FROM `designes` INNER JOIN `materials` USING(`material_id`) INNER JOIN `designers` USING(`designer_id`) where type='curtain' and status='accepted'"
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	return str(data)

@api.route('/User_view_room_designs/')
def User_view_room_designs():
	data={}

	q="SELECT * FROM `designes` INNER JOIN `materials` USING(`material_id`) INNER JOIN `designers` USING(`designer_id`) where type='room' and status='accepted'"
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	return str(data)

@api.route('/user_view_designer/')
def user_view_designer():
	data={}
	d_id=request.args['d_id']
	q="SELECT *,CONCAT(`first_name`,' ',`last_name`) AS d_name FROM `designers` WHERE `designer_id`='%s'"%(d_id)
	res=select(q)
	if res:
		data['data']=res
		# data['method']='view_feedback'
		data['status']='success'
	else:
		data['status']='failed'
	return str(data)


@api.route('/view_feedback/')
def view_feedback():
	data={}
	logid=request.args['logid']
	q="SELECT * FROM `feedback` WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')" %(logid)
	res=select(q)
	if res:
		data['data']=res
		data['method']='view_feedback'
		data['status']='success'
	else:
		data['method']='failed'
	return str(data)

@api.route('/send_feedback/')
def send_feedback():
	data={}
	logid=request.args['logid']
	Feedback=request.args['Feedback']
	q="INSERT INTO `feedback` VALUES(NULL,(SELECT `user_id` FROM `users` WHERE `login_id`='%s'),'%s',NOW())"%(logid,Feedback)
	insert(q)
	data['method']='send_feedback'
	data['status']='success'
	return str(data)


@api.route('/view_complaint/')
def view_complaint():
	data={}
	logid=request.args['logid']
	q="SELECT * FROM `complaint` WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')" %(logid)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	data['method']='view_complaintxxxx'
	return str(data)


@api.route('/send_complaint/')
def send_complaint():
	data={}
	logid=request.args['logid']
	Complaint=request.args['Complaint']
	q="INSERT INTO `complaint` VALUES(NULL,(SELECT `user_id` FROM `users` WHERE `login_id`='%s'),'%s','pending',NOW())"%(logid,Complaint)
	insert(q)
	data['method']='send_complaint'
	data['status']='success'
	return str(data)




@api.route('/send_requirement/')
def send_requirement():
	data={}
	design_type=request.args['design_type']
	qnty=request.args['qnty']
	design_id=request.args['design_id']
	material_id=request.args['material_id']
	logid=request.args['logid']
	q="INSERT INTO `requirements` VALUES(NULL,(SELECT `user_id` FROM `users` WHERE `login_id`='%s'),'%s','%s','%s','%s',NOW(),'pending')"%(logid,design_id,design_type,qnty,material_id)
	insert(q)
	# data['method']='Ambulance_view_request'
	data['status']='success'
	return str(data)

@api.route('/delete_requirement/')
def delete_requirement():
	data={}
	requirement_ids=request.args['requirement_ids']
	q="delete from  `requirements` where  requirement_id='%s' "%(requirement_ids)
	insert(q)
	data['method']='approve'
	data['status']='success'
	return str(data)




@api.route('/user_view_rq/')
def user_view_rq():
	data={}
	logid=request.args['logid']
	q="SELECT * FROM `requirements` INNER JOIN `designes` USING(`design_id`) INNER JOIN `materials` ON `requirements`.`material_id`=`materials`.`material_id` WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')"%(logid)
	
	res=select(q)
	if res:
		data['data']=res
		data['method']='user_view_rq'
		data['status']='success'
	else:
		data['method']='failed'
		data['method']='user_view_rq'
	return str(data)



@api.route('/user_view_proposal/')
def user_view_proposal():
	data={}
	logid=request.args['logid']
	q="SELECT *,CONCAT(`designers`.`first_name`,' ',`designers`.`last_name`) AS designer_name FROM `proposal` INNER JOIN `requirements` USING(`requirement_id`) INNER JOIN `designers` USING(`designer_id`) INNER JOIN `designes` USING(`designer_id`) INNER JOIN `materials` ON `materials`.`material_id`=`designes`.`material_id` WHERE `requirements`.`user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')   group by proposal_id"%(logid)
	print(q)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'

	return str(data)

@api.route('/chat/')
def chat():
	data={}
	msg=request.args['msg']
	from_id=request.args['from_id']
	to_id=request.args['to_id']

	q="INSERT INTO `chat` VALUES(NULL,'%s','user',(SELECT `login_id` FROM `designers` WHERE `designer_id`='%s'),'designer','%s',curdate())"%(from_id,to_id,msg)
	insert(q)
	data['method']='chat'
	data['status']='success'
	return str(data)


@api.route('/get_chat/')
def get_chat():
	data={}
	from_id=request.args['from_id']
	to_id=request.args['to_id']

	q="SELECT * FROM chat c,users u,designers d WHERE ((c.`sender_id`=u.`login_id` AND c.`receiver_id`=d.`login_id`) OR (c.`sender_id`=d.`login_id` AND c.`receiver_id`=u.`login_id`)) AND ((c.`sender_id`='%s' AND c.`receiver_id`=(SELECT `login_id` FROM `designers` WHERE `designer_id`='%s')) OR (c.`sender_id`=(SELECT `login_id` FROM `designers` WHERE `designer_id`='%s') AND c.`receiver_id`='%s') )" %(from_id,to_id,to_id,from_id)
	res=select(q)
	if res:
		data['data']=res
		data['method']='get_chat'
		data['status']='success'
	else:
		data['method']='failed'
	return str(data)


@api.route('/user_view_amount/',methods=['get','post'])
def user_view_amount():
	data={}

	prop_id=request.args['prop_id']
	
	q="SELECT * FROM `proposal` WHERE `proposal_id`='%s'"%(prop_id)
	res=select(q)
	data['data']=res
	data['status']="Success"
	data['method']="user_view_amount"
	return str(data)

@api.route('/user_payment/',methods=['get','post'])
def user_payment():
	data={}
	logid=request.args['logid']
	amount=request.args['amount']
	prop_id=request.args['prop_id']
	
	q="INSERT INTO `payment` VALUES(NULL,'%s','%s',(SELECT `user_id` FROM `users` WHERE `login_id`='%s'),NOW())"%(prop_id,amount,logid)
	insert(q)
	q="UPDATE `proposal` SET `proposal_status`='Paid' WHERE `proposal_id`='%s'"%(prop_id)
	update(q)
	data['status']="Success"
	data['method']="user_payment"
	return str(data)
# Routing functions for milestones 

from flask import Blueprint, request
from google.cloud import datastore
import json
import pandas as pd

client = datastore.Client()

bp = Blueprint('milestones', __name__, url_prefix='/milestones')

# Update entire milestone entity after deleting all milestones
@bp.route('/update_list', methods = ['GET'])
def milestones_update_all():
	if request.method == 'GET':

		try:
			file_name = 'test.xlsx'
			df = pd.read_excel(io=file_name, sheet_name=None)
			
			# https://pythoninoffice.com/get-values-rows-and-columns-in-pandas-dataframe/
			for element in df:
				if element != 'Categories':
					for row in df[element].index:
						if df[element].loc[row][2] == df[element].loc[row][2]:
							activityData = []
							
							for activity in df[element].loc[row][3:]:
								if activity != False and activity == activity:
									activityData.append(activity)

							# Set up entity and add to client
							new_milestone = datastore.Entity(key=client.key('milestones'))
							new_milestone.update({
								'milestone': df[element].loc[row][2],
								'category': df[element].loc[row][0],
								'ageRange': element,
								'activities': activityData
							})
							client.put(new_milestone)
			
			return {}, 200, {'Content-Type':'application/json'} 
		except:
			return {}, 404, {'Content-Type':'application/json'} 
	
	

# Routing function for getting and adding a milestone to the database
@bp.route('', methods = ['GET', 'POST'])
def milestones_get_post():
	if request.method == 'GET':
		query = client.query(kind='milestones')

		# Fetch milestones
		all_milestones = list(query.fetch())
		
		# Set id for each milestone
		for e in all_milestones:
			e["id"] = e.key.id

		return json.dumps(all_milestones), 200, {'Content-Type':'application/json'} 
	elif request.method == 'POST':
		body = request.get_json()

		milestone_required_headers = ['activities', 'ageRange', 'category', 'milestone']
		
		if not set(milestone_required_headers).issubset(body.keys()):
			return json.dumps({'Error': 'The request object is missing at least one of the required attributes.'}), 400, {'Content-Type':'application/json'}  
		
		# Set up entity and add to client
		new_milestone = datastore.Entity(key=client.key('milestones'))
		new_milestone.update({
			'milestone': body['milestone'],
			'category': body['category'],
			'ageRange': body['ageRange'],
			'activities': body['activities']
		})
		client.put(new_milestone)
		
		# Update with self url and return with id
		new_milestone.update({
			'self': request.base_url + '/' + str(new_milestone.key.id)
		})
		client.put(new_milestone)
		
		new_milestone['id'] = new_milestone.key.id

		return json.dumps(new_milestone), 201, {'Content-Type':'application/json'} 
	else:
		return json.dumps({'Error': 'This API does not support this operation.'}), 405, {'Content-Type': 'application/json'}

# Methods for getting a single milestone or deleting a milestone from database
@bp.route('/<milestone_id>', methods = ['GET', 'DELETE'])
def milestones_get_delete_withid(milestone_id):
	if request.method == 'GET':
		milestone_key = client.key('milestones', int(milestone_id))
		single_milestone = client.get(key=milestone_key)
		
		# Make sure milestone exists
		if single_milestone == None:
			return json.dumps({"Error": "No milestone with this milestone_id exists."}), 404, {'Content-Type':'application/json'} 
		
		# Add milestone id to json and return all
		single_milestone['id'] = milestone_id
		return json.dumps(single_milestone), 200, {'Content-Type':'application/json'} 
	elif request.method == 'DELETE':		
		# Get requested milestone
		milestone_key = client.key('milestones', int(milestone_id))
		single_milestone = client.get(key=milestone_key)
		
		# Check if milestone exists
		if not single_milestone:
			return json.dumps({'Error': 'No milestone with this milestone_id exists.'}), 404, {'Content-Type':'application/json'}
		
		# Remove milestone from children
		for child in single_milestone['children_id_assigned']:
			child_key = client.key('children', child)
			single_child = client.get(key=child_key)
			
			# Delete milestone from child
			single_child['milestones_assigned'].remove({
				'id': int(milestone_id),
				'self': single_milestone['self']
			})
			
			client.put(single_child)
		
		client.delete(milestone_key)
		return {}, 204, {'Content-Type':'application/json'} 
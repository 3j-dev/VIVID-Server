import json
import boto3

dynamodb = boto3.client('dynamodb')


def lambda_handler(event, context):
    print(event)

    event_body = json.loads(event['body'])

    response = dynamodb.put_item(

        TableName='canvas_drawing',

        Item={
            'individual_video_id': {
                'S': event_body['individual_video_id']
            },

            'time': {
                'S': event_body['time']
            },

            'state_json': {
                'S': event_body['state_json']
            }
        }
    )

    return {
        'statusCode': 200,
        'headers': {'Content-Type': 'application/json'},
        'body': json.dumps("get")
    }

#!/bin/bash

echo $(apt-get update)
echo $(apt install jq -y)

# -- > Create DynamoDb Table
echo Creating  DynamoDb \'payments\' table ...
echo $(awslocal dynamodb create-table --cli-input-json '{"TableName":"payments", "KeySchema":[{"AttributeName":"id","KeyType":"HASH"}], "AttributeDefinitions":[{"AttributeName":"id","AttributeType":"S"}],"BillingMode":"PAY_PER_REQUEST","StreamSpecification":{"StreamEnabled": true,"StreamViewType":"NEW_AND_OLD_IMAGES"}}')

# -- > Retrieving DynamoDB Stream ARN
NEWS_TABLE_DYNAMODB_STREAM_ARN=$(awslocal dynamodb describe-table --table-name payments | jq -r '.Table.LatestStreamArn')
echo "NEWS_TABLE_DYNAMODB_STREAM_ARN=${NEWS_TABLE_DYNAMODB_STREAM_ARN}"

# -- > Create 'Payments Topic' SNS topic
echo Creating SNS topic  \'dandelion-item-info-topic\'...
echo $(awslocal sns create-topic --name payments-topic --region=us-east-1)

# -- > Create 'Payments Queue' SQS queue
echo Creating SQS queue  \'dandelion-item-info-queue\'...
echo $(awslocal sqs create-queue --queue-name payments-queue --region=us-east-1)

# -- > Subscribing SQS queue 'Payments Queue' to SNS topic 'Payments Topic'
echo Subscribing SQS queue \'payments-queue\' to SNS topic \'payments-topic\'...
echo $(awslocal sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:payments-topic --protocol sqs --notification-endpoint arn:aws:sqs:us-east-1:000000000000:payments-queue --region us-east-1)

# -- > Create Lambda Function
echo $(awslocal lambda create-function --function-name PaymentDynamoDbProcessor --runtime java21 --memory-size 1024 --handler org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest --zip-file fileb:///shared/payment-handler-0.0.1-SNAPSHOT-aws.jar --environment "Variables={MAIN_CLASS=com.klindziuk.aws.handler.DynamodbLambdaFunctionApplication,AWS_REGION=us-east-1,AWS_ACCESS_KEY_ID=key,AWS_SECRET_ACCESS_KEY=secret}" --role arn:aws:iam::000000000000:role/service-role/irrelevant --timeout 60)

# -- > Create Mapping between Lambda Function and DynamoDB events
echo $(awslocal lambda create-event-source-mapping --function-name PaymentDynamoDbProcessor --event-source $NEWS_TABLE_DYNAMODB_STREAM_ARN --starting-position LATEST)


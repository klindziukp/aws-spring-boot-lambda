#!/bin/bash

if ! [[ $(docker ps -q -f name=localstack) ]]; then
  echo "WARNING: The localstack Docker container is not running. Please, start it first."
  exit 1
fi

echo
echo "Initializing LocalStack"
echo "======================="

echo
echo "Installing jq"
echo "-------------"
docker exec -t localstack apt-get update
docker exec -t localstack apt install jq -y
docker exec -t localstack jq --version
docker exec -t localstack echo '{ "name":"John", "age":31, "city":"New York" }' | jq .

echo
echo "Creating news-topic in SNS"
echo "--------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 sns create-topic --name payment-topic

echo
echo "Creating news-consumer-queue in SQS"
echo "-----------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name news-consumer-queue

echo
echo "Subscribing news-consumer-queue to news-topic"
echo "---------------------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 sns subscribe \
  --topic-arn arn:aws:sns:us-east-1:000000000000:payment-topic \
  --protocol sqs \
  --attributes '{"RawMessageDelivery":"true"}' \
  --notification-endpoint arn:aws:sqs:us-east-1:000000000000:news-consumer-queue

echo
echo "Creating Payments table in DynamoDB"
echo "-------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 dynamodb create-table \
  --table-name payments \
  --attribute-definitions AttributeName=id,AttributeType=S \
  --key-schema AttributeName=id,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=10,WriteCapacityUnits=10 \
  --stream-specification StreamEnabled=true,StreamViewType=NEW_AND_OLD_IMAGES

echo
echo "Getting news table DynamoDB Stream ARN"
echo "--------------------------------------"
NEWS_TABLE_DYNAMODB_STREAM_ARN=$(docker exec -t localstack aws --endpoint-url=http://localhost:4566 dynamodb describe-table --table-name payments | jq -r '.Table.LatestStreamArn')
echo "NEWS_TABLE_DYNAMODB_STREAM_ARN=${NEWS_TABLE_DYNAMODB_STREAM_ARN}"

#echo
#echo "Creating Lambda Function called ProcessDynamoDBEvent"
#echo "----------------------------------------------------"
#docker exec -t localstack aws --endpoint-url=http://localhost:4566 lambda create-function \
#  --function-name ProcessDynamoDBEvent \
#  --runtime java17 \
#  --memory-size 512 \
#  --handler org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest \
#  --zip-file fileb:///shared/dynamodb-lambda-function-java17-aws.jar \
#  --environment "Variables={AWS_REGION=us-east-1,AWS_ACCESS_KEY_ID=key,AWS_SECRET_ACCESS_KEY=secret}" \
#  --role arn:aws:iam::000000000000:role/service-role/irrelevant \
#  --timeout 60
#
#echo
#echo "Creating a mapping between news table DynamoDB event source and ProcessDynamoDBEvent lambda function"
#echo "----------------------------------------------------------------------------------------------------"
#docker exec -t localstack aws --endpoint-url=http://localhost:4566 lambda create-event-source-mapping \
#  --function-name ProcessDynamoDBEvent \
#  --event-source $NEWS_TABLE_DYNAMODB_STREAM_ARN \
#  --starting-position LATEST
#
#echo
#echo "LocalStack initialized successfully"
#echo "==================================="
#echo